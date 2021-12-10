import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day08 {

    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Path.of("day08.txt"));

        int sum = 0;
        for (String line : input) {
            String[] split = line.split(" \\| ");
            String[] in = split[0].split(" ");
            String[] out = split[1].split(" ");

            String[] map = new String[10];

            for (String s : in) {
                if (s.length() == 2) map[1] = s;
                if (s.length() == 3) map[7] = s;
                if (s.length() == 4) map[4] = s;
                if (s.length() == 7) map[8] = s;
            }

            for (String s : in) {
                if (s.length() == 6) {
                    if (minus(set(map[1]), set(s)).size() != 0) {
                        map[6] = s;
                    } else if (minus(set(map[4]), set(s)).size() == 0) {
                        map[9] = s;
                    } else if (minus(set(map[4]), set(s)).size() != 0) {
                        map[0] = s;
                    }
                }
            }

            String tr = minus(set(map[1]), set(map[6])).iterator().next();
            for (String s : in) {
                if (s.length() == 5) {
                    if (minus(set(map[1]), set(s)).size() == 0) {
                        map[3] = s;
                    } else if (s.contains(tr)) {
                        map[2] = s;
                    } else {
                        map[5] = s;
                    }
                }
            }

            Map<String, Integer> hmap = new TreeMap<>();
            for (int i = 0; i < map.length; i++) {
                hmap.put(String.join("", set(map[i])), i);
            }

            int val = 0;
            for (String s : out) {
                val = val * 10 + hmap.get(String.join("", set(s)));
            }
            sum += val;
        }
        System.out.println(sum);
    }

    private static Set<String> set(String s) {
        return new TreeSet<>(Arrays.asList(s.split("")));
    }

    private static Set<String> minus(Set<String> a, Set<String> b) {
        TreeSet<String> res = new TreeSet<>(a);
        res.removeAll(b);
        return res;
    }
}
