import { Component } from '@angular/core';

@Component({
  selector: 'insta-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {

  username: string = "abdelrahmanattya94";
  postsCount:number = 0;
  followersCount: number = 0;
  followingCount: number = 3;
  name: string = "Abdelrahman Attya"


}
