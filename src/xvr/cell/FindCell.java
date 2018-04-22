package xvr.cell;

import java.util.ArrayList;
import java.util.List;

import java.util.TreeSet;

public class FindCell {

    private static int mass[][] = {
            {1, 1, 1, 0, 0, 0, 0, 1, 1, 0},
            {1, 1, 0, 1, 1, 1, 0, 0, 1, 0},
            {0, 1, 1, 1, 0, 1, 1, 0, 0, 1},
            {0, 0, 0, 0, 1, 0, 1, 1, 1, 0},
            {1, 0, 0, 1, 1, 1, 0, 0, 1, 0},
            {1, 1, 0, 1, 0, 0, 0, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    };

    private static List<TreeSet> treeSetList = new ArrayList<TreeSet>();
    private static List<TreeSet> treeSetToCheckList = new ArrayList<TreeSet>();
    private static boolean massChecked[][] = new boolean[7][10];


    private static void initArray(int mass[][], boolean massChecked[][]) {
        // this.mass=mass;
        //this.massChecked=massChecked;

        for (int a = 0; a < mass.length; a++) {
            for (int b = 0; b < mass[0].length; b++) {
                if (mass[a][b] == 1) massChecked[a][b] = true;
                else {
                    massChecked[a][b] = false;
                }
            }
        }
    }

    public static void main(String[] args) throws ArrayIndexOutOfBoundsException {
        long startTime, endTime;
        startTime = System.nanoTime();

        initSecondMass();  // init second boolean array where we know check cell or not;

        int i = 0;  // number of Islands in ArrayList;

        for (int x = 0; x < mass.length; x++) {
            for (int y = 0; y < mass[0].length; y++) {

                if (mass[x][y] == 1 && massChecked[x][y] != false) {

                    treeSetList.add(new TreeSet());
                    treeSetToCheckList.add(new TreeSet());
                    //cell is checked
                    massChecked[x][y] = false;
                    mass[x][y] = 0;
                    // checke cell around x+1,x-1,y+1,y-1. or border
                    checkedX(x, y, treeSetList.get(i), treeSetToCheckList.get(i));
                    checkedY(x, y, treeSetList.get(i), treeSetToCheckList.get(i));
//                    System.out.println("TreeSet " + treeSet);
//                    System.out.println("TreeSetToCheck " + treeSetToCheckList.get(i).isEmpty());
                    while (!treeSetToCheckList.get(i).isEmpty()) {
                        // coordinate cell to dec format cell[0][0]= cell №1; cell[0][1]=cell№2;
                        int decCell = (int) treeSetToCheckList.get(i).first();

                        //System.out.println("decCell " + decCell);
                        //inerting cell from dec to [x][y];
                        int xInv = decCell / mass[0].length; // get x coordinate
                        //System.out.println("xInv " + xInv);

                        int yInv = 0;
                        if (x == 0) {
                            yInv = (decCell % mass[0].length) - 1;
                        }
                        yInv = (decCell % mass[0].length); // get y coordinate
                        //System.out.println("yInv " + yInv);

                        checkedX(xInv, yInv, treeSetList.get(i), treeSetToCheckList.get(i));
                        checkedY(xInv, yInv, treeSetList.get(i), treeSetToCheckList.get(i));

                        treeSetToCheckList.get(i).remove(decCell);
                        massChecked[xInv][yInv] = false;

                        //System.out.println("TreeSet " + treeSet);
                        //System.out.println("TreeSetToCheck " + treeSetToCheck);
                    }
                    i = i + 1;
                }
            }
        }

        for (TreeSet treeSet : treeSetList) {
            System.out.println(treeSet);
        }
        endTime = System.nanoTime();
        System.out.println(" time " + (endTime - startTime)/1000000.d + " msc");

    }


    private static void checkedY(int x, int yCell, TreeSet treeSet, TreeSet treeSetToCheck) {
        for (int q = -1; q < 2; q++) {
            if (q == 0)
                continue;
            if (yCell + q < 0 || yCell + q >= mass[0].length || mass[x][yCell + q] == 0 || !massChecked[x][yCell + q])
                continue;
            treeSet.add(toDec(x, yCell));
            treeSet.add(toDec(x, yCell + q));
            treeSetToCheck.add(toDec(x, yCell + q));
        }
    }

    private static void checkedX(int xCell, int y, TreeSet treeSet, TreeSet treeSetToCheck) {
        for (int q = -1; q < 2; q++) {
            if (q == 0)
                continue;
            if (xCell + q < 0 || xCell + q >= mass.length || mass[xCell + q][y] == 0 || !massChecked[xCell + q][y])
                continue;
            treeSet.add(toDec(xCell, y));
            treeSet.add(toDec(xCell + q, y));
            treeSetToCheck.add(toDec(xCell + q, y));
        }
    }

    //TODO check border number. TODO Done.
    private static int toDec(int x, int y) {
        // mass[3][6]; x=3; y=6;
        int dec = 0;
        if (x == 0) {
            if (y == 0) dec = 1;
            dec = y + 1;
            return dec;
        }
        dec = (x * mass[0].length) + y; // get decimal value of cell [x][y];
        return dec;

    }

    private static void initSecondMass(){
        for (int a = 0; a < mass.length; a++) {
            for (int b = 0; b < mass[0].length; b++) {
                if (mass[a][b] == 1) massChecked[a][b] = true;
                else {
                    massChecked[a][b] = false;
                }
            }
        }
    }
    //TODO This method is not used, because body of this method in the main code
    private static void invDec(int decInv) {
        int xInv = decInv / mass[0].length; // get x coordinate
        int yInv = decInv % mass[0].length; // get y coordinate

    }


}
