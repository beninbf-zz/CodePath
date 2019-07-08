#include "bits/stdc++.h"
using namespace std;

// -------------------------- START -----------------------------
// TrieNode structure
struct TrieNode {
    // stores presence of current node in trie
    int cnt;
    // marks true if current node is end of word in trie
    bool isEnd;
    // stores references to all its children
    TrieNode *child[26];
    // paramtrized constructor
    TrieNode() : cnt(1), isEnd(false) {
        for (int i = 0; i < 26; i++) {
            child[i] = NULL;
        }
    }
};

// inserts key "k" in the trie
void insert(TrieNode *root, string &k) {
    TrieNode *tmp = root;
    int l = k.length();
    for (int i = 0; i < l; i++) {
        int idx = k[i] - 'a';
        if (!tmp->child[idx]) {
            tmp->child[idx] = new TrieNode();
        }
        else {
            tmp->child[idx]->cnt++;
        }
        tmp = tmp->child[idx];
    }
    tmp->isEnd = true;
}

// removes key "s" from the trie
void removeKey(string &s, TrieNode *root) {
    int l = s.length();
    TrieNode *curr = root;
    for (int i = 0; i < l; i++) {
        int idx = s[i] - 'a';
        if (!curr->child[idx]) {
            break;
        }
        TrieNode *tmp = curr->child[idx];
        tmp->cnt--;
        if (tmp->cnt == 0) {
            curr->child[idx] = NULL;
        }
        curr = tmp;
    }
}

bool ok(int x, int y, vector<string> &mat, TrieNode *rt) {
    // mat dimension
    int n = mat.size();
    int m = mat[0].size();
    // basic boundary checks
    if (x < 0 or y < 0 or x >= n or y >= m) {
        return false;
    }
    // basic sanity check
    if (rt == NULL) {
        return false;
    }
    // checking if mat[x][y] exists as child for rt
    if (rt->child[mat[x][y] - 'a'] == NULL) {
        return false;
    }
    return true;
}

void dfs(int x, int y, vector<string> &mat, vector<vector<int>> &vis, TrieNode *root, 
    TrieNode *rt, vector<string> &result, string &res) {
    // check if rt node is end of any word in trie
    if (rt and rt->isEnd == true) {
        // unmark the end node
        rt->isEnd = false;
        // push the current word in the result array
        result.push_back(res);
        // remove the current word from the trie
        removeKey(res, root);
    }

    // mark current node as visited
    vis[x][y] = 1;

    // iterate in all 8 - directions from current cell
    for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
            if (i == 0 and j == 0) {
                continue;
            }
            // if this cell(x+i,y+j) is valid and not visited
            if (ok(x + i, y + j, mat, rt) and !vis[x + i][y + j]) {
                int idx = mat[x + i][y + j] - 'a';
                // building the word further
                res.push_back(mat[x + i][y + j]);
                // extend dfs traversal to cell(x+i,y+j)
                dfs(x + i, y + j, mat, vis, root, rt->child[idx], result, res);
                // pop current char from the end of current word
                res.pop_back();
            }
        }
    }
    // mark current cell as non - visited
    vis[x][y] = 0;
}

// @param : dictionary : set of words to be looked in mat
// @param : mat : input matrix
// @return : list of all words found in mat
vector<string> boggle_solver(vector<string> &dictionary, vector<string> &mat) {
    // create a trie
    TrieNode *root = new TrieNode();
    // insert all words from dict in trie
    for (auto word : dictionary) {
        insert(root, word);
    }
    // mat dimensions
    int n = mat.size();
    int m = mat[0].size();
    // visited 2d array to mark visited cells of mat
    vector<vector<int>> vis(n, vector<int>(m, 0));
    // stores all dictionary words found in mat
    vector<string> allFoundWords;

    // iterate over all cells of mat
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            // calculate the child to be looked in root trie node
            int idx = mat[i][j] - 'a';

            // check if the respective child node is present in trie
            if (root->child[idx]) {
                // stores words found with first char as mat[i][j]
                vector<string> foundWords;
                string word = "";
                // initializing word's first char
                word.push_back(mat[i][j]);
                // do a dfs traversal at location (i,j)
                dfs(i, j, mat, vis, root, root->child[idx], foundWords, word);
                // insert all words found in current dfs to overall result
                allFoundWords.insert(end(allFoundWords), begin(foundWords), end(foundWords));
            }
        }
    }
    // return the overall list of all found words
    return allFoundWords;
}

// -------------------------------- STOP --------------------------------

int main() {
    int dictionary_count;
    cin >> dictionary_count;

    vector<string> dictionary(dictionary_count);

    for (int i = 0; i < dictionary_count; i++) {
        string dictionary_item;
        cin >> dictionary_item;
        dictionary[i] = dictionary_item;
    }

    int n, m;
    cin >> n >> m;
    vector<string> mat;
    for (int i = 0; i < n; i++) {
        string tmp;
        cin >> tmp;
        mat.push_back(tmp);
    }
    string txt;
    cin >> txt;

    ostream &fout = cout;

    vector<string> result = boggle_solver(dictionary, mat);

    for (int i = 0; i < result.size(); i++) {
        fout << result[i];

        if (i != (result.size() - 1)) {
            fout << "\n";
        }
    }

    return 0;
}

string ltrim(const string &str) {
    string s(str);

    s.erase(
        s.begin(),
        find_if(s.begin(), s.end(), not1(ptr_fun<int, int>(isspace))));

    return s;
}

string rtrim(const string &str) {
    string s(str);

    s.erase(
        find_if(s.rbegin(), s.rend(), not1(ptr_fun<int, int>(isspace))).base(),
        s.end());

    return s;
}
