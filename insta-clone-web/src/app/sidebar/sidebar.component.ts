import { Component } from '@angular/core';
import { PhotoVideoUploaderComponent } from '../photo-video-uploader/photo-video-uploader.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'insta-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {

  constructor(public dialog: MatDialog) {}

  links = [
    { label: 'Instagram', icon: null, route: '/' },
    { label: 'Home', icon: 'fas fa-home', route: '/home' },
    { label: 'Search', icon: 'fas fa-search', route: '#' },
    { label: 'Explore', icon: 'fas fa-compass', route: '#' },
    { label: 'Reels', icon: 'fas fa-film', route: '#' },
    { label: 'Messages', icon: 'fas fa-envelope', route: '#' },
    { label: 'Notifications', icon: 'fas fa-heart', route: '#' },
    { label: 'Create', icon: 'fas fa-plus', route: '#' },
    { label: 'Profile', icon: 'fas fa-user', route: '/home/profile' },
    // Add more links as needed
  ];

  openUploadDialog(): void {
    
    const dialogRef = this.dialog.open(PhotoVideoUploaderComponent, {
      "width": '600px',
      "maxHeight": '90vh',
      "data": "John",  //TODO: what is this?? remove if not needed
      "autoFocus": false    
    });
  
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }


}
