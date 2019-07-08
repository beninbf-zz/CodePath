#include "bits/stdc++.h"
using namespace std;

// ------------------------------ START ------------------------------

int calculateHammingWeight(vector<long long> &s)
{
    // size of mem dp table
    int sz = 1 << 16;
    // number of elements in s
    int N = s.size();
    // stores set bits in integers
    int memo[sz];
    // 0 set bits in integer 0
    memo[0] = 0;
    // using dp-state relation to populate
    // all dp states
    for (int i = 1; i < sz; i++)
    {
        memo[i] = memo[i >> 1] + (i & 1);
    }
    // total set bits in all N elements of s
    int totalSetBits = 0;
    // bit mask = (1<<16) - 1 = (1111111111111111) in binary
    int bitMask = sz - 1;
    // iterate over all elements in array
    for (int i = 0; i < N; i++)
    {
        // add set bits from (0th to 15th) bits position
        totalSetBits += memo[s[i] & bitMask];
        // shift s[i] 16 positions to right
        s[i] = s[i] >> 16;
        // again add set bits from (0th to 15th) bits position
        totalSetBits += memo[s[i] & bitMask];
    }
    return totalSetBits;
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

    // string testcases;
    // getline(cin, testcases);
    // int t = stoi(testcases);
    // while (t--)
    // {
    int n;
    cin >> n;
    vector<long long> s(n);
    for (int i = 0; i < n; i++)
    {
        cin >> s[i];
    }
    int ans = calculateHammingWeight(s);
    cout << ans << endl;
    // }
    return 0;
}