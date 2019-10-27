package app.eccistudents;

import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;


public class Saver{
    
    public static final String osName = System.getProperty("os.name");

    int counter;

    public Saver(){
        counter = 0;
    }

    public void saveImage(BufferedImage image) throws IOException{
        File file = new File(constructPath());
        ImageIO.write(image, "png", file);
    }


    public String constructPath(){
        String path = "";
        if(osName.equals("Linux")){
            path = "src/main/resources/test" + ++counter + ".png";
        } else{
            if(osName.equals("Windows 10")){
                path = "src\\main\\resources\\test" + ++counter + ".png";
            }
        }
        return path;
    }
}