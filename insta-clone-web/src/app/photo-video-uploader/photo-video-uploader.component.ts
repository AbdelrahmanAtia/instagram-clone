import { Component } from '@angular/core';

@Component({
  selector: 'insta-photo-video-uploader',
  templateUrl: './photo-video-uploader.component.html',
  styleUrls: ['./photo-video-uploader.component.css']
})
export class PhotoVideoUploaderComponent {

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
    console.log("something dropped");
    event.preventDefault();
    event.stopPropagation();
    const files = event.dataTransfer?.files;
    console.log(files);
    if (files && files.length) {
      // Handle the files, perhaps using a service to upload them or process them
    }
  }

  close(): void {
    // logic to close the dialog
  }
}
