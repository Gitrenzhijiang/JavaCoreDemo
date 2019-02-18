package com.test.unit9;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Digest {
    public static void main(String[] args) {
        String algname = args.length >= 2 ? args[1] : "SHA-1";
        try {
            MessageDigest md = MessageDigest.getInstance(algname);
            byte[] input = Files.readAllBytes(Paths.get("nums.txt"));
            byte[] hash = md.digest(input);
            String d = "";
            for (int i = 0;i < hash.length;i++)
            {
                int v = hash[i] & 0xFF;
                if (v < 16) d+= "0";
                d += Integer.toString(v, 16).toUpperCase() + " ";
            }
            System.out.println(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
