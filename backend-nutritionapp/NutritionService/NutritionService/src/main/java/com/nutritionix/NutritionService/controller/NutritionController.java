package com.nutritionix.NutritionService.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutritionix.NutritionService.feignClient.AuthClient;
import com.nutritionix.NutritionService.model.BrandedProduct;
import com.nutritionix.NutritionService.service.NutritionService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/nutrition")
@CrossOrigin(origins = "*")
@Slf4j
public class NutritionController {
	
	@Autowired 
	AuthClient authClient;

	@Autowired
    private NutritionService nutritionService;
	
	private void validateToken(String token) throws Exception {
		log.info("Calling validating token in Authentication..."+token);
		try {
			if (authClient.validateToken(token)==false || !authClient.validateToken(token)) throw new Exception();
		} catch (Exception e) {
			throw new Exception("feign Client Exception"+e);
		}
	}
	
	public String  getUsername(String token) {
		log.info("extractig user name from token : "+token);
		return authClient.getUsername(token);
	}
	

    @GetMapping("/searchProduct/{query}")
    public ResponseEntity<?> searchProducts(@PathVariable String query, @RequestHeader(name = "Authorization", required = true) String token) throws Exception {
    	log.info("Searching products from Nutrition APi with name "+query);
    	
        try {
        	validateToken(token);
    		String username=getUsername(token);
            List<BrandedProduct> products = nutritionService.searchProducts(query);
            log.debug("getting products", products);
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error while fetching nutrition information", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/search/{itemId}")
    public ResponseEntity<?> searchProductByItemId(@PathVariable String itemId, @RequestHeader(name = "Authorization", required = true) String token) throws Exception {
    	log.info("getting product from Nutrition Api with itemId "+itemId);
        try {
        	validateToken(token);
    		String username=getUsername(token);
            BrandedProduct product = nutritionService.getBrandedProduct(itemId);
            log.debug("getting product", product);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while fetching nutrition information", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
