import java.util.ArrayList;

/**
 * Created by steve on 9/9/15.
 */
public class Key {

    private int keySize;
    private byte[][] cipherKey;
    private byte[][] roundKeys;

    private int nk;
    private int nr;
    private int roundKeysSize;

    private int rconIndex = 1;

    private byte[][] cipherKey128 = {
            {        0x2B,        0x7E,        0x15,        0x16 },
            {        0x28, (byte) 0xAE, (byte) 0xD2, (byte) 0xA6 },
            { (byte) 0xAB, (byte) 0xF7,        0x15, (byte) 0x88 },
            {        0x09, (byte) 0xCF,        0x4F,        0x3C }
    };

    private byte[][] cipherKey192 = {
            { (byte) 0x8E,        0x73, (byte) 0xB0, (byte) 0xF7 },
            { (byte) 0xDA,        0x0E,        0x64,        0x52 },
            { (byte) 0xC8,        0x10, (byte) 0xF3,        0x2B },
            { (byte) 0x80, (byte) 0x90,        0x79, (byte) 0xE5 },
            {        0x62, (byte) 0xF8, (byte) 0xEA, (byte) 0xD2 },
            {        0x52,        0x2C,        0x6B,        0x7B }
    };

    private byte[][] cipherKey256 = {
            {        0x60,        0x3d, (byte) 0xeb,        0x10 },
            {        0x15, (byte) 0xca,        0x71, (byte) 0xbe },
            {        0x2b,        0x73, (byte) 0xae, (byte) 0xf0 },
            { (byte) 0x85,        0x7d,        0x77, (byte) 0x81 },
            {        0x1f,        0x35,        0x2c,        0x07 },
            {        0x3b,        0x61,        0x08, (byte) 0xd7 },
            {        0x2d, (byte) 0x98,        0x10, (byte) 0xa3 },
            {        0x09,        0x14, (byte) 0xdf, (byte) 0xf4 }
    };

    private byte[] rcon = { 0x00, //initial placeholder
                   0x01,        0x02,        0x04,        0x08,
                   0x10,        0x20,        0x40, (byte) 0x80,
                   0x1B,        0x36,        0x6C, (byte) 0xD8,
            (byte) 0xAB,        0x4D, (byte) 0x9A,        0x2F,
                   0x5E, (byte) 0xBC,        0x63, (byte) 0xC6,
            (byte) 0x97,        0x35,        0x6A, (byte) 0xD4,
            (byte) 0xB3,        0x7D, (byte) 0xFA, (byte) 0xEF,
            (byte) 0xC5, (byte) 0x91,        0x39,        0x72,
            (byte) 0xE4, (byte) 0xD3, (byte) 0xBD,        0x61,
            (byte) 0xC2, (byte) 0x9F,        0x25,        0x4A,
            (byte) 0x94,        0x33,        0x66, (byte) 0xCC,
            (byte) 0x83,        0x1D,        0x3A,        0x74,
            (byte) 0xE8, (byte) 0xCB, (byte) 0x8D};


    public Key(int keySize) {
        this.keySize = keySize;

        if (keySize == 128) {
            cipherKey = cipherKey128;
            nk = 4;
            nr = 10;
            roundKeysSize = (4 * nr) + 4;
        }

        else if (keySize == 192) {
            cipherKey = cipherKey192;
            nk = 6;
            nr = 12;
            roundKeysSize = (4 * nr) + 4;
        }

        else if (keySize == 256) {
            cipherKey = cipherKey256;
            nk = 8;
            nr = 14;
            roundKeysSize = (4 * nr) + 4;
        }
    }

    public void performKeyExpansion() {

        roundKeys = new byte[roundKeysSize][4];

        for (int i = 0; i < nk; i++)
            roundKeys[i] = cipherKey[i];

        for (int i = nk; i < roundKeys.length; i++) {

            if (i % nk == 0) {

                byte[] result = new byte[4];
                for (int j = 0; j < 4; j++)
                    result[j] = roundKeys[i-1][j];

                byte[] rotatedWord = CipherOps.rotWord(result);
                byte[] subbedBytes = CipherOps.subBytes(rotatedWord);

                for (int j = 0; j < 4; j++) {

                    result[j] = FiniteFieldArithmetic.ffAdd(roundKeys[i - nk][j], subbedBytes[j]);

                    if (j == 0) {
                        result[j] = FiniteFieldArithmetic.ffAdd(result[j], rcon[rconIndex]);
                        rconIndex++;
                    }
                }
                roundKeys[i] = result;
            }
            else {

                byte[] result = new byte[4];
                for (int j = 0; j < 4; j++)
                    result[j] = roundKeys[i-1][j];

                if (keySize == 256 && i % 4 == 0) {
                  result = CipherOps.subBytes(result);
                }

                for (int j = 0; j < 4; j++)
                    result[j] = FiniteFieldArithmetic.ffAdd(roundKeys[i-nk][j], result[j]);

                roundKeys[i] = result;
            }
        }
    }

    public byte[] getRoundKeyVal(int index) {
        return roundKeys[index];
    }

    public int getKeyLength() {
        return roundKeysSize;
    }

    @Override
    public String toString() {
        return CipherOps.stringifyWordArray(roundKeys);
    }

}
