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

        if (keySize == 128)
            nr = 10;

        else if (keySize == 192)
            nr = 12;

        else if (keySize == 256)
            nr = 14;

    }

    public void run() {

        // Add initial round key
        for (int i = 0; i < state.length; i++) {
            state[i] = CipherOps.addRoundKey(state[i], key.getRoundKeyVal(keyIndex));
            keyIndex++;
        }

        // Perform following steps up to nr (num rounds)
        for (int i = 1; i < nr; i++) {

            // Sub bytes
            for (int j = 0; j < state.length; j++) {
                state[j] = CipherOps.subBytes(state[j]);
            }

            // Shift rows
            state = CipherOps.shiftRows(state);

            // Mix columns
            for (int j = 0; j < state.length; j++) {
                state[j] = CipherOps.mixColumns(state[j]);
            }

            // Add round key
            for (int j = 0; j < state.length; j++) {
                state[j] = CipherOps.addRoundKey(state[j], key.getRoundKeyVal(keyIndex));
                keyIndex++;
            }
        }

        // Sub bytes
        for (int i = 0; i < state.length; i++) {
            state[i] = CipherOps.subBytes(state[i]);
        }

        state = CipherOps.shiftRows(state);

        for (int i = 0; i < state.length; i++) {
            state[i] = CipherOps.addRoundKey(state[i], key.getRoundKeyVal(keyIndex));
            keyIndex++;
        }

        System.out.println(CipherOps.stringifyWordArray(state));
    }





}
