import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day09 {

    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Path.of("day09.txt"));

        int[][] map = new int[100][100];
        for (int i = 0; i < input.size(); i++) {
            String s = input.get(i);
            for (int x = 0; x < 100; x++) {
                map[i][x] = s.charAt(x) - '0';
            }
        }

        int sum = 0;
        for (int y = 0; y < 100; y++) {
            for (int x = 0; x < 100; x++) {
                int p = map[y][x];
                if ((x == 99 || map[y][x + 1] > p) &&
                        (x == 0 || map[y][x - 1] > p) &&
                        (y == 99 || map[y + 1][x] > p) &&
                        (y == 0 || map[y - 1][x] > p)) {
                    sum += p + 1;
                }
            }
        }
        System.out.println(sum);

        List<Integer> basin = new ArrayList<>();
        for (int y = 0; y < 100; y++) {
            for (int x = 0; x < 100; x++) {
                if (map[y][x] != 9) {
                    basin.add(fill(map, x, y));
                }
            }
        }
        Collections.sort(basin);
        Collections.reverse(basin);
        System.out.println(basin.get(0) * basin.get(basin.get(1)) * basin.get(basin.get(2)));
    }

    private static int fill(int[][] map, int x, int y) {
        if (x < 0 || x >= 100 || y < 0 || y >= 100 || map[y][x] == 9) return 0;
        map[y][x] = 9;
        return 1
                + fill(map, x - 1, y)
                + fill(map, x + 1, y)
                + fill(map, x, y - 1)
                + fill(map, x, y + 1);
    }
}
