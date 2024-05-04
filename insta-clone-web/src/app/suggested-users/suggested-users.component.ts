import { Component, OnInit} from '@angular/core';
import { UserService } from '../shared/services/user.service';
import { User } from '../shared/models/user.model';
import { ActivatedRoute } from '@angular/router';
import { FileService } from '../shared/services/file.service';
import { DomSanitizer } from '@angular/platform-browser';
@Component({
  selector: 'insta-suggested-users',
  templateUrl: './suggested-users.component.html',
  styleUrls: ['./suggested-users.component.css']
})
export class SuggestedUsersComponent implements OnInit {

  suggestedUsers: User [] = [];
  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private fileService: FileService,
    private sanitizer: DomSanitizer
  ){}
  
  ngOnInit(): void {
    this.loadSuggestedUsers();
  }

  loadSuggestedUsers() {
    console.log(">> loading suggested users..")
    this.userService.querySuggestedUsers('min').subscribe(res => {
      this.suggestedUsers = (res && res.body) ? res.body: [];
      this.suggestedUsers.forEach(u => {
        this.loadUserProfileImage(u.profileImageName, u);
      });
    });
  }

  loadUserProfileImage(fileName: string, user: User): void {
    this.fileService.downloadFile(fileName).subscribe(blob => {
      const objectURL = URL.createObjectURL(blob);
      user.profileImage = this.sanitizer.bypassSecurityTrustUrl(objectURL) as string;
    });
  }


}
