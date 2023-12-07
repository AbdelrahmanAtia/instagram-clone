import { Component, ElementRef, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Post } from 'src/app/profile-posts/models/post.model';
import { PostService } from 'src/app/shared/services/post.service';
@Component({
  selector: 'insta-upload-dialog',
  templateUrl: './upload-dialog.component.html',
  styleUrls: ['./upload-dialog.component.css']
})
export class UploadDialogComponent implements OnInit {


  postCaption: string = '';
  postShared: boolean = false;

  @Output() mediaUploadedEvent = new EventEmitter<FileList>();

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
    this.mediaUploadedEvent.emit(files); // Emitting the event with true as a flag
  }
 
  selectFile() {
    this.fileInput.nativeElement.click();
  }

  close(): void {
    // logic to close the dialog
  }



}
