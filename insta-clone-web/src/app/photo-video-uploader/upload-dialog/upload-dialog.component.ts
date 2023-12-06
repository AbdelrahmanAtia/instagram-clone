import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Post } from 'src/app/profile-posts/models/post.model';
import { PostService } from 'src/app/shared/services/post.service';
@Component({
  selector: 'insta-upload-dialog',
  templateUrl: './upload-dialog.component.html',
  styleUrls: ['./upload-dialog.component.css']
})
export class UploadDialogComponent implements OnInit {

  private filesToUpload: FileList | null = null;
  public imageUrl: string | ArrayBuffer | null = null;
  postCaption: string = '';
  filesSelectedOrDropped: boolean = false;
  postShared: boolean = false;
  increaseDialogSize: boolean = false;

  @ViewChild('fileInput') fileInput!: ElementRef;

  constructor(
    public dialog: MatDialog,
    public postService: PostService){      
  }

  ngOnInit(): void {}

  onDragOver(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
    // Add a visual cue to show an item is being dragged over
    // You can toggle a class for this
  }

  onDragLeave(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
    // Remove the visual cue
  }

  onFilesDrop(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
    const files = event.dataTransfer?.files;
    this.handleSelectedOrDroppedFiles(files);
  }

  onFilesSelected(event: any) {
    const files: FileList = event.target.files;
    this.handleSelectedOrDroppedFiles(files);
  } 

  handleSelectedOrDroppedFiles(files: any){

    
    this.filesSelectedOrDropped = true;
    
    if (files && files.length) {
      this.filesToUpload = files;

      // Read the first file if it's an image
      if(files[0].type.match(/image.*/)) {
         const reader = new FileReader();
         reader.onload = (e: any) => {
            this.imageUrl = e.target.result; //convert file into a data url {base64 string image}
            this.increaseDialogSize = true;
            this.dialog.getDialogById("upload-media-dialog")?.updateSize("740px", "415px");
          };
         reader.readAsDataURL(files[0]);
      }
   }
  }
 
  selectFile() {
    this.fileInput.nativeElement.click();
  }

  close(): void {
    // logic to close the dialog
  }

  onPostShare() {

    if(this.filesToUpload == null){
      //TODO: u need to handle that case
      return;
    }

    //TODO: we need to support uploading multiple files as in instagram
    // Assuming you want to upload the first file
    const fileToUpload: File = this.filesToUpload[0];
    
    this.postService.uploadFile(fileToUpload).subscribe(
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
        this.imageUrl = null;
        this.postShared = true;
        this.dialog.getDialogById("upload-media-dialog")?.updateSize("405px", "415px");
      }, 
      error => {
        console.error('Error uploading file:', error);
      }
    );

  }

}
