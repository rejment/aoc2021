import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day06 {
    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Path.of("day06.txt"));

        List<Integer> l = Arrays.stream(input.get(0).split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
        System.out.println(count(l, 80));
        System.out.println(count(l, 256));
    }

    private static long count(List<Integer> fishes, int f) {
        long[] counts = new long[9];
        for (Integer fish : fishes) {
            counts[fish]++;
        }
        for (int i = 0; i < f; i++) {
            long[] temp = new long[9];
            for (int j = 0, tLength = temp.length; j < tLength; j++) {
                long i1 = counts[j];
                if (j == 0) {
                    temp[8] += i1;
                    temp[6] += i1;
                } else {
                    temp[j - 1] += i1;
                }
            }
            counts = temp;
        }
        return Arrays.stream(counts).sum();
    }
}
