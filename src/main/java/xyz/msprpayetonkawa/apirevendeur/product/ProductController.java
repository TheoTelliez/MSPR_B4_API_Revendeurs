package xyz.msprpayetonkawa.apirevendeur.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Read - Get all employees
     * @return - An Iterable object of Employee full filled
     */
    @GetMapping()
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> toReturn = productService.getProducts();
        return ResponseEntity.ok(toReturn);
    }

    @GetMapping("/{uid}")
    public ResponseEntity<Product> getProductByUid(@PathVariable("uid") String uid) {
        Product toReturn = productService.getProduct(uid);
        return ResponseEntity.ok(toReturn);
    }

    @PostMapping()
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        Product toReturn = productService.saveProducts(product);
        return ResponseEntity.ok(toReturn);
    }

}