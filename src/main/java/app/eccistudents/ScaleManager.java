package app.eccistudents;


public class ScaleManager{
    Figure figure;
    int[][] cleanM;
    int[][] newFigure;
    int[][] figureM;
    int centerMatrixF;
    int centerMatrixC;
    int colorFondo;
    int zoomScope;

    public ScaleManager(Figure x){
        figure = x;
        cleanM = figure.getCleanM();
        figureM = figure.getFigureM();
        setCenters(figure.getCenters());
        colorFondo = figureM[0][0];
        paintBackground();
    }

    public void setCenters(int [] c){
        int counter = 0;
        centerMatrixF = c[counter++];
        centerMatrixC = c[counter++];
        

    }

    public void defineMaxZoom(){
        int zoomMatrixF = figureM.length * 8;
        int zoomMatrixC = figureM[0].length *8;
        if(zoomMatrixF<=cleanM.length && zoomMatrixC<=cleanM[0].length){
            Zoom(8);
            zoomScope = 8;
        } else{
            zoomMatrixF = figureM.length * 4;
            zoomMatrixC = figureM[0].length *4;
            if(zoomMatrixF<=cleanM.length && zoomMatrixC<=cleanM[0].length){
                Zoom(4);
                zoomScope = 4;
            } else 
            {
                zoomMatrixF = figureM.length * 2;
                zoomMatrixC = figureM[0].length *2;
                if(zoomMatrixF<=cleanM.length && zoomMatrixC<=cleanM[0].length){
                    Zoom(2);    
                    zoomScope = 2;
                } else{
                    Zoom(1);
                    zoomScope = 1;
                }
            } 
        }
        figure.setMaxZoom(zoomScope);
    }

    public void Zoom(int scope){
        int newFigureF = figureM.length * scope;
        int newFigureC = figureM[0].length * scope;
        
        newFigure = new int [newFigureF][newFigureC];
        
        for(int f = 0; f < figureM.length; ++f){
            for(int c = 0; c < figureM[0].length ; ++c){
                if(figureM[f][c] != colorFondo){ //ARREGLAR PARA CUANDO FONDO = FIGURAFONDO
                    newFigure[f*scope][c*scope] = figureM[f][c];
                } else {
                    newFigure[f*scope][c*scope] = colorFondo;
                }
                for(int counter = scope -1; counter>= 0; --counter){  
                    for(int nf = 0; nf<scope ; nf++){ 
                        if(figureM[f][c] != colorFondo){
                            newFigure[(f*scope)+nf][(c*scope)+counter] = figureM[f][c];
                        }  else {
                            newFigure[(f*scope)+nf][(c*scope)+counter] = colorFondo;
                        }
                    }
                }
            }
        }
        centerFigure();
        Imagen img = new Imagen (cleanM);
        img.dibujar();
    }

    public void paintBackground(){
        for(int x = 0; x < cleanM.length; ++x){
            for(int y = 0; y < cleanM[0].length; ++y){
                cleanM[x][y] = colorFondo;
            }
        }
    }

    public void centerFigure(){
        paintBackground();
        int centerFigureF = newFigure.length/2;
        int centerFigureC = newFigure[0].length/2;
        cleanM[centerMatrixF][centerMatrixC] = newFigure[centerFigureF][centerFigureC];
        for (int f = 0; f<newFigure.length; f++){
            for(int c = 0 ; c<newFigure[0].length; c++){
                int newPosF = f - centerFigureF;
                int newPosC = c - centerFigureC;
                cleanM[centerMatrixF+newPosF][centerMatrixF+newPosC] = newFigure[f][c];

            }

        }

    }

}