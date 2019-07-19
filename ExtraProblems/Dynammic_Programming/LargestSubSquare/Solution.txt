import java.io.*;
import java.util.*;

class Result {

    // -------------------- START ----------------------
    static int largest_sub_square_matrix(int n, int m, List<List<Integer>> mat){
        int max = 0;
        // initializing
        for (int i = 0; i < n; i++)
            max |= mat.get(i).get(0);
        for (int j = 0; j < m; j++)
            max |= mat.get(0).get(j);

        // populating dp
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (mat.get(i).get(j)-1==0) {
                    int value = Math.min(mat.get(i-1).get(j-1), Math.min(mat.get(i-1).get(j), mat.get(i).get(j-1))) + 1;
                    max = Math.max(mat.get(i).get(j), max);
                    mat.get(i).set(j, value); 
                    max = Math.max(mat.get(i).get(j), max);
                }
            }
        }

        // restoring initial input matrix
        for(int i=1;i <n;i++) {
            for(int j=1;j<m;j++)
            if(mat.get(i).get(j) > 1)
                mat.get(i).set(j,1);
        }
        return max;
    }
    // -------------------- END ------------------------
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
        FileReader fr=new FileReader("input.txt"); 
        BufferedReader bufferedReader = new BufferedReader(fr);
        try{
            FileWriter fw=new FileWriter("output.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fw);
            int tc = Integer.parseInt(bufferedReader.readLine().trim());
            while(tc-->0) {
                int n = Integer.parseInt(bufferedReader.readLine().trim());
                assert (1 <= n && n <= 1000) : "invalid value of n";
                int m = Integer.parseInt(bufferedReader.readLine().trim());
                assert (1 <= m && m <= 1000) : "invalid value of m";
                List<List<Integer>> mat = new ArrayList<>();
                for(int i=0; i<n; i++) {
                    String str[] = bufferedReader.readLine().trim().split(" ");
                    assert (str.length == m) : "number of values in row are not equal to m";
                    List<Integer> row = new ArrayList<Integer>();
                    for(int j=0; j<m; j++) {
                        int num = Integer.parseInt(str[j]);
                        row.add(num);
                        assert (num == 0 || num == 1) : "invalid value in matrix";
                    }
                    mat.add(row);
                }
                int ans = Result.largest_sub_square_matrix(n, m, mat);
                bufferedWriter.write(ans+"\n");
            }
            bufferedReader.close();
            bufferedWriter.close();
            fw.close();
        }
        catch(Exception e){System.out.println(e);}
    }
}

/*
 * Time Complexity: O(n*m)
 * Auxiliary Space used: O(1)
 * Space Complexity: O(n*m)
*/