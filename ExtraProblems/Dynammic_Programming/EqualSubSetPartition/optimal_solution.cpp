#include "bits/stdc++.h"
using namespace std;

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

// ------------------------------ START ------------------------------

vector<bool> equalSubSetSumPartition(vector<int> &s)
{
    // store min and max sum possible for given array
    int sum_neg = 0, sum_pos = 0;
    // calculate min and max subset sums
    for (auto val : s)
    {
        if (val < 0)
            sum_neg += val;
        else
            sum_pos += val;
    }

    // total sum of the array
    int sum = sum_pos + sum_neg;
    // Partition not possible
    if (sum & 1)
    {
        vector<bool> ret;
        // return empty array
        return ret;
    }

    int n = s.size();

    // dp state
    unordered_map<int, bool> dp[n];

    // base state
    // for idx 0 only one sum s[0] is possible
    dp[0][s[0]] = true;

    // iterate on all idx
    for (int i = 1; i < n; i++)
    {
        // iterate on all possible subset sum
        for (int val = sum_neg; val <= sum_pos; val++)
        {
            // dp state-transition

            // 1) state(i,val) = state(i-1,val) without taking current element
            dp[i][val] = dp[i - 1][val];

            // 2) if val == s[i], just taking ith element is sufficient
            if (val == s[i])
                dp[i][val] = true;
            else if (val - s[i] >= sum_neg)
            {
                // 3) state(i,val) = state(i-1,val-s[i]) when taking current element
                dp[i][val] |= dp[i - 1][val - s[i]];
            }
        }
    }

    int required = sum / 2;
    int idx = n - 1;

    // parition not possible
    if (!dp[idx][required])
    {
        vector<bool> ret;
        return ret;
    }

    // tracks partition elements
    vector<bool> resultSubset(s.size(), 0);
    // tracks count of elements included in S1
    int cnt = 0;
    while (idx >= 0)
    {
        if (idx != 0)
        {
            // reverse dp transition
            if (dp[idx][required] and !dp[idx - 1][required])
            {
                resultSubset[idx] = 1;
                cnt++;
                required -= s[idx];
                if (required == 0)
                    break;
            }
        }
        else
        {
            resultSubset[idx] = 1;
            cnt++;
        }
        idx--;
    }

    // if all elements are included in S1
    // All elements will be in S1 if total_sum = 0
    // case when s = [-2,2]
    // partition is not possible in this case
    if (cnt == n)
    {
        resultSubset.clear();
    }
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
    // assert(validator(v, result) == true);
    // }
    return 0;
}
