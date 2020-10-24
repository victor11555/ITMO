import math
import operator


def rayInMirror(rayPoint, rayDirection, mirror):
    global point
    d = sum(map(operator.mul, rayDirection, mirror['n']))
    if (round(d, 1) == 0): return False
    t = sum(map(operator.mul, list(map(operator.sub, mirror['p'], rayPoint)), mirror['n']))/d
    if (round(t, 1) <= 0): return False
    poinIntersect = list(map(operator.add, rayPoint, list(map(operator.mul, rayDirection, [t, t, t]))))
    dist = pow(poinIntersect[0] - rayPoint[0], 2) + pow(poinIntersect[1] - rayPoint[1], 2) + pow(poinIntersect[2] - rayPoint[2], 2)
    point = poinIntersect.copy()
    return dist

def rayInFace(rayPoint, rayDirection, face):
    global point
    d = sum(map(operator.mul, rayDirection, face['n']))
    if (round(d, 1) == 0): return False
    t = sum(map(operator.mul, list(map(operator.sub, face['p'], rayPoint)), face['n']))/d
    if (round(t, 1) < 0): return False
    poinIntersect = list(map(operator.add, rayPoint, list(map(operator.mul, rayDirection, [t, t, t]))))

    if (round(sum(map(operator.mul, rayDirection, face['n'])),2) > 0):
        dist = pow(poinIntersect[0] - rayPoint[0], 2) + pow(poinIntersect[1] - rayPoint[1], 2) + pow(poinIntersect[2] - rayPoint[2], 2)
        point = poinIntersect.copy()
        return dist
    else: return False

inp = open('input.txt', 'r')
A = list(map(float, inp.readline().strip().split()))
B = list(map(float, inp.readline().strip().split()))
C = list(map(float, inp.readline().strip().split()))
D = list(map(float, inp.readline().strip().split()))

faces = [] #словарь граней, для каждой грани - вершина, два выходящих из нее вектора, нормаль
pq = list(map(operator.sub, A, B))
pr = list(map(operator.sub, C, B))
n = [pq[1]*pr[2] - pq[2]*pr[1], - pq[0]*pr[2] + pq[2]*pr[0], pq[0]*pr[1] - pq[1]*pr[0]]
faces.append({'p':B, 'pq':pq, 'pr':pr, 'n':n}) #грань 1

pr = list(map(operator.sub, B, C))
n = [pq[1]*pr[2] - pq[2]*pr[1], - pq[0]*pr[2] + pq[2]*pr[0], pq[0]*pr[1] - pq[1]*pr[0]]
faces.append({'p':D, 'pq':pq, 'pr':pr, 'n':n}) #грань 2

pr = list(map(operator.sub, B, C))
pq = list(map(operator.sub, D, C))
n = [pq[1]*pr[2] - pq[2]*pr[1], -pq[0]*pr[2] + pq[2]*pr[0], pq[0]*pr[1] - pq[1]*pr[0]]
faces.append({'p':C, 'pq':pq, 'pr':pr, 'n':n}) #грань 3

pr = pq
pr = list(map(operator.sub, C, B))
n = [pq[1]*pr[2] - pq[2]*pr[1], - pq[0]*pr[2] + pq[2]*pr[0], pq[0]*pr[1] - pq[1]*pr[0]]
faces.append({'p':A, 'pq':pq, 'pr':pr, 'n':n}) #грань 4

pr = pq
pr = list(map(operator.sub, A, B))
n = [pq[1]*pr[2] - pq[2]*pr[1], -pq[0]*pr[2] + pq[2]*pr[0], pq[0]*pr[1] - pq[1]*pr[0]]
faces.append({'p':B, 'pq':pq, 'pr':pr, 'n':n}) #грань 5

pr = pq
pq = list(map(operator.sub, A, B))
n = [pq[1]*pr[2] - pq[2]*pr[1], - pq[0]*pr[2] + pq[2]*pr[0], pq[0]*pr[1] - pq[1]*pr[0]]
faces.append({'p':C, 'pq':pq, 'pr':pr, 'n':n}) #грань 6


rayDirection = list(map(float, inp.readline().strip().split()))
rayPoint = list(map(float, inp.readline().strip().split()))

energy = int(inp.readline().strip())
numOfMirrors = int(inp.readline().strip())

mirrors = [] #словарь зеркал, для каждого зеркала - вершина, два выходящих из нее вектора, нормаль
for k in range(numOfMirrors): #заполняем словарь зеркал
    p = list(map(float, inp.readline().strip().split()))
    q = list(map(float, inp.readline().strip().split()))
    r = list(map(float, inp.readline().strip().split()))
    pq = list(map(operator.sub, q, p))
    pr = list(map(operator.sub, r, p))
    n = [0, 0, 0]
    mirrors.append({'p':p, 'pq':pq, 'pr':pr, 'n':n, 's':0})
    mirrors[k]['n'][0] = mirrors[k]['pq'][1] * mirrors[k]['pr'][2] - mirrors[k]['pq'][2] * mirrors[k]['pr'][1]
    mirrors[k]['n'][1] = -mirrors[k]['pq'][0] * mirrors[k]['pr'][2] + mirrors[k]['pq'][2] * mirrors[k]['pr'][0]
    mirrors[k]['n'][2] = mirrors[k]['pq'][0] * mirrors[k]['pr'][1] - mirrors[k]['pq'][1] * mirrors[k]['pr'][0]
inp.close()

point = []
while (energy > 0):

    dists = {}
    points = {}
    flagMirror = 0
    for k in range(numOfMirrors): #считаем расстояние до зеркал
        d = rayInMirror(rayPoint, rayDirection, mirrors[k])
        if (d is not False):
            flagMirror = 1 #на траектории есть зеркало
            dists.update({k:d})
            points.update({k:point})
    if (flagMirror == 1):
        nearMirrorNum = min(dists.items(), key=operator.itemgetter(1))[0] #из всез зеркал на траектории выбираем ближайшее, тут его номер
        nearMirrorDist = dists[nearMirrorNum] # тут расстояние до него

    distFaces = {}
    pointFaces = {}
    for k in range(6): #считаем расстояние до граней на траектории
        d = rayInFace(rayPoint, rayDirection, faces[k])
        if (d is not False):
            distFaces.update({k:d})
            pointFaces.update({k: point})

    nearFaceNum = min(distFaces.items(), key=operator.itemgetter(1))[0] #находим ближайшую грань на траектории
    nearFaceDist = distFaces[nearFaceNum]

    if (flagMirror == 0): #если зеркал на траектории нет - вываливаемся с ближайшей гранью
        rayPoint = pointFaces[nearFaceNum]
        break
    if (round(nearFaceDist - nearMirrorDist,1) < 0): #если зеркала на траектории дальше ближней грани  - вываливаемся с ближайшей гранью
        rayPoint = pointFaces[nearFaceNum]
        break
    else:#на траектории ближайшим является зеркало
        energy -= 1
        rayPoint = points[nearMirrorNum]
        n = mirrors[nearMirrorNum]['n']
        scal = 2*sum(map(operator.mul, rayDirection, n))/(pow(n[0], 2) + pow(n[1], 2) + pow(n[2], 2)) #вычисляем отраженное направление
        rayDirection = list(map(operator.sub, rayDirection, list(map(operator.mul, n, [scal, scal, scal]))))

out = open('output.txt', 'w')
if (energy == 0):
    out.write(str(0) + "\n")
    out.write(str(rayPoint[0]) + " " + str(rayPoint[1]) + " " + str(rayPoint[2]))
else:
    out.write(str(1) + "\n")
    out.write(str(energy) + "\n")
    out.write(str(rayPoint[0]) + " " + str(rayPoint[1]) + " " + str(rayPoint[2]) + "\n")
    out.write(str(rayDirection[0]) + " " + str(rayDirection[1]) + " " + str(rayDirection[2]))
out.close()
