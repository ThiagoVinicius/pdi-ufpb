function imresult = desvio_padrao_monoc(im, dim)

    raio = floor( ( dim - 1 ) / 2 );
    
    m = size(im, 1);
    n = size(im, 2);
    
    imresult = zeros(m, n);
    
    xini = 0;
    yini = 0;
    
    xfin = 0;
    yfin = 0;
       
    for i = 1 : m
        for j = 1 : n
       
            if ( (i - raio) < 1 )
                xini = 1;
            else
                xini = i - raio;
            end;
               
            if ( (j - raio) < 1 )
                yini = 1;
            else
                yini = j - raio;
            end;
                
            if ( (i + raio) > m )
                xfin = m;
            else
                xfin = i + raio;
            end;
                
            if ( (j + raio) > n )
                yfin = n;
            else
                yfin = j + raio;
            end;
            
            imresult(i, j) = round( std_mat( im( xini:xfin , yini:yfin ) ) );
            
        end;
    end;

    imresult = uint8(imresult);
    
end