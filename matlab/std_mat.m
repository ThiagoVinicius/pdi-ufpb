function result = std_mat(mat)

    aux = reshape(mat, prod(size(mat)), 1);
    
    desvio_medio = aux - mean(aux);
    
    variancia = mean(desvio_medio .* desvio_medio);
    
    result =  round( sqrt(variancia) );    

end