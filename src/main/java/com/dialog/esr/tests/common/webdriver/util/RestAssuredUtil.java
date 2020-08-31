
package com.dialog.esr.tests.common.webdriver.util;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;

public class RestAssuredUtil {
    public static String path;
    public static String jsonPathTerm;

    //Sets Base URI
    public static RequestSpecification setBaseURI(String url) {
        RestAssured.baseURI = url;
        return RestAssured.given().header("Authorization", "Bearer " + UserProfile.token);
    }

    //Sets base path
    public static void setBasePath(String basePathTerm){
        RestAssured.basePath = basePathTerm;
    }

    //Reset Base URI (after test)
    public static void resetBaseURI (){
        RestAssured.baseURI = null;
    }

    //Reset base path
    public static void resetBasePath(){
        RestAssured.basePath = null;
    }

    //Sets ContentType
    public static void setContentType (ContentType Type){
        given().contentType(Type);
    }

    //Sets Json path term
    public static void  setJsonPathTerm(String jsonPath){
        jsonPathTerm = jsonPath;
    }

    //Created search query path
    public static void getQuery(String queryTerm, String param, String paramValue) {
        path = param + "/" + paramValue;
    }

    public static Response getResponse() {
        return get(path);
    }

    public static JsonPath getJsonPath (Response res) {
        String json = res.asString();
        return new JsonPath(json);
    }
}
