package app.eccistudents;

public class ImageMapping{

    public static final int[] dirR = {0,1,1,1,0,-1,-1,-1};
    public static final int[] dirC = {-1,-1,0,1,1,1,0,-1};
    
    int[][] matrix;
    int[][] componentsMatrix;
    int[][] tempMatrix;
    int[][] tempComponentsMatrix;

	int backgroundColor;
    int borderColor;
    int borderIndex;

    int insideColor;
    int spotsNumber;
    
    int newPosC;
    int newPosR;

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

        newPosC = 0;
        newPosR = 0;
    }
    
    public int[][] setAndGetImage(String imageName){
        image = new Imagen(imageName);
        image.dibujar();
        return image.getMatriz();
    }

    public void mapImage(){
        mapBackground(0,0);
        while(borderIndex < backgroundTag){
            delimitFigure();
            ++borderIndex;
        }
        Imagen image2 = new Imagen(componentsMatrix);
        image2.dibujar();
    }
    
    public void mapBackground(int posR, int posC){
        componentsMatrix[posR][posC] = backgroundTag;
        
        for(int i=0;i<dirC.length;i+=2){
            newPosC = posC + dirC[i];
            newPosR = posR + dirR[i];
            if(positionInBounds(newPosR,newPosC)){
                if(componentsMatrix[newPosR][newPosC] == 0){
                    if(matrix[newPosR][newPosC] == backgroundColor){
                        mapBackground(newPosR,newPosC);
                    } else {
                        borderIndex--;
                        borderColor = matrix[newPosR][newPosC];
                        mapBorder(newPosR,newPosC);
                    }
                }
            }   
        }
    }
    
    public void mapBorder(int posR, int posC){
        componentsMatrix[posR][posC] = borderIndex;
        
        for(int i=0;i<dirC.length;++i){
            newPosC = posC + dirC[i];
            newPosR = posR + dirR[i];
            if(positionInBounds(newPosR,newPosC)){
                if(matrix[newPosR][newPosC] == borderColor && componentsMatrix[newPosR][newPosC] == 0){
                    mapBorder(newPosR,newPosC);
                } 
            }
        }
    }
    
    public void delimitFigure(){

        spotsNumber = 0;

        int[] topMatch = new int[2];    // No tienen que ser vectores
        int[] bottomMatch = new int[2];
        int[] rightMatch = new int[2];
        int[] leftMatch = new int[2];

        int height;
        int width;

        topMatch = searchVertical(true);
        bottomMatch = searchVertical(false);
        leftMatch = searchHorizontal(true);
        rightMatch = searchHorizontal(false);

        height = bottomMatch[0] - topMatch[0] + 2;
        width = rightMatch[1] - leftMatch[1] + 2;

        tempMatrix = new int[height][width];
        tempComponentsMatrix = new int[height][width];

        int rows = 0;
        int columns;
        for(int r = (topMatch[0] - 1); r <= bottomMatch[0]; ++r){
            columns = 0;
            for(int c = (leftMatch[1] - 1); c <= rightMatch[1]; ++c){
                tempMatrix[rows][columns] = matrix[r][c];
                ++columns;
            } 
            ++rows;
        }   
        rows = 0;
        for(int r = (topMatch[0] - 1); r <= bottomMatch[0]; ++r){
            columns = 0;
            for(int c = (leftMatch[1] - 1); c <= rightMatch[1]; ++c){
                tempComponentsMatrix[rows][columns] = componentsMatrix[r][c];
                ++columns;
            } 
            ++rows;
        }   
        
        cleanFigure();
        constructImages();
        findInside();
        System.out.println(spotsNumber);
    }

    public void constructImages(){
        Imagen imageColors = new Imagen(tempMatrix);
        imageColors.dibujar();
        Imagen imageFigure = new Imagen(tempComponentsMatrix);
        imageFigure.dibujar();    
    }

    public void cleanFigure(){
        for(int r = 0; r < tempMatrix.length; ++r){
            for(int c = 0; c < tempMatrix[r].length; ++c){
                if(tempComponentsMatrix[r][c] != backgroundTag && tempComponentsMatrix[r][c] != borderIndex && tempComponentsMatrix[r][c] != 0){
                    cleanBorder(r,c);
                }
            }
        }
    }

    public void cleanBorder(int posR, int posC){
        tempComponentsMatrix[posR][posC] = backgroundTag;
        tempMatrix[posR][posC] = backgroundColor;

        for(int i=0;i<dirC.length;++i){
            newPosC = posC + dirC[i];
            newPosR = posR + dirR[i];
            if(positionInBoundsFigure(newPosR,newPosC)){
                if(tempComponentsMatrix[newPosR][newPosC] != backgroundTag){
                    cleanBorder(newPosR,newPosC);
                } 
            }
        }
    }

    public void findInside(){
        boolean found = false;
        for(int r = 0; r < tempMatrix.length && !found; ++r){
            for(int c = 0; c < tempMatrix[r].length && !found; ++c){
                if(tempComponentsMatrix[r][c] == 0){
                    insideColor = tempMatrix[r][c];
                    searchSpots(r,c);
                    found = true;
                }
            }
        }
    }

    public void searchSpots(int posR, int posC){
        tempComponentsMatrix[posR][posC]= 1;

        for(int i=0;i<dirC.length;i+=2){
            newPosC = posC + dirC[i];
            newPosR = posR + dirR[i];
            if(positionInBoundsFigure(newPosR,newPosC) && tempComponentsMatrix[newPosR][newPosC] == 0){
                if(tempMatrix[newPosR][newPosC] != insideColor){
                    ++spotsNumber;
                    mapSpots(newPosR, newPosC);
                } else {
                    if(tempMatrix[newPosR][newPosC] == insideColor){
                        searchSpots(newPosR, newPosC);
                    }
                }
            }   
        }
    }
    
    public void mapSpots(int posR, int posC){
        tempComponentsMatrix[posR][posC] = 1;
        
        for(int i=0;i<dirC.length;++i){
            newPosC = posC + dirC[i];
            newPosR = posR + dirR[i];
            if(positionInBoundsFigure(newPosR,newPosC)){
                if(tempMatrix[newPosR][newPosC] != insideColor && tempComponentsMatrix[newPosR][newPosC] == 0){
                    mapSpots(newPosR,newPosC);
                } 
            }
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
    
    public int[] searchHorizontal(boolean left){
        boolean found;
        int[] address = new int[2];
        int startingRow = 1;
        int startingColumn = 1;
        int endColumn = 1;
        int addNumber = 1;
        if(left){
            endColumn = columnsLength;
            address[0] = componentsMatrix.length-1;
            address[1] = componentsMatrix[0].length-1;
        } else {
            startingColumn = columnsLength - 1;
            addNumber = -1;
        }
        for(int r = startingRow; r < rowsLength; ++r){
            found = false;
            for(int c = startingColumn;left?(c<endColumn):(c>endColumn) && !found;c+=addNumber){
                if(componentsMatrix[r][c] == borderIndex){
                    found = !found;
                    if(left?(c < address[1]):(c > address[1])){
                        address[0] = r;
                        address[1] = c;
                    }
                }
            }
        }
        return address;
    }
    
    
    
    public boolean positionInBounds(int r, int c){
        return (r < matrix.length) && (c < matrix[0].length) && (r >= 0) && (c >= 0);
    }
    
    public boolean positionInBoundsFigure(int r, int c){
        return (r < tempMatrix.length) && (c < tempMatrix[0].length) && (r >= 0) && (c >= 0);
    }



}