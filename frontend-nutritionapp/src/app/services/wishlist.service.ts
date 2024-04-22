import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class WishlistService {

  constructor(private http: HttpClient) { }

  public addProduct(product: any){
    return this.http.post('http://52.52.255.37:8084/wish/addItem', product);
    // return this.http.post('http://localhost:8084/wish/addItem', product);
  }

  public getWishlistProducts(){
    return this.http.get("http://52.52.255.37:8084/wish/getUserWishList");
    //  return this.http.get("http://localhost:8084/wish/getUserWishList");
   }

  public deleteFromWishList(itemid:string){
    return this.http.delete("http://52.52.255.37:8084/wish/deleteUserProduct/"+itemid);
    //  return this.http.delete("http://localhost:8084/wish/deleteUserProduct/"+itemid);
  }
}
