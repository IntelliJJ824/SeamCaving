import java.awt.image.BufferedImage;
import java.util.List;

public class SeamCarver {

    //Takes input image and returns image omitting horizontal seams implied by negative numbers in alteredEnergyMatrix
    public BufferedImage carveVertical(BufferedImage imgIn, List<List<Integer>> toRemove) {

        //Retrieves height and width to know dimensions of return image
        int height = imgIn.getHeight();
        int inWidth = imgIn.getWidth();
        int outWidth = inWidth - toRemove.size();

        //Initialises return image variable
        BufferedImage imgOut = new BufferedImage(outWidth, height, BufferedImage.TYPE_INT_ARGB);

        //Following statements fill return image with imgIn pixels, omitting seams specified by alteredEnergyMatrix

        //Loops through seams
        for (int i = 0; i < toRemove.size(); i++) {

            //Counter to move through coordinates in seams
            int seamPositionCounter = 0;
            //Loops through image rows
            for (int column = 0; column < outWidth; column++) {//int row = 0; row < height; row++
                //Loops through image columns
                for (int row = 0; row < height; row++) { //int column = 0; column < outWidth; column++

                    //Either increments counter or fills imgOut
                    if (!(seamPositionCounter + 1 > toRemove.size()) || toRemove.get(i).get(seamPositionCounter) == column) {
                        seamPositionCounter++;
                    } else {
                        try {
                            imgOut.setRGB(column, row, imgIn.getRGB(column + seamPositionCounter, row));
                        } catch (ArrayIndexOutOfBoundsException e) {
//                                                    imgOut.setRGB(column, row, imgIn.getRGB(column, row ));
                        }

                    }
                }
            }

        }
        //Return statement
        return imgOut;
    }

}