#include "bits/stdc++.h"
using namespace std;

// ------------------------------ START ----------------------------
const int MOD = 1e9 + 7;

int calculate_power(long long a, long long b) {
    // base case
    if (b == 0) {
        return 1LL;
    }
    a = a % MOD;
    // recursively calculate a^(b/2)
    long long tmp = calculate_power(a , b/2);
    // doubling the power
    // a^(b/2) * a^(b/2) = a^b , if b is even
    tmp = tmp * tmp % MOD;
    // if power is odd
    if (b % 2 == 1) {
        // multiply with extra a
        tmp = tmp * a % MOD;
    }
    return(int)tmp;
}

// ------------------------------ STOP ----------------------------

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
