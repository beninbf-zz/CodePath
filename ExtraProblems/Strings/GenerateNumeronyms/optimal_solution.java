import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Result {

    // -------------------- START ----------------------
    static String[] neuronyms(String word) {
        int n = word.length();

        List<String> neuronyms_strings = new ArrayList<>();

        String answer[];

        if(n<=3){
            answer = new String[0];
            return answer;
        }

        String temp;
        // Iterating over all possible length of valid substrings that can be omitted
        for (int len = 2; len <= n - 2; len++) {
            // Iterating over all possible starting point of valid substrings of length len that can be omitted
            for (int i = 1; i <= n-1-len; i++) {
                temp = word.substring(0, i) + len + word.substring(i + len, n);
                neuronyms_strings.add(temp);
            }
        }
        
        int size = neuronyms_strings.size();
        answer = new String[size];

        for(int i=0;i<size;i++){
            answer[i] = neuronyms_strings.get(i);
        }

        return answer;
    }

    // -------------------- END ----------------------
}

class Solution{
    public static void main(String args[]) {
        /*
        This function is used to increase the size of recursion stack. It makes the size of stack
        2^26 ~= 10^8
        */
        new Thread(null, new Runnable() {
            public void run() {
                try{
                    solve();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }, "1", 1 << 26).start();
    }
    public static void solve() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        String word = bufferedReader.readLine().trim();

        String [] result = Result.neuronyms(word);

        for(String str: result){
            bufferedWriter.write(str+"\n");
        }
        bufferedWriter.write("\n");
        
        bufferedWriter.close();

        bufferedReader.close();
    }

}
