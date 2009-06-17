function imOutput = desviopadrao (imInput, n)
% desviopadrao: aplica o filtro que faz com que cada pixel da imagem
% resultante seja o desvio-padrão dos pixels ao seu redor
% imInput -> Imagem de entrada
% imOutput -> Imagem de saida
% n -> corresponde ao tamanha da máscara, onde n deve ser ímpar

% o desvio-padrão será calculado com a fórmula:
% desvio = sqrt((1/n-1) * ?(i de 1 a n) (xi - xa)²)), onde xa é igual a:
% xa = 1/n * ?(i de 1 a n) xi, onde cada n utilizado é, na verdade, o
% número total de elementos da matriz ao redor, ou seja, é o valor do
% quadrado do valor 'n' recebido como parâmetro na função
% sera calculado um desvio para cada componente da imagem

limite = fix(n/2);
centro = ceil(n/2);

%Lacos para percorrer as linhas e colunas da imagem de entrada
for i = centro:size(imInput,1)-limite
    for j = centro:size(imInput,2)-limite
        
        %matrizes que contém cada componente da matriz que faz parte da 
        % vizinhanca n x n do pixel (i,j) atual
        matriz1 = imInput(i-limite:i+limite, j-limite:j+limite, 1);
        matriz2 = imInput(i-limite:i+limite, j-limite:j+limite, 2);
        matriz3 = imInput(i-limite:i+limite, j-limite:j+limite, 3);
        
        % cada matriz é transformada em uma matriz coluna, para facilitar
        % o cálculo do desvio-padrão
        matriz1 = double(matriz1(:));
        matriz2 = double(matriz2(:));
        matriz3 = double(matriz3(:));
        
        xar = 0;
        xag = 0;
        xab = 0;
        % calcula os valores do xa de cada componente
        for k = 1:n*n
            xar = xar + matriz1(k);
            xag = xag + matriz2(k);
            xab = xab + matriz3(k);
        end;
        xar = 1/(n*n) * xar;
        xag = 1/(n*n) * xag;
        xab = 1/(n*n) * xab;
        
        desvior = 0;
        desviog = 0;
        desviob = 0;
        % calcula o desvio propriamente dito de cada componente
        for k = 1:n*n
            desvior = desvior + ((matriz1(k) - xar) * (matriz1(k) - xar));
            desviog = desviog + ((matriz2(k) - xag) * (matriz2(k) - xag));
            desviob = desviob + ((matriz3(k) - xab) * (matriz3(k) - xab));
        end;
        desvior = sqrt((1/((n*n) - 1)) * desvior);
        desviog = sqrt((1/((n*n) - 1)) * desviog);
        desviob = sqrt((1/((n*n) - 1)) * desviob);
        
        imOutput(i,j,1) = desvior;
        imOutput(i,j,2) = desviog;
        imOutput(i,j,3) = desviob;
    end;
end;

imOutput = uint8(imOutput);