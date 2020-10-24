#include <iostream>
#include <vector>
 
using namespace std;
 
int l = 1;
vector<vector<int>> vevec(200010), up(200010);
vector<bool> dead(200010, false);
vector<int> parent(200010), depth(200010), counter, r(200010), alive(200010);
 
void dfs(int v, int p, int d) {
    up[v][0] = p;
    depth[v] = d;
    for (int i = 1; i <= l; ++i) up[v][i] = up[up[v][i - 1]][i - 1];
    for (auto to : vevec[v]) dfs(to, v, d + 1);
}
 
int find_set(int v) {
    return (v == parent[v]) ? v : parent[v] = find_set(parent[v]);
}
 
void union_sets(int a, int b) {
    a = find_set(a);
    b = find_set(b);
    if (a != b) {
        if (r[a] < r[b]) swap(a, b);
        parent[b] = a;
        if (r[a] == r[b]) ++r[a];
    }
}
 
void check(int v) {
    int temp;
    parent[v] = v;
    r[v] = 0;
    for (auto child : vevec[v]) if (dead[child]) union_sets(child, v);
    alive[find_set(v)] = up[v][0];
    if (dead[up[v][0]]) {
        temp = alive[find_set(up[v][0])];
        union_sets(v, up[v][0]);
        alive[find_set(v)] = temp;
    }
}
 
 
int lca(int v, int u) {
    if (depth[v] > depth[u]) swap(v, u);
    for (int i = l; i >= 0; --i) if (depth[u] - depth[v] >= counter[i]) u = up[u][i];
    if (v == u) return v;
    for (int i = l; i >= 0; --i)
        if (up[v][i] != up[u][i] && depth[up[v][i]] == depth[up[u][i]]) {
            v = up[v][i];
            u = up[u][i];
        }
    return (dead[up[v][0]]) ? alive[find_set(up[v][0])] : up[v][0];
}
 
void add(int parent, int child) {
    depth[child] = depth[parent];
    ++depth[child];
    up[child][0] = parent;
    for (int i = 1; i <= l; ++i) up[child][i] = up[up[child][i - 1]][i - 1];
}
 
int main() {
    int t1, n, t2, numb = 1;
    char c;
    cin >> n;
    l = 1;
    while ((1 << l) <= n) ++l;
    ++l;
    for (int i = 0; i <= l; ++i) counter.push_back(1 << i);
    for (int i = 0; i < n; ++i) up[i].resize(l + 1);
    for (int i = 0; i < 200010; ++i) alive[i] = i;
    dfs(0, 0, 1);
    for (int i = 0; i < n; ++i) {
        cin >> c >> t1;
        t1 -= 1;
        if (c == '+') {
            vevec[t1].push_back(numb);
            add(t1, numb);
            ++numb;
        } else if (c == '-') {
            dead[t1] = true;
            check(t1);
        } else {
            cin >> t2;
            t2 -= 1;
            cout << lca(t1, t2) + 1 << '\n';
        }
    }
    return 0;
}
