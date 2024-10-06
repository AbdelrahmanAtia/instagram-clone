import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PostCreateComponent } from '../create-post/post-create.component';

@Component({
  selector: 'insta-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {

  constructor(public dialog: MatDialog) {}
  isSidebarOpen: boolean = false;

  links = [
    { label: 'Instagram', icon: null, route: '/' },
    { label: 'Home', icon: 'fas fa-home', route: '/home/newsfeed' },
    { label: 'Search', icon: 'fas fa-search', route: '#' },
    { label: 'Explore', icon: 'fas fa-compass', route: '#' },
    { label: 'Reels', icon: 'fas fa-film', route: '#' },
    { label: 'Messages', icon: 'fas fa-envelope', route: '#' },
    { label: 'Notifications', icon: 'fas fa-heart', route: '#' },
    { label: 'Create', icon: 'fas fa-plus', route: '#' },
    { label: 'Profile', icon: 'fas fa-user', route: '/home/profile/posts' },
    // Add more links as needed
  ];

  openUploadDialog(): void {
    
    const dialogRef = this.dialog.open(PostCreateComponent, {
      "width": '405px',
      "maxHeight": '415px',
      "data": "John",  //TODO: what is this?? remove if not needed
      "autoFocus": false,
      "id": "upload-media-dialog"
    });
  
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  toggleSidebar() {
    this.isSidebarOpen = !this.isSidebarOpen;
  }

  onClick(linkLabel: string) {

    if(linkLabel === 'Create'){
      this.openUploadDialog();
    } else if(linkLabel === 'Search') {
      
    }


  }



}
