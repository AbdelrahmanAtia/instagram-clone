import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { ProfilePostsComponent } from './profile-posts/profile-posts.component';


const routes: Routes = [
  {
    path: '', 
    component: HomeComponent
  },
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
];



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
