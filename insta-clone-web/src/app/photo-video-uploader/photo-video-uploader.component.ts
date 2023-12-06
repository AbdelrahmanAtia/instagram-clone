import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';

import { Post } from '../profile-posts/models/post.model';

@Component({
  selector: 'insta-photo-video-uploader',
  templateUrl: './photo-video-uploader.component.html',
  styleUrls: ['./photo-video-uploader.component.css']
})
export class PhotoVideoUploaderComponent implements OnInit {

  public imageUrl: string | ArrayBuffer | null = null;
  postCaption: string = '';

  postShared: boolean = false;

  ngOnInit(): void {
  }

  onPostShare() {}
  
}
