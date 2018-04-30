import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainMethod {

    /**
     *
     * @param args user desired width size.
     */

    public static void main(String[] args) {

        //Takes args[0] to be image file, sends usage message if not
        BufferedImage img = null;

        if (args.length == 1) {
            try {
                //Image assignment
                img = ImageIO.read(new File(args[0]));
            } catch (IOException e) {
                //Exception handling
                System.out.println("IOException: " + e.getMessage());
                System.out.println("Please check your image path");
            }
        } else {
            //Usage message with program termination
            System.out.println("Usage: ImageInTest <Image_Path>");
            return;
        }


        //Assumes desired height and width are unchanged
        int newWidth = img.getWidth();
        int newHeight = img.getHeight();


        //Gets user input for new width and height
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            //Width
            System.out.println("Please enter the desired width in pixels (Current width is " + newHeight + " pixels)");
            newHeight = Integer.parseInt(reader.readLine());//Width
            if (newHeight > img.getHeight() || newHeight <= 0 )  {
                throw new NumberFormatException ("The Width is larger than the original size");

            }

        } catch (IOException e) {
            //Exception handling
            System.out.println("IO Exception: " + e.getMessage());
        }


        //Instantiates Implementer object for methods
        Implementer implementer = new Implementer();

        //Creates new image from carving input image
        BufferedImage imgOut = implementer.removeSeams(img, newWidth, newHeight);

        //Creates new image as a file
        try {
            ImageIO.write(imgOut, "png", new File(args[0] + "-Carved.png"));
            new runGUI(args[0] + "-Carved.png"); //This for for running GUI

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }


    }

}
