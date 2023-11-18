package com.javaworld.instagram.postservice.features.restapi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.javaworld.instagram.postservice.commons.Constants;
import com.javaworld.instagram.postservice.commons.utils.ServiceUtil;
import com.javaworld.instagram.postservice.server.api.FilesApi;
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

	private String getUniqueFileName(String originalFileName) {

		if (originalFileName == null) {
			throw new RuntimeException("file name is null");
		}

		String extension = "";
		int extensionIndex = originalFileName.lastIndexOf(".");
		if (extensionIndex > 0) {
			extension = originalFileName.substring(extensionIndex);
			originalFileName = originalFileName.substring(0, extensionIndex);
		}
		return originalFileName.concat("_").concat(UUID.randomUUID().toString()).concat(extension);
	}

}
