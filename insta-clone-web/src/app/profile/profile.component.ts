import { Component } from '@angular/core';
import { UserService } from '../shared/services/user.service';
import { User } from '../shared/models/user.model';
import { StateService } from '../shared/services/state.service';

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

  constructor(
    private userService: UserService,
    private stateService: StateService){}


  ngOnInit(): void {
    this.loadUserData();
  }

  loadUserData(): void {
    //TODO: userUuid shall not be static
    const userUuid = this.stateService.getCurrentUserUuid();

    this.userService.getUser(userUuid).subscribe((user: User) => {
      
      this.username = user.username;
      this.postsCount = user.postsCount;
      this.followersCount = user.followersCount;
      this.followingCount = user.followingCount;
      this.name = user.name;
      
    }, (error: any) => {
      console.error('Error fetching user data:', error);
    });
  }

}
