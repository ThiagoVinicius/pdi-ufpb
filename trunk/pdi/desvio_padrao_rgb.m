function imresult = desvio_padrao_rgb(im, dim)

    imr = banda_r(im);
    img = banda_g(im);
    imb = banda_b(im);
    
    imresult = juntar_bandas( desvio_padrao_monoc(imr, dim), desvio_padrao_monoc(img, dim), desvio_padrao_monoc(imb, dim) );

end