package com.example.cameraapp;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ImageHasher {

    protected static String getHashForImage(File imageFile) throws IOException {
        byte bytes[] = new byte[(int) imageFile.length()];
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(imageFile));
        DataInputStream dis = new DataInputStream(bis);
        dis.readFully(bytes);

        return encrypt(bytes);
    }

    public static String encrypt(byte[] image) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(image);

        byte[] encoded_bytes = md.digest();

        String hash = bytesToHex(encoded_bytes);

        return hash;
    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
