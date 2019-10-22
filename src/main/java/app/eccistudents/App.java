package app.eccistudents;

public class App 
{

    public static void main( String[] args )
    {
        ImageMapping imageMap = new ImageMapping("src/main/resources/test.gif");
        imageMap.mapImage();
    }
}
