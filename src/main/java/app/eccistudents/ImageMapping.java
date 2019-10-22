package app.eccistudents;

public class ImageMapping{

    public static final int[] dirX = {0,1,1,1,0,-1,-1,-1};
    public static final int[] dirY = {-1,-1,0,1,1,1,0,-1};
    
    int[][] matrix;
    int[][] componentsMatrix;
    int backgroundColor;
    int borderColor;
    int borderIndex;
    int insideColor;
    
    int rowsLength;
    int columnsLength;
    
    int backgroundTag;
    
    int matrixValue;
    int matrixNextValue;
    int counter = 0;
    
    Imagen image;

    public ImageMapping(String imageName){
        this.matrix = setAndGetImage(imageName);
        rowsLength = matrix.length;
        columnsLength = matrix[0].length;
        
        componentsMatrix = new int[rowsLength][columnsLength];

        backgroundColor = matrix[0][0];
        backgroundTag = -1;
        borderIndex = -1;
    }
    
    public int[][] setAndGetImage(String imageName){
        image = new Imagen(imageName);
        image.dibujar();
        return image.getMatriz();
    }

    public void mapImage(){
        mapBackground(0,0);
        while(borderIndex < backgroundTag){
            mapFigure();
            ++borderIndex;
        }
        Imagen image2 = new Imagen(componentsMatrix);
        image2.dibujar();
    }
    
    public void mapBackground(int posX, int posY){
        componentsMatrix[posX][posY] = backgroundTag;
        
        for(int i=0;i<dirX.length;i+=2){
            int newPosX = posX + dirX[i];
            int newPosY = posY + dirY[i];
            if(positionInBounds(newPosX,newPosY)){
                if(componentsMatrix[newPosX][newPosY] == 0){
                    if(matrix[newPosX][newPosY] == backgroundColor){
                        mapBackground(newPosX,newPosY);
                    } else {
                        borderIndex--;
                        borderColor = matrix[newPosX][newPosY];
                        mapBorder(newPosX,newPosY);
                    }
                }
            }   
        }
    }
    
    public void mapBorder(int posX, int posY){
        componentsMatrix[posX][posY] = borderIndex;
        
        for(int i=0;i<dirX.length;++i){
            int newPosX = posX + dirX[i];
            int newPosY = posY + dirY[i];
            if(positionInBounds(newPosX,newPosY)){
                if(matrix[newPosX][newPosY] == borderColor && componentsMatrix[newPosX][newPosY] == 0){
                    mapBorder(newPosX,newPosY);
                } 
            }
        }
    }
    
    public void mapFigure(){
        System.out.println(""+borderIndex);
        int[] topMatch = new int[2];
        int[] bottomMatch = new int[2];
        int[] rightMatch = new int[2];
        int[] leftMatch = new int[2];
        topMatch = searchVertical(true);
        bottomMatch = searchVertical(false);
        rightMatch = searchHorizontal(true);
        leftMatch = searchHorizontal(false);
        System.out.println("\n"+ borderIndex + "\n" + topMatch[0] + " " + topMatch[1]);
        System.out.println("\n"+ borderIndex + "\n" + bottomMatch[0] + " " + bottomMatch[1]);
        System.out.println("\n"+ borderIndex + "\n" + rightMatch[0] + " " + rightMatch[1]);
        System.out.println("\n"+ borderIndex + "\n" + leftMatch[0] + " " + leftMatch[1]);
        for(int i=0;i<dirX.length;i++){
            componentsMatrix[topMatch[0] + dirX[i]][topMatch[1] + dirY[i]] = 2341634;
            componentsMatrix[bottomMatch[0] + dirX[i]][bottomMatch[1] + dirY[i]] = 2341634;
            componentsMatrix[rightMatch[0] + dirX[i]][rightMatch[1] + dirY[i]] = 2341634;
            componentsMatrix[leftMatch[0] + dirX[i]][leftMatch[1] + dirY[i]] = 2341634;
        }
    }
    
    public int[] searchVertical(boolean top){
        boolean found;
        int[] address = new int[2];
        int startingRow = 1;
        int startingColumn = 1;
        int endRow = 1;
        int addNumber = 1;
        if(top){
            endRow = rowsLength;
            address[0] = componentsMatrix.length-1;
            address[1] = componentsMatrix[0].length-1;
        } else {
            startingRow = rowsLength - 1;
            addNumber = -1;
        }
        for(int c = startingColumn; c < columnsLength; ++c){
            found = false;
            for(int r = startingRow;top?(r<endRow):(r>endRow) && !found;r+=addNumber){
                if(componentsMatrix[r][c] == borderIndex){
                    found = !found;
                    if(top?(r < address[0]):(r > address[0])){
                        address[0] = r;
                        address[1] = c;
                    }
                }
            }
        }
        return address;
    }
    
    public int[] searchHorizontal(boolean right){
        boolean found;
        int[] address = new int[2];
        int startingRow = 1;
        int startingColumn = 1;
        int endColumn = 1;
        int addNumber = 1;
        if(right){
            endColumn = columnsLength;
            address[0] = componentsMatrix.length-1;
            address[1] = componentsMatrix[0].length-1;
        } else {
            startingColumn = columnsLength - 1;
            addNumber = -1;
        }
        for(int r = startingRow; r < rowsLength; ++r){
            found = false;
            for(int c = startingColumn;right?(c<endColumn):(c>endColumn) && !found;c+=addNumber){
                if(componentsMatrix[r][c] == borderIndex){
                    found = !found;
                    if(right?(c < address[1]):(c > address[1])){
                        address[0] = r;
                        address[1] = c;
                    }
                }
            }
        }
        return address;
    }
    
    
    
    public boolean positionInBounds(int p1, int p2){
        return (p2 < matrix.length) && (p1 < matrix[0].length) && (p2 >= 0) && (p1 >= 0);
    }

}