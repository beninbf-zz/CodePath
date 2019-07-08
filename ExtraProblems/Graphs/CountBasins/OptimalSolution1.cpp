#include <bits/stdc++.h>
#include <vector>
#include <algorithm>

using namespace std;

string ltrim(const string &);
string rtrim(const string &);
vector<string> split(const string &);

vector<int> find_basins(vector< vector<int> > matrix) {
    int row_count = matrix.size();
    int col_count = matrix[0].size();

    // To maintain ids of each element of matrix
    vector< vector<int> > basins(row_count, vector<int>(col_count, -1));

    // To store sorted entries with key as height and value as list of pair with that height
    map< int, vector< pair<int, int> > > heights;

    for (int i = 0; i < row_count; i++){
        for (int j = 0; j < col_count; j++){
            heights[matrix[i][j]].push_back(make_pair(i, j));
        }
    }
    
    int basin_index = 0;
    
    for (auto height_pair : heights) {
        for (pair<int, int> index : height_pair.second) {
            int row = index.first;
            int col = index.second;

            // If element is sink itself
            if (basins[row][col] == -1){
                basins[row][col] = basin_index++;
            }
            // Check element which is at left
            if (col > 0 && basins[row][col - 1] == -1){
                basins[row][col - 1] = basins[row][col];
            }
            // Check element which is at up
            if (row > 0 && basins[row - 1][col] == -1){
                basins[row - 1][col] = basins[row][col];
            }
            // Check element which is at down
            if (row < matrix.size() - 1 && basins[row + 1][col] == -1){
                basins[row + 1][col] = basins[row][col];
            }
            // Check element which is at right
            if (col < matrix[0].size() - 1 && basins[row][col + 1] == -1){
                basins[row][col + 1] = basins[row][col];
            }
        }
    }

    vector<int> basin_sizes(basin_index);

    for (int i = 0; i < row_count; i++){
        for (int j = 0; j < col_count; j++){
            basin_sizes[basins[i][j]]++;
        }
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
