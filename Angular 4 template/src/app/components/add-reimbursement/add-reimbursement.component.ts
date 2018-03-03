import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CookieService } from 'angular2-cookie/services/cookies.service';
import { environment } from '../../../environments/environment';
import { Reimbursement } from '../../beans/reimbursement';

@Component({
  selector: 'app-add-reimbursement',
  templateUrl: './add-reimbursement.component.html',
  styleUrls: ['./add-reimbursement.component.css']
})
export class AddReimbursementComponent implements OnInit {

  constructor(private client: HttpClient, private cookie: CookieService) { }

  managerCookie;

  newReimbursement = {
    id: 1,
    amount: 0,
    dateSubmitted: new Date(),
    dateResolved: new Date(),
    description: '',
    authorId: 1,
    resolverId: 1,
    statusId: 1,
    typeId: 1
  };

  ngOnInit() {
    this.managerCookie = this.cookie.getObject('user');
  }

  addReimbursement(reimbursement: Reimbursement) {
    this.client.post(`${environment.context}reimbursements/2`, this.newReimbursement,
      { withCredentials: true })
      .subscribe(
        (succ: any) => {
          this.cookie.putObject('reimbursement', succ);
          alert('You have successfully added a reimbursement.');
        },
        (err) => {
          alert('Failed to add reimbursement.');
        }
      );
  }
}
