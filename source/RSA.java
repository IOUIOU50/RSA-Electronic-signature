import javax.crypto.Cipher;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;

public class RSA {
    static final int KEY_SIZE = 2048;
    static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjNw0K5CD6vrhJfZouF8e+vBE6/oYLa43ntnl1Sz/jI2DaaggWem2KNnHoyu2Y+qn2ncBdWvp3eX2cTVRreZo9kJPtNQLQWDTrM6eum61fsBv9O5aKMarpaBisqk+zazeSZojJFkgpF20382ZQN4NkXlxopt04f8M7XnSMzHEUhlc6MBffYsoJALxv34E71/L3mJbcDGbHr3QdW/pzrOr8DEQ9z+UmBAPxCH1wKkKfSal5lcJAM8vtBLgXjReEQH8Tp6nwDWEZYgKoyQK0hD+bclE8UUeE0IeNITNaL3774mMruHF7f+7nIH0iaMBvLwR4CPo7YHG9FLPQV9ehyb7hQIDAQAB";
    static String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCM3DQrkIPq+uEl9mi4Xx768ETr+hgtrjee2eXVLP+MjYNpqCBZ6bYo2cejK7Zj6qfadwF1a+nd5fZxNVGt5mj2Qk+01AtBYNOszp66brV+wG/07looxquloGKyqT7NrN5JmiMkWSCkXbTfzZlA3g2ReXGim3Th/wztedIzMcRSGVzowF99iygkAvG/fgTvX8veYltwMZsevdB1b+nOs6vwMRD3P5SYEA/EIfXAqQp9JqXmVwkAzy+0EuBeNF4RAfxOnqfANYRliAqjJArSEP5tyUTxRR4TQh40hM1ovfvviYyu4cXt/7ucgfSJowG8vBHgI+jtgcb0Us9BX16HJvuFAgMBAAECggEAcZBTS1Au2vK7NKUN1p9x1bSCjJHMjn+n/h0EzDKEMWgiK7vNoU/oedTivC3Q1YtpGpvZhDaljS4K20i2enKJ6wiZRFu0W1haMDdBB2OcVf2GBb1o2Pkbh+PfztTeemnFqSdo77QfugSGf+09gmvEGqYDtKqKuG2tmYQesQGNmvX/V/xJHPmiZPupKnQyiFAZ9A4eTQrCdTZZoOX9DTrtHaDuUsxaMxzFVV8VYDMlDIYZisHvqRhh5AHGtyxPdaG+VkiMUsP73aeFElHZ7RvMpoOGx7pHP3+cUkAY+DGU8WHjkVcB2OoAO0CiSGJvmcIiS/5aUcyAaCZnMOsRXQAXYQKBgQDF/Rsdwif/PqF2JT2r1ecJExhvEBf4pwyfyq5XgVWpNF76oqsEtcznxwNlkP6z8nN4lHg6wNJhWhGlAkbETjnRDGD96MVmIFeyv40NrGOP1xpl+lvXxXQglOuJJyHxdbSaUOMAUPRQmEaAVbIejyMvo3F6rb//nIqqmpUNQ09WyQKBgQC2IfS4eWOFdpt0HwUD4HJg7qXbcOcRoOpEGrQrk1hPqUwywMSU7OIGR+CJoSFIpww9ONpSagX324o2rdHYSrg4FqK4KYCqf6SaQgGlwGvYXXYzjIDKVqrA1TAuL3x5eOSHXzfdlR5nvXI15Rudx+ydxn437NgDBYSYm4T1aBiQ3QKBgFuA9BRX1IZ6GsNwyMTvMj0Wbzd9V6bbBdGgKd1VcBKomrD5l1Kw90ezydUaIy1lg4qaC8sTrOfDad46ZBx8DuqX7wfKmdjuX7nEH9vO7aDgCFpTUDk0D8yzJvyXcm+XN+YONivCPng1OHg9ovnOq7NrtZm+TbtETS42DWc40aVJAoGAJJpwZz6mHKV4mn69cEkEU6zCknZ/SdCwJB3Fxdgn8GKS2MRa/Z8Z0a9SVfaXDNXdpNQvNE7wgh6SCWknejOSEFukOiTFIf7jIslPMXOZePNXlQaQv5lpWbW+qBmNB5CfRQGC5bpZUPoG4QP5Z91NFWxV44Fq7DiIrG7c6sEP5tUCgYEApjG+ROoTq/jCqC1o6rTX5Izr2U9gqTNpBXI6GTLIneM09/sZEgWotVhbnH4YKXnIapSWQu7zM/Kt9IXRlK9vK1T6qDV2iZp2PvoLgnov0tbE3dOVRfRBLrX9pg7luwjWfi5xsQle0yts05NX+FWYjKiTKYJRQcTKaFcE62DjC6k=";

    public static void main(String[] args) {

        System.out.println("???????????? ?????????:" + publicKey);
        System.out.println("???????????? ?????????:" + privateKey);

        String plainText = "????????? ?????????";
        System.out.println("??????: " + plainText);
        
        String encryptedText = encode(plainText, publicKey);        // ???????????? ?????????
        System.out.println("?????????: " + encryptedText);

        String decryptedText = decode(encryptedText, privateKey);   // ???????????? ?????????
        System.out.println("?????????: " + decryptedText);

        String signText = sign(plainText, privateKey);
        System.out.println("??????: " + signText);

        boolean result = verifySignarue(plainText, signText, publicKey);
        System.out.println("??????: " + result);
    }

    /**
     * ?????????
     */
    static String encode(String plainData, String stringPublicKey) {
        String encryptedData = null;
        try {
            // ???????????? ???????????? ???????????? ?????????????????? ????????? ??????
            PublicKey publicKey = getPublicKey(stringPublicKey);
            // ???????????? ?????????????????? ???????????? ?????????????????? ???????????? ??????
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // ????????? ??????????????? ??????
            byte[] byteEncryptedData = cipher.doFinal(plainData.getBytes());
            encryptedData = Base64.getEncoder().encodeToString(byteEncryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedData;
    }

    /**
     * ?????????
     */
    static String decode(String encryptedData, String stringPrivateKey) {
        String decryptedData = null;
        try {
            // ???????????? ???????????? ???????????? ?????????????????? ????????? ??????
            PrivateKey privateKey = getPrivateKey(stringPrivateKey);
            // ???????????? ?????????????????? ???????????? ?????????????????? ???????????? ??????
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            // ???????????? ??????????????? ??????
            byte[] byteEncryptedData = Base64.getDecoder().decode(encryptedData.getBytes());
            byte[] byteDecryptedData = cipher.doFinal(byteEncryptedData);
            decryptedData = new String(byteDecryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedData;
    }

    static PublicKey getPublicKey(String stringPublicKey) {
        PublicKey publicKey = null;
        try {
            // ???????????? ???????????? ???????????? ?????????????????? ????????? ??????
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] bytePublicKey = Base64.getDecoder().decode(stringPublicKey.getBytes());
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bytePublicKey);
            publicKey = keyFactory.generatePublic(publicKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    static PrivateKey getPrivateKey(String stringPrivateKey) {
        PrivateKey privateKey = null;
        try {
            // ???????????? ???????????? ???????????? ?????????????????? ????????? ??????
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] bytePrivateKey = Base64.getDecoder().decode(stringPrivateKey.getBytes());
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(bytePrivateKey);
            privateKey = keyFactory.generatePrivate(privateKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    public static String sign(String plainText, String strPrivateKey) {
        try {
            PrivateKey privateKey = getPrivateKey(strPrivateKey);
            Signature privateSignature = Signature.getInstance("SHA256withRSA");
            privateSignature.initSign(privateKey);
            privateSignature.update(plainText.getBytes("UTF-8"));
            byte[] signature = privateSignature.sign();
            return Base64.getEncoder().encodeToString(signature);
        } catch (NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verifySignarue(String plainText, String signature, String strPublicKey) {
        Signature sig;
        try {
            PublicKey publicKey = getPublicKey(strPublicKey);
            sig = Signature.getInstance("SHA256withRSA");
            sig.initVerify(publicKey);
            sig.update(plainText.getBytes());
            if (!sig.verify(Base64.getDecoder().decode(signature)))
                ;
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
