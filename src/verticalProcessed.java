import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class verticalProcessed extends pictureProcessed{
    /**
     *
     * @param originalPic the array from part one.
     * row is to get the length of x
     * columns is to get the length of y
     */
    public verticalProcessed(double[][] originalPic) {
        super(originalPic);
    }

    /**
     *This method is to calculate the minimum energy calculation value in vertical.
     * totalVerticalLists is to store all the verticalList
     * The component of verticalList are the first element is energy, the rest of them are x.
     */
    public void calculateVerticalPixel(double[][] originalPic, int newWidth) {
//        this.newLength = newWidth;
        row = originalPic.length;
        columns = originalPic[0].length;
        List<List<Integer>> totalVerticalLists = new ArrayList<>(); //this is the total list for the down ward elements

        //creation a list for each the bottom row elements.
        for (int i = 0; i < row; i++) {
            List<Integer>  verticalList = new ArrayList<>();
            verticalList.add((int)Math.abs(originalPic[i][0])); // store all the first row in the beginning.
            totalVerticalLists.add(verticalList);
        }

        //begin to search all the element one by one
        for (int y = 1; y < columns; y++) {
                //this for loop is for storing the non influenced array
                int[] storeArr = new int[row];
                for (int i = 0; i < row; i++) {
                storeArr[i] = totalVerticalLists.get(i).get(0);
                }
            //fix the y, and compare x, x-1, x+1.
            for (int x = 0; x < row; x++) {
                    int xMin = verticalCompare(x, y, storeArr);     // by comparing the surroundings to work out the minimum.
                    int value = (int) Math.abs(originalPic[x][y] + storeArr[xMin]);
                    totalVerticalLists.get(x).set(0, value);        //replace a new value of total energy in each row

                // this is for update the array list to get the newest position and value
                    List<Integer> storeList = updateArray(x, xMin, totalVerticalLists);
                    for (int i = 0; i < totalVerticalLists.get(x).size(); i++) {
                            totalVerticalLists.get(x).set(i, storeList.get(i));
                    }
                    totalVerticalLists.get(x).add(xMin);
            }
        }
        // This is for print out all the array each time
        for (int i = 0; i < totalVerticalLists.size(); i++) {
                totalVerticalLists.get(i).add(i);
        }
        //find out the minimum array list.
        findMinPixel(totalVerticalLists, columns, newWidth);

    }

    /**
     *
     * @param x row.
     * @param y columns.
     * @return xMin the choice of minimum x position.
     */
    public int verticalCompare(int x, int y, int[] totalVerticalLists) {
        double minimum = 1000000;
        int xMin = 0;
            for (int i = x-1; i <= x+1 ; i++) {
                if(validJudgement(i, y-1)) { // this is to make judgment whether it is in range
                    if(totalVerticalLists[i] <= minimum) {
                        minimum = totalVerticalLists[i];
                        xMin = i;
                    }
                }
            }
        return xMin;
    }

    /**
     *
     * @return the list with minimum vertical list.
     */
    public List<List<Integer>> getMinVerticalList() {
        return minList;
    }

}
