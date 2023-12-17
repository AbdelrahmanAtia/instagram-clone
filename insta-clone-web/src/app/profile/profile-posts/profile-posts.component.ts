import { Component, OnInit } from '@angular/core';
import { API_CONFIG } from 'src/app/shared/models/api.config';
import { Post } from 'src/app/shared/models/post.model';
import { PostService } from 'src/app/shared/services/post.service';
import { StateService } from 'src/app/shared/services/state.service';


@Component({
  selector: 'insta-profile-posts',
  templateUrl: './profile-posts.component.html',
  styleUrls: ['./profile-posts.component.css']
})
export class ProfilePostsComponent implements OnInit {

  posts: Post [] = [];
  loadedFiles: any [] = [];


  constructor(
    private postService: PostService,
    private stateService: StateService
  ){}

  ngOnInit(): void {  
    this.stateService.printLocalStorageInfo();
    this.loadUserPosts();
  }

  loadUserPosts(){
    const userUuid = this.stateService.getCurrentUserUuid();
    const page = 0;
    const pageSize = 10;
    this.postService.getPosts(userUuid, page, pageSize).subscribe(
      res => {
        this.posts = res;
        this.posts.forEach(post => {
          post.fullFileUrl = `${API_CONFIG.baseUrl}${API_CONFIG.downloadFileEndpoint}/${post.fileName}`;
        });
        console.log(res);
      }
    )
  }

  /*
  loadFile(fileName: string): void {
    this.postService.downloadFile(fileName).subscribe((data: Blob) => {
      const reader = new FileReader();
      reader.onloadend = () => {
        this.loadedFiles.push(reader.result as string); // Store the base64 string in the images array
      };
      reader.readAsDataURL(data);
    });
  }
  */
} 


