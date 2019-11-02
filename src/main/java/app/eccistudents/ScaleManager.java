package app.eccistudents;


public class ScaleManager{
Figure figure;
private int[][] standartM;
private int[][] zoomFigure;
private int[][] figureM;
private int centerMatrixF;
private int centerMatrixC;
private int colorFondo;
private int centerFigureF;
private int centerFigureC;

public void setFigure(Figure x){
    figure = x;
    figureM = figure.getMatrix();
    colorFondo = figureM[0][0];
}

public void setMeasures(int heightM, int widthM){

    standartM = new int[heightM][widthM];
    setCenters();
}

public void setCenters(){
    centerMatrixF = standartM.length/2;
    centerMatrixC = standartM[0].length/2;

}

public void defineMaxZoom(int zF, int zC, int counter){// primera vez: defineMaxZoom( figureM.length, figureM[0].length, 1);


    if((zF * 2 )<=standartM.length && (zC * 2)<=standartM[0].length){
        defineMaxZoom(zF *2,zC *2, counter * 2);
    } else{
    Zoom(counter);
    figure.setMaxZoom(counter);
    }

    
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
    centerFigureF = zoomFigure.length/2;
    centerFigureC = zoomFigure[0].length/2;

    for (int f = 0; f<zoomFigure.length; f++){
        for(int c = 0 ; c<zoomFigure[0].length; c++){
            standartM[f+centerMatrixF-centerFigureF][c+centerMatrixC-centerFigureC] = zoomFigure[f][c];
        }
    }

}
}