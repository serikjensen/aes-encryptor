/**
 * Created by steve on 9/10/15.
 */
public class CipherOps {

    private static final byte[][] SBOX = {
            {        0x63,        0x7c,        0x77,        0x7b, (byte) 0xf2,        0x6b,        0x6f, (byte) 0xc5,        0x30,        0x01,        0x67,        0x2b, (byte) 0xfe, (byte) 0xd7, (byte) 0xab,        0x76 } ,
            { (byte) 0xca, (byte) 0x82, (byte) 0xc9,        0x7d, (byte) 0xfa,        0x59,        0x47, (byte) 0xf0, (byte) 0xad, (byte) 0xd4, (byte) 0xa2, (byte) 0xaf, (byte) 0x9c, (byte) 0xa4,        0x72, (byte) 0xc0 } ,
            { (byte) 0xb7, (byte) 0xfd, (byte) 0x93,        0x26,        0x36,        0x3f, (byte) 0xf7, (byte) 0xcc,        0x34, (byte) 0xa5, (byte) 0xe5, (byte) 0xf1,        0x71, (byte) 0xd8,        0x31,        0x15 } ,
            {        0x04, (byte) 0xc7,        0x23, (byte) 0xc3,        0x18, (byte) 0x96,        0x05, (byte) 0x9a,        0x07,        0x12, (byte) 0x80, (byte) 0xe2, (byte) 0xeb,        0x27, (byte) 0xb2,        0x75 } ,
            {        0x09, (byte) 0x83,        0x2c,        0x1a,        0x1b,        0x6e,        0x5a, (byte) 0xa0,        0x52,        0x3b, (byte) 0xd6, (byte) 0xb3,        0x29, (byte) 0xe3,        0x2f, (byte) 0x84 } ,
            {        0x53, (byte) 0xd1,        0x00, (byte) 0xed,        0x20, (byte) 0xfc, (byte) 0xb1,        0x5b,        0x6a, (byte) 0xcb, (byte) 0xbe,        0x39,        0x4a,        0x4c,        0x58, (byte) 0xcf } ,
            { (byte) 0xd0, (byte) 0xef, (byte) 0xaa, (byte) 0xfb,        0x43,        0x4d,        0x33, (byte) 0x85,        0x45, (byte) 0xf9,        0x02,        0x7f,        0x50,        0x3c, (byte) 0x9f, (byte) 0xa8 } ,
            {        0x51, (byte) 0xa3,        0x40, (byte) 0x8f, (byte) 0x92, (byte) 0x9d,        0x38, (byte) 0xf5, (byte) 0xbc, (byte) 0xb6, (byte) 0xda,        0x21,        0x10, (byte) 0xff, (byte) 0xf3, (byte) 0xd2 } ,
            { (byte) 0xcd,        0x0c,        0x13, (byte) 0xec,        0x5f, (byte) 0x97,        0x44,        0x17, (byte) 0xc4, (byte) 0xa7,        0x7e,        0x3d,        0x64,        0x5d,        0x19,        0x73 } ,
            {        0x60, (byte) 0x81,        0x4f, (byte) 0xdc,        0x22,        0x2a, (byte) 0x90, (byte) 0x88,        0x46, (byte) 0xee, (byte) 0xb8,        0x14, (byte) 0xde,        0x5e,        0x0b, (byte) 0xdb } ,
            { (byte) 0xe0,        0x32,        0x3a,        0x0a,        0x49,        0x06,        0x24,        0x5c, (byte) 0xc2, (byte) 0xd3, (byte) 0xac,        0x62, (byte) 0x91, (byte) 0x95, (byte) 0xe4,        0x79 } ,
            { (byte) 0xe7, (byte) 0xc8,        0x37,        0x6d, (byte) 0x8d, (byte) 0xd5,        0x4e, (byte) 0xa9,        0x6c,        0x56, (byte) 0xf4, (byte) 0xea,        0x65,        0x7a, (byte) 0xae,        0x08 } ,
            { (byte) 0xba,        0x78,        0x25,        0x2e,        0x1c, (byte) 0xa6, (byte) 0xb4, (byte) 0xc6, (byte) 0xe8, (byte) 0xdd,        0x74,        0x1f,        0x4b, (byte) 0xbd, (byte) 0x8b, (byte) 0x8a } ,
            {        0x70,        0x3e, (byte) 0xb5,        0x66,        0x48,        0x03, (byte) 0xf6,        0x0e,        0x61,        0x35,        0x57, (byte) 0xb9, (byte) 0x86, (byte) 0xc1,        0x1d, (byte) 0x9e } ,
            { (byte) 0xe1, (byte) 0xf8, (byte) 0x98,        0x11,        0x69, (byte) 0xd9, (byte) 0x8e, (byte) 0x94, (byte) 0x9b,        0x1e, (byte) 0x87, (byte) 0xe9, (byte) 0xce,        0x55,        0x28, (byte) 0xdf } ,
            { (byte) 0x8c, (byte) 0xa1, (byte) 0x89,        0x0d, (byte) 0xbf, (byte) 0xe6,        0x42,        0x68,        0x41, (byte) 0x99,        0x2d,        0x0f, (byte) 0xb0,        0x54, (byte) 0xbb,        0x16 }
    };


    private static final byte[][] AX = {
            { 0x02, 0x03, 0x01, 0x01 },
            { 0x01, 0x02, 0x03, 0x01 },
            { 0x01, 0x01, 0x02, 0x03 },
            { 0x03, 0x01, 0x01, 0x02 }
    };

    private CipherOps() {
        throw new AssertionError();
    }

    public static byte[] rotWord(byte[] w) {

        byte temp = w[0];

        for (int i = 1; i < w.length; i++)
            w[i-1] = w[i];

        w[w.length-1] = temp;

        return w;
    }

    public static byte[] subBytes(byte[] w) {

        for (int i = 0; i < w.length; i++) {
            int[] nibbles = getNibbles(w[i]);
            w[i] = SBOX[nibbles[0]][nibbles[1]];
        }

        return w;
    }

    public static byte[] addRoundKey(byte[] w, byte[] k) {

        for (int i = 0; i < w.length; i++) {
            w[i] = FiniteFieldArithmetic.ffAdd(w[i], k[i]);
        }

        return w;
    }

    public static byte[][] shiftRows(byte[][] s) {

        for (int i = 1; i < 4; i++) {
            s = shiftRow(s, i, i);
        }

        return s;
    }

    private static byte[][] shiftRow(byte[][] s, int row, int shiftCount) {

        for (int i = 0; i < shiftCount; i++) {

            byte temp = s[0][row];
            for (int j = 1; j < 4; j++)
                s[j - 1][row] = s[j][row];

            s[3][row] = temp;

        }

        return s;
    }

    public static byte[] mixColumns(byte[] w) {

        byte[] resultArray = new byte[4];

        for (int i = 0; i < w.length; i++) {
            byte result = FiniteFieldArithmetic.ffMultiply(w[0], AX[i][0]);

            for (int j = 1; j < AX[i].length; j++) {
                result = FiniteFieldArithmetic.ffAdd(result, FiniteFieldArithmetic.ffMultiply(w[j], AX[i][j]));
            }
            resultArray[i] = result;
        }
        return resultArray;
    }


    private static int[] getNibbles(byte b) {

        int[] result = new int[2];
        result[0] = 0;
        result[1] = 0;

        if (b == 0)
            return result;

        String bits = Integer.toBinaryString(b);

        if (bits.length() > 4) {

            String firstNibbleStr = bits.substring(0, bits.length()-4);

            if (firstNibbleStr.length() > 4)
                firstNibbleStr = firstNibbleStr.substring(firstNibbleStr.length()-4, firstNibbleStr.length());

            result[0] = Integer.parseInt(firstNibbleStr, 2);

            String secondNibbleStr = bits.substring(bits.length() - 4, bits.length());
            result[1] = Integer.parseInt(secondNibbleStr, 2);
        }
        else {
            String secondNibbleStr = bits.substring(0, bits.length());
            result[1] = Integer.parseInt(secondNibbleStr, 2);
        }
        return result;
    }

    public static String stringifyWordArray(byte[][] state) {

        StringBuilder s = new StringBuilder();

        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < state.length; i++) {
                StringBuilder value = new StringBuilder(Integer.toHexString(state[i][j]));
                if (value.length() < 2) {
                    value.insert(0, '0');
                    s.append(value.toString() + " ");
                }
                else if (value.length() > 2) {
                    value = new StringBuilder(value.substring(value.length()-2, value.length()));
                    s.append(value.toString() + " ");
                }
                else {
                    s.append(value.toString() + " ");
                }
            }
            s.append("\n");
        }

        return s.toString();
    }
}
