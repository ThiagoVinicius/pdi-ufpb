function imresult = media_rgb(im, dim)

    r = banda_r(im);
    g = banda_g(im);
    b = banda_b(im);
    
    r_ = media_monoc(r, dim);
    g_ = media_monoc(g, dim);
    b_ = media_monoc(b, dim);
    
    imresult = juntar_bandas(r_, g_, b_);

end