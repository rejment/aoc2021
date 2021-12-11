import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;

public class Day11 {

    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Path.of("day11.txt"));

        int[][] b = new int[10][10];
        IntStream.range(0, 100).forEach(i -> b[i / 10][i % 10] = input.get(i / 10).charAt(i % 10) - '0');

        int f = 0;
        for (int step = 1; step < 10000; step++) {
            f += evolve(b);
            if (step == 100) System.out.println(f);
            if (IntStream.range(0, 100).allMatch(i -> b[i / 10][i % 10] == b[0][0])) {
                System.out.println(step);
                break;
            }
        }
    }

    private static int evolve(int[][] r) {
        IntStream.range(0, 100).forEach(i -> r[i / 10][i % 10]++);

        int flashes = 0;
        boolean found = true;
        while (found) {
            found = false;
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    int c = r[i][j];
                    if (c > 9 && c < 100) {
                        if (i > 0 && j > 0) r[i - 1][j - 1]++;
                        if (i > 0) r[i - 1][j]++;
                        if (i > 0 && j < 9) r[i - 1][j + 1]++;
                        if (i < 9 && j > 0) r[i + 1][j - 1]++;
                        if (i < 9) r[i + 1][j]++;
                        if (i < 9 && j < 9) r[i + 1][j + 1]++;
                        if (j > 0) r[i][j - 1]++;
                        if (j < 9) r[i][j + 1]++;
                        r[i][j] = 200;
                        found = true;
                        flashes++;
                    }
                }
            }
        }

        IntStream.range(0, 100).forEach(i -> {
            if (r[i / 10][i % 10] > 9) r[i / 10][i % 10] = 0;
        });

        return flashes;
    }
}
