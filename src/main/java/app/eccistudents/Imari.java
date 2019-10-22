package app.eccistudents;

public class Imari{

    public final String menu = "\n1-Agregar imagen\n2-Mostrar inventario\n3-Mostrar imagenes por rango\n4-Salir\n";
    private Interface interface1;

    public Imari(){
        interface1 = new Interface();
    }

    public void run(){
        interface1.printMessage("_____IMARI_____");
        int option = 0;
        do{
            interface1.printMessage(menu);
            option = getOption();
            runOption(option);
        }while(option != 4);
    }

    public void runOption(int option){
        switch(option){
            case 1:
                String imagePath;
                imagePath = interface1.chooseFile();
                System.out.println(imagePath);
                ImageMapping imageMap = new ImageMapping(imagePath);
                imageMap.mapImage();
                break;
            case 2:
                break;
            case 3:
                break;    
        }
    }

    public int getOption(){
        int option = interface1.askOption("Seleccione una opcion");

        while(option <= 0 || option > 4){
            System.out.println("Valor no valido.");
            option = interface1.askOption("Seleccione una opcion");
        }

        return option;
    }
}