function imresult = mediana_rgb(im, mascara)

    imr = banda_r(im);
    img = banda_g(im);
    imb = banda_b(im);
    
    imresult = juntar_bandas( medfilt2(imr, mascara), medfilt2(img, mascara), medfilt2(imb, mascara) );

end