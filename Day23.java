import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;

public class Day23 {

    static final int[] cost = {1, 10, 100, 1000};
    static final int[] home = {3, 5, 7, 9};
    static final int[] safe = {1, 2, 4, 6, 8, 10, 11};
    static final int[] safe_r = {11, 10, 8, 6, 4, 2, 1};
    static int maxy;

    record Bot(int c, int x, int y) {
        void moves(List<Bot> all, Consumer<Bot> cb) {
            if (y == 1) {
                int l = Math.min(x, home[c]);
                int r = Math.max(x, home[c]);
                int count = 0;
                for (Bot a : all) {
                    if (a != this && a.y == 1 && l < a.x && a.x < r) return;
                    if (a != this && a.y > 1 && a.x == home[c] && a.c != c) return;
                    if (a.x == home[c]) count++;
                }
                cb.accept(new Bot(c, home[c], maxy - count));
            } else {
                int count = 0;
                Set<Integer> blocked = new HashSet<>();
                for (Bot a : all) {
                    if (a != this && a.x == x && a.y == y - 1) return;
                    if (a.x == x && a.c == c && a.y > y) count++;
                    if (a.y == 1) blocked.add(a.x);
                }
                if (home[c] == x && count == (maxy - y)) return;

                for (int s : safe) {
                    if (s > x) {
                        if (blocked.contains(s)) break;
                        cb.accept(new Bot(c, s, 1));
                    }
                }
                for (int s : safe_r) {
                    if (s < x) {
                        if (blocked.contains(s)) break;
                        cb.accept(new Bot(c, s, 1));
                    }
                }

            }
        }
    }

    record State(List<Bot> bots, int score) {
        void moves(Consumer<State> cb) {
            for (int i = 0; i < bots.size(); i++) {
                final int ii = i;
                Bot bot = bots.get(i);
                bot.moves(bots, moved -> {
                    List<Bot> b2 = new ArrayList<>(bots);
                    b2.set(ii, moved);
                    cb.accept(new State(b2, score + cost[moved.c] * (Math.abs(bot.x - moved.x) + Math.abs(bot.y - moved.y))));
                });
            }
        }

        boolean done() {
            for (Bot b : bots) {
                if (b.y <= 1 || b.x != home[b.c]) return false;
            }
            return true;
        }
    }

    public static void main(String[] args) throws IOException {
        List<String> strings = Files.readAllLines(Paths.get("day23.txt"));
        System.out.println(solve(strings));
        strings.add(3, "  #D#C#B#A#");
        strings.add(4, "  #D#B#A#C#");
        System.out.println(solve(strings));
    }

    private static int solve(List<String> strings) {
        List<Bot> bots = new ArrayList<>();
        for (int y = 0; y < strings.size(); y++) {
            String line = strings.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                if (Character.isLetter(c)) {
                    bots.add(new Bot(c - 'A', x, y));
                }
            }
        }
        maxy = bots.stream().mapToInt(Bot::y).max().orElse(0);
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(State::score));
        Set<State> seen = new HashSet<>();
        pq.add(new State(bots, 0));
        while (!pq.isEmpty()) {
            State top = pq.poll();
            if (seen.contains(top)) continue;
            seen.add(top);
            if (top.done()) return top.score;
            top.moves(pq::add);
        }
        return -1;
    }
}
