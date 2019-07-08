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
        
        SinglyLinkedListNode result = l_b;
        // We are storing resultant sum in l_b.
        int carryForward = 0, sum=0;
        // To store carry and current sum.
        // We are iterating till we reach at end of one of linkedlist.
        // and update l_b with resultant sum.
        while(true){
            sum = l_a.data + l_b.data + carryForward;
            l_b.data = sum%10;
            carryForward = sum/10;
            if(l_a.next == null || l_b.next == null){
                break;
            }
            l_a = l_a.next;
            l_b = l_b.next;
        }
        // If we reached at end of l_b but l_a is still remaining then we point next of l_b to next of l_a. So, we can utilise already created linkedlist node of l_a by appending it with l_b.
        if(l_a.next != null && l_b.next == null){
            l_b.next = l_a.next;
        }
        // We iterate through remaining nodes of l_b and update it with sum of node and carry.
        while(carryForward > 0 && l_b.next != null){
            l_b = l_b.next;
            sum = carryForward + l_b.data;
            l_b.data = sum%10;
            carryForward = sum/10;
        }
        // If still carry is remaining then we add extra node at tail of linkedlist l_b.
        if(carryForward > 0){
            SinglyLinkedListNode new_node = new SinglyLinkedListNode(carryForward);
            l_b.next = new_node;
        }
        // Result will be head node of l_b (which is storing our resultant sum)
        return result;
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

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        SinglyLinkedListNode result = Result.addTwoNumbers(l_a.head, l_b.head);

        SinglyLinkedListPrintHelper.printList(result, "\n", bufferedWriter);
        bufferedWriter.newLine();

        bufferedWriter.close();
    }
}

/**
 * Time complexity: O(lenA+lenB)
 * Space complexity: O(lenA+lenB)
 */

