import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';

import { Post } from '../profile-posts/models/post.model';

@Component({
  selector: 'insta-photo-video-uploader',
  templateUrl: './photo-video-uploader.component.html',
  styleUrls: ['./photo-video-uploader.component.css']
})
export class PhotoVideoUploaderComponent implements OnInit {

  mediaUploaded: boolean = false;
  postShared: boolean = false;


  ngOnInit(): void {
  }

  // Method to handle the emitted event from insta-upload-dialog
  onMediaUploaded(flag: boolean) {
    this.mediaUploaded = flag; 
  }

  onPostShare(){
  
  }
  
}
