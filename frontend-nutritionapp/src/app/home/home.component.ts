import { Component } from '@angular/core';
import { LoginService } from '../services/login.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { NutritionService } from '../services/nutrition.service';
import { WishlistService } from '../services/wishlist.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  isLoggedIn = false;
  user: any;
  nutritionData : any;
  dynamicProductIds = ["c6405291f1393db78caf5a7e", "63e2324be2ef800008abed89", "578707998eb128fc315a5be2", "641180f3bbba7600082b4c4d"]
  nutritionDataArray: any[]= [];

  constructor(public login: LoginService, public nutrition: NutritionService, public wishlist: WishlistService, private router:Router) {}

  ngOnInit():void {
    this.isLoggedIn = this.login.isLoggedIn();
    this.user = this.login.getUser();
    
    this.nutrition.getAllProduct("grape").subscribe(data=>{
      console.log(data);
      this.nutritionData = data;
    })

    this.dynamicProductIds.forEach(productId => {
      this.nutrition.getProduct(productId).subscribe(data=>{
        this.nutritionDataArray.push(data);

        if(this.nutritionDataArray.length === this.dynamicProductIds.length){
          console.log(this.nutritionDataArray);
        }
      },
      error => {
        console.error('Error fetching the data for productId: ', productId);
      });
    });
  }

  addToWishlist(product: any): void{
    console.log("wishlist method called", product);
    this.wishlist.addProduct(product).subscribe(
      (data) => {
        console.log("Product added to wishlist:", data);
        Swal
        .fire('Success', 'Product Added To Wishlist !!', 'success');
      }, (error) => {
          console.log(error);
          Swal.fire('Failed', 'Unable To Add Product To Wishlist.', 'error');
        });
  }
    

  public logout(){
    this.login.logout();
    this.router.navigate(['login']);
  }

}
