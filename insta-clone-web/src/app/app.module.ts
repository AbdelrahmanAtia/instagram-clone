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
import { UserService } from './shared/services/user.service';
import { LoginService } from './shared/services/login.service';
import { StateService } from './shared/services/state.service';
import { RegisterComponent } from './register/register.component';


const COMPONENTS = [ AppComponent,
  CommentComponent,
  SidebarComponent,
  ProfileComponent,
  FooterComponent,
  HomeComponent,
  ProfilePostsComponent,
  LoginComponent];
  
const MODULES = [BrowserModule, AppRoutingModule, HttpClientModule, FormsModule];
const SERVICES = [LoginService, UserService, StateService];

@NgModule({
  declarations: [COMPONENTS, RegisterComponent],
  imports: [MODULES],
  providers: [SERVICES],
  bootstrap: [AppComponent]
})
export class AppModule { }
