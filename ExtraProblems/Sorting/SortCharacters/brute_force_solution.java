import java.io.*;
import java.util.*;

public class brute_force_solution{

    public static void main(String[] args) throws IOException{
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int arrCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Character> arr = new ArrayList<>();

        for (int i = 0; i < arrCount; i++) {
            char arrItem = bufferedReader.readLine().charAt(0);
            arr.add(arrItem);
        }

        List<Character> ch = Result.sort_array(arr);

        for (int i = 0; i < ch.size(); i++) {
            bufferedWriter.write(String.valueOf(ch.get(i)));

            if (i != ch.size() - 1) {
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
        int n = arr.size();
        char ch[] = new char[n];
        for(int i = 0; i<n; i++) ch[i] = arr.get(i);
        for(int i = 0; i<n; i++){
            char min = ch[i]; // stores minimum character encountered so far
            int temp = i; // stores the position of the minimum character
            for (int j = i+1; j<n; j++){
                if(ch[j]<min){
                    min = ch[j]; /*updating the min value whenever we 
                                 encounter a character having a lower ascii value.*/
                    temp = j; /*updating the temp value with the position 
                              of the character having a lower ascii value.*/
                }
            }
            char c = ch[i];
            ch[i] = min;
            ch[temp] = c; 
        }
        List<Character> result = new ArrayList<>();
        for(char c: ch) result.add(c); 
        return result;
    }
    /*====================END========================*/
}