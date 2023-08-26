package xyz.msprpayetonkawa.apirevendeur.entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.msprpayetonkawa.apirevendeur.security.payload.response.MessageResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MessageResponseTest {

    private MessageResponse messageResponse;

    @Before
    public void setUp() {
        messageResponse = new MessageResponse("Hello");
    }


    @Test
    public void testGetAndSetMessageResponse(){
        messageResponse.setMessage("Hi");
        String message = messageResponse.getMessage();
        assertEquals("Hi",message);
    }
}
