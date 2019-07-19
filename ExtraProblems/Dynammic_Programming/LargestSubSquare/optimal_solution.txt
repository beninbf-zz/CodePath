#include <bits/stdc++.h>
using namespace std;

string ltrim(const string &);
string rtrim(const string &);
vector<string> split(const string &);

// -------------------------- START --------------------------
// @param n: integer n, denoting number of rows of matrix
// @param m: integer m, denoting number of columns of matrix
// @param mat: denoting 2D integer array (matrix) of size n*m
int largest_sub_square_matrix(int n, int m, vector<vector<int>> &mat)
{   
    // stores the size of largest square sub matrix
    int maxi = 0;

    // initializing max sub square size "maxi"
    // by checking first row and first column of mat
    for (int i = 0; i < n; i++)
        maxi |= mat[i][0];
    for (int j = 0; j < m; j++)
        maxi |= mat[0][j];

    // populating dp
    for (int i = 1; i < n; i++) {
        for (int j = 1; j < m; j++) {
            if (mat[i][j] == 1) {
                // getting minimum from the below states
                // state (i-1, j-1) 
                // state (i, j-1) 
                // state (i-1, j)
                int value = min(mat[i - 1][j - 1],min(mat[i - 1][j], mat[i][j - 1])) + 1;
                // using current matrix state as dp state
                mat[i][j] = value;
                // updating maximum size encountered so far
                maxi = max(mat[i][j], maxi);
            }
        }
    }

    return maxi;
}
// -------------------------- STOP --------------------------

int main()
{
    // freopen(
    //     "..//test_cases//handmade_test_cases_expected_output.txt", "w",
    //     stdout);
    // freopen(
    //     "..//test_cases//handmade_test_cases_input.txt", "r",
    //     stdin);
    // freopen(
    //     "..//test_cases//generated_big_test_cases_input.txt", "r",
    //     stdin);
    // freopen(
    //     "..//test_cases//generated_big_test_cases_expected_output.txt", "w",
    //     stdout);
    // string testcases;
    // getline(cin,testcases);
    // int t = stoi(testcases);
    // while(t--) {
    string mat_rows_temp;
   getline(cin, mat_rows_temp);
    int n = stoi(ltrim(rtrim(mat_rows_temp)));

    string mat_columns_temp;
   getline(cin, mat_columns_temp);

    int m = stoi(ltrim(rtrim(mat_columns_temp)));

    vector<vector<int>> mat(n);

    for (int i = 0; i < n; i++)
    {
        mat[i].resize(m);
        string mat_row_temp_temp;
       getline(cin, mat_row_temp_temp);
        vector<string> mat_row_temp = split(rtrim(mat_row_temp_temp));
        for (int j = 0; j < m; j++)
        {
            int mat_row_item = stoi(mat_row_temp[j]);
            mat[i][j] = mat_row_item;
            assert(mat[i][j] <= 1000000000);
        }
    }
    int ans = largest_sub_square_matrix(n, m, mat);
    cout << ans << endl;
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

vector<string> split(const string &str)
{
    vector<string> tokens;

    string::size_type start = 0;
    string::size_type end = 0;

    while ((end = str.find(" ", start)) != string::npos)
    {
        tokens.push_back(str.substr(start, end - start));

        start = end + 1;
    }

    tokens.push_back(str.substr(start));

    return tokens;
}
