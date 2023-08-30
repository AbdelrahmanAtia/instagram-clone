import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PostComponent } from './post/post.component';

@NgModule({
  declarations: [ // your components are defined here
    AppComponent,
    PostComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],  //your services are defined here
  bootstrap: [AppComponent]
})
export class AppModule { }
