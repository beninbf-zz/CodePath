import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {

        // TODO Auto-generated method stub

        Scanner sc = new Scanner(System.in);
        String text = sc.nextLine(), pattern = sc.nextLine();
        System.out.println(pattern_matcher(text, pattern));
        sc.close();
    }

    // --------------------- START ------------------------------------

    public static boolean pattern_matcher(String text,String pattern) {

        int pLen = pattern.length();

        //This will store true at location i, if simplifiedPattern has "*" character at location i.

        boolean isStarChar[] = new boolean[pLen];
        String simplifiedPattern = simplifyPattern(pattern, isStarChar, pLen);
        return matcherBrute(simplifiedPattern, text, 0, 0,isStarChar);
        
    }

    /* 
    * Below function will remove duplicate '*' characters and '*' itself from pattern String, and will
    * return that string.
    * e.g. pattern = [a*a*bcd*] returns [abcd] and make true to isStarChar at position {0,3}. 
    */

    public static String simplifyPattern(String pattern, boolean isStarChar[], int pLen) {

            int ind = 0;
            char simplifiedChars[] = new char[pattern.length()];

            for(int i = 0 ; i < pLen ; ) {

                simplifiedChars[ind] = pattern.charAt(i);

                // If i'th character is followed by '*', then mark isStartChar[i] true.
                if(i + 1 < pLen && pattern.charAt(i+1) == '*') {

                    // Below 'if' condition is to handle Duplicate character.
                    // e.g. [a*a*bc*dd*] = [a*bc*dd*] because, 
                    //      you can write [a*a*] = [a*] which meaning is same.

                    if(ind - 1 >= 0 && isStarChar[ind-1] && simplifiedChars[ind-1] == simplifiedChars[ind]) {
                        ind--;
                    } else {
                        isStarChar[ind] = true;
                    }

                    // i+1'th character is "*". So, i increases by 2.

                    i = i + 2;
                } else {
                    i++;
                }
                ind++;
            }

            // Converting character array to string for simplicity.
            
            return new String(simplifiedChars , 0 , ind);
    }

    public static boolean matcherBrute(
                            String pattern, String text, int pInd, int tInd, boolean isStarChar[] 
                                         ){

        // If both the pointer reaches at the end, then it matches.

        if(pInd == pattern.length() && tInd == text.length()) {
            return true;
        }

        // If only pattern pointer reaches at the end, then there is no match.

        if(pInd == pattern.length()) {
            return false;
        }

        /*  If only text pointer reaches at the end,
        *  then there should be only star characters ("*") in pattern for match.
        */

        if(tInd == text.length()) {
            
            while(pInd < pattern.length()) {
                if( !isStarChar[pInd] ) {
                    return false;
                }
                pInd++;
            }

            return true;
        }

        /* When pattern character is not followed by '*', 
        *  but If it is a '.' or matches with the text character,
        *  then increases both the pointer and check.
        */

        if( 
           !isStarChar[pInd] && (pattern.charAt(pInd) == '.' 
               || pattern.charAt(pInd) == text.charAt(tInd))
                    ) {
                        
            return matcherBrute(pattern, text, pInd + 1, tInd + 1, isStarChar);
            
        }

        /* If pattern character has '*', then there can be two cases,
        *      1) It's a '.' (2 cases)
        .* 	        a) Match with the text character
        *           b) Ignore the '.'
        *       
        *       2) It's an alphabet
        *           if it matches with text character (2 cases)
        *               a) consider the pattern character
        *               b) ignore the pattern character
        *           else
        *               a) ignore the pattern character
        */

        if(isStarChar[pInd]) {

            if(pattern.charAt(pInd) == '.') {
                return matcherBrute(pattern, text, pInd, tInd + 1, isStarChar) 
                        || matcherBrute(pattern, text, pInd + 1 , tInd, isStarChar);
                
            } else {
                if(pattern.charAt(pInd) == text.charAt(tInd)) {
                    return matcherBrute(pattern, text, pInd, tInd + 1, isStarChar) 
                    || matcherBrute(pattern, text, pInd + 1 , tInd, isStarChar);

                } else {
                    return matcherBrute(pattern, text, pInd + 1, tInd, isStarChar);

                }
            }
        }

        return false;
    }

    // ----------------------------- STOP -------------------------
}