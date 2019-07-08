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

    void link_random_node(SinglyLinkedListNode *random_node) {
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

};

void print_singly_linked_list_with_random_node(SinglyLinkedListNode *node, string sep)
{   
    string delimitter = " ";
    while (node)
    {
        cout << node->data << delimitter;

        if(node->next != nullptr) {
            cout << node->next->data << delimitter;
        }
        else
            cout << -1 << delimitter;

        if(node->randomPointer != nullptr) {
            cout << node->randomPointer->data;
        }
        else {
            cout << "-1";
        }

        node = node->next;

        if (node)
        {
            cout << sep;
        }
    }
}

bool cloneCheck(SinglyLinkedListNode *head,unordered_set < SinglyLinkedListNode* > originalNodes) {
    SinglyLinkedListNode *tmp = head;
    while(tmp) {
        if(originalNodes.find(tmp) != originalNodes.end()) {
            return false;
        }
        tmp = tmp->next;
    }
    return true;
}

// ------------------------------ START ------------------------------

/*
 * @param list: pointer to the head node of the original linkedList
 * @returns the pointer to the newly cloned linkedList
 * This function clones the provided linkedList
 * Time Complexity : O(N*N)
 */
SinglyLinkedListNode* cloneLinkedList(SinglyLinkedListNode *list) {
    // initializing new clone list
    SinglyLinkedListNode *clonedListHead = nullptr;
    SinglyLinkedListNode *clonedListTail = nullptr;

    // first cloning the list by traversing the next pointeres
    SinglyLinkedListNode *headIterator = list;

    while (headIterator)
    {
        if (clonedListHead == nullptr)
        {
            clonedListHead = new SinglyLinkedListNode(headIterator->data);
            clonedListTail = clonedListHead;
        }
        else
        {
            clonedListTail->next = new SinglyLinkedListNode(headIterator->data);
            clonedListTail = clonedListTail->next;
        }
        headIterator = headIterator->next;
    }

    SinglyLinkedListNode *clonedHeadIterator = clonedListHead;
    headIterator = list;

    // now cloning link for random pointers
    while (headIterator)
    {   
        // getting random linked node for current node from original list
        SinglyLinkedListNode *randomNode = headIterator->randomPointer;
        if(randomNode == nullptr) {
            // if no random link exists 
            // mark the same for cloned list node
            clonedHeadIterator->randomPointer = nullptr;
        }
        else {
            SinglyLinkedListNode *tempHead = list;
            // this stores the distance of the randomNode from the head of 
            //  original list
            int steps = 0;

            // iteratig to count the steps required to reach randomNode
            while(tempHead != randomNode) {
                steps+=1;
                tempHead = tempHead->next;
            }

            // now going steps distance in the cloned list to get the corresponding
            // randomNode from the original list
            tempHead = clonedListHead;
            while(steps--) {
                tempHead = tempHead->next;
            }

            // linking the random node
            clonedHeadIterator->randomPointer = tempHead;
        }

        // moving to next original node 
        headIterator = headIterator->next;
        // moving to correspnding next cloned node
        clonedHeadIterator = clonedHeadIterator->next;
    }
    return clonedListHead;
}

// ------------------------------ STOP -------------------------------

int main()
{   
    unordered_map < int , SinglyLinkedListNode* > mapper;
    unordered_set < SinglyLinkedListNode* >originalNodes;
    SinglyLinkedList *List = new SinglyLinkedList();

    string linkedList_count_temp;
    getline(cin, linkedList_count_temp);

    int linkedList_count = stoi(ltrim(rtrim(linkedList_count_temp)));

    for(int i=1;i<=linkedList_count;i++) {
        List->insert_node(i);
        mapper[i] = List->tail;
        originalNodes.insert(List->tail);
    }

    for (int i = 1; i <= linkedList_count; i++) {
        string linkedList_item_temp;
        getline(cin, linkedList_item_temp);
        int linkedList_item = stoi(ltrim(rtrim(linkedList_item_temp)));
        if(linkedList_item != 0)
            mapper[i]->link_random_node(mapper[linkedList_item]);
    }

    SinglyLinkedListNode *clonedListHead = cloneLinkedList(List->head);

    if (cloneCheck(clonedListHead, originalNodes) == false)
    {
        cerr << "linked list not cloned";
        assert(false);
    }
    else {
        string seperator = "\n";
        print_singly_linked_list_with_random_node(clonedListHead,seperator);
    }

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
