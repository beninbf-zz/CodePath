import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class optimal_solution{


    public static void main(String[] args) throws IOException{
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int arrCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Character> arr = new ArrayList<>();

        for (int i = 0; i < arrCount; i++) {
            char arrItem = bufferedReader.readLine().charAt(0);
            arr.add(arrItem);
        }

        List<Character> result = Result.sort_array(arr);

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
class Result {

    /*====================START========================*/
    public static List<Character> sort_array(List<Character> arr) {
        int frequency[] = new int[128]; /*an array to store the number of 
                                        occurence of each character in the string*/
        for(char c : arr){
            frequency[c]++;
        }
        arr.clear();
        for(int i  = 0; i<128;i++){ /*traversing from charcter having 
                              lowest ascii value to that of highest ascii value*/
            for(int j = 0; j<frequency[i]; j++){
                arr.add((char)i); /*appending the result with the 
                        number of occurences of character in the string*/
            }
        }
        return arr;
    }
    /*====================END========================*/
}