#include <bits/stdc++.h>

using namespace std;

// ============================ Start ============================

void printStringSinusoidally(string s, ofstream &fout){
    int len = s.length();
    char wavedString[3][len];
    
    for(int i=0;i<len;i++){
        for(int j=0;j<3;j++){
            wavedString[j][i]=' ';
        }
    }

    for(int i = 2; i < s.length(); i += 4){
        wavedString[0][i] = (s[i]==' ')?'~':s[i];
    }

    for(int i = 1; i<s.length(); i += 2){
        wavedString[1][i] = (s[i]==' ')?'~':s[i];
    }

    for(int i=0;i<s.length(); i += 4){
        wavedString[2][i] = (s[i]==' ')?'~':s[i];
    }
    string firstRow = "";
    string secondRow = "";
    string thirdRow = "";
    
    for(int i=0;i<len;i++){
        firstRow += wavedString[0][i];
        secondRow += wavedString[1][i];
        thirdRow += wavedString[2][i];
    }

    fout<<firstRow<<endl;
    fout<<secondRow<<endl;
    fout<<thirdRow<<endl;
}

// ============================ End ============================

void solve(string inputFile, string outputFile){
    ifstream fin(inputFile);
    ofstream fout(outputFile);
    cerr<<"Running "<<inputFile<<endl;
    int testCase;
    fin>>testCase;
    fin.ignore();
    for(int i=0;i<testCase;i++){
        string s;
        getline(fin,s);
        printStringSinusoidally(s, fout);
        fout<<endl;
    }
}

int main(){
    solve("..//test_cases//sample_test_cases_input.txt", "..//test_cases//sample_test_cases_expected_output.txt");
    solve("..//test_cases//handmade_test_cases_input.txt", "..//test_cases//handmade_test_cases_expected_output.txt");
    solve("..//test_cases//generated_big_test_cases_input.txt", "..//test_cases//generated_big_test_cases_expected_output.txt");
    return 0;
}    