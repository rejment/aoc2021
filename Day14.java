import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 {
    public static void main(String[] args) throws IOException {
        List<String> strings = Files.readAllLines(Paths.get("day14.txt"));

        String input = strings.remove(0);
        strings.remove(0);
        Map<String, String> rules = new HashMap<>();
        for (String string : strings) {
            String[] split = string.split(" -> ");
            rules.put(split[0], split[1]);
        }

        System.out.println(count(input, rules, 10));
        System.out.println(count(input, rules, 40));
    }

    private static long count(String input, Map<String, String> rules, int reps) {
        Map<String, Long> counts = new HashMap<>();
        for (int i = 1; i < input.length(); i++) {
            String pair = input.substring(i - 1, i + 1);
            counts.put(pair, counts.getOrDefault(pair, 0L) + 1);
        }

        for (int r = 0; r < reps; r++) {
            Map<String, Long> newcount = new HashMap<>();

            counts.forEach((k, v) -> {
                if (rules.containsKey(k)) {
                    String s = rules.get(k);
                    String p1 = k.charAt(0) + s;
                    String p2 = s + k.charAt(1);
                    newcount.put(p1, newcount.getOrDefault(p1, 0L) + v);
                    newcount.put(p2, newcount.getOrDefault(p2, 0L) + v);
                } else {
                    newcount.put(k, newcount.getOrDefault(k, 0L) + v);
                }
            });
            counts = newcount;
        }

        Map<String, Long> freq = new HashMap<>();
        freq.put(input.substring(input.length()-1), 1L);
        counts.forEach((k, v) -> {
            String c0 = k.substring(0, 1);
            freq.put(c0, freq.getOrDefault(c0, 0L) + v);
        });
        List<Long> freqs = freq.values().stream().sorted().toList();
        return freqs.get(freqs.size() - 1) - freqs.get(0);
    }
}
