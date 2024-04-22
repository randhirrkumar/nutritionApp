import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class NutritionService {

  constructor(private http: HttpClient) { }

  public getAllProduct(name: string){
    return this.http.get('http://52.52.255.37:8083/nutrition/searchProduct/' + name);
    // return this.http.get('http://localhost:8083/nutrition/searchProduct/' + name);
    
  }

  public getProduct(productId: string){
    return this.http.get('http://52.52.255.37:8083/nutrition/search/' + productId);
    // return this.http.get('http://localhost:8083/nutrition/search/' + productId);
  }


  public getProducts(query:String){
    return this.http.get("http://52.52.255.37:8083/nutrition/searchProduct/"+query);
    //  return this.http.get("http://localhost:8083/nutrition/searchProduct/"+query);
     
   }
}
