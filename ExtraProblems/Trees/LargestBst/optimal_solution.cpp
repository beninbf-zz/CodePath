#include <bits/stdc++.h>

using namespace std;

class TreeNode{
public:
    int val;
    TreeNode* left_ptr;
    TreeNode* right_ptr;

    TreeNode(int _val){
        val = _val;
        left_ptr = NULL;
        right_ptr = NULL;
    }
};

class BinaryTree{
public:
    class Edge{
    public:
        int parentNodeIndex;
        int childNodeIndex;
        string leftRightFlag;

        Edge(){}

        Edge(int _parentNodeIndex, int _childNodeIndex, string _leftRightFlag){
            parentNodeIndex = _parentNodeIndex;
            childNodeIndex = _childNodeIndex;
            leftRightFlag = _leftRightFlag;
        }
    };

    int noOfNodes;
    vector<int> nodeValues;
    vector<TreeNode*> nextRightNodes;
    int rootIndex;
    int noOfEdges;
    vector<Edge> edges;
    TreeNode* root;

    BinaryTree(){
        noOfNodes = 0;
        nodeValues.clear();
        rootIndex = -1;
        noOfEdges = 0;
        edges.clear();
        nextRightNodes.clear();
        root = NULL;
    }

    void readRawValues(ifstream &cin){
        cin >> noOfNodes;
        nodeValues.assign(noOfNodes, 0);
        for(int i = 0; i < noOfNodes; i++){
            cin >> nodeValues[i];
        }

        cin >> rootIndex;

        Edge tempEdge;
        cin >> noOfEdges;
        edges.assign(noOfEdges, tempEdge);
        for(int i = 0; i < noOfEdges; i++){
            cin >> edges[i].parentNodeIndex
                >> edges[i].childNodeIndex 
                >> edges[i].leftRightFlag;
        }
    }

    void buildFromRawValues(){
        if(noOfNodes == 0){
            root = NULL;
            return;
        }

        vector<TreeNode*> nodes(noOfNodes);
        for(int i = 0; i < noOfNodes; i++){
            nodes[i] = new TreeNode(nodeValues[i]);
        }

        for(int i = 0; i < noOfEdges; i++){
            if(edges[i].leftRightFlag == "L"){
                nodes[edges[i].parentNodeIndex]->left_ptr = nodes[edges[i].childNodeIndex];
            }else{
                nodes[edges[i].parentNodeIndex]->right_ptr = nodes[edges[i].childNodeIndex];
            }
        }

        root = nodes[rootIndex];
        return;
    }

    void validateInput(){
        if(noOfNodes == 0){
            assert(noOfEdges == 0);
            return;
        }
        assert(noOfNodes == noOfEdges+1);
        int indegree[noOfNodes], outDegree[noOfNodes], leftChild[noOfNodes], rightChild[noOfNodes];
        for(int i=0;i<noOfNodes;i++){
            indegree[i] = 0;
            outDegree[i] = 0;
            leftChild[i] = 0;
            rightChild[i] = 0;
        }
        for(int i=0;i<noOfEdges;i++){
            outDegree[edges[i].parentNodeIndex]++;
            indegree[edges[i].childNodeIndex]++;
            
            if(edges[i].leftRightFlag == "L"){
                leftChild[edges[i].parentNodeIndex]++;
            }
            else{
                rightChild[edges[i].parentNodeIndex]++;
            }
        }
        int rootCount = 0;
        int root = -1;
        for(int i=0;i<noOfNodes;i++){
            assert(indegree[i]<=1);
            assert(outDegree[i]<=2);
            assert(leftChild[i]<=1);
            assert(rightChild[i]<=1);
            if(indegree[i]==0){
                rootCount++;
                root = i;
            }
        }
        assert(rootCount == 1);
        assert(root == rootIndex);
    }

    void considerEdge(
        string lOrR, TreeNode* parent, TreeNode* child, 
        unordered_map<TreeNode*, int>& nodeToId, int& id, bool& edge_creates_cycle
    ){
        if (child){
            if (nodeToId.find(child) == nodeToId.end()){
                nodeToId[child] = id++;
                nodeValues.push_back(child->val);
            } else {
                edge_creates_cycle = true;
                cerr << "Cycle detected in the tree. Cycle contains the edge: " 
                     << nodeToId[parent] << " " << nodeToId[child] 
                     << " " << lOrR << endl;
            }
            edges.push_back(Edge(nodeToId[parent], nodeToId[child], lOrR));
        }
    }

    void getNodeValuesAndEdges(
        TreeNode* root, unordered_map<TreeNode*, int>& nodeToId, int& id
    ){
        if(root == NULL){
            return;
        }

        bool left_edge_creates_cycle = false;
        bool right_edge_creates_cycle = false;

        considerEdge(
            "L", root, root->left_ptr, 
            nodeToId, id, left_edge_creates_cycle
        );
        considerEdge(
            "R", root, root->right_ptr,
            nodeToId, id, right_edge_creates_cycle
        );

        if (left_edge_creates_cycle == false){
            getNodeValuesAndEdges(root->left_ptr, nodeToId, id);
        }
        if (right_edge_creates_cycle == false){
            getNodeValuesAndEdges(root->right_ptr, nodeToId, id);
        }
    }

    void populateRawValuesFromTree(){
        noOfNodes = 0;
        nodeValues.clear();
        rootIndex = -1;
        noOfEdges = 0;
        edges.clear();
        nextRightNodes.clear();

        if (root != NULL){
            int id = 0;
            unordered_map<TreeNode*, int> nodeToId;

            rootIndex = id;  // That is 0.
            nodeToId[root] = id++;
            nodeValues.push_back(root->val);

            getNodeValuesAndEdges(root, nodeToId, id);
            noOfNodes = nodeValues.size();
            noOfEdges = edges.size();
        }
    }

    void writeRawValues(ofstream &cout){
        cout << noOfNodes << endl;
        for(int i = 0; i < noOfNodes; i++){
            if(i){
                cout << " ";
            }
            cout << nodeValues[i];
        }

        if (noOfNodes > 0){
            cout << endl;
        }

        cout << rootIndex << endl;

        cout << noOfEdges << endl;
        for(int i = 0; i < noOfEdges; i++){
            cout << edges[i].parentNodeIndex 
                 << " " << edges[i].childNodeIndex
                 << " " << edges[i].leftRightFlag
                 << endl;
        }
    }
};

TreeNode* readBinaryTree(ifstream &fin){
    BinaryTree input_binary_tree;
    input_binary_tree.readRawValues(fin);
    input_binary_tree.validateInput();
    input_binary_tree.buildFromRawValues();
    return input_binary_tree.root;
}

void printBinaryTree(TreeNode* resultRoot, ofstream &fout){
    BinaryTree output_binary_tree;
    output_binary_tree.root = resultRoot;
    output_binary_tree.populateRawValuesFromTree();
    output_binary_tree.writeRawValues(fout);
}

// ============================ Start ============================

/*
 * Complete the function below.
 */

class BSTInfo{
public:
    int mn;
    int mx;
    bool isBST;
    int size;
    int mxSize;

    BSTInfo(){
        mn = -1000000009;
        mx = 1000000009;
        isBST = true;
        size = 0;
        mxSize = 0;
    }
};

BSTInfo findLargestBSTHelper(TreeNode *root){
    BSTInfo currentTreeInfo;
    if(root == NULL) {
        return currentTreeInfo;
    }
    // Leaf node check
    if(root->left_ptr == NULL && root->right_ptr == NULL){
        currentTreeInfo.mn = root->val;
        currentTreeInfo.mx = root->val;
        currentTreeInfo.size = 1;
        currentTreeInfo.isBST = true;
        currentTreeInfo.mxSize = 1;
    } 
    // Left child is null
    else if(root->left_ptr == NULL){
        BSTInfo rightTreeInfo = findLargestBSTHelper(root->right_ptr);
        if(rightTreeInfo.isBST && root->val<= rightTreeInfo.mn){
            currentTreeInfo.mn = root->val;
            currentTreeInfo.mx = rightTreeInfo.mx;
            currentTreeInfo.size = 1 + rightTreeInfo.size;
            currentTreeInfo.isBST = true;
            currentTreeInfo.mxSize = max(currentTreeInfo.size, rightTreeInfo.mxSize);
        } else {
            currentTreeInfo.isBST = false;
            currentTreeInfo.mxSize = max(1, rightTreeInfo.mxSize);
        }
    } 
    // Right child is null
    else if(root->right_ptr == NULL){
        BSTInfo leftTreeInfo = findLargestBSTHelper(root->left_ptr);
        if(leftTreeInfo.isBST && leftTreeInfo.mx <= root->val){
            currentTreeInfo.mn = leftTreeInfo.mn;
            currentTreeInfo.mx = root->val;
            currentTreeInfo.size = 1 + leftTreeInfo.size;
            currentTreeInfo.isBST = true;
            currentTreeInfo.mxSize = max(currentTreeInfo.size, leftTreeInfo.mxSize);
        } else {
            currentTreeInfo.isBST = false;
            currentTreeInfo.mxSize = max(1, leftTreeInfo.mxSize);
        }
    } 
    // Both child present
    else {
        BSTInfo leftTreeInfo = findLargestBSTHelper(root->left_ptr);
        BSTInfo rightTreeInfo = findLargestBSTHelper(root->right_ptr);
        if(leftTreeInfo.isBST == true && rightTreeInfo.isBST == true && leftTreeInfo.mx <= root->val && root->val <= rightTreeInfo.mn){
            currentTreeInfo.mn = leftTreeInfo.mn;
            currentTreeInfo.mx = rightTreeInfo.mx;
            currentTreeInfo.size = 1 + leftTreeInfo.size + rightTreeInfo.size;
            currentTreeInfo.isBST = true;
            currentTreeInfo.mxSize = max(currentTreeInfo.size, max(leftTreeInfo.mxSize, rightTreeInfo.mxSize));
        } else {
            currentTreeInfo.isBST = false;
            currentTreeInfo.mxSize = max(1,max(leftTreeInfo.mxSize, rightTreeInfo.mxSize));
        }
    }
    return currentTreeInfo;
}

int findLargestBST(TreeNode *root) {
    if(root == NULL){
        return 0;
    }
    BSTInfo result = findLargestBSTHelper(root);
    return result.mxSize;
}

// ============================ End ============================

void solve(string inputFile, string outputFile){
    ifstream fin(inputFile);
    ofstream fout(outputFile);
    cerr<<"Running "<<inputFile<<endl;
    int testCase;
    fin>>testCase;
    for(int i=0;i<testCase;i++){
        cerr<<"Running case number "<<i<<endl;
        TreeNode *root = readBinaryTree(fin);
        int result = findLargestBST(root);
        fout<<result<<endl;
    }
}

int main(){
    solve("..//test_cases//sample_test_cases_input.txt", "..//test_cases//sample_test_cases_expected_output.txt");
    solve("..//test_cases//handmade_test_cases_input.txt", "..//test_cases//handmade_test_cases_expected_output.txt");
    solve("..//test_cases//generated_small_test_cases_input.txt", "..//test_cases//generated_small_test_cases_expected_output.txt");
    solve("..//test_cases//generated_big_test_cases_input.txt", "..//test_cases//generated_big_test_cases_expected_output.txt");
    return 0;
}    