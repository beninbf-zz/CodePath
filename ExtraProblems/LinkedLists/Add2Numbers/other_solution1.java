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

        // To store carry while adding two numbers
        int carryForward = 0;
        // Creating another singly linked list to store resultant sum
        SinglyLinkedListNode result = new SinglyLinkedListNode(-1);
        // To store head of result list, so we can return at the end of function
        SinglyLinkedListNode head = result;

        while(l_a!=null||l_b!=null) {
            int l_a_data = 0;
            if(l_a != null){
                l_a_data = l_a.data;
                l_a = l_a.next;
            }

            int l_b_data = 0;
            if(l_b != null){
                l_b_data = l_b.data;
                l_b = l_b.next;
            }

            int sum = l_a_data + l_b_data + carryForward;
            // If it's first node of result linkedlist then update data from -1 to sum%10.
            if(result.data == -1){
                result.data = sum%10;
            }
            else{
                // Create new next node in result linkedlist.
                SinglyLinkedListNode new_node = new SinglyLinkedListNode(sum%10);
                result.next = new_node;
                result = result.next;
            }
            carryForward = sum/10;
        }
        // If still carry is remaining then create another node in result linkedlist to store carry.
        if(carryForward>0){
            SinglyLinkedListNode new_node = new SinglyLinkedListNode(carryForward);
            result.next = new_node;
        }
        // Result head node
        return head;
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

