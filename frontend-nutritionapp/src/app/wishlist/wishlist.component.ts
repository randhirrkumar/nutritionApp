import { Component } from '@angular/core';
import { LoginService } from '../services/login.service';
import { WishlistService } from '../services/wishlist.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.css']
})
export class WishlistComponent {

  isLoggedIn = false;
  user: any;
  nutritionData: any;

  constructor(public login: LoginService, public wishlist: WishlistService, private router:Router) {}

  ngOnInit():void {
    this.isLoggedIn = this.login.isLoggedIn();
    this.user = this.login.getUser();

    this.wishlist.getWishlistProducts().subscribe(data=>{
      console.log(data);
      this.nutritionData = data;
    })
    
    
  }

  public deleteItem(data: any){
    this.wishlist.deleteFromWishList(data).subscribe(res=>{
      console.log(res);
      Swal
        .fire('Success', 'Deleted Successfully !!', 'success')
        .then(() => {
          this.wishlist.getWishlistProducts().subscribe(data=>{
            console.log(data);
            this.nutritionData = data;
            console.log(this.nutritionData.length)
          })
        })

    }, (error) => {
      console.log(error);
      Swal.fire('Failed', 'Unable To Delete.', 'error');
    })
  }

  public logout(){
    this.login.logout();
    this.router.navigate(['login']);
  }

}
