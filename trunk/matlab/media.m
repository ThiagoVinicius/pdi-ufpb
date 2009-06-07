function imresult = media(im, dim)

    h = fspecial('average', [dim dim]);
    
    imresult = imfilter(im, h, 'replicate');

end