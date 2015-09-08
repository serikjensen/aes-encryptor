public class Main {

    public static void main(String[] args) {
        byte a = 0x57;
        byte b = 0x13;
        FiniteFieldArithmetic ffa = new FiniteFieldArithmetic();
        byte answer = ffa.ffmultiply(a, b);
        System.out.println(Integer.toBinaryString((byte) answer));
    }
}
