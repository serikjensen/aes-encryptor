/**
 * Created by steve on 9/7/15.
 */
public class FiniteFieldArithmetic {

    public byte ffmultiply(byte a, byte b) {
        byte[] bytes = getXtimeArray(a, b);

        //reverse the bits string so it is
        //in a more intuitive iterable order
        StringBuilder bitsWrapper = new StringBuilder(Integer.toBinaryString(b));
        String bits = bitsWrapper.reverse().toString();

        //find the first binary instance of a 1
        //and set the initial value
        byte value = bytes[bits.indexOf('1')];

        //for every 1 bit, use the corresponding
        //xtime value, and sum using xor in order
        //to produce the result
        for (int i = bits.indexOf('1') + 1; i < bits.length(); i++) {
            if (bits.charAt(i) == '1')
                value ^= bytes[i];
        }
        return value;
    }

    public byte[] getXtimeArray(byte a, byte b) {

        //returns an array of all xtime values
        //up to the binary length of input b
        String bits = Integer.toBinaryString(b);
        byte[] vals = new byte[bits.length()];
        vals[0] = a;
        for (int i = 1; i < bits.length(); i++) {
            vals[i] = xtime(vals[i-1]);
        }
        return vals;
    }

    public byte xtime(byte a) {
        Integer placeholder = new Integer(a);
        Integer modulo = new Integer(0x1b);

        //left shift bits
        placeholder <<= 1;
        String bits = Integer.toBinaryString(placeholder);

        //check for overflow, and modulo if necessary
        if (bits.length() > 8)
            placeholder ^= modulo;

        return placeholder.byteValue();
    }
}
