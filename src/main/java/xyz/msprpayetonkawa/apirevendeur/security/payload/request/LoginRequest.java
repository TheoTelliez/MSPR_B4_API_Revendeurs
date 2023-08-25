package xyz.msprpayetonkawa.apirevendeur.security.payload.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
public class LoginRequest {

	private String username;

	private String password;

}
