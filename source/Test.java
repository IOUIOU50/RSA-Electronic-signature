import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        byte[] byteArray = { 0x48, 0x65, (byte) 0x6C, (byte) 0x6C, (byte) 0x6f, 0x20, 0x57, (byte) 0x6f, 0x72,
                (byte) 0x6c, 0x64 };

        System.out.println(Arrays.toString(byteArray));
        String strHello = new String(byteArray);

        System.out.println(strHello);

    }
}
