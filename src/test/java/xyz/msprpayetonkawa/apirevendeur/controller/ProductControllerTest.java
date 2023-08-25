package xyz.msprpayetonkawa.apirevendeur.controller;

import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.msprpayetonkawa.apirevendeur.WebSecurityConfig;
import xyz.msprpayetonkawa.apirevendeur.product.Product;
import xyz.msprpayetonkawa.apirevendeur.product.ProductController;
import xyz.msprpayetonkawa.apirevendeur.product.ProductService;
import xyz.msprpayetonkawa.apirevendeur.retailer.Retailer;
import xyz.msprpayetonkawa.apirevendeur.tools.SpringBeanMockUtil;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Import(WebSecurityConfig.class)
public class ProductControllerTest {
    @Autowired
    ProductController productController;

    @Test
    public void testGetProducts() {
        ProductService productServiceMock = SpringBeanMockUtil.mockFieldOnBean(productController, ProductService.class);
        doReturn(List.of(new Product())).when(productServiceMock).getProducts();
        Response response = given().when().get("/api/product");

        response.then().statusCode(200);
    }

    @Test
    public void testGetProductById() {
        ProductService productServiceMock = SpringBeanMockUtil.mockFieldOnBean(productController, ProductService.class);
        doReturn(new Product()).when(productServiceMock).getProduct("uid");
        Response response = given().when().pathParams("uid", "uid").get("/api/product/{uid}");
        response.then().statusCode(200);
    }

    @Test
    public void testAddProduct() {
        Product product = new Product(1L,"uid-key","Name","Description",11.11f,new Retailer(), 1, "image","bleu");
        ProductService productServiceMock = SpringBeanMockUtil.mockFieldOnBean(productController, ProductService.class);
        doReturn(new Product()).when(productServiceMock).saveProducts(Mockito.any(Product.class));
        Response response = given().contentType("application/json").when().body(product).post("/api/product");
        response.then().statusCode(200);
    }

    @Test
    public void testDeleteProductById() {
        Response response = given().when().pathParams("uid", "uid").delete("/api/product/{uid}");
        response.then().statusCode(200);
    }
    @Test
    public void testGetProductByRetailer() {
        ProductService productServiceMock = SpringBeanMockUtil.mockFieldOnBean(productController, ProductService.class);
        doReturn(List.of(new Product())).when(productServiceMock).getProductsByRetailer("uid");
        Response response = given().when().pathParams("uid", "uid").get("/api/product/retailer/{uid}");
        response.then().statusCode(200);
    }
}
