public class Main {

    public static void main(String[] args) {

//        byte a = 0x27;
//        byte b = 0x70;//should be c9
//        byte answer = FiniteFieldArithmetic.ffMultiply(a, b);
//
//        System.out.println(Integer.toBinaryString(answer));

        int keySize = 128;
        AesExecutor aesExecutor = new AesExecutor(keySize);
        aesExecutor.applyCipher();
        aesExecutor.applyInverseCipher();

    }
}
