package com.javaworld.instagram.postservice.features.restapi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.javaworld.instagram.postservice.commons.Constants;
import com.javaworld.instagram.postservice.commons.exceptions.NotFoundException;
import com.javaworld.instagram.postservice.commons.utils.ServiceUtil;
import com.javaworld.instagram.postservice.server.api.FilesApi;
import com.javaworld.instagram.postservice.server.dto.GenericResponseApiDto;
import com.javaworld.instagram.postservice.server.dto.UploadFilesResponseApiDto;

@RestController
public class FilesApiImpl implements FilesApi {

	private static final Logger logger = LoggerFactory.getLogger(FilesApiImpl.class);

	@Autowired
	private ServiceUtil serviceUtil;

	// TODO: make this service more generic..for example: for uploading videos &
	// reels not only images
	@Override
	public UploadFilesResponseApiDto uploadFile(MultipartFile file) {

		String uniqueFileName = getUniqueFileName(file.getOriginalFilename());

		Path postsImagesPath = Paths.get(Constants.POSTS_IMAGES_LOCATION);

		try {
			if (file.isEmpty()) {
				//TODO: find a way to return 404 status error code for the response
				//one way u can use ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
				//but this will require updating the swagger definition also
				throw new IOException("Failed to store empty file " + file.getOriginalFilename());
			}
			// This is where we save the file
			Files.copy(file.getInputStream(), postsImagesPath.resolve(uniqueFileName),
					StandardCopyOption.REPLACE_EXISTING);

			return new UploadFilesResponseApiDto().fileName(uniqueFileName).message("File uploaded successfully")
					.serviceAddress(serviceUtil.getServiceAddress());

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Resource downloadFile(String fileName) {
		try {
			Path fileLocation = Paths.get(Constants.POSTS_IMAGES_LOCATION).resolve(fileName).normalize();
			Resource resource = new UrlResource(fileLocation.toUri());

			if (resource.exists() && resource.isReadable()) {
				return resource;
			} else {
				throw new FileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException | FileNotFoundException ex) {
			throw new RuntimeException("Error while trying to download file: " + fileName, ex);
		}
	}
	
	@Override
	public GenericResponseApiDto deleteFile(String fileName) {

		logger.info("deleting file with name: " + fileName);

		try {
			Path filePath = Paths.get(Constants.POSTS_IMAGES_LOCATION).resolve(fileName).normalize();

			if (!Files.exists(filePath)) {
				throw new NotFoundException("File not found: " + fileName);
			}

			Files.deleteIfExists(filePath);
			return new GenericResponseApiDto().message("File deleted successfully");
		} catch (IOException ex) {
			throw new RuntimeException("Error while trying to delete file: " + fileName, ex);
		}
	}

	private String getUniqueFileName(String originalFileName) {

		String uniqueFileName = "";
		
		if (originalFileName == null) {
			throw new RuntimeException("file name is null");
		}

		String extension = "";
		int extensionIndex = originalFileName.lastIndexOf(".");
		if (extensionIndex > 0) {
			extension = originalFileName.substring(extensionIndex);
		}
		return uniqueFileName.concat(UUID.randomUUID().toString()).concat(extension);
	}



}
