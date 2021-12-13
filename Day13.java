import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day13 {
    private record Point(int x, int y) {
    }

    public static void main(String[] args) throws IOException {
        List<String> strings = Files.readAllLines(Paths.get("day13.txt"));

        boolean instr = false;
        boolean first = true;
        Set<Point> points = new HashSet<>();
        for (String string : strings) {
            if (string.isBlank()) {
                instr = true;
                continue;
            }
            if (!instr) {
                String[] split = string.split(",");
                points.add(new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
            } else {
                if (string.startsWith("fold along x=")) {
                    points = foldx(points, Integer.parseInt(string.substring(13)));
                }
                if (string.startsWith("fold along y=")) {
                    points = foldy(points, Integer.parseInt(string.substring(13)));
                }
                if (first) {
                    System.out.println(points.size());
                    first = false;
                }
            }
        }

        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 50; x++) {
                int fx = x;
                int fy = y;
                System.out.print(points.stream().anyMatch(p -> (p.y == fy && p.x == fx)) ? "▓" : " ");
            }
            System.out.println();
        }
    }

    private static Set<Point> foldx(Set<Point> points, int p) {
        Set<Point> res = new HashSet<>();
        for (Point point : points) {
            if (point.x > p) {
                res.add(new Point(2 * p - point.x, point.y));
            } else {
                res.add(point);
            }
        }
        return res;
    }

    private static Set<Point> foldy(Set<Point> points, int p) {
        Set<Point> res = new HashSet<>();
        for (Point point : points) {
            if (point.y > p) {
                res.add(new Point(point.x, 2 * p - point.y));
            } else {
                res.add(point);
            }
        }
        return res;
    }

}
