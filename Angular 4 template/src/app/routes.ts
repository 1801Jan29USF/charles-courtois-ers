import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { UsersComponent } from './components/users/users.component';
import { LoggedInGuard } from './guards/logged-in.guard';
import { ViewReimbursementComponent } from './components/view-reimbursement/view-reimbursement.component';
import { AddReimbursementComponent } from './components/add-reimbursement/add-reimbursement.component';
import { HomeComponent } from './components/home/home.component';

export const appRoutes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [
      LoggedInGuard
    ]
  },
  {
    path: 'users',
    component: UsersComponent,
    canActivate: [
      LoggedInGuard
    ]
  },
  {
    path: 'reimbursements/1',
    component: ViewReimbursementComponent,
    canActivate: [
      LoggedInGuard
    ]
  },
  {
    path: 'reimbursements/2',
    component: AddReimbursementComponent,
    canActivate: [
      LoggedInGuard
    ]
  },
  {
    path: '**',
    pathMatch: 'full',
    redirectTo: 'login'
  }
];
