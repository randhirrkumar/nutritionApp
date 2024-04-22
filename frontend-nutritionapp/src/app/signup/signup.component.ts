import { Component, ViewChild } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { UserService } from '../services/user.service';
import { NgForm } from '@angular/forms';
import { UserProfile } from '../UserProfile';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  constructor(private userService: UserService, private snack: MatSnackBar, private router: Router) { }

  user: UserProfile = new UserProfile();

  // public user = {
  //   username: '',
  //   password: '',
  //   email: '',
  // };

  ngOnInit() { }

  formSubmit() {
    this.userService.addUser(this.user).subscribe((data) => {
      console.log(data);
      Swal
        .fire('Success', 'User Registered Successfully !!', 'success')
        .then(() => {
          this.router.navigateByUrl("/login");
        })

    }, (error) => {
      console.log(error);
      Swal.fire('Username must be unique', 'User not registered', 'error');
    })

  }

}
