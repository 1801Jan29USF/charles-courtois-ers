import { Component, OnInit } from '@angular/core';
import { environment } from '../../../environments/environment.prod';
import { HttpClient } from '@angular/common/http';
import { Reimbursement } from '../../beans/reimbursement';
import { CookieService } from 'angular2-cookie/services/cookies.service';
import { User } from '../../beans/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-view-reimbursement',
  templateUrl: './view-reimbursement.component.html',
  styleUrls: ['./view-reimbursement.component.css']
})
export class ViewReimbursementComponent implements OnInit {

  reimbursements: Array<Reimbursement> = [];

  managerCookie;

  constructor(private client: HttpClient, private cookie: CookieService, private router: Router) { }

  ngOnInit() {
    this.managerCookie = this.cookie.getObject('user');
    console.log(this.managerCookie);
    this.client.get(`${environment.context}reimbursements/1`,
      { withCredentials: true })
      .subscribe(
        (succ: Array<Reimbursement>) => {
          this.reimbursements = succ;
        },
        (err) => {
          console.log('Failed to load reimbursements.');
        }
      );
  }

  allowReimbursement(reimbursement: Reimbursement, statusId: number) {
    if (statusId === 2) {
      reimbursement.statusId = 2;
    } else if (statusId === 3) {
      reimbursement.statusId = 3;
    }

    this.client.put(`${environment.context}reimbursements/1`, reimbursement,
      { withCredentials: true })
      .subscribe(
        (succ: number) => {
          if (statusId === 2) {
            alert('You have successfully approved a reimbursement.');
            this.router.navigateByUrl(`${environment.context}reimbursements/1`);
            // Route to view reimbursements
          }
          if (statusId === 3) {
            alert('You have successfully denied a reimbursement.');
            this.router.navigateByUrl(`${environment.context}reimbursements/1`);
            // Route to view reimbursements
          }
        },
        (err) => {
          alert('Failed to approve or deny reimbursement.');
        }
      );
  }
}
