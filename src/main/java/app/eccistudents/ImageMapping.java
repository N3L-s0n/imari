package app.eccistudents;

public class ImageMapping{
    
    public static final int[] DIR_R = {-1,-1,0,1,1,1,0,-1};
    public static final int[] DIR_C = {0,1,1,1,0,-1,-1,-1};

    public static final boolean ORIGINAL_MATRIX = true;
    public static final boolean POSITIVE_DIRECTION = true;
    public static final boolean VERTICAL_DIRECTION = true;
    
    private Catalog catalog;

    int[][] matrix;
    int[][] componentsMatrix;

    int[][] tempMatrix;
    int[][] tempComponentsMatrix;

	int backgroundColor;
    int backgroundTag;

    int borderColor;
    int borderTag;

    int insideColor;
    int spotsNumber;
    
    int newPosC;
    int newPosR;

    int rowsLength;
    int columnsLength;
    
    Imagen image;

    public ImageMapping(){
        backgroundTag = -1;
        borderTag = -1;
    }
    
    public int[][] setAndGetImage(String imageName){
        image = new Imagen(imageName);
        image.dibujar();
        return image.getMatriz();
    }
    
    public void init_variables(){
        rowsLength = matrix.length;
        columnsLength = matrix[0].length;
        
        componentsMatrix = new int[rowsLength][columnsLength];
    
        backgroundColor = matrix[0][0];
        
        newPosC = 0;
        newPosR = 0;
    }

    public void mapImage(String imageName){
        matrix = setAndGetImage(imageName);
        init_variables();

        mapBackground(0,0);
        
        int figures = -1 * (borderTag + 1);
        
        catalog = new Catalog(figures, image);
        while(borderTag < backgroundTag){
            delimitFigure();
            ++borderTag;
        }
        Imagen image2 = new Imagen(componentsMatrix);
        image2.dibujar();
    }

    public void delimitFigure(){

        int additionalPixels = 3;        
        int newPixels = 1;

        int bottomMatch = 0;
        int rightMatch = 0;
        int leftMatch = 0;
        int topMatch = 0;

        int height;
        int width;
        
        spotsNumber = 0;

        topMatch = searchMatch( POSITIVE_DIRECTION , VERTICAL_DIRECTION);
        bottomMatch = searchMatch( !POSITIVE_DIRECTION , VERTICAL_DIRECTION);
        leftMatch = searchMatch( POSITIVE_DIRECTION , !VERTICAL_DIRECTION);
        rightMatch = searchMatch( !POSITIVE_DIRECTION , !VERTICAL_DIRECTION);

        height = bottomMatch - topMatch + additionalPixels;
        width = rightMatch - leftMatch + additionalPixels;

        tempMatrix = new int[height][width];
        tempComponentsMatrix = new int[height][width];

        int rows = 0;
        int columns;
        
        for(int r = (topMatch - newPixels); r <= (bottomMatch + newPixels); ++r){
            columns = 0;
        
            for(int c = (leftMatch - newPixels); c <= (rightMatch + newPixels); ++c){
                tempMatrix[rows][columns] = matrix[r][c];
                tempComponentsMatrix[rows][columns] = componentsMatrix[r][c];
                ++columns;
            } 
            ++rows;
        }   
        
        cleanFigure();
        findInside();
        constructFigure();
    }
    
    public void mapBackground(int posR, int posC){
        componentsMatrix[posR][posC] = backgroundTag;
        
        for(int i=0;i<DIR_C.length;i+=2){
            newPosC = posC + DIR_C[i];
            newPosR = posR + DIR_R[i];
        
            if(positionInBounds(newPosR,newPosC,ORIGINAL_MATRIX)){
        
                if(componentsMatrix[newPosR][newPosC] == 0){
        
                    if(matrix[newPosR][newPosC] == backgroundColor){
                        mapBackground(newPosR,newPosC);
                    } else {
                        borderTag--;
                        borderColor = matrix[newPosR][newPosC];
                        mapBorder(newPosR,newPosC,true);
                    }
                }
            }   
        }
    }

    public void mapBorder(int posR, int posC, boolean add){
        
        if(add){
            componentsMatrix[posR][posC] = borderTag;
        } else {
            tempComponentsMatrix[posR][posC] = backgroundTag;
            tempMatrix[posR][posC] = backgroundColor;    
        }

        for(int i=0;i<DIR_C.length;++i){
            newPosC = posC + DIR_C[i];
            newPosR = posR + DIR_R[i];
        
            if(positionInBounds(newPosR,newPosC,add)){
        
                if(add ? addBorder() : cleanBorder()){
                    mapBorder(newPosR,newPosC,add);
                } 
            }
        }
    }

    public void cleanFigure(){
   
        for(int r = 0; r < tempMatrix.length; ++r){
   
            for(int c = 0; c < tempMatrix[r].length; ++c){
   
                if(borderToClean(r,c)){
                    mapBorder(r,c,false);
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
        
        for(int i=0;i<DIR_C.length;i+=2){
            newPosC = posC + DIR_C[i];
            newPosR = posR + DIR_R[i];
            
            if(positionInBounds(newPosR,newPosC, !ORIGINAL_MATRIX) && tempComponentsMatrix[newPosR][newPosC] == 0){
                
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
        
        for(int i=0;i<DIR_C.length;++i){
            newPosC = posC + DIR_C[i];
            newPosR = posR + DIR_R[i];

            if(positionInBounds(newPosR,newPosC, !ORIGINAL_MATRIX)){
                
                if(tempMatrix[newPosR][newPosC] != insideColor && tempComponentsMatrix[newPosR][newPosC] == 0){
                    mapSpots(newPosR,newPosC);
                } 
            }
        }
    }
    
    public int searchMatch(boolean positiveDir, boolean verticalDir){
        
        boolean found;

        int addNumber = 1;
        int address = 0;
        int start1 = 1;
        int start2 = 1;
        int end1 = 1;
        int end2 = 1;
        
        if(verticalDir){
            end1 = columnsLength;

            if(positiveDir){
                end2 = rowsLength;
                address = rowsLength - 1;
            } else {
                start2 = rowsLength - 1;
                addNumber = -1;
            }
        } else {
            end1 = rowsLength;

            if(positiveDir){
                end2 = columnsLength;
                address = columnsLength -1; 
            } else {
                start2 = columnsLength -1;
                addNumber = -1;
            }
        }
        for(int i = start1; i < end1; ++i){
            found = false;

            for(int j = start2;positiveDir?(j<end2):(j>end2) && !found;j+=addNumber){
                if(verticalDir?(componentsMatrix[j][i] == borderTag):(componentsMatrix[i][j] == borderTag)){
                    found = !found;
                    if(positiveDir?(j < address):(j > address)){
                        address = j;
                    }
                }
            }
        }
        return address;
    }
    
    public int calcPixelsArea(){
        int pixelsArea = 0;
        int rowLength = tempMatrix.length;
        int columnLength = tempMatrix[0].length;

        for(int r = 0; r < rowLength; ++r){
            for(int c = 0; c < columnLength; ++c){
                if(tempComponentsMatrix[r][c] != backgroundTag){
                    ++pixelsArea;
                }
            }
        }

        return pixelsArea;
    }

    public void constructFigure(){
        Imagen imageColors = new Imagen(tempMatrix);
        Figure processedFigure = new Figure(imageColors);

        processedFigure.setArea(calcPixelsArea());
        processedFigure.setStainNumber(spotsNumber);        
        catalog.addFigure(processedFigure);
    }

    public boolean cleanBorder(){
        return (tempComponentsMatrix[newPosR][newPosC] != backgroundTag);
    }

    public boolean addBorder(){
        return (matrix[newPosR][newPosC] == borderColor && componentsMatrix[newPosR][newPosC] == 0);
    }

    public boolean borderToClean(int r, int c){
        return (tempComponentsMatrix[r][c] != backgroundTag && tempComponentsMatrix[r][c] != borderTag && tempComponentsMatrix[r][c] != 0);
    }
    
    public boolean positionInBounds(int r, int c, boolean imageMatrix){
        boolean inBounds = false;
        
        if(imageMatrix){
            inBounds = (r < matrix.length) && (c < matrix[0].length) && (r >= 0) && (c >= 0); 
        } else {
            inBounds = (r < tempMatrix.length) && (c < tempMatrix[0].length) && (r >= 0) && (c >= 0); 
        }
        return inBounds;
    }
}