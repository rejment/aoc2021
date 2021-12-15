import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Day15 {
    record Point(int x, int y, int score) {
    }

    public static void main(String[] args) throws IOException {
        List<String> strings = Files.readAllLines(Paths.get("day15.txt"));

        int[][] board = new int[100][100];
        int[][] board2 = new int[500][500];
        for (int y = 0; y < 100; y++) {
            for (int x = 0; x < 100; x++) {
                board[y][x] = strings.get(y).charAt(x) - '0';
                for (int y2 = 0; y2 < 5; y2++) {
                    for (int x2 = 0; x2 < 5; x2++) {
                        board2[y + 100 * y2][x + 100 * x2] = add(add(board[y][x], y2), x2);
                    }
                }
            }
        }

        System.out.println(find(board, new Point(0, 0, 0)));
        System.out.println(find(board2, new Point(0, 0, 0)));
    }

    private static int add(int i, int d) {
        return (((i - 1) + d) % 9) + 1;
    }

    private static int find(int[][] board, Point point) {
        int DIM = board.length;
        int D = DIM - 1;

        int[][] min = new int[DIM][DIM];
        for (int y = 0; y < DIM; y++) {
            for (int x = 0; x < DIM; x++) {
                min[y][x] = Integer.MAX_VALUE;
            }
        }

        PriorityQueue<Point> q = new PriorityQueue<>(Comparator.<Point>comparingInt(p1 -> p1.score).thenComparingInt(p1 -> p1.x).thenComparingInt(p1 -> p1.y));
        q.add(point);

        while (!q.isEmpty()) {
            Point p = q.poll();

            if (p.y > 0 && (p.score + board[p.y - 1][p.x]) < min[p.y - 1][p.x]) {
                min[p.y - 1][p.x] = (p.score + board[p.y - 1][p.x]);
                q.add(new Point(p.x, p.y - 1, p.score + board[p.y - 1][p.x]));
            }
            if (p.y < D && (p.score + board[p.y + 1][p.x]) < min[p.y + 1][p.x]) {
                min[p.y + 1][p.x] = (p.score + board[p.y + 1][p.x]);
                q.add(new Point(p.x, p.y + 1, p.score + board[p.y + 1][p.x]));
            }
            if (p.x > 0 && (p.score + board[p.y][p.x - 1]) < min[p.y][p.x - 1]) {
                min[p.y][p.x - 1] = (p.score + board[p.y][p.x - 1]);
                q.add(new Point(p.x - 1, p.y, p.score + board[p.y][p.x - 1]));
            }
            if (p.x < D && (p.score + board[p.y][p.x + 1]) < min[p.y][p.x + 1]) {
                min[p.y][p.x + 1] = (p.score + board[p.y][p.x + 1]);
                q.add(new Point(p.x + 1, p.y, p.score + board[p.y][p.x + 1]));
            }
        }
        return min[D][D];
    }
}
