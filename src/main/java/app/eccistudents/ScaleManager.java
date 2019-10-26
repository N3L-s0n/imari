package app.eccistudents;



public class ScaleManager{
    Figure figure;
    int[][] standartM;
    int[][] zoomFigure;
    int[][] figureM;
    int heightM;
    int widthM;
    int centerMatrixF;
    int centerMatrixC;
    int colorFondo;
    int zoomScope;
 

    public void setFigure(Figure x){
        figure = x;
        figureM = figure.getMatrix();
        colorFondo = figureM[0][0];
    }

    public void setMeasures(int h, int w){
        heightM = h;
        widthM = w;
        standartM = new int[h][w];
        setCenters();
    }

    public void setCenters(){
        centerMatrixF = standartM.length/2;
        centerMatrixC = standartM[0].length/2;

    }

    public void defineMaxZoom(){
        int zoomMatrixF = figureM.length * 8;
        int zoomMatrixC = figureM[0].length *8;
        if(zoomMatrixF<=standartM.length && zoomMatrixC<=standartM[0].length){
            Zoom(8);
            zoomScope = 8;
        } else{
            zoomMatrixF = figureM.length * 4;
            zoomMatrixC = figureM[0].length *4;
            if(zoomMatrixF<=standartM.length && zoomMatrixC<=standartM[0].length){
                Zoom(4);
                zoomScope = 4;
            } else 
            {
                zoomMatrixF = figureM.length * 2;
                zoomMatrixC = figureM[0].length *2;
                if(zoomMatrixF<=standartM.length && zoomMatrixC<=standartM[0].length){
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

    public void Zoom(int zoom){
        int newFigureF = figureM.length * zoom;
        int newFigureC = figureM[0].length * zoom;

        zoomFigure = new int [newFigureF][newFigureC];

        for(int f = 0; f < figureM.length; ++f){
            for(int c = 0; c < figureM[0].length ; ++c){
                if(figureM[f][c] != colorFondo){ 
                    zoomFigure[f*zoom][c*zoom] = figureM[f][c];
                } else {
                    zoomFigure[f*zoom][c*zoom] = colorFondo;
                }
                for(int counter = zoom -1; counter>= 0; --counter){  
                    for(int nf = 0; nf<zoom ; nf++){ 
                        if(figureM[f][c] != colorFondo){
                            zoomFigure[(f*zoom)+nf][(c*zoom)+counter] = figureM[f][c];
                        }  else {
                            zoomFigure[(f*zoom)+nf][(c*zoom)+counter] = colorFondo;
                        }
                    }
                }
            }
        }
        centerFigure();
        Imagen img = new Imagen (standartM);
        img.dibujar();

    }

    public void paintBackground(){
        for(int x = 0; x < standartM.length; ++x){
            for(int y = 0; y < standartM[0].length; ++y){
                standartM[x][y] = colorFondo;
            }
        }
    }

    public void centerFigure(){
        paintBackground();
        int centerFigureF = zoomFigure.length/2;
        int centerFigureC = zoomFigure[0].length/2;

        for (int f = 0; f<zoomFigure.length; f++){
            for(int c = 0 ; c<zoomFigure[0].length; c++){
                standartM[f+centerMatrixF-centerFigureF][c+centerMatrixC-centerFigureC] = zoomFigure[f][c];
            }
        }

    }
}