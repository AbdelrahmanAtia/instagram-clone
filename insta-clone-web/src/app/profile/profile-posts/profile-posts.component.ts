import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { API_CONFIG } from 'src/app/shared/models/api.config';
import { Post } from 'src/app/shared/models/post.model';
import { FileService } from 'src/app/shared/services/file.service';
import { PostService } from 'src/app/shared/services/post.service';
import { StateService } from 'src/app/shared/services/state.service';

@Component({
  selector: 'insta-profile-posts',
  templateUrl: './profile-posts.component.html',
  styleUrls: ['./profile-posts.component.css']
})
export class ProfilePostsComponent implements OnInit {
  posts: Post [] = [];
  loadedFiles: string[] = []; // to store blob URLs

  constructor(
    private postService: PostService,
    private fileService: FileService,
    private stateService: StateService,
    private sanitizer: DomSanitizer // Add DomSanitizer
  ){}

  ngOnInit(): void {  
    this.loadUserPosts();
  }

  loadUserPosts(){
    const userUuid = this.stateService.getCurrentUserUuid();
    const page = 0;
    const pageSize = 10;
    this.postService.getPosts(userUuid, page, pageSize).subscribe(
      res => {
        this.posts = res;
        this.posts.forEach((post, index) => {
          this.fileService.downloadFile(post.fileName).subscribe(blob => {
            const objectURL = URL.createObjectURL(blob);
            this.loadedFiles[index] = this.sanitizer.bypassSecurityTrustUrl(objectURL) as string;
          });
        });
        console.log(res);
      }
    )
  }
}
