import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day19 {

    record Point(int x, int y, int z) {
        @SuppressWarnings("SuspiciousNameCombination")
        Point perm(int t) {
            return switch (t) {
                case 0 -> new Point(-x, -y, z);
                case 1 -> new Point(-x, -z, -y);
                case 2 -> new Point(-x, y, -z);
                case 3 -> new Point(-x, z, y);
                case 4 -> new Point(-y, -x, -z);
                case 5 -> new Point(-y, -z, x);
                case 6 -> new Point(-y, x, z);
                case 7 -> new Point(-y, z, -x);
                case 8 -> new Point(-z, -x, y);
                case 9 -> new Point(-z, -y, -x);
                case 10 -> new Point(-z, x, -y);
                case 11 -> new Point(-z, y, x);
                case 12 -> new Point(x, -y, -z);
                case 13 -> new Point(x, -z, y);
                case 14 -> new Point(x, y, z);
                case 15 -> new Point(x, z, -y);
                case 16 -> new Point(y, -x, z);
                case 17 -> new Point(y, -z, -x);
                case 18 -> new Point(y, x, -z);
                case 19 -> new Point(y, z, x);
                case 20 -> new Point(z, -x, -y);
                case 21 -> new Point(z, -y, x);
                case 22 -> new Point(z, x, y);
                case 23 -> new Point(z, y, -x);
                default -> new Point(0,0,0);
            };
        }

        public Point sub(Point p) {
            return new Point(x - p.x, y - p.y, z - p.z);
        }

        public Point add(Point p) {
            return new Point(x + p.x, y + p.y, z + p.z);
        }

        public long dist() {
            return Math.abs(x) + Math.abs(y) + Math.abs(z);
        }
    }

    record Match(int transformation, Point origin) {}

    public static Match findTransformation(List<Point> s1, List<Point> s2) {
        for (int t = 0; t < 24; t++) {
            Map<Point, Integer> c = new HashMap<>();
            for (Point p1 : s1) {
                for (Point p2 : s2) {
                    Point d = p1.sub(p2.perm(t));
                    if (c.merge(d, 1, Integer::sum) >= 12) return new Match(t, d);
                }
            }
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        List<List<Point>> beacons = new ArrayList<>(Arrays.stream(Files.readString(Paths.get("day19.txt")).
                split("\n\n")).map(s -> Arrays.stream(s.split("\n")).skip(1).map(b -> {
            String[] m = b.split(",");
            return new Point(Integer.parseInt(m[0]), Integer.parseInt(m[1]), Integer.parseInt(m[2]));
        }).toList()).toList());

        List<Point> scanners = new ArrayList<>();
        Set<Integer> done = new TreeSet<>(List.of(0));
        List<Integer> queue = new ArrayList<>(List.of(0));
        while (!queue.isEmpty()) {
            int s1 = queue.remove(0);
            for (var s2 = 0; s2 < beacons.size(); s2++) {
                if (done.contains(s2)) continue;
                if (s1 == s2) continue;
                var ck = findTransformation(beacons.get(s1), beacons.get(s2));
                if (ck != null) {
                    beacons.set(s2, beacons.get(s2).stream().map(b -> b.perm(ck.transformation).add(ck.origin)).toList());
                    done.add(s2);
                    queue.add(s2);
                    scanners.add(ck.origin);
                }
            }
        }
        System.out.println(beacons.stream().flatMap(Collection::stream).distinct().count());

        long max = Long.MIN_VALUE;
        for (Point s1 : scanners) {
            for (Point s2 : scanners) {
                max = Math.max(max, s1.sub(s2).dist());
            }
        }
        System.out.println(max);
    }
}
