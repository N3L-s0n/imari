package app.eccistudents;

import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JButton;

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
        
        JButton open = new JButton();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if(fileChooser.showOpenDialog(open) == JFileChooser.APPROVE_OPTION){

        }
        path = fileChooser.getSelectedFile().getAbsolutePath();
        return path;
    }
}
