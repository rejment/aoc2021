import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day02 {
    public static void main(String[] args) throws IOException {
        List<String> strings = Files.readAllLines(Paths.get("day02.txt"));
        System.out.println(solve(strings, false));
        System.out.println(solve(strings, true));
    }

    private static int solve(List<String> strings, boolean p2) {
        int x=0;
        int y=0;
        int aim=0;
        for (String string : strings) {
            String[] split = string.split(" ");
            switch (split[0]) {
                case "up" -> {
                    aim -= Integer.parseInt(split[1]);
                    if (!p2) {
                        y -= Integer.parseInt(split[1]);
                    }
                }
                case "down" -> {
                    aim += Integer.parseInt(split[1]);
                    if (!p2) {
                        y += Integer.parseInt(split[1]);
                    }
                }
                case "forward" -> {
                    x += Integer.parseInt(split[1]);
                    if (p2) {
                        y += (aim * Integer.parseInt(split[1]));
                    }
                }
            }
        }
        return x * y;
    }
}
