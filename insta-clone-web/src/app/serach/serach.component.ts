import { Component, OnInit } from '@angular/core';
import { UserService } from '../shared/services/user.service';
import { HttpParams } from '@angular/common/http';
import { User } from '../shared/models/user.model';
import { FileService } from '../shared/services/file.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'insta-serach',
  templateUrl: './serach.component.html',
  styleUrls: ['./serach.component.css']
})
export class SerachComponent implements OnInit {
  searchTerm: string = '';
  users: User[] = [];

  constructor(
    private userService: UserService,
    private fileService: FileService,
    private sanitizer: DomSanitizer
  ){}

  ngOnInit(): void {

  }

  onSearch() {

    if(!this.searchTerm){
      this.users = [];
      return;
    }

    let reqParams = new HttpParams() 
      .set('name.contains', this.searchTerm)
      .set('fullName.contains', this.searchTerm);

    this.userService.searchForUsers(reqParams).subscribe(res => {
      this.users = res.body ? res.body : [];
      this.setUsresProfileImage();
    });
   
  }

  setUsresProfileImage(){
    this.users.forEach(u => {
      if(u.profileImageName){
        u.profileImage = this.setImage(u);
        console.log(u.profileImage);
      }
    });
  }

  setImage(user: User): any {
    this.fileService.downloadFile(user.profileImageName).subscribe(blob => {
      const objectURL = URL.createObjectURL(blob);
      user.profileImage =  this.sanitizer.bypassSecurityTrustUrl(objectURL) as string;
    });
  }
}
