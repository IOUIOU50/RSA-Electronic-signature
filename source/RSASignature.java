import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.*;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.net.ssl.HttpsURLConnection;

public class RSASignature {
    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {
        /**
         * 1. publicKey / privateKey 쌍을 생성해주는 객체 2. Strongly randome number 생성 3. 키 쌍 생성
         */
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA"); // RSA 암호화 알고리즘으로 키 쌍 생성
        SecureRandom random = new SecureRandom(); // 난수생성
        kpg.initialize(1024, random); // 초기화
        KeyPair keyPair = kpg.genKeyPair(); // 공개키 생성
        PublicKey publicKey = keyPair.getPublic(); // 비밀키 생성
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] pubk = publicKey.getEncoded();
        byte[] prik = privateKey.getEncoded();

        System.out.println("\n\nRSA key generation ");
        System.out.print("\nPublic Key : ");
        for (byte b : pubk)
            System.out.printf("%02X ", b);
        System.out.println("\nPublic Key Length : " + pubk.length + " byte");
        System.out.print("\nPrivate Key : ");
        for (byte b : prik)
            System.out.printf("%02X ", b);
        System.out.println("\nPrivate Key Length : " + prik.length + " byte");

        String sigData = "전자서명 테스트";
        byte[] data = sigData.getBytes("UTF8");
        System.out.print("\nPlaintext : " + sigData + "\n");

        // -----------------------------------------
        System.out.println("\n\nMD5WithRSA");
        // 전자서명
        Signature sig = Signature.getInstance("MD5WithRSA");
        sig.initSign(keyPair.getPrivate());
        sig.update(data);
        byte[] signatureBytes = sig.sign();
        System.out.print("\nSingature: ");
        for (byte b : signatureBytes)
            System.out.printf("%02X ", b);
        System.out.print("\ndata: ");
        for (byte b : data)
            System.out.printf("%02X ", b);
        System.out.print("\nSingature length: " + signatureBytes.length * 8 + " bits");

        //전자서명 검증
        sig.initVerify(keyPair.getPublic());
        sig.update(data); // 암호화 이전 text????
        System.out.print("\ndata: ");
        for (byte b : data)
            System.out.printf("%02X ", b);
        System.out.print("\nVerification: ");
        System.out.print(sig.verify(signatureBytes));

        // -----------------------------------------
        System.out.println("\n\nSHA1WithRSA");
        Signature sig1 = Signature.getInstance("SHA1WithRSA");
        sig1.initSign(keyPair.getPrivate());
        sig1.update(data);
        byte[] signatureBytes1 = sig1.sign();
        System.out.print("\nSingature: ");
        for (byte b : signatureBytes1)
            System.out.printf("%02X ", b);
        System.out.print("\nSingature length: " + signatureBytes1.length * 8 + " bits");

        sig1.initVerify(keyPair.getPublic());
        sig1.update(data);
        System.out.print("\nVerification: ");
        System.out.print(sig1.verify(signatureBytes1));

        // Cipher cipher = Cipher.getInstance("RSA");
        // cipher.init(Cipher.DECRYPT_MODE, publicKey);

        // byte[] byteEncryptedData = Base64.getDecoder().decode(signatureBytes1);
        // String strDecryptedData = new String(cipher.doFinal(byteEncryptedData));

        // System.out.println();
        // System.out.println();
        // System.out.println();
        // System.out.println("decryption : " + strDecryptedData);
        // System.out.println();
        // System.out.println();

        // -----------------------------------------
        System.out.println("\n\nSHA512WithRSA");
        Signature sig2 = Signature.getInstance("SHA512WithRSA");
        sig2.initSign(keyPair.getPrivate());
        sig2.update(data);
        byte[] signatureBytes2 = sig2.sign();
        System.out.print("\nSingature: ");
        for (byte b : signatureBytes2)
            System.out.printf("%02X ", b);
        System.out.print("\nSingature length: " + signatureBytes2.length * 8 + " bits");

        sig2.initVerify(keyPair.getPublic());
        sig2.update(data);
        System.out.print("\nVerification: ");
        System.out.print(sig2.verify(signatureBytes2));

        // -----------------------------------------
        KeyPairGenerator kpg1 = KeyPairGenerator.getInstance("EC");
        kpg1.initialize(160, random);
        KeyPair keyPair1 = kpg1.genKeyPair();
        PublicKey publicKey1 = keyPair1.getPublic();
        PrivateKey privateKey1 = keyPair1.getPrivate();
        byte[] pubk1 = publicKey1.getEncoded();
        byte[] prik1 = privateKey1.getEncoded();

        System.out.println("\n\nEC key generation ");
        System.out.print("\nPublic Key : ");
        for (byte b : pubk1)
            System.out.printf("%02X ", b);
        System.out.println("\nPublic Key Length : " + pubk1.length + " byte");
        System.out.print("\nPrivate Key : ");
        for (byte b : prik1)
            System.out.printf("%02X ", b);
        System.out.println("\nPrivate Key Length : " + prik1.length + " byte");

        System.out.println("\n\nSHA1withECDSA");
        Signature sig3 = Signature.getInstance("SHA1withECDSA");
        sig3.initSign(keyPair1.getPrivate());
        sig3.update(data);
        byte[] signatureBytes3 = sig3.sign();
        System.out.print("\nSingature: ");
        for (byte b : signatureBytes3)
            System.out.printf("%02X ", b);
        System.out.print("\nSingature length: " + signatureBytes3.length * 8 + " bits");

        sig3.initVerify(keyPair1.getPublic());
        sig3.update(data);
        System.out.print("\nVerification: ");
        System.out.print(sig3.verify(signatureBytes3));

    }

    // HTTP POST request
    private void sendPost(String targetUrl, String parameters) throws Exception {

        URL url = new URL(targetUrl);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

        con.setRequestMethod("POST"); // HTTP POST 메소드 설정
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setDoOutput(true); // POST 파라미터 전달을 위한 설정

        // Send post request
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(parameters);
        wr.flush();
        wr.close();

        // int responseCode = con.getResponseCode();
        // BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        // String inputLine;
        // StringBuffer response = new StringBuffer();

        // while ((inputLine = in.readLine()) != null) {
        //     response.append(inputLine);
        // }
        // in.close();

        // // print result
        // System.out.println("HTTP 응답 코드 : " + responseCode);
        // System.out.println("HTTP body : " + response.toString());

    }

}