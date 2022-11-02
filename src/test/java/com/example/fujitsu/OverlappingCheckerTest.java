package com.example.fujitsu;

import com.example.fujitsu.rental.utils.OverlappingChecker;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OverlappingCheckerTest {

    @Test
    public void checkIfOverlapTest(){
        assertTrue(OverlappingChecker.checkIfOverlap("01.01.2000", "5", "03.01.2000", "2"));
        assertFalse(OverlappingChecker.checkIfOverlap("01.01.2000", "1", "03.01.2000", "2"));
        assertTrue(OverlappingChecker.checkIfOverlap("03.01.2000", "5", "01.01.2000", "2"));
    }

}
