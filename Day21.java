import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day21 {

    record Scores(long a, long b) {
    }

    public static void main(String[] args) throws IOException {

        int p1 = 7;
        int p2 = 10;
        int p1s = 0;
        int p2s = 0;
        while (true) {
            p1 = ((p1 + roll() + roll() + roll() - 1) % 10) + 1;
            p1s += p1;
            if (p1s >= 1000) {
                System.out.println(p2s * dice);
                break;
            }
            p2 = ((p2 + roll() + roll() + roll() - 1) % 10) + 1;
            p2s += p2;
            if (p2s >= 1000) {
                System.out.println(p1s * dice);
                break;
            }
        }

        Scores scores = play(7, 10, 0, 0);
        System.out.println(Math.max(scores.a, scores.b));
    }

    static Map<String, Scores> memo = new HashMap<>();

    private static Scores play(int pl1, int pl2, int pl1s, int pl2s) {

        if (pl1s >= 21) return new Scores(1, 0);
        if (pl2s >= 21) return new Scores(0, 1);

        String key = pl1 + "," + pl2 + "," + pl1s + ", " + pl2s;
        if (memo.containsKey(key)) return memo.get(key);
        long w1 = 0;
        long w2 = 0;

        for (int d1 = 1; d1 <= 3; d1++) {
            for (int d2 = 1; d2 <= 3; d2++) {
                for (int d3 = 1; d3 <= 3; d3++) {
                    int pos = ((pl1 + d1 + d2 + d3 - 1) % 10) + 1;
                    int score = pl1s + pos;

                    Scores sub = play(pl2, pos, pl2s, score);
                    w1 += sub.b;
                    w2 += sub.a;
                }
            }
        }
        Scores res = new Scores(w1, w2);
        memo.put(key, res);
        return res;
    }

    public static int dice = 0;

    private static int roll() {
        dice++;
        return dice;
    }

}
