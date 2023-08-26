package xyz.msprpayetonkawa.apirevendeur.entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.msprpayetonkawa.apirevendeur.security.payload.response.UserInfoResponse;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserInfoResponseTest {

    private UserInfoResponse userInfoResponse;
    private final List<String> roles = Arrays.asList("Admin","Retailer");

    @Before
    public void setUp() {
        userInfoResponse = new UserInfoResponse(1L,"username","email@email.com",roles);
    }

    @Test
    public void testGetAndSetId(){
        userInfoResponse.setId(2L);
        Long id = userInfoResponse.getId();
        assertEquals(2L,id);
    }

    @Test
    public void testGetAndSetEmail(){
        userInfoResponse.setEmail("other.email@email.com");
        String email = userInfoResponse.getEmail();
        assertEquals("other.email@email.com",email);
    }

    @Test
    public void testGetAndSetUsername(){
        userInfoResponse.setUsername("Other Username");
        String username = userInfoResponse.getUsername();
        assertEquals("Other Username",username);
    }

    @Test
    public void testGetRoles(){
        List<String> getRoles = userInfoResponse.getRoles();
        assertEquals(roles,getRoles);
    }


}
