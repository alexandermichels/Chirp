package com.westminster.caitlynpeace.chirp;

import java.security.Key;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class StringUtil
{
    public static byte [] applyECDSASig(PrivateKey key, String input)
    {
        Signature signature;
        byte [] output = new byte[0];

        try
        {
            signature = Signature.getInstance("ECDSA", "BC");
            signature.initSign(key);
            byte [] stringBytes = input.getBytes();
            signature.update(stringBytes);
            output = signature.sign();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return output;
    }

    public static String applySha256(String input)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++)
            {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static String decryptData(PrivateKey key, String data)
    {
        try
        {
            SecretKeySpec secretKey = new SecretKeySpec(key.toString().getBytes(), "AES/CBC/PKCS5Padding");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(data.getBytes()), "UTF-8");
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static String encryptData(PrivateKey key, String data)
    {
        try
        {
            SecretKeySpec secretKey = new SecretKeySpec(key.toString().getBytes(),"AES/CBC/PKCS5Padding");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return new String(cipher.doFinal(data.getBytes()), "UTF-8");
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }


    public static boolean verifyECDSASig(PublicKey publicKey, String data, byte [] signature)
    {
        try
        {
            Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
            ecdsaVerify.initVerify(publicKey);
            ecdsaVerify.update(data.getBytes());
            return ecdsaVerify.verify(signature);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
