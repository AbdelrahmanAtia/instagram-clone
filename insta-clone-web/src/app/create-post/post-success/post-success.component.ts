import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'insta-post-success',
  templateUrl: './post-success.component.html',
  styleUrls: ['./post-success.component.css']
})
export class PostSuccessComponent implements OnInit {
  
  constructor(public dialog: MatDialog){}  
  
  ngOnInit(): void {
    this.dialog.getDialogById("upload-media-dialog")?.updateSize("405px", "415px");
  }

}
