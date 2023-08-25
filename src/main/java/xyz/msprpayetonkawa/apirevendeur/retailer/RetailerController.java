package xyz.msprpayetonkawa.apirevendeur.retailer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/retailer")
public class RetailerController {

    @Autowired
    RetailerService retailerService;

    @GetMapping()
    public ResponseEntity<List<Retailer>> getRetailers() {
        List<Retailer> toReturn = retailerService.getRetailers();
        return ResponseEntity.ok(toReturn);
    }

    @GetMapping("/{emailOrUid}")
    public ResponseEntity<Optional<Retailer>> getRetailerByEmailOrUid(@PathVariable("emailOrUid") String emailOrUid) {
        Optional<Retailer> toReturn;
        if(emailOrUid.contains("@")){
            toReturn = retailerService.getRetailerByEmail(emailOrUid);
        }else{
            toReturn = retailerService.getRetailerByUid(emailOrUid);
        }
        return ResponseEntity.ok(toReturn);
    }


}
