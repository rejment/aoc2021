public class Day17 {

    static int MINX = 244;
    static int MAXX = 303;
    static int MINY = -91;
    static int MAXY = -54;

    public static void main(String[] args) {
        int maxy = 0;
        for (int idx = 1; idx <= MAXX; idx++) {
            for (int idy = MINY; idy < 1000; idy++) {
                maxy = Math.max(maxy, simulate(idx, idy));
            }
        }
        System.out.println(maxy);
        System.out.println(sum);
    }

    static int sum = 0;

    private static int simulate(int dx, int dy) {
        int x = 0;
        int y = 0;
        int maxy = 0;
        do {
            x += dx;
            y += dy;
            maxy = Math.max(maxy, y);
            dx -= Integer.compare(dx, 0);
            dy--;

            if (x >= MINX && x <= MAXX && y >= MINY && y <= MAXY) {
                sum++;
                return maxy;
            }
            if (dx == 0 && (x < MINX || x > MAXX)) {
                break;
            }
        } while (y >= MINY);
        return Integer.MIN_VALUE;
    }
}
