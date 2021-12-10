import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;

public class Day01 {
    public static void main(String[] args) throws IOException {
        int[] ints = Files.readAllLines(Path.of("day01.txt")).stream().mapToInt(Integer::parseInt).toArray();

        System.out.println(IntStream.range(1, ints.length).filter(i1 -> ints[i1] > ints[i1 - 1]).count());

        int a = 0;
        for (int i = 3; i < ints.length; i++) {
            int s1 = ints[i - 2] + ints[i - 1] + ints[i];
            int s2 = ints[i - 3] + ints[i - 2] + ints[i - 1];
            if (s1 > s2) a++;
        }
        System.out.println(a);
    }
}
