import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ProfilePostsComponent } from './profile/profile-posts/profile-posts.component';

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
    path: '', 
    redirectTo: 'login',
    pathMatch: 'full'
  },
  {
    path: 'login', 
    component: LoginComponent
  },
  {
    path: 'register', 
    component: RegisterComponent
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
