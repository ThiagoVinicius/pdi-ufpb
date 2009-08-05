import pygame
import Image
from pygame.locals import *
import sys

import ctypes_opencv as opencv
#this is important for capturing/displaying images
from ctypes_opencv import highgui

from util import convert

import ImageFilter
import ImageChops
import ImageOps
import ImageEnhance

camera = highgui.cvCreateCameraCapture(0)

print highgui.cvSetCaptureProperty(camera, highgui.CV_CAP_PROP_FRAME_HEIGHT, 640)
print highgui.cvSetCaptureProperty(camera, highgui.CV_CAP_PROP_FRAME_WIDTH, 480)
print highgui.cvSetCaptureProperty(camera, highgui.CV_CAP_PROP_FPS, 30)

#highgui.cvSize(640, 480)

def get_image():
    im = highgui.cvQueryFrame(camera)
    im.origin = 1
    # Add the line below if you need it (Ubuntu 8.04+)
    #im = opencv.cvGetMat(im)
    #convert Ipl image to PIL image
    return convert.ipl_to_pil(im)

fps = 30.0
pygame.init()

size = get_image().size
window1 = pygame.display.set_mode(size)
pygame.display.set_caption("WebCam Demo")
screen = pygame.display.get_surface()

oldim  = get_image()


contrast  = 1.0
increment = 0.0
while True:
    events = pygame.event.get()
    for event in events:
        if event.type == QUIT:
            sys.exit(0)
        if event.type == KEYDOWN:
            print 'Keydown'
            if   event.key == ord('.'): increment =  0.025
            elif event.key == ord(','): increment = -0.025
            else: sys.exit(0)
        if event.type == KEYUP:
            print 'Keyup'
            increment = 0.0

    contrast += increment
    if increment != 0.0: print contrast
            
            
    thisim = im = get_image()

    #im = im.filter(ImageFilter.MedianFilter(5))
    #for i in range(1):
    #    im = im.filter(ImageFilter.FIND_EDGES)
    #im = im.filter(ImageFilter.DETAIL)
    #im = im.filter(ImageFilter.CONTOUR)
    #im = im.filter(ImageFilter.BLUR)
    #im = im.filter(ImageFilter.SHARPEN)
    
    #im = ImageChops.difference(thisim, oldim)
    #im = ImageChops.darker(thisim, oldim)
    #im = ImageChops.lighter(thisim, oldim)

    #im = ImageEnhance.Contrast(im).enhance(contrast)

    #im = im.convert('1')

    #im = im.filter(ImageFilter.ModeFilter(3))
    #im = im.filter(ImageFilter.MinFilter(3))
    #im = im.filter(ImageFilter.MaxFilter(3))

    #im = im.filter(ImageFilter.RankFilter(3, 8))
    #im = im.filter(ImageFilter.RankFilter(3, 7))

    #im = ImageOps.equalize(im)

    im = im.convert('L')

    im = Image.eval(im, (lambda x: 0 if x <= 127 else 255))
    
    im = Image.merge('RGB', (im, im, im))
    #im = im.filter(ImageFilter.SMOOTH)
    
    pg_img = pygame.image.frombuffer(im.tostring(), im.size, im.mode)
    screen.fill((0,0,0))
    screen.blit(pg_img, (0,0))
    
    #marker = get_marker(im)
    #marker.draw(screen)
    #print marker
    
    pygame.display.flip()
    oldim = thisim
    #pygame.time.delay(int(1000 * 1.0/fps))
    

