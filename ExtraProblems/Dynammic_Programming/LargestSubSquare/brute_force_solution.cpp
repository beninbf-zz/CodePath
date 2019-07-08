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
    // stores max size of sub square matrix
    int maxi = 0;

    // iterating on all cells of mat 
    for(int i=0;i<n;i++) {
        for(int j=0;j<m;j++) {
            if(!mat[i][j])
                continue;
            // Assuming mat[i][j] as top left corner
            // and checking for all sizes of sub-squre matrix
            int flag = 1;
            for(int sz = 0;(i+sz) < n && (j+sz) < m; sz++) {
                if(!flag)
                    break;
                for(int col = j;col <= (j+sz);col++) {
                    if(!mat[i+sz][col]) {
                        flag = 0;
                        break;
                    }
                }
                for(int row = i;flag && row <= (i+sz); row++) {
                    if(!mat[row][j+sz]) {
                        flag = 0;
                        break;
                    }
                }
                // updating maximum size encountered so far
                if(flag)
                    maxi = max(maxi,sz+1);
            }
        }
    }
    return maxi;
}
// -------------------------- STOP --------------------------

int main()
{
    // freopen(
    //     "..//test_cases//handmade_test_cases_expected_output1.txt", "w",
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

    vector < vector < int > > mat(n);

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
