#include <iostream>
#include <vector>

using namespace std;

int main(void) {
  ios::sync_with_stdio(false);
  cin.tie();

  long long res = 0;
  int a, b, n, x, y;
  long long prev;
  cin >> n;
  cin >> x;
  cin >> y;
  cin >> prev;
  vector<long long> sum(n);
  sum[0] = prev;
  for (int i = 1; i < n; i++) {
    prev = (x * prev + y) % 65536;
    sum[i] = prev + sum[i - 1];
  }
  int m;
  int z, t1;
  cin >> m;
  cin >> z;
  cin >> t1;
  if (m == 0) {
    cout << 0;
  }
  else {
    vector<int> carr(2 * m);
    long long prev1;
    cin >> prev1;
    carr[0] = prev1 % n;
    for (int i = 1; i < 2 * m; i++) {
      long long tmp = z * prev1 + t1;
      if (tmp < 0) {
        prev1 = ((tmp + 1073741824) % 1073741824);
      }
      else {
        prev1 = (tmp % 1073741824);
      }
      carr[i] = (prev1 % n);
    }
    for (int i = 0; i < m; i++) {
      if (carr[2 * i + 1] > carr[2 * i]) {
        a = carr[2 * i];
        b = carr[2 * i + 1];
      }
      else {
        b = carr[2 * i];
        a = carr[2 * i + 1];
      }
      if (a < 1) {
        res += sum[b];
      }
      else {
        res += sum[b] - sum[a - 1];
      }
    }
    cout << res;
  }
  return 0;
}
