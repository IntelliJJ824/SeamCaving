import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class pictureProcessed {
    protected double[][] originalPic;
    protected int row, columns;
    protected List<List<Integer>> minList = new ArrayList<>();

    public pictureProcessed(double[][] originalPic) {
        this.originalPic = originalPic;
    }
    /**
     *
     * @param x is the row.
     * @param y is the columns.
     * @return whether it is out of bound, especially in border elements.
     */

    public boolean validJudgement(int x, int y) {
        if(x < 0 || x >= row) {
            return false;
        }
        else if(y < 0 || y >= columns) {
            return false;
        }
        else return true;
    }

    /**
     *
     * @param originalPosition the position of current element.
     * @param minPosition the minimum of last position.
     * @param lists all the list for vertical or horizontal.
     * @return the array need to be changed with newest position.
     */
    public List<Integer> updateArray(int originalPosition, int minPosition, List<List<Integer>> lists) {

        if(minPosition != originalPosition) {
            try {
                for (int i = 1; i < lists.get(originalPosition).size(); i++) {
                    lists.get(originalPosition).set(i, lists.get(minPosition).get(i));
                }
            } catch(IndexOutOfBoundsException e) {

            }
        }
        return lists.get(originalPosition);
    }

    /**
     *
     * @param Lists all the list that need to be removed
     * @param length the length of the original picture
     * @param newLength the desired size of the users.
     */
    public void findMinPixel(List<List<Integer>> Lists, int length, int newLength) {
        //ordering the list in descending order.
        Collections.sort(Lists, new Comparator<List<Integer>>() {
            @Override
            public int compare(List<Integer> t1, List<Integer> t2) {
                return t1.get(0).compareTo(t2.get(0));
            }
        });

        int count = length - newLength;
            for (int i = 0; i < count; i++) {
                Lists.get(i).remove(0);
                minList.add(Lists.get(i));
            }

    }
}
