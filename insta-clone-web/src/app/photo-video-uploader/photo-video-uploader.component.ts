import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';

import { Post } from '../profile-posts/models/post.model';
import { PreviewAndCaptionComponent } from './preview-and-caption/preview-and-caption.component';

@Component({
  selector: 'insta-photo-video-uploader',
  templateUrl: './photo-video-uploader.component.html',
  styleUrls: ['./photo-video-uploader.component.css']
})
export class PhotoVideoUploaderComponent implements OnInit {


  uploadedFiles: FileList | null = null;
  postShared: boolean = false;
  mediaUploaded: boolean = false;

  @ViewChild(PreviewAndCaptionComponent) previewAndCaptionComponent!: PreviewAndCaptionComponent;

  constructor(){}

  ngOnInit(): void { }

  // Method to handle the emitted event from insta-upload-dialog
  onMediaUploaded(files: FileList) {
    this.uploadedFiles = files;
    this.mediaUploaded = true;
  }

  // Method to handle the emitted event from preview-and-caption component
  onPostCreationSuccess(postShared: boolean) {
    this.postShared = postShared;
  }

  onShareClick(){
    this.previewAndCaptionComponent.sharePost();
  }
  
}
