import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day10 {

    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Path.of("day10.txt"));

        var m = Map.of("[", "]", "{", "}", "<", ">", "(", ")");
        var cost = Map.of(")", 3, "]", 57, "}", 1197, ">", 25137);

        long sum = 0;
        for (String line : input) {
            var stack = new Stack<String>();
            for (int i = 0; i < line.length(); i++) {
                String c = line.substring(i, i + 1);
                if (m.containsKey(c)) {
                    stack.push(c);
                } else {
                    if (!m.get(stack.pop()).equals(c)) {
                        sum += cost.get(c);
                        break;
                    }
                }
            }
        }
        System.out.println(sum);

        var cost2 = Map.of(")", 1, "]", 2, "}", 3, ">", 4);
        var sums = new ArrayList<Long>();
        line: for (String line : input) {
            var stack = new Stack<String>();
            for (int i = 0; i < line.length(); i++) {
                String c = line.substring(i, i + 1);
                if (m.containsKey(c)) {
                    stack.push(c);
                } else {
                    if (!m.get(stack.pop()).equals(c)) continue line;
                }
            }
            long c = 0;
            while (!stack.isEmpty()) {
                c = c * 5 + cost2.get(m.get(stack.pop()));
            }
            sums.add(c);
        }
        Collections.sort(sums);
        System.out.println(sums.get(sums.size() / 2));
    }
}
