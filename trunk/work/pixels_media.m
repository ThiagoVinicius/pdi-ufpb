function result = pixels_media(im, ij, mn, xy)

    i = ij(1);
    j = ij(2);
    
    x = xy(1);
    y = xy(2);

    m = mn(1);
    n = mn(2);
    
    if i - m < 1
        min_i = 1;
    else
        min_i = i - m;
    end
        
    if i + m > x 
        max_i = x;
    else
        max_i = i + m;
    end
        
    if j - n < 1
        min_j = 1;
    else
        min_j = j - n;
    end
        
    if j + n > y
        max_j = y;
    else
        max_j = j + n;
    end
    
    pixels = im(min_i : max_i, min_j : max_j, :);
    
    result = round( mean( mean( pixels ) ) );

end