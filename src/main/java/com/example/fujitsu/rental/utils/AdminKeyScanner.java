package com.example.fujitsu.rental.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Scanner;

public class AdminKeyScanner {
    public static boolean validateKey(String key){
        try {
            Resource resource = new ClassPathResource("admin-keys.txt");
            Scanner myReader = new Scanner(resource.getFile());
            while (myReader.hasNextLine()) {
                if (myReader.nextLine().equals(key)){
                    return true;
                }
            }
            myReader.close();
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
