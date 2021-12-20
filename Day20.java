import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day20 {

    public static void main(String[] args) throws IOException {
        List<String> strings = Files.readAllLines(Paths.get("day20.txt"));

        String index = strings.remove(0);
        strings.remove(0);

        List<String> map = new ArrayList<>(strings);
        for (int i = 0; i < 2; i++) {
            map = lap(map, index, i & 1);
        }
        System.out.println(count(map));

        map = new ArrayList<>(strings);
        for (int i = 0; i < 50; i++) {
            map = lap(map, index, i & 1);
        }
        System.out.println(count(map));
    }

    private static int count(List<String> strings) {
        int sum = 0;
        for (String string : strings) {
            for (int i = 0; i < string.length(); i++) {
                if (string.charAt(i) == '#') sum++;
            }
        }
        return sum;
    }

    private static List<String> lap(List<String> map, String index, int odd) {
        List<String> res = new ArrayList<>();

        for (int y = -1; y < map.size() + 1; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = -1; x < map.get(0).length() + 1; x++) {

                int v = 0;
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dx = -1; dx <= 1; dx++) {
                        v = (v << 1) + read(map, y + dy, x + dx, odd);
                    }
                }
                line.append(index.charAt(v));
            }
            res.add(line.toString());
        }
        return res;
    }

    private static int read(List<String> map, int y, int x, int def) {
        if (y < 0 || x < 0) return def;
        if (y >= map.size()) return def;
        String s = map.get(y);
        if (x >= s.length()) return def;
        return s.charAt(x) == '#' ? 1 : 0;
    }
}
