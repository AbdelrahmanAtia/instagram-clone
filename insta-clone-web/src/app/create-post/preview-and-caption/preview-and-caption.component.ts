import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Post } from 'src/app/shared/models/post.model';
import { FileService } from 'src/app/shared/services/file.service';
import { PostService } from 'src/app/shared/services/post.service';

@Component({
  selector: 'insta-preview-and-caption',
  templateUrl: './preview-and-caption.component.html',
  styleUrls: ['./preview-and-caption.component.css']
})
export class PreviewAndCaptionComponent implements OnInit {

  @Output() postCreationSuccessEvent = new EventEmitter<boolean>();

  @Input() uploadedFiles: FileList | null = null;

  imageUrl: string | ArrayBuffer | null = null;
  postCaption: string = '';

  constructor(
    public dialog: MatDialog,
    private fileService: FileService,
    public postService: PostService){      
  }

  ngOnInit(): void {
    this.dialog.getDialogById("upload-media-dialog")?.updateSize("740px", "415px");
    this.previewMedia();
  }
  
  previewMedia(){

    if (this.uploadedFiles && this.uploadedFiles.length) {

      // Read the first file if it's an image
      if(this.uploadedFiles[0].type.match(/image.*/)) {
         const reader = new FileReader();
         reader.onload = (e: any) => {
            this.imageUrl = e.target.result; //convert file into a data url {base64 string image}            
          };
         reader.readAsDataURL(this.uploadedFiles[0]);
      }
    }

  }

  sharePost() {

    if(this.uploadedFiles == null){
      //TODO: u need to handle that case
      return;
    }

    //TODO: we need to support uploading multiple files as in instagram
    // Assuming you want to upload the first file
    const postMedia: File = this.uploadedFiles[0];
    
    this.fileService.uploadFile(postMedia).subscribe(
      response => {
        this.createPost(response.fileName);
      },
      error => {
        console.error('Error uploading file:', error);
      }
    );

  }

  createPost(uploadedFileName: string){

    const newPost: Post = {
      caption: this.postCaption,
      fileName: uploadedFileName
    }

    this.postService.sharePost(newPost).subscribe(
      res => {
        this.postCreationSuccessEvent.emit(true);        
      }, 
      error => {
        console.error('Error uploading file:', error);
      }
    );

  }
}
