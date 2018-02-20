package com.mobile.androidtotpauth;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Kartik Kumar Gujarati on 2/20/18 1:18 PM
 * Copyright (c) 2018. All rights reserved.
 */

public class OTPUtils {


    private static final String HMAC_ALGO = "HMACSHA1";

    /** Powers of 10 used to shorten the pin to the desired number of digits */
    private static final int[] DIGITS_POWER
            // 0 1  2   3    4     5      6       7        8         9
            = {1,10,100,1000,10000,100000,1000000,10000000,100000000,1000000000};
    private static final int PASS_CODE_LENGTH = 6;


    public static String getOTPPin(String secret) {
        byte[] secretBytes = secret.getBytes();
        long time = (System.currentTimeMillis() / 1000 / 30);
        byte[] timeBytes = ByteBuffer.allocate(8).putLong(time).array();
        SecretKeySpec signKey = new SecretKeySpec(secretBytes, HMAC_ALGO);
        try {
            Mac mac = Mac.getInstance(HMAC_ALGO);
            mac.init(signKey);
            byte[] hash = mac.doFinal(timeBytes);
            int offset = hash[hash.length - 1] & 0xF;
            // Grab a positive integer value starting at the given offset.
            int truncatedHash = hashToInt(hash, offset) & 0x7FFFFFFF;
            int pinValue = truncatedHash % DIGITS_POWER[PASS_CODE_LENGTH];

            return padOutput(pinValue);

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String padOutput(int value) {
        String result = Integer.toString(value);
        for (int i = result.length(); i < PASS_CODE_LENGTH; i++) {
            result = "0" + result;
        }
        return result;
    }

    private static int hashToInt(byte[] bytes, int start) {
        DataInput input = new DataInputStream(
                new ByteArrayInputStream(bytes, start, bytes.length - start));
        int val;
        try {
            val = input.readInt();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return val;
    }

}
