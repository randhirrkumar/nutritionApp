import { Component } from '@angular/core';
import { LoginService } from '../services/login.service';
import { NutritionService } from '../services/nutrition.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { WishlistService } from '../services/wishlist.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent {

  isLoggedIn = false;
  user: any;
  nutritionData: any;
  foodListForm: FormGroup;

  constructor(public login: LoginService, public nutrition: NutritionService, public wishlist: WishlistService, private fb: FormBuilder, private router:Router) {
    this.foodListForm = this.fb.group({
      query: ['', [Validators.required]]
    });
  }

  ngOnInit():void {
    this.isLoggedIn = this.login.isLoggedIn();
    this.user = this.login.getUser();

    
    
    
  }

  onSubmit() {
    const formData = this.foodListForm.value;
    const query: String = formData.query
    console.log('query', query);

    this.nutrition.getProducts(query).subscribe(response=>{
      console.log(response);
      this.nutritionData = response;

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
