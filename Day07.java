import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.lang.Math.abs;
import static java.util.Arrays.stream;
import static java.util.stream.IntStream.*;

public class Day07 {
    public static void main(String[] args) throws IOException {
        List<String> file = Files.readAllLines(Path.of("day07.txt"));
        String input = file.get(0);
        int[] ints = stream(input.split(",")).mapToInt(Integer::parseInt).sorted().toArray();
        int p1 = rangeClosed(ints[0], ints[ints.length - 1]).map(i -> of(ints).map(a -> abs(a - i)).sum()).min().orElse(-1);
        int p2 = rangeClosed(ints[0], ints[ints.length - 1]).map(i -> of(ints).map(a -> range(0, abs(a - i)).sum()).sum()).min().orElse(-1);
        System.out.println(p1);
        System.out.println(p2);
    }
}
