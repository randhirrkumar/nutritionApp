package com.nutritionix.NutritionService.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nutritionix.NutritionService.feignClient.AuthClient;
import com.nutritionix.NutritionService.model.BrandedProduct;
import com.nutritionix.NutritionService.service.NutritionService;

class NutritionControllerTest {
	
	@Mock
    private AuthClient authClient;

    @Mock
    private NutritionService nutritionService;

    @InjectMocks
    private NutritionController nutritionController;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchProducts() throws Exception {
        // Mocking the service response
    	String token = "validToken";
        String username = "user123";
        List<BrandedProduct> mockedProducts = Arrays.asList(new BrandedProduct(), new BrandedProduct());
        when(authClient.validateToken(token)).thenReturn(true);
        when(authClient.getUsername(token)).thenReturn(username);
        when(nutritionService.searchProducts("testQuery")).thenReturn(mockedProducts);

        // Testing the controller method
        ResponseEntity<?> responseEntity = nutritionController.searchProducts("testQuery", token);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof List);
        assertEquals(mockedProducts, responseEntity.getBody());
    }

//    @Test
//    void testSearchProductsException() throws Exception {
//        // Mocking an exception in the service
//        when(nutritionService.searchProducts("testQuery")).thenThrow(new Exception("Test Exception"));
//
//        // Testing the controller method
//        ResponseEntity<?> responseEntity = nutritionController.searchProducts("testQuery");
//
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
//        assertTrue(responseEntity.getBody() instanceof String);
//        assertEquals("Error while fetching nutrition information", responseEntity.getBody());
//    }

    @Test
    void testSearchProductByItemId() throws Exception {
        // Mocking the service response
    	String token = "validToken";
        String username = "user123";
        BrandedProduct mockedProduct = new BrandedProduct();
        
        when(authClient.validateToken(token)).thenReturn(true);
        when(authClient.getUsername(token)).thenReturn(username);
        when(nutritionService.getBrandedProduct("testItemId")).thenReturn(mockedProduct);

        // Testing the controller method
        ResponseEntity<?> responseEntity = nutritionController.searchProductByItemId("testItemId", token);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof BrandedProduct);
        assertEquals(mockedProduct, responseEntity.getBody());
    }

    @Test
    void testSearchProductByItemIdException() throws Exception {
        // Mocking an exception in the service
    	String token = "validToken";
        String username = "user123";
        when(authClient.validateToken(token)).thenReturn(true);
        when(authClient.getUsername(token)).thenReturn(username);
        when(nutritionService.getBrandedProduct("testItemId")).thenThrow(new Exception("Test Exception"));

        // Testing the controller method
        ResponseEntity<?> responseEntity = nutritionController.searchProductByItemId("testItemId", token);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof String);
        assertEquals("Error while fetching nutrition information", responseEntity.getBody());
    }
}
