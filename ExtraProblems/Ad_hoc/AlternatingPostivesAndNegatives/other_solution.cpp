#include<bits/stdc++.h>

using namespace std;

const int MAX_N = 500000, MIN_VAL = -2000000000, MAX_VAL = 2000000000;

// -------------------------- START --------------------------

void rotate(vector<int> &array, int wrong_index, int right_index)
{
    int n = array.size();
    int temp = array[right_index];
    for (int i = right_index; i > wrong_index; i--)
    {
        array[i] = array[i-1];
    }
    array[wrong_index] = temp;
}

//This solution uses O(1) extra space and is solved with O(n^2) time complexity.
vector<int> alternating_positives_and_negatives(vector<int> &array)
{
    int n = array.size();

    // wrong_index points to either at the index where the element is at wrong position
    // or defaults at -1.
    int wrong_index = -1;

    for (int i = 0; i < n; i++)
    {

        // If wrong_index points to some element in the array.
        if(wrong_index != -1)
        {
            // Check if the current element should be at wrong_index.
            if (((array[i] >= 0) && (array[wrong_index] < 0)) ||
                ((array[i] < 0) && (array[wrong_index] >= 0)))
            {
                rotate(array, wrong_index, i);

                // the new wrong_index is now 2 steps ahead
                if (i - wrong_index > 2)
                {
                    wrong_index = wrong_index + 2;
                }

                else
                {
                    wrong_index = -1;
                }

            }
        }

        // If wrong_index is not pointing anywhere currently.
        if(wrong_index == -1)
        {
            // check if current element is at a wrong index.
            if (((array[i] >= 0) && (i%2)) || ((array[i] < 0) && !(i%2)))
            {
                wrong_index = i;
            }
        }
    }

    return array;
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
    // freopen(
    // 	"..//test_cases//generated_big_test_cases_input.txt",
    // 	"r", stdin
    // );
    // freopen(
    // 	"..//test_cases//generated_big_test_cases_expected_output.txt",
    // 	"w", stdout
    // );
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
