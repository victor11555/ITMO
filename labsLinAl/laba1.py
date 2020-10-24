def getMatrix(n, m):

    arrayLine = list(map(float, inp.readline().strip().split()))

    res = [arrayLine[i * m:(i + 1) * m] for i in range(0,n)]

    return res




def  SumMatrix(a, b, n_a, m_a, n_b, m_b):

    if not (n_a == n_b and m_a == m_b):

        return 0

    res = [[a[i][j] + b[i][j] for j in range(m_a)] for i in range(n_a)]

    return res



def umnognachis(a, n, m, c):

    res = [[a[i][j] * c for j in range(m)] for i in range(n)]

    return res



def umnogMatrix(a, b, n_a, m_a, n_b, m_b):

    if m_a != n_b:

        return 0

    else:

        res = [[0 for j in range(m_b)] for i in range(n_a)]

        for i in range(0,n_a):

            for j in range(0,m_b):

                for k in range(m_a):

                    res[i][j] += a[i][k] * b[k][j]

        return res


def Transpose(a, n, m):

    res = [[a[i][j] for i in range(n)] for j in range(m)]

    return res


def Check(x):

    if x == 0:

        return True

    return False





def decision (a, n_a, m_a, b, n_b, m_b, c, n_c, m_c, d, n_d, m_d, f, n_f, m_f):

    check = True

    a1 = umnognachis(a, n_a, m_a, alpha)

    a2 = umnognachis(Transpose(b, n_b, m_b), m_b, n_b, betta)

    a3 = SumMatrix(a1, a2, len(a1), len(a1[0]), len(a2), len(a2[0]))

    if Check(a3):

        return 0

    a4 = Transpose(a3, len(a3), len(a3[0]))

    a5 = umnogMatrix(c, a4, n_c, m_c, len(a4), len(a4[0]))

    if Check(a5):

        return 0

    a6 = umnogMatrix(a5, d, len(a5), len(a5[0]), n_d, m_d)

    if Check(a6):

        return 0

    a7 = SumMatrix(a6, umnognachis(f, n_f, m_f, -1), len(a6), len(a6[0]), n_f, m_f)

    if Check(a7):

        return 0

    return a7


inp = open('input.txt', 'r')

alpha, betta = map(float, inp.readline().strip().split())

n_a, m_a = map(int,inp.readline().strip().split())
a = getMatrix(n_a, m_a)

n_b, m_b = map(int,inp.readline().strip().split())
b = getMatrix(n_b, m_b)

n_c, m_c = map(int,inp.readline().strip().split())
c = getMatrix(n_c, m_c)

n_d, m_d = map(int,inp.readline().strip().split())
d = getMatrix(n_d, m_d)

n_f, m_f = map(int,inp.readline().strip().split())
f = getMatrix(n_f, m_f)

Answer = decision(a, n_a, m_a, b, n_b, m_b, c, n_c, m_c, d, n_d, m_d, f, n_f, m_f)

inp.close()


out = open('output.txt', 'w')

if Check(Answer):

    out.write(str(0))

else:

    out.write(str(1) + "\n")

    out.write(str(len(Answer)) + " " + str(len(Answer[0])) + "\n")

    for i in range(0, len(Answer)):

        for j in range(0, len(Answer[0])):

            out.write(str(Answer[i][j]) + " ")

        out.write("\n")

out.close()
