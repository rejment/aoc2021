import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day24 {

    public static void main(String[] args) throws IOException {
        List<String> strings = Files.readAllLines(Paths.get("day24.txt"));

        System.out.println(solve(strings, new long[4], 0, -1));
        cache.clear();
        System.out.println(solve(strings, new long[4], 0, 1));
    }

    public static Map<String, String> cache = new HashMap<>();

    public static String solve(List<String> ins, long[] reg, int ip, int dir) {
        if (reg[3] > 1000000) return null; // this was a bet
        if (ip == ins.size()) return reg[3] == 0 ? "" : null;
        String in = ins.get(ip);
        String[] args = in.split(" ");
        switch (args[0]) {
            case "inp" -> {
                for (int i = (dir > 0 ? 1 : 9); (dir > 0 ? i <= 9 : i >= 1); i += dir) {
                    long[] r2 = Arrays.copyOf(reg, 4);
                    r2[args[1].charAt(0) - 'w'] = i;
                    String key = Arrays.toString(r2) + "|" + ip;
                    String res;
                    if (cache.containsKey(key)) {
                        res = cache.get(key);
                    } else {
                        res = solve(ins, r2, ip + 1, dir);
                        cache.put(key, res);
                    }
                    if (res != null) {
                        return i + res;
                    }
                }
                return null;
            }
            case "add" -> reg[args[1].charAt(0) - 'w'] += arg2(reg, args);
            case "mul" -> reg[args[1].charAt(0) - 'w'] *= arg2(reg, args);
            case "div" -> reg[args[1].charAt(0) - 'w'] /= arg2(reg, args);
            case "mod" -> reg[args[1].charAt(0) - 'w'] %= arg2(reg, args);
            case "eql" -> reg[args[1].charAt(0) - 'w'] = reg[args[1].charAt(0) - 'w'] == arg2(reg, args) ? 1 : 0;
        }
        return solve(ins, reg, ip + 1, dir);
    }

    private static long arg2(long[] reg, String[] args) {
        if (Character.isLetter(args[2].charAt(0)))
            return reg[args[2].charAt(0) - 'w'];
        return Integer.parseInt(args[2]);
    }
}
