package app.eccistudents;
 
public class Figure{

    private int[][] matrix;
    private int stains;
    private int area;
    private int maxZoom;

    public Figure(Imagen n){
        Imagen i = n;
        matrix = i.getMatriz();

    } 

    public int[][] getMatrix(){
        return matrix;
    }
    
    public int getMaxZoom(){
    return maxZoom;
    }
    
    public int getArea(){
    return area;
    }
    
    public int getStains(){
    return stains;
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