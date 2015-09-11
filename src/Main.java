public class Main {

    public static void main(String[] args) {

//        byte a = 0x27;
//        byte b = 0x70;//should be c9
//        byte answer = FiniteFieldArithmetic.ffMultiply(a, b);
//
//        System.out.println(Integer.toBinaryString(answer));




        //---------------- test encryption for 128 bits -----------------

//        int keySize = 128;
//
//        byte[][] testState = {
//                {        0x00,        0x11,        0x22,        0x33 },
//                {        0x44,        0x55,        0x66,        0x77 },
//                { (byte) 0x88, (byte) 0x99, (byte) 0xaa, (byte) 0xbb },
//                { (byte) 0xcc, (byte) 0xdd, (byte) 0xee, (byte) 0xff }
//        };
//
//        byte[][] testKey = {
//                {        0x00,        0x01,         0x02,       0x03 },
//                {        0x04,        0x05,         0x06,       0x07 },
//                {        0x08,        0x09,         0x0a,       0x0b },
//                {        0x0c,        0x0d,         0x0e,       0x0f }
//
//        };


        //---------------- test encryption for 192 bits -----------------

//        int keySize = 192;
//
//        byte[][] testState = {
//                {        0x00,        0x11,        0x22,        0x33 },
//                {        0x44,        0x55,        0x66,        0x77 },
//                { (byte) 0x88, (byte) 0x99, (byte) 0xaa, (byte) 0xbb },
//                { (byte) 0xcc, (byte) 0xdd, (byte) 0xee, (byte) 0xff }
//        };
//
//        byte[][] testKey = {
//                {        0x00,        0x01,        0x02,        0x03 },
//                {        0x04,        0x05,        0x06,        0x07 },
//                {        0x08,        0x09,        0x0a,        0x0b },
//                {        0x0c,        0x0d,        0x0e,        0x0f },
//                {        0x10,        0x11,        0x12,        0x13 },
//                {        0x14,        0x15,        0x16,        0x17 }
//        };

        //---------------- test encryption for 256 bits -----------------

        int keySize = 256;

        byte[][] testState = {
                {        0x00,        0x11,        0x22,        0x33 },
                {        0x44,        0x55,        0x66,        0x77 },
                { (byte) 0x88, (byte) 0x99, (byte) 0xaa, (byte) 0xbb },
                { (byte) 0xcc, (byte) 0xdd, (byte) 0xee, (byte) 0xff }
        };

        byte[][] testKey = {
                {        0x00,        0x01,        0x02,        0x03 },
                {        0x04,        0x05,        0x06,        0x07 },
                {        0x08,        0x09,        0x0a,        0x0b },
                {        0x0c,        0x0d,        0x0e,        0x0f },
                {        0x10,        0x11,        0x12,        0x13 },
                {        0x14,        0x15,        0x16,        0x17 },
                {        0x18,        0x19,        0x1a,        0x1b },
                {        0x1c,        0x1d,        0x1e,        0x1f }
        };


        AesExecutor aesExecutor = new AesExecutor(keySize, testState, testKey);

        // Print key expansion and initial state
        System.out.println("Expanded Key with " + keySize + " bits:");
        System.out.println(aesExecutor.stringifyKey() + "\n");

        System.out.println("Initial State:");
        System.out.println(aesExecutor.stringifyState() + "\n");

        // Apply the cipher
        aesExecutor.applyCipher();

        // Print the encrypted state
        System.out.println("Encrypted State:");
        System.out.println(aesExecutor.stringifyState() + "\n");

        // Apply the inverse cipher
        aesExecutor.applyInverseCipher();

        // Print the decrypted state (should match initial)
        System.out.println("Decrypted State:");
        System.out.println(aesExecutor.stringifyState() + "\n");

    }
}
