import java.security.*;
import java.util.Arrays;
import java.util.Base64;

public class Test {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println("***************************************************");
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA"); // RSA 암호화 알고리즘으로 키 쌍 생성
        SecureRandom random = new SecureRandom(); // 난수생성
        kpg.initialize(1024, random); // 초기화
        KeyPair keyPair = kpg.genKeyPair(); // 생성한 공개키/비밀키 쌍 가져오기
        PublicKey publicKey = keyPair.getPublic(); // 공개 키 가져오기
        PrivateKey privateKey = keyPair.getPrivate(); // 비밀 키 가져오기
        byte[] bufPublicKey = publicKey.getEncoded(); // PrivateKey객체 -> byte[]형태로 비밀 키 변환
        byte[] bufPrivateKey = privateKey.getEncoded(); // PublicKey객체 -> byte[]형태로 공개 키 변환

        System.out.print("변조 전 byte array\n");
        for (byte b : bufPublicKey)
            System.out.printf("%02X ", b);

        String bufToString = Base64.getEncoder().encodeToString(publicKey.getEncoded());

        // byte[] stringToBuf = bufToString.getBytes();
        byte[] stringToBuf = Base64.getDecoder().decode(bufToString);

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.print("변조 후 byte array\n");
        for (byte b : stringToBuf)
            System.out.printf("%02X ", b);
        System.out.println();

        if (Arrays.equals(bufPublicKey, stringToBuf))
            System.out.println("변조 없음");
        else
            System.out.println("변조 생김");

    }
}
