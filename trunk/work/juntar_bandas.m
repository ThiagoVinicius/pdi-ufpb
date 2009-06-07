function imresult = juntar_bandas(imr, img, imb)

    imresult( : , : , 1 ) = imr;
    imresult( : , : , 2 ) = img;
    imresult( : , : , 3 ) = imb;
    
end