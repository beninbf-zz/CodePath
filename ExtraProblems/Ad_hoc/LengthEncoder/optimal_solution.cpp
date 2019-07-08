#include <bits/stdc++.h>

using namespace std;

// ============================ Start ============================

string convertToString(int num){
    string ret="";
    while(num>0){
        int rem = num % 10;
        ret+=(char)(rem+'0');
        num/=10;
    }
    reverse(ret.begin(), ret.end());
    return ret;
}

string RLE(string strInput){
    string ans="";
    int cnt=1;
    char prev = strInput[0];
    for(int i=1;i<strInput.length();i++){
        if(strInput[i]==prev){
            cnt++;
        } else {
            if(cnt>1){
                ans+=convertToString(cnt);
            }
            ans+=prev;
            cnt=1;
            prev = strInput[i];
        }
    }
    if(cnt>1){
        ans+=convertToString(cnt);
    }
    ans+=prev;
    return ans;
}

// ============================ End ============================

void solve(string inputFile, string outputFile){
    ifstream fin(inputFile);
    ofstream fout(outputFile);
    cerr<<"Running "<<inputFile<<endl;
    int testCase;
    fin>>testCase;
    for(int i=0;i<testCase;i++){
        string s;
        fin>>s;
        string ans = RLE(s);
        fout<<ans<<endl;
    }
}

int main(){
    solve("..//test_cases//sample_test_cases_input.txt", "..//test_cases//sample_test_cases_expected_output.txt");
    solve("..//test_cases//handmade_test_cases_input.txt", "..//test_cases//handmade_test_cases_expected_output.txt");
    solve("..//test_cases//generated_big_test_cases_input.txt", "..//test_cases//generated_big_test_cases_expected_output.txt");
    return 0;
}    