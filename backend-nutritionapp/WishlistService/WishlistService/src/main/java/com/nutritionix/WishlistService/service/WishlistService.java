package com.nutritionix.WishlistService.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nutritionix.WishlistService.exception.DuplicateProductException;
import com.nutritionix.WishlistService.exception.UserWishlistNotFoundException;
import com.nutritionix.WishlistService.model.BrandedProduct;
import com.nutritionix.WishlistService.model.UserWishlist;
import com.nutritionix.WishlistService.repository.WishListRepository;

@Service
public class WishlistService {
	@Autowired
	WishListRepository wishListRepository;
	@Transactional
	public List<BrandedProduct> addProdctToWishList(BrandedProduct product, String username)
	        throws  DuplicateProductException {
	    Optional<UserWishlist> userWishList = wishListRepository.findById(username);
	    List<BrandedProduct> products;
	    if (userWishList.isEmpty()) {
	        products = new ArrayList<>();
	    } else {
	        products = userWishList.get().getProducts();
	        if (products.stream().anyMatch(p -> p.getNix_item_id().equals(product.getNix_item_id()))) {
	            throw new DuplicateProductException("Product already exists in the wishlist.");
	        }
	    }
	    products.add(product);
	    UserWishlist wishList = new UserWishlist(username, products);
	    wishListRepository.save(wishList);

	    return wishList.getProducts();
	}


	@Transactional
	public List<BrandedProduct> getUserProducts(String username) throws UserWishlistNotFoundException {
	    Optional<UserWishlist> userWishList = wishListRepository.findById(username);
	    if (userWishList.isEmpty()) {
	        throw new UserWishlistNotFoundException("User Wishlist does not exist");
	    }
	    return userWishList.get().getProducts();
	}

	
	
	
	@Transactional
	public List<BrandedProduct> deleteWishListProduct(String itemId, String username) throws UserWishlistNotFoundException {
	    Optional<UserWishlist> userWishList = wishListRepository.findById(username);

	    if (userWishList.isEmpty()) {
	        throw new UserWishlistNotFoundException("User Wishlist does not exist");
	    }

	    UserWishlist wishList = userWishList.get();
	    List<BrandedProduct> products = wishList.getProducts();

	    boolean productdeleted=products.removeIf(prod -> itemId.equals(prod.getNix_item_id()));
	    if(productdeleted) {
		    wishList.setProducts(products);
		    wishListRepository.save(wishList);
	    }
	    return wishList.getProducts();
	}
	
	
}
