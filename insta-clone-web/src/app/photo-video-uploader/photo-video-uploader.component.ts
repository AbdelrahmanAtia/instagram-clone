import { Component } from '@angular/core';
import { PostService } from '../shared/services/post.service';

@Component({
  selector: 'insta-photo-video-uploader',
  templateUrl: './photo-video-uploader.component.html',
  styleUrls: ['./photo-video-uploader.component.css']
})
export class PhotoVideoUploaderComponent {


  constructor(private postService: PostService){

  }

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
      //TODO: we need to support uploading multiple files as in instagram
      const fileToUpload: File = files[0]; // Assuming you want to upload the first file
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
  close(): void {
    // logic to close the dialog
  }
}
