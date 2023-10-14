import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { ProfilePostsComponent } from './profile-posts/profile-posts.component';
import { LoginComponent } from './login/login.component';

const routes: Routes = [
  {
    path: 'home', 
    component: HomeComponent,
    children: [
      {
        path: 'profile',
        component: ProfileComponent,
        children: [
          {
            path: 'posts', 
            component: ProfilePostsComponent
          }
        ]
      }
    ]
  },
  {
    path: 'login', 
    component: LoginComponent
  },
  {
    path: '', 
    redirectTo: 'login',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
