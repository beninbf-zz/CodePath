import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {
    //----------------------------START--------------------------------
    public static boolean is_valid(String expression) {
        boolean result = true;
        // To store digits
        Stack<Integer> st1 = new Stack<>();
        // To store operators and parenthesis
        Stack<Character> st2 = new Stack<>();
        boolean isTrue = true; // To check no continuous digits (excluding braces) without a operator in between
        for (int i = 0; i < expression.length(); i++) {
            char temp = expression.charAt(i);

            if (temp >= '0' && temp <= '9') {
                // If character temp is digit push in st1
                st1.push(temp - '0');
                if(isTrue) {
                    isTrue = false;
                }
                else {
                    return false;
                }
            } else if (temp == '+' || temp == '-' || temp == '*') {
                // If character temp is operator push in st2
                st2.push(temp);
                isTrue = true;
            } else {
                if (temp == '(' || temp == '[' || temp == '{') {
                    // If character temp is left parenthesis push in st2
                    st2.push(temp);
                } else {
                    // If character temp is right parenthesis
                    // Validate expression inside left and right parenthesis
                    boolean flag = true;
                    while (!st2.isEmpty()) {
                        char c = st2.pop();
                        if (c == getCorrespondingChar(temp)) {
                            flag = false;
                            break;
                        } else {
                            if (st1.size() < 2) {
                                return false;
                            }
                            else {
                                st1.pop();
                            }
                        }
                    }
                    if (flag) {
                        return false;
                    }

                }
            }
        }
        while (!st2.isEmpty()) {
            char c = st2.pop();
            if (!(c == '+' || c == '-' || c == '*')) {
                return false;
            }
            if (st1.size() < 2) {
                return false;
            }
            else {
                st1.pop();
            }
        }
        if (st1.size() > 1 || !st2.isEmpty()) {
            return false;
        }
        return result;
    }

    public static char getCorrespondingChar(char c) {
        if (c == ')') {
            return '(';
        }
        else if (c == ']') {
            return '[';
        }
        return '{';
    }
    //----------------------------END--------------------------------
}

class Solution {

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String expression = bufferedReader.readLine();
        
        assert (1 <= expression.length() && expression.length() <= 100000) : "invalid expression length";

        boolean result = Result.is_valid(expression);

        bufferedWriter.write(String.valueOf(result ? 1 : 0));
        bufferedWriter.newLine();

        bufferedWriter.close();

        bufferedReader.close();
    }
}

