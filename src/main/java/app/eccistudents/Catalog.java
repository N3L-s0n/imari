package app.eccistudents;
public class Catalog{
    int n;
    Imagen imagen;
    Figure[] catalog;
    public Catalog(int nFigures, Imagen imagen) {
        this.imagen = imagen;
        n = 0;
        catalog = new Figure [nFigures];
    }    
    public void addFigure(Figure figura){
        catalog[n] = figura;
        n++;
    }
}
