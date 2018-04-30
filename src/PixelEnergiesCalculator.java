import java.awt.image.BufferedImage;

public class PixelEnergiesCalculator {

    //Takes an input of a BufferedImage and returns a 2D double array
    //USES ROW, COLUMN NOTATION, EXCEPT getRGB, WHICH USES (X,Y)
    public double[][] calculateEnergies(BufferedImage img) {

        //Initialises rows and columns variables here to avoid multiple retrievals later
        int rows = img.getHeight();
        int columns = img.getWidth();

        //Declares return variable
        double[][] energyMatrix = new double[rows][columns];


        //Declarations here for scope and to avoid multiple initialisation in following code
        int[] upRGB;
        int[] rightRGB;
        int[] downRGB;
        int[] leftRGB;

        //Following statements create an energy matrix for the whole image

        //Calculates energies for top row here (outside of nested for loop) to avoid OutOfBoundsException
        for (int i = 0; i < columns; i++) {
            //Avoids magic numbers
            final int TOP_ROW = 0;
            final int SECOND_ROW = 1;

            //Assigns appropriate values for upRGB and downRGB
            upRGB = getRGBValues(img.getRGB(i, rows - 1));
            downRGB = getRGBValues(img.getRGB(i, SECOND_ROW));

            //Checks for left most and right most rows, and assigns appropriate values
            if (i - 1 < 0) {
                leftRGB = getRGBValues(img.getRGB(columns - 1, TOP_ROW));
                rightRGB = getRGBValues(img.getRGB(i + 1, TOP_ROW));
            } else if (i + 1 > columns - 1) {
                leftRGB = getRGBValues(img.getRGB(i - 1, TOP_ROW));
                rightRGB = getRGBValues(img.getRGB(0, TOP_ROW));
            } else {
                leftRGB = getRGBValues(img.getRGB(i - 1, TOP_ROW));
                rightRGB = getRGBValues(img.getRGB(i + 1, TOP_ROW));
            }

            //Calculates energy for pixel
            double energy = energyFunction(upRGB, rightRGB, downRGB, leftRGB);

            //Assigns energy to respective energy matrix element
            energyMatrix[TOP_ROW][i] = energy;
        }


        //Calculates energies for bottom row here (outside of nested for loop) to avoid OutOfBoundsException
        for (int i = 0; i < columns; i++) {
            //Avoids magic numbers
            final int TOP_ROW = 0;

            //Assigns appropriate values for upRGB and downRGB
            upRGB = getRGBValues(img.getRGB(i, (rows - 1) - 1));
            downRGB = getRGBValues(img.getRGB(i, TOP_ROW));

            //Checks for left most and right most rows, and assigns appropriate values
            if (i - 1 < 0) {
                leftRGB = getRGBValues(img.getRGB(columns - 1, rows - 1));
                rightRGB = getRGBValues(img.getRGB(i + 1, rows - 1));
            } else if (i + 1 > columns - 1) {
                leftRGB = getRGBValues(img.getRGB(i - 1, rows-1));
                rightRGB = getRGBValues(img.getRGB(0, rows-1));
            } else {
                leftRGB = getRGBValues(img.getRGB(i - 1, rows-1));
                rightRGB = getRGBValues(img.getRGB(i + 1, rows -1));
            }

            //Calculates energy for pixel
            double energy = energyFunction(upRGB, rightRGB, downRGB, leftRGB);

            //Assigns energy to respective energy matrix element
            energyMatrix[rows-1][i] = energy;
        }


        //Nested for loop to iterate through rest of image, calculating energy values for each pixel
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 0; j < columns; j++) {

                //Assigns appropriate values for upRGB and downRGB
                upRGB = getRGBValues(img.getRGB(j, i - 1));
                downRGB = getRGBValues(img.getRGB(j, i + 1));

                //Checks for left most and right most rows, and assigns appropriate values
                //NOTE: img.getRGB takes (x,y) input, not (row,column)
                if (j - 1 < 0) {
                    leftRGB = getRGBValues(img.getRGB(columns-1, i));
                    rightRGB = getRGBValues(img.getRGB(j + 1, i));
                } else if (j + 1 > columns - 1) {
                    leftRGB = getRGBValues(img.getRGB(j - 1, i));
                    rightRGB = getRGBValues(img.getRGB(0, i));
                } else {
                    leftRGB = getRGBValues(img.getRGB(j - 1, i));
                    rightRGB = getRGBValues(img.getRGB(j + 1, i));
                }

                //Calculates energy of pixel using energyFunction method
                double energy = energyFunction(upRGB, rightRGB, downRGB, leftRGB);

                //Assigns respective position in returnMatrix to energy
                energyMatrix[i][j] = energy;

            }
        }

        //for testing (success!)
//        energyMatrix[0][0] = 1;
//        energyMatrix[1][0] = 2;
//        energyMatrix[2][0] = 3;
//        energyMatrix[3][0] = 4;
//        energyMatrix[0][1] = 5;
//        energyMatrix[1][1] = 6;
//        energyMatrix[2][1] = 7;
//        energyMatrix[3][1] = 8;
//        energyMatrix[0][2] = 9;
//        energyMatrix[1][2] = 10;
//        energyMatrix[2][2] = 11;
//        energyMatrix[3][2] = 12;



        //Returns energyMatrix (double[][])
        return energyMatrix;

    }


    //Using bits from single integer pixel RGBA value, returns RBG values as int[]
    public int[] getRGBValues(int pixel) {

        //Calculates red, green and blue using bits from input pixel
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = pixel & 0xff;

        //Creates return array
        int[] returnArray = {red, green, blue};

        //Returns return variable
        return returnArray;

    }

    //Calculates energy of pixel using surrounding pixels
    public double energyFunction(int[] upRGB, int[] rightRGB, int[] downRGB, int[] leftRGB) {

        //Calculates squareDeltaX value (squared gradient between left and right)
        double squareDeltaX = calculateSquareDelta(leftRGB, rightRGB);

        //Calculates squareDeltaY value (squared gradient between up and down)
        double squareDeltaY = calculateSquareDelta(upRGB, downRGB);

        //Calculates energy from squareDelta values
        double energy = Math.sqrt(squareDeltaX + squareDeltaY);

        //Returns energy
        return energy;

    }

    //Calculates square difference in pixels
    public double calculateSquareDelta(int[] RGB1, int[] RGB2) {

        //Calculates differences in for each of RBG
        double deltaR = RGB1[0] - RGB2[0];
        double deltaG = RGB1[1] - RGB2[1];
        double deltaB = RGB1[2] - RGB2[2];

        //Calculates squareDelta
        double squareDelta = (deltaR * deltaR) + (deltaG * deltaG) + (deltaB * deltaB);

        //Returns squareDelta
        return squareDelta;

    }

}
