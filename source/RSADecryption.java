import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.NoSuchPaddingException;

public class RSADecryption {
    private static PublicKey publicKey;
    private static Signature sig;
    // private Cipher cipher;

    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, SignatureException {
        String strPublicKey = args[0];
        String strPlainText = args[1];
        String strSignature = args[2];

        init(strPublicKey);

        System.out.println(verify(strPlainText, strSignature));
        
    }

    public static void init(String strPublicKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        byte[] bufPublicKey = Base64.getDecoder().decode(strPublicKey);
        publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bufPublicKey));
        sig = Signature.getInstance("SHA1WithRSA");

        // this.cipher = Cipher.getInstance("RSA");
        // cipher.init(Cipher.DECRYPT_MODE, this.publicKey);

    }

    // public String decrypt(String data) throws IllegalBlockSizeException,
    // BadPaddingException{
    // byte[] byteEncryptedData = Base64.getDecoder().decode(data.getBytes());
    // return new String(cipher.doFinal(byteEncryptedData));
    // }

    // public boolean verify(String data) throws InvalidKeyException,
    // SignatureException{
    // byte[] byteEncryptedData = Base64.getDecoder().decode(data.getBytes());
    // this.sig.initVerify(this.publicKey);
    // this.sig.update(byteEncryptedData);

    // return sig.verify(byteEncryptedData);
    // }

    public static boolean verify(String plainText, String strSignature) throws InvalidKeyException, SignatureException {
        byte[] bufPlaninText = plainText.getBytes();
        byte[] bufSignature = Base64.getDecoder().decode(strSignature);

        sig.initVerify(publicKey);
        sig.update(bufPlaninText);

        return sig.verify(bufSignature);
    }

}
