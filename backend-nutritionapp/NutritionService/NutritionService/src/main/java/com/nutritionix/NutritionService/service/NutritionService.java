package com.nutritionix.NutritionService.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutritionix.NutritionService.exception.DataFetchingNutritionUrlException;
import com.nutritionix.NutritionService.model.BrandedProduct;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;



@Service
public class NutritionService{

    @Value("${nutrition.api.key}")
    private String nutritionApiKey;

    @Value("${nutrition.api.app.id}")
    private String nutritionApiAppId;
	

    public List<BrandedProduct> searchProducts(String query) throws Exception {
    	

		String nutritionApiUrl="https://trackapi.nutritionix.com/v2/search/";
		String nutritionixInstantSearch = nutritionApiUrl+"instant?query="+query;
		System.out.println(nutritionApiAppId+"------"+nutritionApiKey);
		HttpGet getRequest = createHttpGetRequest(nutritionixInstantSearch);

        String responseJson = fetchHttpGet(getRequest);

        JSONObject jsonObject = new JSONObject(responseJson);
        JSONArray branded = jsonObject.getJSONArray("branded");

        return parseBrandedProducts(branded);
    }

  
    public BrandedProduct getBrandedProduct(String nixItemId) throws Exception {
        String itemUrl = "https://trackapi.nutritionix.com/v2/search/item?nix_item_id=" + nixItemId;
        System.out.println(itemUrl);
        HttpGet getRequest = createHttpGetRequest(itemUrl);
        String responseJson = fetchHttpGet(getRequest);
        JSONObject jsonObject = new JSONObject(responseJson);
        JSONArray foods = jsonObject.getJSONArray("foods");
        JSONObject product = foods.getJSONObject(0);

        return createBrandedProductFromJson(product);
    }
    
    
    
    public String fetchHttpGet(HttpGet get) throws Exception {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(get);
        HttpEntity entity = response.getEntity();

        if (entity == null) {
            throw new DataFetchingNutritionUrlException("No data received from the URL");
        }

        return EntityUtils.toString(entity);
    }

    

    private HttpGet createHttpGetRequest(String url) {
        HttpGet getRequest = new HttpGet(url);
        getRequest.setHeader("Content-Type", "application/json");
        getRequest.setHeader("x-app-id", nutritionApiAppId);
        getRequest.setHeader("x-app-key", nutritionApiKey);
        return getRequest;
    }

    private List<BrandedProduct> parseBrandedProducts(JSONArray branded) throws  Exception {
        List<BrandedProduct> products = new ArrayList<>();

//        for (int i = 0; i < branded.length(); i++) {
        for (int i = 0; i < 3; i++) {
            JSONObject product = branded.getJSONObject(i);
            products.add(getBrandedProduct(product.getString("nix_item_id")));
        }

        return products;
    }
    
    

    private BrandedProduct createBrandedProductFromJson(JSONObject product) throws Exception{
        BrandedProduct prod = new BrandedProduct();
        prod.setNix_item_id(product.getString("nix_item_id"));
    	prod.setNix_item_name(product.getString("food_name"));
    	
    	prod.setNix_brand_id(product.getString("nix_brand_id"));
    	prod.setNix_brand_name(product.getString("brand_name"));
    	prod.setServing_qty(product.getInt("serving_qty"));
    	try{prod.setServing_unit(product.getString("serving_unit"));}catch(Exception e) {prod.setServing_unit("null");}
    	try{prod.setServing_weight_grams(product.getInt("serving_weight_grams"));}catch(Exception e) {prod.setServing_weight_grams(0);}
    	try{prod.setNf_metric_qty(product.getInt("nf_metric_qty"));}catch(Exception e) {prod.setNf_metric_qty(0);}
    	try{prod.setNf_metric_uom(product.getString("nf_metric_uom"));}catch(Exception e) {prod.setNf_metric_uom("null");}
    	try{prod.setNf_calories(product.getInt("nf_calories"));}catch(Exception e) {prod.setNf_calories(0);}
    	try{prod.setNf_total_fat(product.getInt("nf_total_fat"));}catch(Exception e) {prod.setNf_total_fat(0);}
    	
    	try{prod.setNf_saturated_fat(product.getInt("nf_saturated_fat"));}catch(Exception e) {prod.setNf_saturated_fat(0);}
    	try{prod.setNf_cholesterol(product.getInt("nf_cholesterol"));}catch(Exception e) {prod.setNf_cholesterol(0);}
    	try{prod.setNf_sodium(product.getInt("nf_sodium"));}catch(Exception e) {prod.setNf_sodium(0);}
    	try{prod.setNf_total_carbohydrate(product.getInt("nf_total_carbohydrate"));}catch(Exception e) {prod.setNf_total_carbohydrate(0);}
    	try{prod.setNf_dietary_fiber(product.getInt("nf_dietary_fiber"));}catch(Exception e) {prod.setNf_dietary_fiber(0);}
    	try{prod.setNf_sugars(product.getInt("nf_sugars"));}catch(Exception e) {prod.setNf_sugars(0);}
    	try{prod.setNf_protein(product.getInt("nf_protein"));}catch(Exception e) {prod.setNf_protein(0);}
    	try{prod.setNf_potassium(product.getInt("nf_potassium"));}catch(Exception e) {prod.setNf_potassium(0);}
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	JsonNode photoNode = objectMapper.readTree(product.get("photo").toString());
    	prod.setImage_url(photoNode.get("thumb").asText());
    	
    	
        return prod;
    }
}

