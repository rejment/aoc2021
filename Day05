import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Day05 {

    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Path.of("day05.txt"));

        System.out.println(solve(input, false));
        System.out.println(solve(input, true));
    }

    private static long solve(List<String> input, boolean p2) {
        int[][] board = new int[10000][10000];
        for (String line : input) {
            String[] split = line.split(" -> |,");
            int x1 = Integer.parseInt(split[0]);
            int y1 = Integer.parseInt(split[1]);
            int x2 = Integer.parseInt(split[2]);
            int y2 = Integer.parseInt(split[3]);
            int dx = x2 - x1;
            int dy = y2 - y1;
            if (x1 == x2 || y1 == y2 || (p2 && Math.abs(dx) == Math.abs(dy))) {
                int x = x1;
                int y = y1;
                board[x][y]++;
                while (x != x2 || y != y2) {
                    x += Math.signum(dx);
                    y += Math.signum(dy);
                    board[x][y]++;
                }
            }
        }

        return Arrays.stream(board).flatMapToInt(Arrays::stream).filter(a -> a > 1).count();
    }
}
