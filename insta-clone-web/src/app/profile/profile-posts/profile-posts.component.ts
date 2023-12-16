import { Component, OnInit } from '@angular/core';
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

  constructor(
    private postService: PostService,
    private stateService: StateService
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
        console.log(res);
      }
    )
  }



}
