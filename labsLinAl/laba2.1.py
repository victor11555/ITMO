import math

inp = open('input.txt', 'r')

v_x, v_y, x = map(float, inp.readline().strip().split())

a_x, a_y, y = map(float, inp.readline().strip().split())

m_x, m_y, z = map(float, inp.readline().strip().split())

w_x, w_y, d = map(float, inp.readline().strip().split())

inp.close()
bort = 0
if ((w_x - v_x) * a_y + (w_y - v_y) * (- a_x)) > 0:
    bort = -1
    if ((m_y == 0) and (m_x == 0)) or (math.degrees(math.acos((m_x * (- a_y) + m_y * a_x) / (
    math.sqrt((m_x * m_x + m_y * m_y +1) * ((- a_y) * (- a_y) + a_x * a_x))))) == 0):
        gamma = 0
    else:
        gamma = 90- round(math.degrees(math.acos(
            (m_x * a_y + m_y * (-a_x)) / (math.sqrt((m_x * m_x + m_y * m_y+1) * (a_y * a_y + (-a_x) * (-a_x)))))), 2)

    tmp = math.degrees(math.acos(((w_x - v_x) * a_y + (w_y - v_y) * (-a_x)) / (math.sqrt(math.pow((w_x - v_x), 2) + math.pow((w_y - v_y), 2)) * math.sqrt(math.pow(a_y, 2) + math.pow(a_x, 2)))))

    if ((w_x - v_x) * a_x + (w_y - v_y) * a_y) >= 0:
        betta = tmp
    else:
        betta = -tmp
        
    if (math.fabs(betta) > 60) or (math.fabs(gamma) > 60):
        bort = 0

elif ((w_x - v_x) * a_y + (w_y - v_y) * (- a_x)) < 0:
    bort = 1
    if ((m_y == 0) and (m_x == 0)) or (math.degrees(math.acos((m_x * (- a_y) + m_y * a_x) / (
    math.sqrt((m_x * m_x + m_y * m_y + 1) * ((- a_y) * (- a_y) + a_x * a_x))))) == 0):
        gamma = 0

    else:
        gamma =90 - round(math.degrees(math.acos(
            (m_x * (- a_y) + m_y * a_x) / (math.sqrt((m_x * m_x + m_y * m_y+1) * ((- a_y) * (- a_y) + a_x * a_x))))), 2)
    tmp = math.degrees(math.acos(((w_x - v_x) * (- a_y) + (w_y - v_y) * a_x) / (
                math.sqrt(math.pow((w_x - v_x), 2) + math.pow((w_y - v_y), 2)) * math.sqrt(
            math.pow(a_y, 2) + math.pow(a_x, 2)))))

    if ((w_x - v_x) * a_x + (w_y - v_y) * a_y) >= 0:
        betta = tmp
    else:
        betta = -tmp
    if (math.fabs(betta) > 60) or (math.fabs(gamma) > 60):
        bort = 0

out = open('output.txt', 'w')
if bort == 0:
    out.write(str(0) + "\n")
else:
    out.write(str(bort) + "\n")
    out.write(str(betta) + "\n")
    out.write(str(gamma) + "\n")
    out.write(str("bye"))
out.close()
