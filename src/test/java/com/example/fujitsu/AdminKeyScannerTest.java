package com.example.fujitsu;

import com.example.fujitsu.rental.utils.AdminKeyScanner;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AdminKeyScannerTest {

    @Test
    public void readTxtFile() {
        assertTrue(AdminKeyScanner.validateKey("55364bacd7df15aa3c264d"));
        assertFalse(AdminKeyScanner.validateKey("wrong_admin_key"));
        assertDoesNotThrow(()->{
            AdminKeyScanner.validateKey("55364bacd7df15aa3c264d");
        });
    }
}
