import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day18 {

    public static void main(String[] args) throws IOException {
        List<String> strings = Files.readAllLines(Paths.get("day18.txt"));
        List<Num> nums = strings.stream().map(a -> parse(new StringBuilder(a))).toList();
        Num sum = nums.get(0);
        for (int i = 1; i < nums.size(); i++) {
            sum = new Num(sum, nums.get(i)).reduce();
        }
        System.out.println(sum.magnitude());

        long max = 0;
        for (int i = 0; i < nums.size(); i++) {
            for (int j = 0; j < nums.size(); j++) {
                if (i != j) {
                    max = Math.max(max, new Num(nums.get(i), nums.get(j)).reduce().magnitude());
                }
            }
        }
        System.out.println(max);
    }

    public static class Num {
        public Long value;
        public Num left;
        public Num right;

        public Num(long value) {
            this.value = value;
        }

        public Num(Num leftNum, Num rightNum) {
            this.left = leftNum;
            this.right = rightNum;
        }

        public long magnitude() {
            if (value != null) return value;
            return 3 * left.magnitude() + 2 * right.magnitude();
        }

        record Split(boolean c, Num res) {}

        public Split split() {
            if (value != null) {
                return value >= 10 ?
                        new Split(true, new Num(new Num(value / 2), new Num(value - (value / 2)))) :
                        new Split(false, this);
            }

            Split l = left.split();
            if (l.c) return new Split(true, new Num(l.res, right));

            Split r = right.split();
            if (r.c) return new Split(true, new Num(left, r.res));

            return new Split(false, this);
        }

        record Explode(boolean c, Num res, long add_to_left, long add_to_right) {}

        private Explode explode(int depth) {
            if (value != null) return new Explode(false,this, 0, 0);
            if (depth == 4) return new Explode(true, new Num(0), left.value, right.value);

            Explode l = left.explode(depth + 1);
            if (l.c) {
                return new Explode(true, new Num(l.res, right.addLeft(l.add_to_right)), l.add_to_left, 0);
            }

            Explode r = right.explode(depth + 1);
            if (r.c) {
                return new Explode(true, new Num(left.addRight(r.add_to_left), r.res), 0, r.add_to_right);
            }

            return new Explode(false, this, 0, 0);
        }

        private Num addRight(long a) {
            return value != null ? new Num(value + a) : new Num(left, right.addRight(a));
        }

        private Num addLeft(long a) {
            return value != null ? new Num(value + a) : new Num(left.addLeft(a), right);
        }

        public Num reduce() {
            Num res = this;
            while (true) {
                Explode e = res.explode(0);
                if (e.c) {
                    res = e.res;
                    continue;
                }

                Split split = res.split();
                if (split.c) {
                    res = split.res;
                    continue;
                }
                break;
            }
            return res;
        }

        @Override
        public String toString() {
            if (value != null) return Long.toString(value);
            return "[" + left + "," + right + "]";
        }
    }

    private static Num parse(StringBuilder in) {
        if (Character.isDigit(in.charAt(0))) {
            Num result = new Num(in.charAt(0) - '0');
            in.delete(0, 1);
            return result;
        }
        if (in.charAt(0) == '[') {
            in.delete(0, 1);
            Num a = parse(in);
            in.delete(0, 1);
            Num b = parse(in);
            in.delete(0, 1);
            return new Num(a, b);
        }
        throw new RuntimeException();
    }
}
