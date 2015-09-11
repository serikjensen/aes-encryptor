import javax.crypto.Cipher;

/**
 * Created by steve on 9/10/15.
 */
public class AesExecutor {

    private Key key;
    private int keyIndex = 0;
    private int nr;


    private byte[][] state = {
            {        0x32,        0x43, (byte) 0xF6, (byte) 0xA8 },
            { (byte) 0x88,        0x5A,        0x30, (byte) 0x8D },
            {        0x31,        0x31, (byte) 0x98, (byte) 0xA2 },
            { (byte) 0xE0,        0x37,        0x07,        0x34 }
    };

    public AesExecutor(int keySize) {
        key = new Key(keySize);
        key.performKeyExpansion();

        this.setRoundCount(keySize);

    }


    public AesExecutor(int keySize, byte[][] customState, byte[][] customCipherKey) {
        key = new Key(keySize);
        key.setCustomCipherKey(customCipherKey);
        key.performKeyExpansion();

        this.state = customState;

        this.setRoundCount(keySize);

    }

    private void setRoundCount(int keySize) {
        if (keySize == 128)
            nr = 10;

        else if (keySize == 192)
            nr = 12;

        else if (keySize == 256)
            nr = 14;
    }


    public void applyCipher() {

        // Add initial round key
        for (int i = 0; i < state.length; i++) {
            state[i] = CipherOps.addRoundKey(state[i], key.getRoundKeyVal(keyIndex));
            keyIndex++;
        }

        // Perform following steps up to nr (num rounds)
        for (int i = 1; i < nr; i++) {

            // ------------- Sub bytes -------------
            //System.out.println(CipherOps.stringifyWordArray(state));
            for (int j = 0; j < state.length; j++) {
                state[j] = CipherOps.subBytes(state[j]);
            }
            //System.out.println(CipherOps.stringifyWordArray(state));


            // ------------- Shift rows -------------
            //System.out.println(CipherOps.stringifyWordArray(state));
            state = CipherOps.shiftRows(state);
            //System.out.println(CipherOps.stringifyWordArray(state));


            // ------------- Mix columns -------------
            //System.out.println(CipherOps.stringifyWordArray(state));
            for (int j = 0; j < state.length; j++) {
                state[j] = CipherOps.mixColumns(state[j]);
            }
            //System.out.println(CipherOps.stringifyWordArray(state));


            // ------------- Add round key -------------
            //System.out.println(CipherOps.stringifyWordArray(state));
            for (int j = 0; j < state.length; j++) {
                state[j] = CipherOps.addRoundKey(state[j], key.getRoundKeyVal(keyIndex));
                keyIndex++;
            }
            //System.out.println(CipherOps.stringifyWordArray(state));
        }

        // Sub bytes one last time
        for (int i = 0; i < state.length; i++) {
            state[i] = CipherOps.subBytes(state[i]);
        }

        // Shift rows one last time
        state = CipherOps.shiftRows(state);

        // Add round key one last time
        for (int i = 0; i < state.length; i++) {
            state[i] = CipherOps.addRoundKey(state[i], key.getRoundKeyVal(keyIndex));
            keyIndex++;
        }

    }

    public void applyInverseCipher() {

        // Add round key
        keyIndex -= 4;
        for (int i = 0; i < state.length; i++) {
            state[i] = CipherOps.addRoundKey(state[i], key.getRoundKeyVal(keyIndex + i));
        }

        for (int i = 1; i < nr; i++) {

            // ---------- Inverse shift rows -----------
            //System.out.println(CipherOps.stringifyWordArray(state));
            state = CipherOps.inverseShiftRows(state);
            //System.out.println(CipherOps.stringifyWordArray(state));


            // ---------- Inverse sub bytes -------------
            //System.out.println(CipherOps.stringifyWordArray(state));
            for (int j = 0; j < state.length; j++) {
                state[j] = CipherOps.inverseSubBytes(state[j]);
            }
            //System.out.println(CipherOps.stringifyWordArray(state));


            // ------------ Add round key -------------
            //System.out.println(CipherOps.stringifyWordArray(state));
            keyIndex -= 4;
            for (int j = 0; j < state.length; j++) {
                state[j] = CipherOps.addRoundKey(state[j], key.getRoundKeyVal(keyIndex + j));
            }
            //System.out.println(CipherOps.stringifyWordArray(state));


            // ------------ Inverse mix columns ---------
            //System.out.println(CipherOps.stringifyWordArray(state));
            for (int j = 0; j < state.length; j++) {
                state[j] = CipherOps.inverseMixColumns(state[j]);
            }
            //System.out.println(CipherOps.stringifyWordArray(state));

        }

        // Inverse shift rows one last time
        state = CipherOps.inverseShiftRows(state);

        // Inverse sub bytes one last time
        for (int i = 0; i < state.length; i++) {
            state[i] = CipherOps.inverseSubBytes(state[i]);
        }

        // Add round key one last time
        keyIndex -= 4;
        for (int i = 0; i < state.length; i++) {
            state[i] = CipherOps.addRoundKey(state[i], key.getRoundKeyVal(keyIndex + i));
        }
    }

    public String stringifyState() {
        return CipherOps.stringifyWordArray(state);
    }

    public String stringifyKey() {
        return key.toString();
    }


}
