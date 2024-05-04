import { Component, OnInit } from '@angular/core';
import { UserService } from '../shared/services/user.service';
import { StateService } from '../shared/services/state.service';
import { FileService } from '../shared/services/file.service';
import { User } from '../shared/models/user.model';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'insta-news-feed',
  templateUrl: './news-feed.component.html',
  styleUrls: ['./news-feed.component.css']
})
export class NewsFeedComponent implements OnInit {

  userUuid: string = "";
  userDetails?: User;
  profileImage: string | null = null;

  constructor(
    private userService: UserService,
    private stateService: StateService,
    private sanitizer: DomSanitizer, // Add DomSanitizer
    private fileService: FileService){}
    
  ngOnInit(): void {   
    this.userUuid = this.stateService.getCurrentUserUuid();
    this.loadUser();
  }
  
  loadUser(): void {
    this.userService.getUser(this.userUuid).subscribe((user: User) => {
      this.userDetails = user;
      this.viewProfileImageByName(this.userDetails.profileImageName);
    }, (error: any) => {
      console.error('Error fetching user data:', error);
    });
  }

  private viewProfileImageByName(fileName: string){
    this.fileService.downloadFile(fileName).subscribe(blob => {
      const objectURL = URL.createObjectURL(blob);
      this.profileImage = this.sanitizer.bypassSecurityTrustUrl(objectURL) as string;
    });
  }

}
