#include <iostream>
#include <vector>
 
using namespace std;
 
vector<vector<int>> vevec(200010);
vector<int> vec(200010), level(200010, -1);
 
int calc(int v, int size, int &x, int p = -1) {
    int s = 1;
    for (auto to : vevec[v]){
        if (level[to] == -1 && to != p){
            s += calc(to, size, x, v);
        }
    }
    if (x == -1 && (2 * s >= size || p == -1)){
        x = v;
    }
    return s;
}
 
void dfs(int v, int size, int depth, int last) {
    int x = -1;
    calc(v, size, x);
    level[x] = depth;
    vec[x] = last;
    for (auto to : vevec[x]){
        if (level[to] == -1){
            dfs(to, size / 2, depth + 1, x);
        }
    }
}
 
int main() {
    int n;
    int t1, t2;
    cin >> n;
    for (int i = 0; i < n - 1; ++i) {
        cin >> t1 >> t2;
        t1 -= 1;
        t2 -= 1;
        vevec[t1].push_back(t2);
        vevec[t2].push_back(t1);
    }
    dfs(0, n, 0, -1);
    for (int i = 0; i < n; ++i){
        cout << vec[i] + 1 << ' ';
    }
    return 0;
}
