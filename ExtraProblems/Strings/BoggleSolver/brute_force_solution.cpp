#include <bits/stdc++.h>

using namespace std;

string ltrim(const string &);
string rtrim(const string &);

// ------------------------- START ------------------------------

// validates cell (x,y) of mat
bool ok(int x, int y, vector<string> &mat) {
    // mat dimensions
    int n = mat.size();
    int m = mat[0].size();
    // boundary conditions check
    if (x < 0 or y < 0 or x >= n or y >= m) {
        return false;
    }
    return true;
}

void solveforPosition(int length, int maxLength, int x, int y, vector<string> &mat,
    vector<vector<int>> &vis, unordered_set<string> &dict, vector<string> &result, string &s) {
    // if length of current formed word is greater than max length
    // of dictionary word - we break our recursion
    if (length > maxLength) {
        return;
    }
    // marked current position as visited
    vis[x][y] = 1;
    // build current word s by appending current cell
    s.push_back(mat[x][y]);
    // if current word exists in dictionary
    if (dict.find(s) != dict.end()) {
        // push current word in the result array
        result.push_back(s);
        // remove the current word from dictionary
        dict.erase(s);
    }
    // iterate on all 8 directions
    for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
            if (i == 0 and j == 0) {
                continue;
            }
            // if cell (x+i,y+j) is valid and not visited
            if (ok(x + i, y + j, mat) and !vis[x + i][y + j]) {
                // recursively keep building current word
                solveforPosition(length + 1, maxLength, x + i, y + j, mat, vis, dict, result, s);
            }
        }
    }
    // remove current char from the current word built so for
    s.pop_back();
    // mark current cell as non-visited
    vis[x][y] = 0;
}

// @param : dictionary : set of words to be looked in mat
// @param : mat : input matrix
// @return : list of all words found in mat
vector<string> boggle_solver(vector<string> &dictionary, vector<string> &mat) {
    // insert all words from dictionary into hash set dict
    unordered_set<string> dict(dictionary.begin(), dictionary.end());

    // getting maximum length word in dictionary
    int maxWordLength = 0;
    for (auto word : dictionary) {
        maxWordLength = max(maxWordLength, (int)word.length());
    }

    // stores all words found in mat
    vector<string> ret;
    // mat dimensions
    int rows = mat.size();
    int cols = mat[0].size();
    // tracks visited cells of mat
    vector<vector<int>> visited(rows, vector<int>(cols, 0));
    // iterate over all cells of mat
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            vector<string> result;
            string tmp = "";
            // build word for current position and match with dict
            solveforPosition(1, maxWordLength, i, j, mat, visited, dict, result, tmp);
            // add all found words for this cell to overall result ret
            ret.insert(end(ret), begin(result), end(result));
        }
    }
    // return all words found in mat
    return ret;
}
// ------------------------------- STOP ----------------------------------

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
