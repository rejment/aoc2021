import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day16 {

    public static class In {
        public StringBuilder sb;

        public In(StringBuilder sb) {
            this.sb = sb;
        }

        public int readBits(int n) {
            int res = Integer.parseInt(sb.substring(0, n), 2);
            sb.delete(0, n);
            return res;
        }
    }

    public static void main(String[] args) throws IOException {
        List<String> strings = Files.readAllLines(Paths.get("day16.txt"));

        StringBuilder binString = new StringBuilder();
        String s = strings.get(0);
        for (char c : s.toCharArray()) {
            binString.append(Integer.toBinaryString(Integer.parseInt("1" + c, 16)).substring(1));
        }
        In in = new In(binString);
        long p2 = readPacket(in);
        System.out.println(sum);
        System.out.println(p2);
    }

    static int sum = 0;

    private static long readPacket(In in) {
        int version = in.readBits(3);
        sum += version;
        int type = in.readBits(3);
        return switch (type) {
            case 0 -> readOperator(in).stream().mapToLong(a -> a).sum();
            case 1 -> product(readOperator(in));
            case 2 -> readOperator(in).stream().mapToLong(a -> a).min().orElse(Long.MIN_VALUE);
            case 3 -> readOperator(in).stream().mapToLong(a -> a).max().orElse(Long.MIN_VALUE);
            case 4 -> readLiteral(in);
            case 5 -> gt(readOperator(in));
            case 6 -> lt(readOperator(in));
            case 7 -> eq(readOperator(in));
            default -> Long.MIN_VALUE;
        };
    }

    private static long gt(List<Long> x) {
        return x.get(0) > x.get(1) ? 1L : 0L;
    }

    private static long lt(List<Long> x) {
        return x.get(0) < x.get(1) ? 1L : 0L;
    }

    private static long eq(List<Long> x) {
        return x.get(0).equals(x.get(1)) ? 1L : 0L;
    }

    private static long product(List<Long> readOperator) {
        long p = readOperator.get(0);
        for (int i = 1; i < readOperator.size(); i++) p *= readOperator.get(i);
        return p;
    }

    private static long readLiteral(In in) {
        long lit = 0;
        while (in.readBits(1) == 1) {
            lit = (lit << 4) + in.readBits(4);
        }
        lit = (lit << 4) + in.readBits(4);
        return lit;
    }

    private static List<Long> readOperator(In in) {
        int lentype = in.readBits(1);
        List<Long> sub = new ArrayList<>();
        if (lentype == 0) {
            int len = in.readBits(15);
            In subs = new In(new StringBuilder(in.sb.substring(0, len)));
            in.sb.delete(0, len);
            while (subs.sb.length() > 0) {
                sub.add(readPacket(subs));
            }
        } else {
            int subs = in.readBits(11);
            for (int i = 0; i < subs; i++) {
                sub.add(readPacket(in));
            }
        }
        return sub;
    }

}
