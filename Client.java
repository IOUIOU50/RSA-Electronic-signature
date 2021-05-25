import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.security.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.net.*;

public class Client {

    public static void main(String[] args) throws Exception {
        /**
         * 1. publicKey / privateKey 쌍을 생성해주는 객체 2. Strongly randome number 생성 3. 키 쌍 생성
         */
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA"); // RSA 암호화 알고리즘으로 키 쌍 생성
        SecureRandom random = new SecureRandom(); // 난수생성
        kpg.initialize(1024, random); // 초기화
        KeyPair keyPair = kpg.genKeyPair(); // 생성한 공개키/비밀키 쌍 가져오기
        PublicKey publicKey = keyPair.getPublic(); // 공개 키 가져오기
        PrivateKey privateKey = keyPair.getPrivate(); // 비밀 키 가져오기
        byte[] bufPublicKey = publicKey.getEncoded(); // PrivateKey객체 -> byte[]형태로 비밀 키 변환
        byte[] bufPrivateKey = privateKey.getEncoded(); // PublicKey객체 -> byte[]형태로 공개 키 변환

        // 평문 -> 암호문 변환
        byte[] encodedBuf = encode("plain text", privateKey);

        System.out.print("평문 -> 암호문 : ");
        for (byte b : encodedBuf)
            System.out.printf("%02X ", b);

        System.out.println();
        System.out.println();
        System.out.println();

        // 암호문 -> 평문 변환
        boolean isVerified = decode(encodedBuf, publicKey);
        System.out.println("암호문 -> 평문 : " + isVerified);

        // POST에 body에 담길 params 생성
        String strPublicKey = new String(publicKey.getEncoded());
        // System.out.println("strPublicKey : " + strPublicKey);
        // System.out.println("strEncodedKey : " + strEncodedText);
        Map<String, byte[]> postRequestParams = new HashMap<>();
        postRequestParams.put("encodedText", encodedBuf);
        postRequestParams.put("publicKey", bufPublicKey);

        // for (byte b : publicKey.getEncoded())
        // System.out.printf("%02X ", b);
        // System.out.println();
        // System.out.println();
        // System.out.println();

        // for (byte b : strPublicKey.getBytes())
        // System.out.printf("%02X ", b);
        // System.out.println();

        // POST요청
        postRequest("http://localhost:3000/", postRequestParams);
    }

    // HTTP POST request
    private static void postRequest(String targetUrl, Map<String, byte[]> params) throws Exception {
        URL url = new URL(targetUrl); // URL 설정
        StringBuilder postData = new StringBuilder();
        for (Entry<String, byte[]> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        try {
            // URL 설정 하고 접속하기

            HttpURLConnection http = (HttpURLConnection) url.openConnection(); // 접속
            // System.out.println("http : " + http);
            // --------------------------
            // 전송 모드 설정 - 기본적인 설정
            // --------------------------
            http.setRequestMethod("POST"); // 전송 방식은 POST
            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            http.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            http.setDoOutput(true); // 서버로 쓰기 모드 지정

            // --------------------------
            // 헤더 세팅
            // --------------------------
            // 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다

            // --------------------------
            // 서버로 값 전송
            // --------------------------
            // System.out.println("buffer : " + buffer.toString());
            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
            PrintWriter writer = new PrintWriter(outStream);
            writer.write(postDataBytes.toString());
            writer.flush();

            int code = http.getResponseCode();
            System.out.println("응답코드 : " + code);
            System.out.println("request");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    private static byte[] encode(String str, PrivateKey privateKey)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        byte[] strToBuf = str.getBytes();
        Signature sig = Signature.getInstance("SHA1WithRSA");
        sig.initSign(privateKey);
        sig.update(strToBuf);

        return sig.sign();
    }

    private static boolean decode(byte[] buf, PublicKey publicKey)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature sig = Signature.getInstance("SHA1WithRSA");
        sig.initVerify(publicKey);
        sig.update(buf);

        return sig.verify(buf);
    }
}
