import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Day03 {
    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Path.of("day03.txt"));

        int b[] = new int[12];
        int t = 0;
        for (String s : input) {
            t++;
            for (int i=0; i<s.length(); i++) {
                if (s.charAt(i) == '1') {
                    b[i]++;
                }
            }
        }
        int res = 0;
        int res2 = 0;
        for (int i = 0; i < b.length; i++) {
            int i1 = b[i];
            res <<= 1;
            res2 <<= 1;
            if (i1 > (t/2)) {
                res += 1;
            } else {
                res2 += 1;
            }
        }
        System.out.println(res * res2);

        int a = count(input, 0, true);
        int bb = count(input, 0, false);
        System.out.println(a*bb);
    }

    private static int count(List<String> input, int i, boolean b) {
        if (input.size()==1) return Integer.parseInt(input.get(0), 2);
        int sum = 0;
        for (String s : input) {
            if (s.charAt(i) == '1') {
                sum++;
            }
        }
        boolean same =  (sum == input.size() / 2.0);
        boolean ones = (sum > input.size()/2.0);
        if (b) {
            return count(input.stream().filter(a-> (same||ones)?a.charAt(i)=='1':a.charAt(i)=='0').collect(Collectors.toList()), i+1, b);
        } else {
            return count(input.stream().filter(a-> (same||ones)?a.charAt(i)=='0':a.charAt(i)=='1').collect(Collectors.toList()), i+1, b);
        }
    }
}
