function imresult = image_r(im)

    imresult = im;
    
    imresult( : , : , 2:3 ) = 0;

end