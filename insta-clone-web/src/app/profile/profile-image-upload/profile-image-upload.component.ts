import { Component, ElementRef, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';

@Component({
  selector: 'insta-profile-image-upload',
  templateUrl: './profile-image-upload.component.html',
  styleUrls: ['./profile-image-upload.component.css']
})
export class ProfileImageUploadComponent implements OnInit {
  
  @ViewChild('fileInput') fileInput!: ElementRef;  //initialized by the hidden input of type file
  @Output() profileImageUploadedEvent = new EventEmitter<FileList>();

  uploadedFiles: FileList | null = null;

  ngOnInit(): void {

  }

  onFilesSelected(event: any) {
    const files: FileList = event.target.files;
    this.uploadedFiles = files; //TODO: not needed delete it & the member variable
    this.profileImageUploadedEvent.emit(files); // Emitting the event with true as a flag

    //TODO: in the profile component..we shall listen to this 
    //emitted event and change the viewed profile picture..
  } 

  selectFile() {
    this.fileInput.nativeElement.click();
  }

}
