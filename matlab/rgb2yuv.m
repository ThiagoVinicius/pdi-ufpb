function imresult = rgb2yuv(im)

    y = 0.299 * im(:, :, 1) + 0.587 * im(:, :, 2) + 0.114 * im(:, :, 3);
    u = 0.436 * im(:, :, 3) - 0.147 * im(:, :, 1) - 0.289 * im(:, :, 2); 
    v = 0.615 * im(:, :, 1) - 0.515 * im(:, :, 2) - 0.1 * im(:, :, 3);
    
    imresult = juntar_bandas(y, u, v);

end