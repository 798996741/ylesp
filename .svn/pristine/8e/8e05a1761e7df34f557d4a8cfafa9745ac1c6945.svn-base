package com.yulun.utils;
import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * DES加密 解密算法
 * @author zhangdi
 *
 */
public class DesUtil {

    private final static String DES = "DES";
    private final static String ENCODE = "GBK";

    /**
     * smarkpark15606063662 的md5
     */

    public final static String defaultKey = "795caaca35536eba43bff4fba1285316";

    /**
     * 使用 默认key 加密
     * @param data 待加密数据
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        byte[] bt = encrypt(data.getBytes(ENCODE), defaultKey.getBytes(ENCODE));
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }

    /**
     * 使用 默认key 解密
     * @param data 待解密数据
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data) throws IOException, Exception {
        if (data == null) {
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf, defaultKey.getBytes(ENCODE));
        return new String(bt, ENCODE);
    }

    /**
     * Description 根据键值进行加密
     * @param data 待加密数据
     * @param key 密钥
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes(ENCODE), key.getBytes(ENCODE));
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }

    /**
     * 根据键值进行解密
     * @param data 待解密数据
     * @param key    密钥
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws IOException,
            Exception {
        if (data == null) {
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf, key.getBytes(ENCODE));
        return new String(bt, ENCODE);
    }

    /**
     * Description 根据键值进行加密
     * 
     * @param data
     * @param key
     *            加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }

    /**
     * Description 根据键值进行解密
     * 
     * @param data
     * @param key 加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }
    
    public static void main(String[] args){
        //String data = "{\"data\":{\"ucode\":\"admin\",\"dialphone\":\"15606063662\",\"upwd\":\"123456\"},\"cmd\":\"DIAL\"}";
        //String data ="{\"cmd\":\"LOGIN\",\"data\":{\"gid\":\"3829322B-ABB5-E089-5C19-B79615AE2DC5\",\"devid\":\"358240051111110\",\"upwd\":\"123456\",\"ucode\":\"admin\"}}";
        //String data="{cmd:'SYNC',data:{devid:'358240051111110',gid:'04eb6c65-0a75-4701-9ec9-ef262a3dce23',uid:'1'}}";
        String data="{\"api\":\"org.yulun.constellation\",\"data\":{\"openid\":\"99acb548fccb473ebc4e3f02879cfd02\",\"pageIndex\":\"1\",\"pageSize\":\"10\"},\"cmd\":\"home\",\"mac\":\"a4ec9df9818ed4f93f0bf2dca4bd73c0\",\"seq\":\"222\"}";
        String key =DesUtil.defaultKey;
        System.out.println("加密前===>"+data);
        try {
           System.out.println(encrypt(data, key));
            System.out.println(decrypt(encrypt(data, key), key));
            String jiamihou = encrypt(data);
            String jiami="HU+yWfV5ZrISYg2cLkdWYD8nkILsI1wrUcAQdVnHHrsrBE09VC7Psg==";
            System.out.println("加密后===>"+jiamihou);
            System.out.println("解密后===>"+decrypt(jiami));
       } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}