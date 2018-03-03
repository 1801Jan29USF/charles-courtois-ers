import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { NavComponent } from './nav/nav.component';

import { appRoutes } from './routes';
import { LoginComponent } from './components/login/login.component';
import { UsersComponent } from './components/users/users.component';
import { LoggedInGuard } from './guards/logged-in.guard';
import { CookieService } from 'angular2-cookie/services/cookies.service';
import { ViewReimbursementComponent } from './components/view-reimbursement/view-reimbursement.component';
import { AddReimbursementComponent } from './components/add-reimbursement/add-reimbursement.component';
import { HomeComponent } from './components/home/home.component';


@NgModule({
  imports: [
    BrowserModule,
    HttpModule,
    HttpClientModule,
    NgbModule.forRoot(),
    RouterModule.forRoot(appRoutes, { useHash: true }),
    FormsModule
  ],
  declarations: [
    AppComponent,
    NavComponent,
    LoginComponent,
    UsersComponent,
    ViewReimbursementComponent,
    AddReimbursementComponent,
    HomeComponent,
  ],
  providers: [
    LoggedInGuard,
    CookieService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
