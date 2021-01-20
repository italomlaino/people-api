package com.italomlaino.peopleapi;

import com.italomlaino.peopleapi.Application;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationTest {

    @Test
    void contextLoads() {
    }

    @Test
    public void contextTest() {
        Application.main(new String[]{});
    }
}
