import { Component } from '@angular/core';
import { PartialUpdateUser, UserService } from '../shared/services/user.service';
import { User } from '../shared/models/user.model';
import { StateService } from '../shared/services/state.service';
import { MatDialog } from '@angular/material/dialog';
import { ProfileImageUploadComponent } from './profile-image-upload/profile-image-upload.component';
import { DomSanitizer } from '@angular/platform-browser';
import { FileService } from '../shared/services/file.service';

@Component({
  selector: 'insta-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {

  userDetails?: User;
  profileImage: string | null = null;
  userUuid: string = "";
  userFollowers: User[] = [];
  showFollowersModal: boolean = false;

  constructor(
    private userService: UserService,
    private stateService: StateService,
    private fileService: FileService,
    private sanitizer: DomSanitizer, // Add DomSanitizer
    public dialog: MatDialog){}


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

  openUploadDialog(){
    const dialogRef = this.dialog.open(ProfileImageUploadComponent, {
      "width": '400px',
      "height": '224px',
      "data": "John",  //TODO: what is this?? remove if not needed
      "autoFocus": false,
      "id": "upload-media-dialog"
    });

    dialogRef.componentInstance.profileImageUploadedEvent.subscribe((files: FileList) => {
      // Handle the emitted event here
      dialogRef.close();
      this.handleProfileImageUploadingEvent(files);
    });

    dialogRef.componentInstance.profileImageRemovalEvent.subscribe(() => {
      dialogRef.close();
      this.handleProfileImageRemovalEvent();
    });

    dialogRef.componentInstance.dialogClosingEvent.subscribe(event => {
      dialogRef.close();
    });

    dialogRef.afterClosed().subscribe(event => {
      //console.log('The dialog was closed');
    });
  }
  
  // Event handler method for the emitted event from ProfileImageUploadComponent
  handleProfileImageUploadingEvent(files: FileList): void {
    const uploadedFile = files[0];

    if(!this.userDetails) {
      return;
    }

    //delete old profile image
    if(this.userDetails.profileImageName){
      this.fileService.deleteFile(this.userDetails.profileImageName).subscribe(res => {});
    }

    //upload new profile image & update user entity profile image name in DB
    this.fileService.uploadFile(uploadedFile).subscribe(res => {

      let partialUpdateUser :PartialUpdateUser = {
        "userUuid": this.userUuid,
        "profileImageName": res.fileName
      };

      this.userService.partialUpdate(partialUpdateUser).subscribe(res => {
        
        if(this.userDetails && res.body){
          this.userDetails.profileImageName = res.body.profileImageName;
        }
        
        this.viewProfileImage(uploadedFile);
      });

    });
  }

  handleProfileImageRemovalEvent() {
    
    if(!this.userDetails) {
      return;
    }

    this.fileService.deleteFile(this.userDetails?.profileImageName).subscribe(res => {

      // update image name in user enity in DB 
      let partialUpdateUser :PartialUpdateUser = {
        "userUuid": this.userUuid,
        "profileImageName": ""
      };
  
      this.userService.partialUpdate(partialUpdateUser).subscribe(res => {
        const dataFromBody = res.body;

        if(this.userDetails){
          this.userDetails.profileImageName = "";
        }

        this.viewDummyProfileImage();
      });

    });
    
  }

  /**
   * 
   * this function is used to view profile image once 
   * it is uploaded
   */
  private viewProfileImage(file: File): void {
    const reader = new FileReader();
    reader.onload = (e: any) => {
      const dataURL = e.target.result;
      this.profileImage = dataURL; //views profile image 
    };
    reader.readAsDataURL(file);
  }

  viewDummyProfileImage() {
    this.profileImage = "";
  }

  /**
   * 
   * this function is used to view profile image 
   * when we refresh the profile
   */
  private viewProfileImageByName(fileName: string){
    this.fileService.downloadFile(fileName).subscribe(blob => {
      const objectURL = URL.createObjectURL(blob);
      this.profileImage = this.sanitizer.bypassSecurityTrustUrl(objectURL) as string;
    });
  }
  
  setUserProfileImage(user: User){
    this.fileService.downloadFile(user.profileImageName).subscribe(blob => {
      const objectURL = URL.createObjectURL(blob);
      user.profileImage = this.sanitizer.bypassSecurityTrustUrl(objectURL) as string;
    });
  }

  openFollowersModal(): void {

    this.showFollowersModal = true;

    this.userService.getUserFollowers(this.userUuid).subscribe(res => {
      this.userFollowers = res.body ? res.body : [];
      this.userFollowers.forEach(follower => {
        this.setUserProfileImage(follower);
      });
    });

  }

  closeFollowersModal(): void {
    this.showFollowersModal = false;
  }


  onFollowUserClick(followedId: string) {

    let followUserReq = {
      followedId: followedId
    };

    this.userService.followUser(followUserReq).subscribe(res => {

      const followedUser: User | undefined = this.userFollowers.find(u => u.userUuid === followedId);
      if(followedUser){
        followedUser.followedByCurrentUser = true;
      }

    });
  
  }


}
