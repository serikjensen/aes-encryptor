/**
 * Created by steve on 9/7/15.
 */
public final class FiniteFieldArithmetic {

    private FiniteFieldArithmetic() {
        throw new AssertionError();
    }

    public static byte ffAdd(byte a, byte b) {
        return a ^= b;
    }

    public static byte ffMultiply(byte a, byte b) {
        byte[] xtimeArray = getXtimeArray(a, b);

        //reverse the bits string so it is
        //in a more intuitive iterable order
        StringBuilder bitsWrapper = new StringBuilder(Integer.toBinaryString(b));
        String bits = bitsWrapper.reverse().toString();

        //find the first binary instance of a 1
        //and set the initial value
        byte value = xtimeArray[bits.indexOf('1')];

        //for every 1 bit, use the corresponding
        //xtime value, and sum using xor in order
        //to produce the result
        for (int i = bits.indexOf('1') + 1; i < bits.length(); i++) {
            if (bits.charAt(i) == '1')
                value = ffAdd(value, xtimeArray[i]);
        }
        return value;
    }

    public static byte[] getXtimeArray(byte a, byte b) {
        //returns an array of all xtime values
        //up to the binary length of input b
        String bits = Integer.toBinaryString(b);

        byte[] vals = new byte[bits.length()];
        vals[0] = a;

        for (int i = 1; i < bits.length(); i++)
            vals[i] = xtime(vals[i-1]);

        return vals;
    }

    public static byte xtime(byte a) {
        boolean carry = (a & 0x80) == 0x80;

        a <<= 1;

        if (carry)
            a ^= (byte) 0x1b;

        return a;
    }
}
