function imresult = yuv2rgb(im)

    r = 1 * im(:, :, 1) + 1.13983 * im(:, :, 3);
    g = 1 * im(:, :, 1) - 0.39465 * im(:, :, 2) - 0.58060 * im(:, :, 3);
    b = 1 * im(:, :, 1) + 2.03211 * im(:, :, 2);
    
    imresult = juntar_bandas(r, g, b);    
    
end