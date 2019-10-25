package app.eccistudents;
 

public class Figure{
    private Imagen i;
    private int[][] matrix;
    private int [][] cleanM;
    
    private int centerMatrixF;
    private int centerMatrixC;
    private int[] centers;
    
    private int stains;
    private int area;
    private int maxZoom;


    public Figure(Imagen n){
        Imagen i = n;
         matrix = i.getMatriz();
         cleanM = new int[900][900];
         centers = new int[2];
        centerMatrixF = cleanM.length/2;
        centerMatrixC = cleanM[0].length/2; //Si me dan la matriz de la figura recortada.
        setCenters();
    } 
    
    public void setCenters(){
     centers[0] = centerMatrixF;
    centers[1] = centerMatrixC;
    }
    
   public int[][] getCleanM(){
       return cleanM;
   }
   
     public int[][] getFigureM(){
       return matrix;
   }
   
    public int [] getCenters(){
       return centers;
    }
    
    public void setMaxZoom(int zoom){
    maxZoom = zoom;
    }
    public void setArea(int area){
    this.area = area;
    }
    public void setStainNumber(int stains){
    this.stains = stains;
    }
}