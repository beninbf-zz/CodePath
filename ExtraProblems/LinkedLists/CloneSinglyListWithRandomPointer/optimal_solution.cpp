#include <bits/stdc++.h>

using namespace std;

string ltrim(const string &);
string rtrim(const string &);

class SinglyLinkedListNode
{
  public:
    int data;
    SinglyLinkedListNode *next;
    SinglyLinkedListNode *randomPointer;

    SinglyLinkedListNode(int node_data)
    {
        this->data = node_data;
        this->next = nullptr;
        this->randomPointer = nullptr;
    }

    // overloaded funtion to directly add constructed node
    void link_random_node(SinglyLinkedListNode *random_node)
    {
        this->randomPointer = random_node;
    }
};

class SinglyLinkedList
{
  public:
    SinglyLinkedListNode *head;
    SinglyLinkedListNode *tail;

    SinglyLinkedList()
    {
        this->head = nullptr;
        this->tail = nullptr;
    }

    void insert_node(int node_data)
    {
        SinglyLinkedListNode *node = new SinglyLinkedListNode(node_data);

        if (!this->head)
        {
            this->head = node;
        }
        else
        {
            this->tail->next = node;
        }

        this->tail = node;
    }

    void insert_node(SinglyLinkedListNode *node)
    {
        if (!this->head)
        {
            this->head = node;
        }
        else
        {
            this->tail->next = node;
        }

        this->tail = node;
    }
};

void print_singly_linked_list_with_random_node(SinglyLinkedListNode *node, string sep)
{   
    string delimitter = " ";
    while (node)
    {
        cout << node->data << delimitter;

        if (node->next != nullptr)
        {
            cout << node->next->data << delimitter;
        }
        else
            cout << -1 << delimitter;

        if (node->randomPointer != nullptr)
        {
            cout << node->randomPointer->data;
        }
        else
        {
            cout << "-1";
        }

        node = node->next;

        if (node)
        {
            cout << sep;
        }
    }
}

bool cloneCheck(SinglyLinkedListNode *head, unordered_set<SinglyLinkedListNode *> originalNodes)
{
    SinglyLinkedListNode *tmp = head;
    while (tmp)
    {
        if (originalNodes.find(tmp) != originalNodes.end())
        {
            return false;
        }
        tmp = tmp->next;
    }
    return true;
}

// ------------------------------ START ------------------------------

/*
 * @param list: pointer to the head node of the original linkedList
 * @returns the pointer to the newly cloned linkedList head node
 * This function clones the provided linkedList
 * Time Complexity : O(N)
 */
SinglyLinkedListNode *cloneLinkedList(SinglyLinkedListNode *list)
{   
    // Step 1:
    // inserting duplicate nodes between the consecutive nodes of 
    // original linked list
    // initial list:
    //  1 -- 2 -- 3 -- 4 -- null  (with some random links at each node)
    // after inserting new duplicate nodes
    //  1 -- {1} -- 2 -- {2} -- 3 -- {3} -- 4 -- {4} -- null
    SinglyLinkedListNode *tmpHead = list;
    while(tmpHead) {
        SinglyLinkedListNode *tmpNext = tmpHead -> next;
        tmpHead->next = new SinglyLinkedListNode(tmpHead->data);
        tmpHead->next->next = tmpNext;
        tmpHead = tmpNext;
    }

    // Step 2:
    // linking the random nodes for cloned nodes corresponding to
    // to its original node 
    tmpHead = list;
    while(tmpHead) {
        // getting the corresponding current node of the cloned list;
        // through out the iteration the "next" node of the current original
        // node tmpHead will give the current node of cloned list
        SinglyLinkedListNode *currentClonedNode = tmpHead->next;

        // getting the random linked node from the original list node
        SinglyLinkedListNode *tmpRandomLinkedNode = tmpHead->randomPointer;

        if(tmpRandomLinkedNode != nullptr) {
        // getting the corresponding random linked node for the cloned node;
        // as the "next" node of the tmpHead->randomPointer will point to the 
        // corresponding the random link node for the current cloned node
        tmpRandomLinkedNode = tmpRandomLinkedNode->next;
        }

        // linking the random pointed node for the current cloned node
        currentClonedNode->randomPointer = tmpRandomLinkedNode;

        // moving to next node of original linked list by jumping
        // two steps odd indexed nodes are original nodes (1-based indexing)
        tmpHead = tmpHead->next->next;
    }

    // Step 3:
    // detaching the cloned node from the original list 
    // and restoring the original list.
    // also extracting the cloned list nodes and forming the 
    // cloned linked list.
    tmpHead = list;
    SinglyLinkedListNode *clonedListHead = nullptr;
    SinglyLinkedListNode *clonedListTail = nullptr;
    while (tmpHead)
    {   
        // getting corresponding cloned node to tmpHead
        SinglyLinkedListNode *currentClonedNode = tmpHead->next;

        // detaching the cloned node and restoring original "next" node
        tmpHead->next = currentClonedNode->next;

        // building/chaining the cloned nodes to form separate linkedlist
        if(clonedListHead == nullptr) {
            clonedListHead = currentClonedNode;
            clonedListTail = clonedListHead;
        }
        else {
            clonedListTail->next = currentClonedNode;
            clonedListTail = clonedListTail->next;
        }

        // moving to next node of original linked list by jumping
        // one step as we have restored original configuration till this node
        tmpHead = tmpHead->next;
    }

    return clonedListHead;
}

// ------------------------------ STOP -------------------------------

int main()
{
    // freopen(
    //     "..//test_cases//handmade_test_cases_input.txt", "r",
    //     stdin);
    // freopen(
    //     "..//test_cases//handmade_test_cases_expected_output.txt", "w",
    //     stdout);

    // freopen(
    //     "..//test_cases//generated_medium_test_cases_input.txt", "r",
    //     stdin);

    // freopen(
    //     "..//test_cases//generated_medium_test_cases_expected_output.txt", "w",
    //     stdout);

    // freopen(
    //     "..//test_cases//generated_big_test_cases_input.txt", "r",
    //     stdin);

    // freopen(
    //     "..//test_cases//generated_big_test_cases_expected_output.txt", "w",
    //     stdout);

    // string testcases;
    // getline(cin,testcases);
    // int t = stoi(ltrim(rtrim(testcases)));
    // while(t--) {
    unordered_map<int, SinglyLinkedListNode *> mapper;
    unordered_set<SinglyLinkedListNode *> originalNodes;
    SinglyLinkedList *List = new SinglyLinkedList();

    string linkedList_count_temp;
    getline(cin, linkedList_count_temp);

    int linkedList_count = stoi(ltrim(rtrim(linkedList_count_temp)));

    for (int i = 1; i <= linkedList_count; i++)
    {
        List->insert_node(i);
        mapper[i] = List->tail;
        originalNodes.insert(List->tail);
    }

    for (int i = 1; i <= linkedList_count; i++)
    {
        string linkedList_item_temp;
        getline(cin, linkedList_item_temp);
        int linkedList_item = stoi(ltrim(rtrim(linkedList_item_temp)));
        if (linkedList_item != -1)
            mapper[i]->link_random_node(mapper[linkedList_item]);
    }

    SinglyLinkedListNode *clonedListHead = cloneLinkedList(List->head);

    if (cloneCheck(clonedListHead, originalNodes) == false)
    {
        cerr << "linked list not cloned";
        assert(false);
    }
    else
    {
        string seperator = "\n";
        // cout << linkedList_count << endl;
        print_singly_linked_list_with_random_node(clonedListHead, seperator);
    }
    // if(t)
    //     cout << endl;
    // }

    return 0;
}

string ltrim(const string &str)
{
    string s(str);

    s.erase(
        s.begin(),
        find_if(s.begin(), s.end(), not1(ptr_fun<int, int>(isspace))));

    return s;
}

string rtrim(const string &str)
{
    string s(str);

    s.erase(
        find_if(s.rbegin(), s.rend(), not1(ptr_fun<int, int>(isspace))).base(),
        s.end());

    return s;
}
