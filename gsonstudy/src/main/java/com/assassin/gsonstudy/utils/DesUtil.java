package com.assassin.gsonstudy.utils;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/9 15:28
 * @Version:
 * @Description:
 */

public class DesUtil
{

    
    private static String Algorithm = "DESede";
    //后台和前台以此为解密算法
    private static final String Default_Key = "T@ngL@ng";

    public static String encryptString(String value) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        return encryptString(Default_Key, value);
    }


    public static String encryptString(String key, String value) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
        byte[] bytesKey = null, bytes = null, bytesCipher = null;
        SecretKey deskey = null;
        if (value == null)
            new IllegalArgumentException("待加密字串不允许为空");
        //密码器
        Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        try{
//            bytesKey = Base64.decode(key.getBytes(),Base64.DEFAULT);
            bytesKey = Default_Key.getBytes("UTF-8");

            Log.i("Main","key解密后:"+new String(bytesKey,"UTF-8"));
            deskey = new SecretKeySpec(bytesKey, Algorithm);
            bytes = value.getBytes();//待解密的字串
            desCipher.init(Cipher.ENCRYPT_MODE, deskey);//初始化密码器，用密钥deskey,进入解密模式
            bytesCipher = desCipher.doFinal(bytes);
            return Base64.encodeToString(bytesCipher,Base64.DEFAULT).trim();
        }
        finally{
            bytesKey = null;
            bytes = null;
            bytesCipher = null;
        }
    }

    public static String decryptString(String value) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
        return decryptString(Default_Key, value);
    }

    public static String decryptString(String key, String value) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException
    {
        byte[] bytesKey = null, bytes = null, bytesCipher = null;
        SecretKey deskey = null;
       // if (value == null)
           // new IllegalArgum
        //ddsfisoio
        return null;
    }

}

  