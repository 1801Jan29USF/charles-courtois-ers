import { Component, OnInit } from '@angular/core';
import { User } from '../../beans/user';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  users: Array<User> = [];

  constructor(private client: HttpClient) { }

  ngOnInit() {
    this.client.get(`${environment.context}login`,
      {withCredentials: true})
      .subscribe(
        (succ: Array<User>) => {
          this.users = succ;
        },
        (err) => {
          console.log('Failed to load users');
        }
      );
  }

}
