import { Component } from '@angular/core';
import { PartialUpdateUser, UserService } from '../shared/services/user.service';
import { User } from '../shared/models/user.model';
import { StateService } from '../shared/services/state.service';
import { MatDialog } from '@angular/material/dialog';
import { ProfileImageUploadComponent } from './profile-image-upload/profile-image-upload.component';
import { v4 as uuidv4 } from 'uuid';

@Component({
  selector: 'insta-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {

  userDetails?: User;
  profileImage: string | null = null;
  userUuid: string = "";

  /*
  username: string = "abdelrahmanattya94"; //TODO: why there is a fixed name
  postsCount:number = 0;
  followersCount: number = 0;
  followingCount: number = 3;
  name: string = "Abdelrahman Attya"; //TODO: why there is a fixed name 
  */

  constructor(
    private userService: UserService,
    private stateService: StateService,
    public dialog: MatDialog){}


  ngOnInit(): void {   
    this.userUuid = this.stateService.getCurrentUserUuid();
    this.loadUser();
  }

  loadUser(): void {
    this.userService.getUser(this.userUuid).subscribe((user: User) => {
      this.userDetails = user;
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
  
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }    

  // Event handler method for the emitted event from ProfileImageUploadComponent
  handleProfileImageUploaded(files: FileList): void {
    console.log("profile image uploaded..");
    const uploadedFile = files[0];
    // Convert the image to a data URL
    this.createImageFromBlob(uploadedFile);
  }

  createImageFromBlob(file: File): void {
    const reader = new FileReader();
    reader.onload = (e: any) => {
      const dataURL = e.target.result;
      this.profileImage = dataURL;

      //TODO: save image as a file in the backend 
      this.updateUserProfileImageName(this.userUuid, this.generateFilename(file));

    };
    reader.readAsDataURL(file);
  }
  
  updateUserProfileImageName(userId: string, profileImageName: string){

    //TODO: call backend service to save user profile image..and also 
    //when loading this component set the prfile image member variable
    let partialUpdateUser :PartialUpdateUser = {
      "id": userId,
      "profileImageName": profileImageName
    };

    this.userService.partialUpdate(partialUpdateUser).subscribe(res => {
      const dataFromBody = res.body;
      //TODO: DO SOMETHING IF NEEDED
    });

  }

  generateFilename(file: File): string {
    const extension = file.name.split('.').pop(); // Get the file extension
    const uniqueId = uuidv4(); // Generate a unique identifier

    // Construct the filename using a combination of timestamp, unique ID, and extension
    const filename = `${Date.now()}_${uniqueId}.${extension}`;
    return filename;
  }

}
