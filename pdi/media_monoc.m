function imresult = media_monoc(im, dim)

    h = fspecial('average', [dim dim]);
    
    imresult = filter2(h, im)/255;

end