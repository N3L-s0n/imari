package app.eccistudents;

import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Interface{

    private Scanner scanner;

    public Interface(){
        scanner = new Scanner(System.in);
    }

    public void printMessage(String message){
        System.out.println(message);
    }

    public int askOption(String message){
        int option = 0;
        String optionString = "";

        while(option == 0){
            System.out.print(message + ": ");
            optionString = scanner.nextLine();
            try {
                option = Integer.parseInt(optionString);
            } catch (Exception e) {
                System.out.println("Valor no valido.");
            }
        }

        return option;
    }

    public String chooseFile(){
        String path = "";
        
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " +
            chooser.getSelectedFile().getName());
        }
        if(chooser.getSelectedFile() != null){
            path = chooser.getSelectedFile().getAbsolutePath();
        }
        return path;
    }
}
