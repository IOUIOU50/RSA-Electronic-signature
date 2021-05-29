import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.NoSuchPaddingException;

public class RSAMain {
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException,
            InvalidKeyException, NoSuchPaddingException, InvalidKeySpecException, SignatureException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA"); // RSA 암호화 알고리즘으로 키 쌍 생성
        SecureRandom random = new SecureRandom(); // 난수생성
        kpg.initialize(1024, random); // 초기화
        KeyPair keyPair = kpg.genKeyPair(); // 공개키 생성
        PublicKey publicKey = keyPair.getPublic(); // 비밀키 생성
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] pubk = publicKey.getEncoded();
        byte[] prik = privateKey.getEncoded();

        String strPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String strPrivateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        String sigData = "전자서명 테스트";
        byte[] data = sigData.getBytes("UTF8");

        Signature sig1 = Signature.getInstance("SHA1WithRSA");
        sig1.initSign(keyPair.getPrivate());
        sig1.update(data);
        byte[] signatureBytes1 = sig1.sign();

        RSADecryption rsa = new RSADecryption(pubk);

        boolean isVerified = rsa.verify(new String(data), Base64.getEncoder().encodeToString(signatureBytes1));

        System.out.println(isVerified);
    }
}
