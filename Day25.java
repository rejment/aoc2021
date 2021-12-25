import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day25 {

    public static void main(String[] args) throws IOException {
        String board = Files.readString(Paths.get("day25.txt"));
        for (int step = 1; ; step++) {
            String board2 = moveEast(board);
            board2 = moveSouth(board2);
            if (board2.equals(board)) {
                System.out.println(step);
                break;
            }
            board = board2;
        }
    }

    private static char get(String board, int x, int y) {
        if (x < 0) x += 139;
        if (x > 139) x -= 139;
        if (y < 0) y += 137;
        if (y > 137) y -= 137;
        return board.charAt(y * 140 + x);
    }

    private static void set(StringBuilder board, int x, int y, char c) {
        if (x < 0) x += 139;
        if (x > 139) x -= 139;
        if (y < 0) y += 137;
        if (y > 137) y -= 137;
        board.setCharAt(y * 140 + x, c);
    }

    private static String moveEast(String board) {
        StringBuilder sb = new StringBuilder(board);
        for (int y = 0; y < 137; y++) {
            for (int x = 0; x < 139; x++) {

                char c1 = get(board, x - 1, y);
                char c2 = get(board, x, y);
                if (c1 == '>' && c2 == '.') {
                    set(sb, x, y, '>');
                    set(sb, x - 1, y, '.');
                }
            }
        }
        return sb.toString();
    }

    private static String moveSouth(String board) {
        StringBuilder sb = new StringBuilder(board);
        for (int y = 0; y < 137; y++) {
            for (int x = 0; x < 139; x++) {

                char c1 = get(board, x, y - 1);
                char c2 = get(board, x, y);
                if (c1 == 'v' && c2 == '.') {
                    set(sb, x, y, 'v');
                    set(sb, x, y - 1, '.');
                }
            }
        }
        return sb.toString();
    }
}
