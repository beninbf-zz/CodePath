#include<bits/stdc++.h>

using namespace std;

const int MAX_N = 500000, MIN_VAL = -2000000000, MAX_VAL = 2000000000;

// -------------------------- START --------------------------

vector<int> alternating_positives_and_negatives(vector<int> array)
{
    int n = array.size();
    vector<int> ans;
    // positive_pointer and negative_pointer would correspond to the next positive and negative
    // element's index in the output array.
    int positive_pointer = 0;
    int negative_pointer = 0;

    // Each iteration would result in addition of next element in the output array.
    for (int i = 0; i < n; i++)
    {

        // Find the next negative element in the input array.
        while(array[negative_pointer] >= 0 && negative_pointer <= n-1)
        {
            negative_pointer++;
        }

        // Find the next positive element in the input array.
        while(array[positive_pointer] < 0 && positive_pointer <= n-1)
        {
            positive_pointer++;
        }

        // If no more positive elements are left, push the next negative element as the 'i'th element
        // in the output array.
        if (positive_pointer == n)
        {
            ans.push_back(array[negative_pointer]);
            negative_pointer++;
        }

        // If no more negative elements are left, push the next positive element as the 'i'th element
        // in the output array.
        else if (negative_pointer == n)
        {
            ans.push_back(array[positive_pointer]);
            positive_pointer++;
        }

        // Both positive and negative elements are remaining to be added in output array.
        else
        {
            // At even index in the output array, push the next positive element.
            if(i % 2 == 0)
            {
                ans.push_back(array[positive_pointer]);
                positive_pointer++;
            }

            // At odd index in the output array, push the next negative element.
            else
            {
                ans.push_back(array[negative_pointer]);
                negative_pointer++;
            }
        }
    }
    return ans;
}

// -------------------------- STOP ---------------------------

int main()
{
    // freopen(
    // 	"..//test_cases//sample_test_cases_input.txt",
    // 	"r", stdin
    // );
    // freopen(
    // 	"..//test_cases//sample_test_cases_expected_output.txt",
    // 	"w", stdout
    // );
    // freopen(
    // 	"..//test_cases//handmade_test_cases_input.txt",
    // 	"r", stdin
    // );
    // freopen(
    // 	"..//test_cases//handmade_test_cases_expected_output.txt",
    // 	"w", stdout
    // );
    // freopen(
    // 	"..//test_cases//generated_small_test_cases_input.txt",
    // 	"r", stdin
    // );
    // freopen(
    // 	"..//test_cases//generated_small_test_cases_expected_output.txt",
    // 	"w", stdout
    // );
    freopen(
    	"..//test_cases//generated_big_test_cases_input.txt",
    	"r", stdin
    );
    freopen(
    	"..//test_cases//generated_big_test_cases_expected_output.txt",
    	"w", stdout
    );
    // freopen(
    // 	"..//test_cases//ignore.txt",
    // 	"w", stdout
    // );

    int test_cases;
    cin >> test_cases;
    assert(test_cases >= 0);
    while (test_cases--)
    {
        int n;
        cin >> n;
        assert(1 <= n);
        assert(n <= MAX_N);
        vector<int> array(n);
        for (int i = 0; i < n; i++)
        {
            cin >> array[i];
            assert(MIN_VAL <= array[i]);
            assert(array[i] <= MAX_VAL);
        }
        vector<int> ans = alternating_positives_and_negatives(array);
        int len = ans.size();
        for (int i = 0; i < len; i++)
        {
            cout << ans[i] << endl;
        }
        cout << endl;
    }

    return 0;
}
