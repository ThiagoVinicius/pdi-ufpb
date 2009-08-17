
__author__="Thiago"
__date__ ="$08/08/2009 12:29:48$"

import pygame.draw
import numpy
from math import sqrt

_BORDER = (
                lambda point: (point[0]-1, point[1]-1), #0
                lambda point: (point[0]-1, point[1]  ), #1
                lambda point: (point[0]-1, point[1]+1), #2
                lambda point: (point[0]  , point[1]+1), #3

                lambda point: (point[0]+1, point[1]+1), #4
                lambda point: (point[0]+1, point[1]  ), #5
                lambda point: (point[0]+1, point[1]-1), #6
                lambda point: (point[0]  , point[1]-1)  #7
          )


NORTH_WEST = 0
NORTH = 1
NORTH_EAST = 2
EAST = 3
SOUTH_EAST = 4
SOUTH = 5
SOUTH_WEST = 6
WEST = 7

#print _BORDER[NORTH_WEST]((1, 1))
#print _BORDER[1]((1, 1))
#print _BORDER[2]((1, 1))
#print _BORDER[3]((1, 1))
#print _BORDER[4]((1, 1))
#print _BORDER[5]((1, 1))
#print _BORDER[6]((1, 1))
#print _BORDER[7]((1, 1))

class point (tuple):
    x = property(fget=(lambda self: self[0]))
    y = property(fget=(lambda self: self[1]))


def _angle(pt0, pt1):
    pt2   = point((pt0.x, 1 + pt0.y))
    
    dx1 = pt1.x - pt0.x;
    dy1 = pt1.y - pt0.y;
    dx2 = pt2.x - pt0.x;
    dy2 = pt2.y - pt0.y;
    return (dx1*dx2 + dy1*dy2)/sqrt((dx1*dx1 + dy1*dy1)*(dx2*dx2 + dy2*dy2) + 1e-10);

def _vec_angle( pt1, pt2, pt0 ):
    dx1 = pt1.x - pt0.x;
    dy1 = pt1.y - pt0.y;
    dx2 = pt2.x - pt0.x;
    dy2 = pt2.y - pt0.y;
    return (dx1*dx2 + dy1*dy2)/sqrt((dx1*dx1 + dy1*dy1)*(dx2*dx2 + dy2*dy2) + 1e-10);

print point((1, 2)).x, point((1, 2)).y

print _angle(point((1,1)), point((1,3)))

_LIST_LEN = 10

class border (list):

#    _tmp_list = []
#    _line_cos = 0.0
#    _ignored = 0

    def __init__(self, seq=[]):
        list.__init__(seq)
        self._tmp_list = []
        self._line_cos = 0.0
        self.push_point = self._fill_first


    def _fill_first (self, point):
        if len(self._tmp_list) < _LIST_LEN:
            self._tmp_list.append(point)
        else:
            self._line_cos = _angle(self._tmp_list[0], self._tmp_list[_LIST_LEN-1])
            self.push_point = self._push_for_real

    def _push_for_real (self, point):
        first = self._tmp_list.pop(0)
        ang = _angle(first, point)
        if abs(ang - self._line_cos) < 0.25:
            self._tmp_list.append(point)
        else:
            self.append(self._tmp_list[(_LIST_LEN-1)//2])
            self._tmp_list = [point]
            self.push_point = self._fill_first


    push_point = _fill_first

'''
walk (im, pos) -> list

im = uma imagem PIL
pos = uma tupla de dois, contendo as coordenadas x,y na imagem

'''
def walk (im, pos, zbuffer, came_from=WEST):
    xi, yi = pos
    data = im.getdata()

    this_border = border()
    
    w, h = im.size

    def out_of_im(x, y):
        return x < 0 or x >= h-1 or y < 0 or y >= w-1

    _came_from = came_from

    xc, yc = xi, yi

    #this_border += ((xc, yc), )
    this_border.push_point(point((xc, yc)))
    
    count = 10000

    while True:
        #print 'boo -',
        for i in range(8):
            _came_from += 1
            #print _came_from
            look_now = _came_from % 8
            x, y = _BORDER[look_now]((xc, yc))
            if (out_of_im(x, y)):
                continue
            if data[x*w + y] < 128:
                #this_border.append((x, y))
                this_border.push_point(point((x, y)))
                zbuffer[x*w + y] = False
                xc, yc = x, y
                _came_from = (look_now + 4) % 8
                break

        if ((xc, yc) == (xi, yi)):
            break
        count -= 1
        if count < 1:
            break

    return this_border


def nemo (im, line_increment=10):

    data = im.getdata()
    w, h = im.size

    #print w, h
    zbuffer = numpy.ones(w*h, dtype=bool)

    a_lot_of_borders = []

    i = 0

    while i < h-1:
        j = 0
        while j < w-1:
            if data[i*w + j] >= 128:
                j += 1
                if data[i*w + j] < 128:
                    if zbuffer[i*w + j]:
                        a_lot_of_borders.append(walk(im, (i, j), zbuffer))
                j -= 1
            j += 1
        i += line_increment

    return a_lot_of_borders

_MAX_INT = (1 << 16) - 1
def _bbox (poly):

    xmin, ymin = _MAX_INT, _MAX_INT
    xmax, ymax = 0, 0

    for i in poly:
        xmin = i.x if i.x < xmin else xmin
        xmax = i.x if i.x > xmax else xmax
        ymin = i.x if i.x < ymin else ymin
        ymax = i.x if i.x > ymax else ymax

    return [point((xmin, ymin)),
            point((xmin, ymax)),
            point((xmax, ymin)),
            point((xmax, ymax))]

'''
first e second devem ser bounding boxes.

retorna  0, se nem first nem second estao um dentro do outro.
retorna -1, se first esta dentro de second
retorna  1, se second esta dentro de first
'''
def _is_inside (first, second):
    pass
    


find_quads = lambda borders: [i for i in borders if len(i) == 4]

def calculate_angles (quad):
    angles = []
    i = 0
    _len = len(quad)

    while i < _len:
        angles.append(_vec_angle(quad[i-1 % _len], quad[(i+1) % _len], quad[i]))
        i += 1

    angles.sort()
    return angles

def get_marker (quads):

    result = []

    angles = map(calculate_angles, quads)

    i = 0
    while i < len(angles):
        j = 0
        while j < len(angles):
            if i == j:
                j += 1
                continue
            k = 0
            count = 0
            while k < 4:
                if abs(angles[i][k] - angles[j][k]) < 0.1:
                    count += 1
                k += 1
            if count == k:
                result.append(quads[i])
            j += 1
        i += 1

    return result


def draw_points (border, surface, xi = 0, yi = 0):
    red = (255, 0, 0)
    try:
       for i in border:
           pygame.draw.circle(surface, red, (i[1], i[0]), 3, 1)
    except:
        pass

def draw_quads (border, surface, xi = 0, yi = 0):
    green = (0, 255, 0)
    try:
       if len(border) == 4:
           points = [(i.y, i.x) for i in border]
           pygame.draw.polygon(surface, green, points, 3)
    except:
        pass

def draw_marker (border, surface, xi = 0, yi = 0):
    blue = (0, 0, 255)
    try:
       #if len(border) == 4:
       points = [(i.y, i.x) for i in border]
       pygame.draw.polygon(surface, blue, points, 3)
    except:
        pass

if __name__ == "__main__":
    print "Hello";