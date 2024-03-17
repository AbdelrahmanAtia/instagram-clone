import { Component } from '@angular/core';
import { PartialUpdateUser, UserService } from '../shared/services/user.service';
import { User } from '../shared/models/user.model';
import { StateService } from '../shared/services/state.service';
import { MatDialog } from '@angular/material/dialog';
import { ProfileImageUploadComponent } from './profile-image-upload/profile-image-upload.component';
import { PostService } from '../shared/services/post.service';
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
      this.handleProfileImageUploaded(files);
    });

    dialogRef.componentInstance.profileImageRemovalEvent.subscribe(() => {

      this.handleProfileImageRemovalEvent();
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
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
        this.viewDummyProfileImage();
      });

    });
  
    
  }


  // Event handler method for the emitted event from ProfileImageUploadComponent
  handleProfileImageUploaded(files: FileList): void {
    const uploadedFile = files[0];

    if(!this.userDetails) {
      return;
    }

    console.log(">> old image name: " + this.userDetails.profileImageName)

    //delete old profile image
    this.fileService.deleteFile(this.userDetails.profileImageName).subscribe(res => {
      console.log(">> deleted old profile image")
    });

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


}
