import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;

public class Test {
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String str = "hello";
        byte[] data = str.getBytes("UTF8");
        String bufToStr = new String(data);

        System.out.println(bufToStr);
        


    }
}
