import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CommentComponent } from './comment/comment.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { ProfileComponent } from './profile/profile.component';
import { FooterComponent } from './footer/footer.component';
import { HomeComponent } from './home/home.component';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './login/login.component'; 
import { FormsModule } from '@angular/forms';
import { UserService } from './shared/services/user.service';
import { AuthService } from './shared/services/auth.service';
import { StateService } from './shared/services/state.service';
import { RegisterComponent } from './register/register.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialogModule } from '@angular/material/dialog';
import { PostService } from './shared/services/post.service';
import { MatIconModule } from '@angular/material/icon';
import { PostCreateComponent } from './create-post/post-create.component';
import { UploadDialogComponent } from './create-post/upload-dialog/upload-dialog.component';
import { PreviewAndCaptionComponent } from './create-post/preview-and-caption/preview-and-caption.component';
import { PostSuccessComponent } from './create-post/post-success/post-success.component';
import { ProfilePostsComponent } from './profile/profile-posts/profile-posts.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './shared/interceptor/auth.interceptor';


const COMPONENTS = [ AppComponent,
  CommentComponent,
  SidebarComponent,
  ProfileComponent,
  FooterComponent,
  HomeComponent,
  ProfilePostsComponent,
  LoginComponent,
  RegisterComponent, 
  PostCreateComponent,
  UploadDialogComponent,
  PreviewAndCaptionComponent, 
  PostSuccessComponent
];
  
const MODULES = [
  BrowserModule, 
  AppRoutingModule, 
  HttpClientModule, 
  FormsModule,
  MatIconModule,
  MatDialogModule,
  BrowserAnimationsModule
];

const SERVICES = [AuthService, UserService, StateService, PostService];

@NgModule({
  declarations: [COMPONENTS],
  imports: [MODULES],
  providers: [
    SERVICES,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
