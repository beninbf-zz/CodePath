#include "bits/stdc++.h"
using namespace std;

// ------------------------------ START ---------------------------
const int MOD = 1e9 + 7;

int calculate_power(long long a, long long b) {
    // stores final evaluated power a^b
    long long result = 1;
    // stores a^(power of two)
    // initialixzed with a^(2^0)
    long long powerOfTwo = a % MOD;

    // iterate over all bits of b
    while (b != 0) {
        // if current bit is set
        if (b % 2 == 1) {
            // multiply the current power in result
            result = result * powerOfTwo % MOD;
        }
        // double the power of two
        // a^i * a^i = a^(2*i)
        powerOfTwo = powerOfTwo * powerOfTwo % MOD;
        // move to next bit of b
        b = b / 2;
    }
    return(int)result;
}

// ------------------------------ STOP ----------------------------

int main(int argc, char const * argv[]) {
    // freopen("..//test_cases//handmade_test_cases_input.txt", "r", stdin);
    // freopen("..//test_cases//handmade_test_cases_expected_output.txt", "w", stdout);
    // freopen("..//test_cases//generated_big_test_cases_input.txt", "r", stdin);
    // freopen("..//test_cases//generated_big_test_cases_expected_output.txt", "w", stdout);
    // int t;
    // cin >> t;
    // while (t --) {
    long long a;
    long long b;
    cin >> a >> b;
    int ans = calculate_power(a, b);
    cout << ans << endl;
    // }
    return 0;
}
