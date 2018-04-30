import java.util.ArrayList;
import java.util.List;

public class horizontalProcessed extends pictureProcessed{

    /**
     *
     *@param originalPic the picture is submitted to processed.
     */
    public horizontalProcessed(double[][] originalPic) {
        super(originalPic);
    }

    /**
     * this method is to calculate the minimum energy calculation value in horizontal.
     * the component of horizontalList are the first element is energy, the rest of them are y.
     */
    public void calculateHorizontalPixel(double[][] originalPic, int newHeight) {
//        this.newLength = newHeight;
        row = originalPic.length;
        columns = originalPic[0].length;
        List<List<Integer>> totalHorizontalLists = new ArrayList<>();//create the horizontal total lists.

        //creation a list for each the right hand side columns
        for (int i = 0; i < columns; i++) {
            List<Integer> horizontalList = new ArrayList<>();
            horizontalList.add((int)Math.abs(originalPic[0][i]));
            totalHorizontalLists.add(horizontalList);
        }

        //begin to search all the element one by one
        for (int x = 1; x < row; x++) {
                //this is the array without influence for every next elements select their paths
                int[] storeArr = new int[columns];
                for (int i = 0; i < columns; i++) {
                    storeArr[i] = totalHorizontalLists.get(i).get(0);
                }
            //fix the x-1, and compare y, y-1, y+1.
            for (int y = 0; y < columns; y++) {
                    int yMin = horizontalCompare(x, y, storeArr);
                    int value = (int) Math.abs(originalPic[x][y] + storeArr[yMin]);
                    totalHorizontalLists.get(y).set(0, value);

                    // this is for update the array list to get the newest position and value
                    List<Integer> storeList = updateArray(y, yMin, totalHorizontalLists);
                    for (int i = 1; i < totalHorizontalLists.get(y).size(); i++) {
                        totalHorizontalLists.get(y).set(i, storeList.get(i));
                    }
                    totalHorizontalLists.get(y).add(yMin);
            }
        }

        //printout the details of every column.
        for (int i = 0; i < totalHorizontalLists.size(); i++) {
            totalHorizontalLists.get(i).add(i);
//            System.out.println(totalHorizontalLists.get(i));
        }
        //print out the minimum list
        findMinPixel(totalHorizontalLists, row, newHeight);
//        System.out.println(totalHorizontalLists);
    }

    /**
     *
     * @param x row from the current element.
     * @param y columns from the current element.
     * @param totalHorizontalLists the value of last columns.
     * @return yMin the choice of minimum y position.
     */

    public int horizontalCompare(int x, int y, int[] totalHorizontalLists) {
        double minimum = 1000000;
        int yMin = 0;
        for (int i = y-1; i <= y+1; i++) {
            if(validJudgement(x-1,i)) {
                if(totalHorizontalLists[i] <= minimum) {
                    minimum = totalHorizontalLists[i];
                    yMin = i;
                }
            }
        }
        return yMin;
    }

    public List<List<Integer>> getMinHorizontalList() {
        return minList;
    }

}
