import java.io.*;
import java.util.*;

class Result {

    // ============================ Start ============================
    public static List<Boolean> equalSubSetSumPartition(List<Integer> s) {
        List<Boolean> result = new ArrayList<Boolean>();

        // Store min and max sum possible for given array
        int negative_nos_sum = 0;
        int positive_nos_sum = 0;
        int n = s.size();

        // Calculate min and max sum
        for (int i=0; i<n; i++) {
            if (s.get(i) < 0) {
                negative_nos_sum += s.get(i);
            } else {
                positive_nos_sum += s.get(i);
            }
        }

        if ((negative_nos_sum + positive_nos_sum) % 2 > 0) {
            return result;
        }

        DefaultHashMap<Integer, Boolean> dp[] = new DefaultHashMap[n];
        for (int i=0; i<n; i++) {
            dp[i] = new DefaultHashMap<Integer, Boolean>(Boolean.FALSE);
        }

        // base state
        // for idx 0 only one sum s[0] is possible
        dp[0].put(s.get(0), true);

        // iterate on all idx
        for (int i=1; i<n; i++) {
            // iterate on all possible subset sum
            for (int val = negative_nos_sum; val <= positive_nos_sum; val++) {
                // dp state-transition
                // 1) state(i,val) = state(i-1,val) without taking current element
                dp[i].put(val, dp[i - 1].get(val));

                // 2) if val == s[i], just taking ith element is sufficient
                if (val == s.get(i)) {
                    dp[i].put(val, true);
                } else if (val - s.get(i) - negative_nos_sum >= 0) {
                    // 3) state(i,val) = state(i-1, val-s[i]) when taking current element
                    dp[i].put(val, dp[i].get(val) || dp[i - 1].get(val - s.get(i)));
                }
            }
        }
        int required_sum = (negative_nos_sum + positive_nos_sum) / 2;
        int idx = n-1;

        // Partition not possible
        if (dp[idx].get(required_sum) == Boolean.FALSE) {
            return result;
        }

        // tracks partition elements
        result = new ArrayList<Boolean>(Arrays.asList(new Boolean[n]));
        Collections.fill(result, Boolean.FALSE);

        // tracks count of elements included in S1
        int cnt = 0;
        while (idx >= 0) {
            if (idx != 0) {
                // reverse dp transition
                if (dp[idx].get(required_sum) && dp[idx - 1].get(required_sum) == Boolean.FALSE) {
                    result.set(idx, Boolean.TRUE);
                    cnt++;
                    required_sum -= s.get(idx);
                    if (required_sum == 0) {
                        break;
                    }
                }
            } else {
                result.set(idx, Boolean.TRUE);
                cnt++;
            }
            idx--;
        }
        // if all elements are included in S1
        // All elements will be in S1 if total_sum = 0
        // case when s = [-2,2]
        // partition is not possible in this case
        return (cnt == n || cnt == 0)? new ArrayList<Boolean>() : result;
    }

    public static class DefaultHashMap<K,V> extends HashMap<K,V> {
        protected V defaultValue;
        public DefaultHashMap(V defaultValue) {
            this.defaultValue = defaultValue;
        }
        
        @Override
        public V get(Object k) {
            return containsKey(k) ? super.get(k) : defaultValue;
        }
    }
    // ============================= End ==============================
}

class Solution {
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
        
        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> s = new ArrayList<Integer>();

        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(bufferedReader.readLine().trim());
            s.add(num);
        }

        bufferedReader.close();
        List<Boolean> result = Result.equalSubSetSumPartition(s);

        if (result.size() == 0) {
            bufferedWriter.write("-1\n");
            bufferedWriter.close();
            return;
        }

        int s1 = 0, s2 = 0;

        for (int i = 0; i < result.size(); i++) {
            if (result.get(i) == true) {
                s1++;
            }
            else {
                s2++;
            }
        }

        bufferedWriter.write(String.valueOf(s1));
        bufferedWriter.newLine();
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i) == true) {
                bufferedWriter.write(String.valueOf(s.get(i)));
                bufferedWriter.newLine();
            }
        }

        bufferedWriter.write(String.valueOf(s2));
        bufferedWriter.newLine();
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i) == false) {
                bufferedWriter.write(String.valueOf(s.get(i)));
                bufferedWriter.newLine();
            }
        }

        bufferedWriter.close();
    }
}
