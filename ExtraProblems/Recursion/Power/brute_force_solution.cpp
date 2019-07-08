#include "bits/stdc++.h"
using namespace std;

// ------------------------------ START ---------------------
const int MOD = 1e9 + 7;

int calculate_power(long long a, long long b) {
    // stores final evaluated value of a^b % MOD
    long long result = 1LL;
    // iterate 1 to b
    for (int i = 0; i < b; i ++) {
        // keep multiplying a
        result = result * a % MOD;
    }
    return(int)result;
}
// ------------------------------ STOP ----------------------

int main(int argc, char const * argv[]) {
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
    long long a;
    long long b;
    cin >> a >> b;
    int ans = calculate_power(a, b);
    cout << ans << endl;
    // }
    return 0;
}
