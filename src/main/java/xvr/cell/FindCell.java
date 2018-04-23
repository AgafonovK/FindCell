package xvr.cell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.TreeSet;

public class FindCell {

    private static int arrayToCheck[][] = {
          // 1  2  3  4  5  6  7  8  9  10
            {1, 1, 1, 0, 0, 0, 0, 1, 1, 0},
          //11 12 13 14 15 16 17 ........
            {1, 1, 0, 1, 1, 1, 0, 0, 1, 0},
            {0, 1, 1, 1, 0, 1, 1, 0, 0, 1},
            {0, 0, 0, 0, 1, 0, 1, 1, 1, 0},
            {1, 0, 0, 1, 1, 1, 0, 0, 1, 0},
            {1, 1, 0, 1, 0, 0, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    };

    private static List<TreeSet<Integer>> treeSetList = new ArrayList<TreeSet<Integer>>();
    private static List<TreeSet<Integer>> treeSetToCheckList = new ArrayList<TreeSet<Integer>>();
    private static boolean arrayChecked[][] = new boolean[7][10];
    private static final String ANSI_GREEN = "\u001B[32m";

    public static void main(String[] args) throws ArrayIndexOutOfBoundsException {
        long startTime, endTime;
        startTime = System.nanoTime();

        arrayChecked = initSecondMass(arrayChecked, arrayToCheck);  // init second boolean arrayToCheck where we know check cell or not;

        int i = 0;  // number of Islands in ArrayList;

        for (int x = 0; x < arrayToCheck.length; x++) {
            for (int y = 0; y < arrayToCheck[0].length; y++) {

                if (arrayToCheck[x][y] == 1 && arrayChecked[x][y] != false) {

                    // add to list new islands (TreeSet)
                    treeSetList.add(new TreeSet());
                    treeSetToCheckList.add(new TreeSet());

                    //cell is checked
                    arrayChecked[x][y] = false;
                    arrayToCheck[x][y] = 0;

                    // checke cell around x+1,x-1,y+1,y-1. or border
                    checkedX(x, y, treeSetList.get(i), treeSetToCheckList.get(i));
                    checkedY(x, y, treeSetList.get(i), treeSetToCheckList.get(i));

                    while (!treeSetToCheckList.get(i).isEmpty()) {

                      // coordinate cell to dec format cell[0][0]= cell №1; cell[0][1]=cell№2;
                        int decCell = treeSetToCheckList.get(i).first();

                      //inverting cell from dec to [x][y];
                        int invArray[]= new int[2];
                        invArray = invDec(decCell);
                        int xInv = invArray[0];
                        int yInv = invArray[1];

                        checkedX(xInv, yInv, treeSetList.get(i), treeSetToCheckList.get(i));
                        checkedY(xInv, yInv, treeSetList.get(i), treeSetToCheckList.get(i));
                        //remove checked cell and flag false to this cell
                        treeSetToCheckList.get(i).remove(decCell);
                        arrayChecked[xInv][yInv] = false;

                    }
                    //iteration next islands for the new index in List islands;
                    i = i + 1;
                }
            }
        }


        for (TreeSet treeSet : treeSetList) {
            if (treeSet.size()<2){
                continue;
            }
            System.out.println(treeSet);
        }
        endTime = System.nanoTime();
        System.out.println(" time " + (endTime - startTime)/1000000.d + " msc");

        //TODO display islands another color in console;
        //display(arrayToCheck,treeSetList);
    }


    private static void checkedY(int x, int yCell, TreeSet treeSet, TreeSet treeSetToCheck) {
        //check cell in the x axis (x+1, x-1)

        for (int q = -1; q < 2; q++) {
            if (q == 0) {
                continue;
            }
            //check array border+-, cell to 1 or 0, cell boolean[][]=true;

            if (yCell + q < 0 || yCell + q >= arrayToCheck[0].length || arrayToCheck[x][yCell + q] == 0 || !arrayChecked[x][yCell + q]) {
                continue;
            }
            // add cell to tree if they have neiborhouds;
            treeSet.add(toDec(x, yCell));

            //add neiborhouds, because he is islands too;
            treeSet.add(toDec(x, yCell + q));

            //add neiborhouds for check in next;
            treeSetToCheck.add(toDec(x, yCell + q));
        }
    }

    private static void checkedX(int xCell, int y, TreeSet treeSet, TreeSet treeSetToCheck) {
        //check cell in the x axis (x+1, x-1)
        for (int q = -1; q < 2; q++) {
            if (q == 0) {
                continue;
            }
            //check array border+-, cell to 1 or 0, cell boolean[][]=true;
            if (xCell + q < 0 || xCell + q >= arrayToCheck.length || arrayToCheck[xCell + q][y] == 0 || !arrayChecked[xCell + q][y]) {
                continue;
            }
            // add cell to tree if they have neiborhouds;
            treeSet.add(toDec(xCell, y));

            //add neiborhouds, because he is islands too;
            treeSet.add(toDec(xCell + q, y));

            //add neiborhouds for check in next;
            treeSetToCheck.add(toDec(xCell + q, y));
        }
    }

    private static int toDec(int x, int y) {
        // arrayToCheck[3][6]; x=3; y=6;
        int dec = 0;
        dec = (x * arrayToCheck[0].length) + y; // get decimal value of cell [x][y];
        //border
        if (x==0) {
            return dec = dec + 1;
        }
        //border
        if (y==0){
            return dec = dec+1;
        }
        //start point [0][0];
        if (x==0 & y==0){
            return dec=1;
        }
        return dec+1;

    }
// initialize second Array boolean to help us;
    private static boolean[][] initSecondMass(final boolean[][] secondMass, final int[][] initializer){
        boolean [][] result = Arrays.copyOf(secondMass,secondMass.length);
        for (int a = 0; a < result.length; a++) {
            for (int b = 0; b < result[0].length; b++) {
                result[a][b] = initializer[a][b] == 1;
            }
        }
        return result;
    }

    // Invert coordinate cell from dec to [x][y]
    private static int [] invDec(int decCell) {
        int xToInvert, yToInvert;
        int invDecArrayXY[]= new int[2];

        // get x coordinate
        xToInvert = decCell / arrayToCheck[0].length;
        //check unique
        if (decCell == arrayToCheck[0].length*xToInvert){
            xToInvert = xToInvert-1;
        }
        //add coordinate X to array for the return 2 point;
        invDecArrayXY[0]=xToInvert;

        // get y coordinate
        yToInvert = (decCell % arrayToCheck[0].length)-1;
        if (yToInvert < 0) {
            yToInvert=arrayToCheck[0].length-1;
            //yToInvert = yToInvert+1;
        }
        //add coordinate y to array for return;
        invDecArrayXY[1]=yToInvert;

        return invDecArrayXY;
    }
//TODO
    private static void display(int massStart[][], List<TreeSet<Integer>> treeSetToDispay){
        int arrayDisplay[][] = new int[massStart.length][massStart[0].length];
        for (int i=0; i<massStart.length;i++){
            for (int j=0; j<massStart[0].length;i++){
                if (massStart[i][j]==1){
                    int m = toDec(i,j);
                    if(treeSetToDispay.contains(m)){
                        System.out.println("yes");
                    }





                }
            }
        }
    }

}
