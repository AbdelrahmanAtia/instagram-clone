import { Component } from '@angular/core';

@Component({
  selector: 'insta-preview-and-caption',
  templateUrl: './preview-and-caption.component.html',
  styleUrls: ['./preview-and-caption.component.css']
})
export class PreviewAndCaptionComponent {

  public imageUrl: string | ArrayBuffer | null = null;
  postCaption: string = '';

}
