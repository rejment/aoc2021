import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day04 {
    public static class Board {
        public int[][] nums = new int[5][5];
        public boolean[][] have = new boolean[5][5];

        public boolean draw(int cur) {
            for (int i = 0; i < nums.length; i++) {
                int[] num = nums[i];
                for (int j = 0; j < num.length; j++) {
                    int i1 = num[j];
                    if (i1 == cur) {
                        have[i][j] = true;
                    }
                }
            }
            return check();
        }

        private boolean check() {
            for (int i = 0; i < nums.length; i++) {
                int[] num = nums[i];
                boolean row = true;
                boolean col = true;
                for (int j = 0; j < num.length; j++) {
                    if (!have[i][j]) {
                        row = false;
                    }
                    if (!have[j][i]) {
                        col = false;
                    }
                }
                if (row) return true;
                if (col) return true;
            }

            return false;
        }

        public int win(int cur) {
            int sum = 0;
            for (int i = 0; i < nums.length; i++) {
                int[] num = nums[i];
                for (int j = 0; j < num.length; j++) {
                    int i1 = num[j];
                    if (!have[i][j]) {
                        sum += i1;
                    }
                }
            }
            return sum * cur;
        }
    }

    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Path.of("day04.txt"));
        int[] draw = Arrays.stream(input.remove(0).split(",")).mapToInt(Integer::parseInt).toArray();

        List<Board> boards = new ArrayList<>();
        while (!input.isEmpty()) {
            input.remove(0);
            Board board = new Board();
            for (int i=0; i<5; i++) {
                String line = input.remove(0);
                board.nums[i] = Arrays.stream(line.split(" ")).filter(a->a.trim().length()>0).mapToInt(a->Integer.parseInt(a.trim())).toArray();
            }
            boards.add(board);
        }

        for (int cur : draw) {
            for (Iterator<Board> iterator = boards.iterator(); iterator.hasNext(); ) {
                Board b = iterator.next();
                if (b.draw(cur)) {
                    System.out.println(b.win(cur));
                    iterator.remove();
                }
            }
        }
    }
}
