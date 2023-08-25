package xyz.msprpayetonkawa.apirevendeur.security;

import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.msprpayetonkawa.apirevendeur.qrcode.GenerateQRCode;
import xyz.msprpayetonkawa.apirevendeur.retailer.Retailer;
import xyz.msprpayetonkawa.apirevendeur.retailer.RetailerRepository;
import xyz.msprpayetonkawa.apirevendeur.security.jwt.JwtUtils;
import xyz.msprpayetonkawa.apirevendeur.security.payload.request.LoginRequest;
import xyz.msprpayetonkawa.apirevendeur.security.payload.request.SignupRequest;
import xyz.msprpayetonkawa.apirevendeur.security.payload.response.MessageResponse;
import xyz.msprpayetonkawa.apirevendeur.security.payload.response.Token;
import xyz.msprpayetonkawa.apirevendeur.security.services.UserDetailsImpl;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.UUID;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RetailerRepository retailerRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    GenerateQRCode generateQRCode;

    @Value("${customer.password}")
    private String defaultPassword;

    @PostMapping("/signin")
    public ResponseEntity<Token> authenticateUser(@RequestBody LoginRequest loginRequest) throws IOException, WriterException, MessagingException {
        loginRequest.setPassword(defaultPassword);
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateToken(userDetails.getUsername(), userDetails.getEmail());

        generateQRCode.createQRCode(jwtToken, userDetails.getUsername(), userDetails.getEmail());

        return ResponseEntity.ok(new Token(jwtToken));
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (retailerRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
        }

        Retailer retailer = new Retailer();
        retailer.setUid(UUID.randomUUID().toString());
        retailer.setName(signUpRequest.getName());
        retailer.setEmail(signUpRequest.getEmail());
        retailer.setPassword(encoder.encode(defaultPassword));
        retailer.setRole("ROLE_RETAILER");
        retailerRepository.save(retailer);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
