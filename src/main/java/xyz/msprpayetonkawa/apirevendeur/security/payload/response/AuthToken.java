package xyz.msprpayetonkawa.apirevendeur.security.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthToken {
    private String token;
}
