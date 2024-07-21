package com.javaworld.instagram.userinfoservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
//@ActiveProfiles("test")
public class SampleTest {

    @Test
    public void testCreateUser_Success() {
    	String name = "BatMan";
        assertEquals("BatMan", name);
    }
}
