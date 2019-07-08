#include <bits/stdc++.h>

using namespace std;

struct LinkedListNode{
    int val;
    LinkedListNode *next;

    LinkedListNode(int _val){
        val = _val;
        next = NULL;
    }
};

LinkedListNode* createLinkedList(int *inputArray, int inputSize){
    LinkedListNode *head = NULL;
    LinkedListNode *tail = NULL;
    for(int i = 0; i<inputSize; i++){
        LinkedListNode *currentNode = new LinkedListNode(inputArray[i]);
        if(head == NULL){
            head = currentNode;
            tail = head;
        } else{
            tail->next = currentNode;
            tail = tail->next;
        }
    }
    return head;
}

LinkedListNode* readInput(ifstream &fin){
    int n; fin>>n;
    int *ar;
    ar = new int[n];
    for(int i=0;i<n;i++){
        fin>>ar[i];
    }
    return createLinkedList(ar, n);
}

// ============================ Start ============================
/*
    Complete the following methods
*/

vector<LinkedListNode*> alternativeSplit(LinkedListNode *head){
    LinkedListNode *evenListHead = NULL;
    LinkedListNode *evenListTail = NULL;

    LinkedListNode *oddListHead = NULL;
    LinkedListNode *oddListTail = NULL;

    int isEvenIndex = 1;
    while(head){
        LinkedListNode *currentNode = new LinkedListNode(head->val);
        if(isEvenIndex == 1){
            if(evenListHead == NULL){
                evenListHead = currentNode;
                evenListTail = currentNode;
            } else {
                evenListTail->next = currentNode;
                evenListTail = evenListTail->next;
            }
        } else {
            if(oddListHead == NULL){
                oddListHead = currentNode;
                oddListTail = currentNode;
            } else {
                oddListTail->next = currentNode;
                oddListTail = oddListTail->next;
            }
        }
        isEvenIndex = isEvenIndex^1;
        head = head->next;
    }
    vector<LinkedListNode*> result;
    result.push_back(evenListHead);
    result.push_back(oddListHead);
    return result;
}

// ============================ End ============================

void printList(LinkedListNode *head, ofstream &cout){
    int id = 0;
    while(head){
        if(id > 0) cout<<" ";
        cout<<head->val;
        head = head->next;
        id++;
    }
    cout<<endl;
}

void solve(string inputFileName, string outputFileName){
    ifstream fin(inputFileName);
    ofstream fout(outputFileName);
    int testCase; fin>>testCase;
    while(testCase--){
        LinkedListNode *root = readInput(fin);
        vector<LinkedListNode*> result = alternativeSplit(root);
        if(result.size()!=2){
            cerr<<"Answer size must be 2"<<endl;
            return;
        }
        printList(result[0], fout);
        printList(result[1], fout);
    }
}

int main(){
    solve("..//test_cases//sample_test_cases_input.txt", "..//test_cases//sample_test_cases_expected_output.txt");
    solve("..//test_cases//handmade_test_cases_input.txt", "..//test_cases//handmade_test_cases_expected_output.txt");
    solve("..//test_cases//generated_big_test_cases_input.txt", "..//test_cases//generated_big_test_cases_expected_output.txt");
    return 0;
}    

