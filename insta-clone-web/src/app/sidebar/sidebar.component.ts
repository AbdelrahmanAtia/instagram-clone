import { Component } from '@angular/core';

@Component({
  selector: 'insta-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {

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


}
