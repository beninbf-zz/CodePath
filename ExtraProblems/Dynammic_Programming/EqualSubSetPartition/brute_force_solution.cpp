#include "bits/stdc++.h"
using namespace std;

// ------------------------------ START ------------------------------

// returns true if the result array subsets gives
// a valid partition for the array v
bool validator(vector<int> &v, vector<bool> &subsets)
{
    // stores sum of subset s1
    int sum1 = 0;
    // stores sum of subset s2
    int sum2 = 0;
    // counts number of elements in s2
    int sz = count(subsets.begin(), subsets.end(), true);
    int n = v.size();
    // basic sanity check
    if (sz == 0 or sz == n or subsets.size() != n)
    {
        return false;
    }
    // loop into array v and calculate s1 and s2 sum
    for (int i = 0; i < n; i++)
    {
        // if 0 belongs to s1
        if (subsets[i] == 0)
            sum1 += v[i];
        // if 1 belongs to s1
        else if (subsets[i] == 1)
            sum2 += v[i];
        else
        { // invalid output
            return false;
        }
    }
    // check if subset sum s1 equals subset sum s2
    if (sum1 != sum2)
    {
        return false;
    }
    return true;
}

// resursive backtracking to generate all subset partition
void solver(vector<bool> &vis, vector<int> &s, int idx, bool &solutionFound)
{
    if (idx == s.size())
    {
        // check if current partition is valid or not
        solutionFound = validator(s, vis);
        return;
    }

    if (!solutionFound)
    {
        solver(vis, s, idx + 1, solutionFound);
    }

    if (!solutionFound)
    {
        // add current idx element in subset 1
        vis[idx] = 1;
        solver(vis, s, idx + 1, solutionFound);
        // remove current element forom subset 1
        vis[idx] = solutionFound;
    }
}

vector<bool> equalSubSetSumPartition(vector<int> &s)
{
    // flag to indicate if solution is found in
    // recursion
    bool solutionFound = false;
    vector<bool> resultSubset(s.size(), 0);
    // recursively generates all possible partitions
    // and checks for solution
    solver(resultSubset, s, 0, solutionFound);
    if (!solutionFound)
    {
        // empty the array if no result found
        resultSubset.clear();
    }
    // return the resultant subset partition
    return resultSubset;
}
// ------------------------------ STOP ------------------------------

int main(int argc, char const *argv[])
{
    // freopen(
    //     "..//test_cases//handmade_test_cases_input.txt", "r",
    //     stdin);
    // freopen(
    //     "..//test_cases//handmade_test_cases_expected_output.txt", "w",
    //     stdout);
    // freopen(
    //     "..//test_cases//generated_big_test_cases_input.txt", "r",
    //     stdin);

    // freopen(
    //     "..//test_cases//generated_big_test_cases_expected_output.txt", "w",
    //     stdout);

    // int t;
    // cin >> t;
    // while (t--)
    // {
    int n;
    cin >> n;
    vector<int> v(n);
    for (int i = 0; i < n; i++)
    {
        cin >> v[i];
    }
    vector<bool> result = equalSubSetSumPartition(v);
    if (result.size() == 0)
    {
        cout << -1 << endl;
        return 0;
    }
    // assert(result.size() == n);
    int sz = 0;
    for (int i = 0; i < n; i++)
    {
        if (result[i])
            sz++;
    }
    cout << sz << endl;
    for (int i = 0; i < n; i++)
    {
        if (result[i])
            cout << v[i] << endl;
    }
    cout << n - sz << endl;
    for (int i = 0; i < n; i++)
    {
        if (!result[i])
            cout << v[i] << endl;
    }
    //     assert(validator(v, result) == true);
    // }
    return 0;
}
