import java.io.*;
import java.util.*;

public class suboptimal_solution{

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
        
        Collections.sort(arr); //Sorting using inbuilt sort function
        return arr;

    }

    /*====================END========================*/

}