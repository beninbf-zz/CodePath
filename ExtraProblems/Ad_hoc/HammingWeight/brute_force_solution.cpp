#include "bits/stdc++.h"
using namespace std;

// ------------------------------ START ------------------------------

int calculateHammingWeight(vector<long long> &s)
{
    // stores total number of set bits in all elements
    int totalSetBits = 0;
    // max number of bits in an integer (input constraint)
    int maxNumberOfBits = 32;
    int N = s.size();
    for (int i = 0; i < N; i++)
    {
        // iterate over all bits of s[i]
        for (int j = 0; j < maxNumberOfBits; j++)
        {
            // if jth bit is set increment totalSetBits
            if (s[i] & (1LL << j))
                totalSetBits++;
        }
    }
    // return final count of set bits
    return totalSetBits;
}
// ------------------------------ STOP ------------------------------

int main(int argc, char const *argv[])
{
    // freopen(
    //     "..//test_cases//handmade_test_cases_input.txt", "r",
    //     stdin);
    // freopen(
    //     "..//test_cases//handmade_test_cases_expected_output1.txt", "w",
    //     stdout);
    // freopen(
    //     "..//test_cases//generated_big_test_cases_input.txt", "r",
    //     stdin);

    // freopen(
    //     "..//test_cases//generated_big_test_cases_expected_output1.txt", "w",
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