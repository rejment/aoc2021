import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day22 {

    record Cube(long x1, long x2, long y1, long y2, long z1, long z2) {
        public long volume() {
            return (x2 - x1) * (y2 - y1) * (z2 - z1);
        }

        public boolean valid() {
            return x1 < x2 && y1 < y2 && z1 < z2;
        }

        public Cube intersection(Cube b) {
            return new Cube(
                    Math.max(x1, b.x1), Math.min(x2, b.x2),
                    Math.max(y1, b.y1), Math.min(y2, b.y2),
                    Math.max(z1, b.z1), Math.min(z2, b.z2));
        }

        public boolean intersects(Cube b) {
            return intersection(b).valid();
        }
    }

    // 587097
    // 1359673068597669
    public static void main(String[] args) throws IOException {
        List<String> strings = Files.readAllLines(Paths.get("day22.txt"));

        List<Cube> oncubes = new ArrayList<>();
        for (String string : strings) {
            String[] split = string.split(" x=|,y=|,z=|(\\.\\.)");
            long[] a = Arrays.stream(split).skip(1).mapToLong(Long::parseLong).toArray();
            Cube cube = new Cube(a[0], a[1] + 1, a[2], a[3] + 1, a[4], a[5] + 1);

            for (int j = 0; j < oncubes.size(); j++) {
                Cube on = oncubes.get(j);
                if (!cube.intersects(on)) continue;
                oncubes.remove(j--);

                // split into 27 parts and keep the ones in A, but not in B
                long[] xs = {on.x1, on.x2, cube.x1, cube.x2};
                long[] ys = {on.y1, on.y2, cube.y1, cube.y2};
                long[] zs = {on.z1, on.z2, cube.z1, cube.z2};
                Arrays.sort(xs);
                Arrays.sort(ys);
                Arrays.sort(zs);
                for (int x = 0; x < 3; x++) {
                    for (int y = 0; y < 3; y++) {
                        for (int z = 0; z < 3; z++) {
                            Cube piece = new Cube(xs[x], xs[x + 1], ys[y], ys[y + 1], zs[z], zs[z + 1]);
                            if (piece.valid() && on.intersects(piece) && !cube.intersects(piece)) {
                                oncubes.add(piece);
                            }
                        }
                    }
                }
            }
            if (split[0].equals("on")) oncubes.add(cube);
        }

        Cube c50 = new Cube(-50, 50, -50, 50, -50, 50);
        System.out.println(oncubes.stream().filter(c50::intersects).mapToLong(Cube::volume).sum());
        System.out.println(oncubes.stream().mapToLong(Cube::volume).sum());
    }
}
