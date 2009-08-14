
from ctypes import *
from ctypes_opencv.cxcore import *
from ctypes_opencv.highgui import CV_CVTIMG_SWAP_RB

__all__ = []

#-----------------------------------------------------------------------------
# PIL -- by Jeremy Bethmont
#-----------------------------------------------------------------------------
import Image
from ctypes_opencv.cv import cvCvtColor, CV_RGB2BGR

def pil_to_ipl(im_pil):
    im_ipl = cvCreateImageHeader(cvSize(im_pil.size[0], im_pil.size[1]),
IPL_DEPTH_8U, 3)
    data = im_pil.tostring('raw', 'RGB', im_pil.size[0] * 3)
    cvSetData(im_ipl, cast(data, POINTER(c_byte)), im_pil.size[0] * 3)
    cvCvtColor(im_ipl, im_ipl, CV_RGB2BGR)
    im_ipl._depends = (data,)
    return im_ipl
    
def ipl_to_pil(im_ipl):
    size = (im_ipl.width, im_ipl.height)
    data = im_ipl.data_as_string()
    im_pil = Image.fromstring(
                "RGB", size, data,
                'raw', "BGR", im_ipl.widthStep, -1
    )
    return im_pil
    
__all__ += ['ipl_to_pil', 'pil_to_ipl']
    
    
