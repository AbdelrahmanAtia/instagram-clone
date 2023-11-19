import { Component, OnInit } from '@angular/core';
import { PostService } from '../shared/services/post.service';

@Component({
  selector: 'insta-photo-video-uploader',
  templateUrl: './photo-video-uploader.component.html',
  styleUrls: ['./photo-video-uploader.component.css']
})
export class PhotoVideoUploaderComponent implements OnInit {
  

  private filesToUpload: FileList | null = null;
  public imageUrl: string | ArrayBuffer | null = null;

  constructor(private postService: PostService){}

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

  onDrop(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
    const files = event.dataTransfer?.files;
 
    if (files && files.length) {
       this.filesToUpload = files;
 
       console.log(">> file type: " + files[0].type)
       // Read the first file if it's an image
       if(files[0].type.match(/image.*/)) {
          const reader = new FileReader();

          //convert file into a data url {base64 string image}
          reader.onload = (e: any) => {
             this.imageUrl = e.target.result;
             console.log(">> umageUrl: " + this.imageUrl);
          };
          reader.readAsDataURL(files[0]);
       }
    }
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
        console.log('File is uploaded successfully:', response);
      },
      error => {
        console.error('Error uploading file:', error);
      }
    );

  }

}
