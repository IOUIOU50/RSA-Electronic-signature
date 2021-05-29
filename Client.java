import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
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

        /**
         * publicKey/PrivateKey : 객체 타입의 RSA키
         * bufPublicKey/bufPrivateKey : byte[] 타입의 RSA 키
         * strPublicKey/strPrivateKey : String 타입의 RSA 키
         * 
         * buf -> String : Base64.getEncoder().encodeToString(bufKey: byte[])
         * String -> buf : Base64.getDecoder().decode(strKey: String)
         */
        PublicKey   publicKey = keyPair.getPublic(); // 공개 키 가져오기
        byte[]      bufPublicKey = publicKey.getEncoded(); // PrivateKey객체 -> byte[]형태로 비밀 키 변환
        String      strPublicKey = Base64.getEncoder().encodeToString(bufPublicKey);
        System.out.println(strPublicKey);

        PrivateKey  privateKey = keyPair.getPrivate(); // 비밀 키 가져오기
        byte[]      bufPrivateKey = privateKey.getEncoded(); // PublicKey객체 -> byte[]형태로 공개 키 변환
        String      strPrivateKey = Base64.getEncoder().encodeToString(bufPrivateKey);
        

        // privateKey로 전자서명
        String plainText = "plainText";
        byte[] sig = encode(plainText, privateKey);
        String strSig = Base64.getEncoder().encodeToString(sig);

        // POST에 body에 담길 params 생성. HTTP RequestBody에 담길 예정이므로 String 이용
        Map<String, String> postRequestParams = new HashMap<>();
        postRequestParams.put("plainText", plainText);
        postRequestParams.put("signature", strSig);
        postRequestParams.put("publicKey", strPublicKey);

        // request POST
        postRequest("http://127.0.0.1:3000/", postRequestParams);
    }

    // HTTP POST request
    private static void postRequest(String targetUrl, Map<String, String> params) throws Exception {
        StringBuilder postData = new StringBuilder();
        for (Entry<String, String> param : params.entrySet()) {
            if (postData.length() != 0)
            postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        
        URL url = new URL(targetUrl); // URL 설정
        try {
            // URL 설정 하고 접속하기

            HttpURLConnection http = (HttpURLConnection) url.openConnection(); // 접속
            // System.out.println("http : " + http);
            // --------------------------
            // 전송 모드 설정 - 기본적인 설정
            // --------------------------
            http.setRequestMethod("POST"); // 전송 방식은 POST
            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            http.setRequestProperty("Content-Length", String.valueOf(postData.length()));
            http.setRequestProperty("charset", "utf-8");
            http.setUseCaches( false );
            http.setDoOutput(true); // 서버로 쓰기 모드 지정

            // --------------------------
            // 헤더 세팅
            // --------------------------
            // 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다

            // --------------------------
            // 서버로 값 전송
            // --------------------------
            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
            PrintWriter writer = new PrintWriter(outStream);
            writer.write(postData.toString());
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

}
