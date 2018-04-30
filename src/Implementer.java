import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.List;

public class Implementer {
    /**
     * @param img       the img that user want to input.
     * @param newWidth  the desired width that user wants.
     * @param newHeight the desired height that user wants.
     * @return img the picture that being processed in horizontal and vertical
     */
    public BufferedImage removeSeams(BufferedImage img, int newWidth, int newHeight) {
        //Declares a new BufferedImage for return
        BufferedImage imgOut;

        //Calculates number of seams being removed
        int noOfVerticalSeams = img.getWidth() - newWidth;
        int noOfHorizontalSeams = img.getHeight() - newHeight;

        // Uses calculator to generate energy matrix
        PixelEnergiesCalculator calculator = new PixelEnergiesCalculator();
        double[][] energyMatrix = calculator.calculateEnergies(img);
        /**
         * vertical process began with here
         */
        //Instantiates processor using energyMatrix(vertical)
        horizontalProcessed horModel = new horizontalProcessed(energyMatrix);
        horModel.calculateHorizontalPixel(energyMatrix, newHeight);

        //This is for return the list after being processed in vertical.
        List<List<Integer>> verticalList = horModel.getMinHorizontalList();

        SeamCarver carver = new SeamCarver();
        imgOut = carver.carveVertical(img, verticalList);

        return imgOut;
    }
}









