function imresult = mediana_rgb(im, dim)

    imr = banda_r(im);
    img = banda_g(im);
    imb = banda_b(im);
    
    imresult = juntar_bandas( medfilt2(imr, [dim dim]), medfilt2(img, [dim dim]), medfilt2(imb, [dim dim]) );

end