#include <bits/stdc++.h>
#include <vector>
#include <algorithm>

using namespace std;

string ltrim(const string &);
string rtrim(const string &);
vector<string> split(const string &);

int get_sink(vector< vector<int> > &matrix, vector< vector<int> > &basins, int row, int col) {
    int min_row = row, min_col = col;
    // Check element which is at left
    if (col > 0 && matrix[row][col - 1] < matrix[min_row][min_col]) {
        min_col = col - 1;
        min_row = row;
    }
    // Check element which is at down
    if (row > 0 && matrix[row - 1][col] < matrix[min_row][min_col]) {
        min_col = col;
        min_row = row - 1;
    }
    // Check element which is at up
    if (row < matrix.size() - 1 && matrix[row + 1][col] < matrix[min_row][min_col]) {
        min_col = col;
        min_row = row + 1;
    }
    // Check element which is at right
    if (col < matrix[0].size() - 1 && matrix[row][col + 1] < matrix[min_row][min_col]) {
        min_col = col + 1;
        min_row = row;
    }

    if (row == min_row && col == min_col){
        // If we reached at sink
        return basins[min_row][min_col];
    }

    // Recursively call to get sink for element matrix[min_row][min_col]
    return get_sink(matrix, basins, min_row, min_col);
}

vector<int> find_basins(vector< vector<int> > matrix) {
    int row_count = matrix.size();
    int col_count = matrix[0].size();

    // To maintain ids of each element of matrix
    vector< vector<int> > basins(row_count, vector<int>(col_count));

    int idx = 0;

    for (int i = 0; i < row_count; i++){
        for (int j = 0; j < col_count; j++){
            basins[i][j] = idx++;
        }
    }

    // To maintain id wise count of elements which will sink in that id
    unordered_map<int, int> basin_indexes;

    for (int i = 0; i < row_count; i++) {
        for (int j = 0; j < col_count; j++) {
            int sink = get_sink(matrix, basins, i, j);
            basin_indexes[sink] = basin_indexes[sink] + 1;
        }
    }
    
    vector<int> basin_sizes;
    
    for (auto pair : basin_indexes) {
        basin_sizes.push_back(pair.second);
    }

    // Sorting by size of sinks
    sort(basin_sizes.begin(), basin_sizes.end(), greater<int>());

    return basin_sizes;
}

int main() {
    string row_count_temp;
    getline(cin, row_count_temp);

    int row_count = stoi(ltrim(rtrim(row_count_temp)));

    string col_count_temp;
    getline(cin, col_count_temp);

    int col_count = stoi(ltrim(rtrim(col_count_temp)));

    vector< vector<int> > matrix(row_count);
    for (int i = 0; i < row_count; i++) {
        matrix[i].resize(col_count);

        string row;
        getline(cin, row);

        vector<string> values = split(rtrim(row));

        for (int j = 0; j < col_count; j++) {
            matrix[i][j] = stoi(values[j]);
        }
    }

    ostream &fout = cout;

    vector<int> basin_sizes = find_basins(matrix);

    for (int i = 0; i < basin_sizes.size(); i++){
        fout << basin_sizes[i] << endl;
    }

    return 0;
}

string ltrim(const string &str) {
    string s(str);

    s.erase(
        s.begin(),
        find_if(s.begin(), s.end(), not1(ptr_fun<int, int>(isspace)))
    );

    return s;
}

string rtrim(const string &str) {
    string s(str);

    s.erase(
        find_if(s.rbegin(), s.rend(), not1(ptr_fun<int, int>(isspace))).base(),
        s.end()
    );

    return s;
}

vector<string> split(const string &str) {
    vector<string> tokens;

    string::size_type start = 0;
    string::size_type end = 0;

    while ((end = str.find(" ", start)) != string::npos) {
        tokens.push_back(str.substr(start, end - start));

        start = end + 1;
    }

    tokens.push_back(str.substr(start));

    return tokens;
}
