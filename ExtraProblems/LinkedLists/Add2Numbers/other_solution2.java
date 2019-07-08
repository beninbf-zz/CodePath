import java.io.*;

class SinglyLinkedListNode {
    public int data;
    public SinglyLinkedListNode next;

    public SinglyLinkedListNode(int nodeData) {
        this.data = nodeData;
        this.next = null;
    }
}

class SinglyLinkedList {
    public SinglyLinkedListNode head;
    public SinglyLinkedListNode tail;

    public SinglyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    public void insertNode(int nodeData) {
        SinglyLinkedListNode node = new SinglyLinkedListNode(nodeData);

        if (this.head == null) {
            this.head = node;
        } else {
            this.tail.next = node;
        }

        this.tail = node;
    }
}

class SinglyLinkedListPrintHelper {
    public static void printList(SinglyLinkedListNode node, String sep, BufferedWriter bufferedWriter) throws IOException {
        while (node != null) {
            bufferedWriter.write(String.valueOf(node.data));

            node = node.next;

            if (node != null) {
                bufferedWriter.write(sep);
            }
        }
    }
}

class Result {

    /*
     * Complete the 'addTwoNumbers' function below.
     *
     * The function is expected to return an INTEGER_SINGLY_LINKED_LIST.
     */
    
    // ============================ Start ============================

    static SinglyLinkedListNode addTwoNumbers(SinglyLinkedListNode l_a, SinglyLinkedListNode l_b) {
        
        int lenA = 0, lenB = 0;

        // Finding length of l_a
        SinglyLinkedListNode travA = l_a;
        while (travA != null) {
            lenA++;
            travA = travA.next;
        }

        // Finding length of l_a
        SinglyLinkedListNode travB = l_b;
        while (travB != null) {
            lenB++;
            travB = travB.next;
        }

        // if lenA > lenB, swap linked list pointers, so that after this point onwards,
        // l_a contains smaller length linked list out of given two input linked lists
        if (lenA > lenB) {
            SinglyLinkedListNode l_temp = l_a;
            l_a = l_b;
            l_b = l_temp;
        }

        int carryForward = 0;
        travA = l_a;
        travB = l_b;

        while (true) {
            // ith iteration of this loop denotes that we are processing ith significant digit
            int l_a_data = (travA == null) ? 0 : travA.data;

            // let say sumD = travB.data + travA.data + carryForward
            travB.data += l_a_data + carryForward;

            // Setting carryForward = sumD/10
            carryForward = travB.data / 10;

            // In ith iteration, setting ith significant digit of sum = sumD % 10
            travB.data = travB.data % 10;

            // Moving on to (i+1)th significant digit
            if(travA != null)
                travA = travA.next;
            
            if (travB.next == null){
                // If carryForward>0, Adding a node in l_b, which denotes new most significant digit of sum
                // as there is no more digit to carry forward the carryForward
                if (carryForward > 0) {
                    SinglyLinkedListNode new_node = new SinglyLinkedListNode(carryForward);
                    travB.next = new_node;
                }
                break;
            }
            travB = travB.next;
        }

        return l_b;
    }
    
    // ============================ End =============================
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
        
        SinglyLinkedList l_a = new SinglyLinkedList();
        int lenA = Integer.parseInt(bufferedReader.readLine().trim());

        for (int i = 0; i < lenA; i++) {
            int node = Integer.parseInt(bufferedReader.readLine().trim());

            l_a.insertNode(node);
        }

        SinglyLinkedList l_b = new SinglyLinkedList();

        int lenB = Integer.parseInt(bufferedReader.readLine().trim());

        for (int i = 0; i < lenB; i++) {
            int node = Integer.parseInt(bufferedReader.readLine().trim());

            l_b.insertNode(node);
        }

        SinglyLinkedListNode result = Result.addTwoNumbers(l_a.head, l_b.head);

        SinglyLinkedListPrintHelper.printList(result, "\n", bufferedWriter);
        bufferedWriter.write("\n");
        bufferedWriter.close();
    }
}

/**
 * Time complexity: O(lenA+lenB)
 * Space complexity: O(lenA+lenB)
 */

