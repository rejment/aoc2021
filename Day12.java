import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day12 {

    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Path.of("day12.txt"));

        Map<String, Set<String>> seg = new HashMap<>();
        for (String l : input) {
            String[] split = l.split("-");
            seg.computeIfAbsent(split[0], a -> new TreeSet<>()).add(split[1]);
            seg.computeIfAbsent(split[1], a -> new TreeSet<>()).add(split[0]);
        }

        System.out.println(find(seg, "start", Set.of("start")));
        System.out.println(find2(seg, "start", Set.of("start"), false));
    }

    private static int find(Map<String, Set<String>> seg, String start, Set<String> visited) {
        if (start.equals("end")) return 1;
        int res = 0;
        for (String s : seg.get(start)) {
            if (Character.isLowerCase(s.charAt(0))) {
                if (!visited.contains(s)) {
                    res += find(seg, s, add(visited, s));
                }
            } else {
                res += find(seg, s, visited);
            }
        }
        return res;
    }

    private static int find2(Map<String, Set<String>> seg, String start, Set<String> visited, boolean twice) {
        if (start.equals("end")) return 1;
        int res = 0;
        for (String s : seg.get(start)) {
            if (s.equals("start")) continue;
            if (Character.isLowerCase(s.charAt(0))) {
                if (visited.contains(s)) {
                    if (!twice) {
                        res += find2(seg, s, visited, true);
                    }
                } else {
                    res += find2(seg, s, add(visited, s), twice);
                }
            } else {
                res += find2(seg, s, visited, twice);
            }
        }
        return res;
    }

    private static Set<String> add(Set<String> set, String s) {
        HashSet<String> strings = new HashSet<>(set);
        strings.add(s);
        return strings;
    }
}
