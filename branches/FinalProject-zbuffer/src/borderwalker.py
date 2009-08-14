
__author__="Thiago"
__date__ ="$08/08/2009 12:29:48$"

import Image
import pygame.draw

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

print _BORDER[NORTH_WEST]((1, 1))
#print _BORDER[1]((1, 1))
#print _BORDER[2]((1, 1))
#print _BORDER[3]((1, 1))
#print _BORDER[4]((1, 1))
#print _BORDER[5]((1, 1))
#print _BORDER[6]((1, 1))
#print _BORDER[7]((1, 1))


'''
walk (im, pos) -> list

im = uma imagem PIL
pos = uma tupla de dois, contendo as coordenadas x,y na imagem

'''
def walk (im, pos, zbuffer, came_from=WEST):
    xi, yi = pos
    data = im.getdata()

    border = []
    
    w, h = im.size

    def out_of_im(x, y):
        return x < 0 or x >= h-1 or y < 0 or y >= w-1

    _came_from = came_from

    xc, yc = xi, yi

    border += ((xc, yc), )
    
    count = 10000

    while True:
        #print 'boo -',
        for i in range(8):
            _came_from += 1
            #print _came_from
            look_now = _came_from % 8
            x, y = _BORDER[look_now]((xc, yc))
            index = x*w + y
            if (out_of_im(x, y)):
                continue
            if data[index] < 128:
                #border.append((x, y))
                border += ((x, y), )
                #zbuffer[index] = None
                xc, yc = x, y
                _came_from = (look_now + 4) % 8
                break

        if ((xc, yc) == (xi, yi)):
            break
        count -= 1
        if count < 1:
            break

    return border


#    while i < h-1:
#        while j < w-1:
#            #print data[i*w + j]
#            #data[i, j] = 128
#            #print data[i*h + j]
#            j += 1
#        i += 20

    #im.putdata(data)

def nemo (im, line_increment=1):

    if line_increment < 1:
        line_increment = 1

    data = im.getdata()
    w, h = im.size

    zbuffer = { }
    a_lot_of_borders = []

    i = 0
    while i < h-1:
        j = 0
        while j < w-1:
            pos = i*w + j
            if data[pos] >= 128:
                if data[pos+1] < 128 and pos+1 not in zbuffer:
                    #print 'oie!!'
                    a_lot_of_borders.append(walk(im, (i, j), zbuffer))
                    return a_lot_of_borders
            j += 1
        i += line_increment

    return a_lot_of_borders


def draw (border, surface, xi = 0, yi = 0):
        red = (255, 0, 0)
        try:
            for i in border:
                    pygame.draw.circle(surface, red, (i[1], i[0]), 2, 1)
        except:
            pass

if __name__ == "__main__":
    print "Hello";