import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class SortCharsOptimalSolution {
    public static void main(String[] args) throws IOException{
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int arrCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Character> arr = new ArrayList<>();

        for (int i = 0; i < arrCount; i++) {
            char arrItem = bufferedReader.readLine().charAt(0);
            arr.add(arrItem);
        }

        List<Character> result = SortCharsOptimalSolutionResult.sort_array(arr);
        for (int i = 0; i < result.size(); i++) {
            bufferedWriter.write(String.valueOf(result.get(i)));
            if (i != result.size() - 1) {
                bufferedWriter.write("\n");
            }
        }

        bufferedWriter.newLine();
        bufferedWriter.close();
        bufferedReader.close();
    }

}
class SortCharsOptimalSolutionResult {
    public static List<Character> sort_array(List<Character> arr) {
        /* an array to store the number of occurrence of each character in the string */
        int[] frequency = new int[128]; 
        for(char c : arr){
            frequency[c]++;
        }
        arr.clear();
        /* traversing from character having lowest ascii value to that of highest ascii value */
        for(int i  = 0; i < 128; i++){ 
            for(int j = 0; j < frequency[i]; j++){
                /* appending the result with the  number of occurrences of character in the string */
                arr.add((char)i);
            }
        }
        return arr;
    }
}
