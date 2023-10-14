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
import { ProfilePostsComponent } from './profile-posts/profile-posts.component';
import { LoginComponent } from './login/login.component'; 
import { FormsModule } from '@angular/forms';


const COMPONENTS = [ AppComponent,
  CommentComponent,
  SidebarComponent,
  ProfileComponent,
  FooterComponent,
  HomeComponent,
  ProfilePostsComponent,
  LoginComponent];
  
const MODULES = [BrowserModule, AppRoutingModule, HttpClientModule, FormsModule];

@NgModule({
  declarations: [COMPONENTS],
  imports: [MODULES],
  providers: [],  //your services are defined here
  bootstrap: [AppComponent]
})
export class AppModule { }
