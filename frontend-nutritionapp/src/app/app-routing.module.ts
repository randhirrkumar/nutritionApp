import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { LandingpageComponent } from './landingpage/landingpage.component';
import { HomeComponent } from './home/home.component';
import { AuthGuard } from './services/auth.guard';
import { WishlistComponent } from './wishlist/wishlist.component';
import { SearchComponent } from './search/search.component';

const routes: Routes = [
  {
    path: '',
    component: LandingpageComponent,
    pathMatch: 'full'
  },
  
  {
    path: 'signup',
    component: SignupComponent,
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent,
    pathMatch: 'full'
  },
  {
    path: 'wishlist',
    component: WishlistComponent,
    pathMatch: 'full',
    canActivate:[AuthGuard]
  },
  {
    path: 'search',
    component: SearchComponent,
    pathMatch: 'full',
    canActivate:[AuthGuard]
  },
  {
    path: 'home',
    canActivate:[AuthGuard],
    children: [
      {
        path: "",
        component: HomeComponent
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
