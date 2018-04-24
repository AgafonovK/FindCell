package xvr.cell;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FindCellTest {
    String[][] filename = {{"test01 5 5.txt"},
            {"test02 22 6.txt"},
            {"test03 10404 .txt"},
            {"test03 41616 .txt"},
            {"world map1.txt"},
            {"stars.txt"}
    };
    int results[] = {    //if unknown - 0,0
            4, 4,        //"test01 5 5.txt"
            13, 6,        //"test02 22 6.txt"
            10404, 11,    //"test03 10404 .txt"
            41616, 6,    //"test03 41616 .txt"
            0, 0,        //"world map1.txt"
            0, 0            //"stars.txt"
    };

    @Test
    public final void testMainOnce() {
        long temp;
        for (int j = 0; j < filename.length-2; j++) {
            temp = System.currentTimeMillis();
            FindCell.main(filename[j]);
            System.out.println(filename[j][0] + "  time (sec): \t\t" + (double) (System.currentTimeMillis() - temp) / 1000);
            if (results[2 * j] != 0) {
                assertEquals(FindCell.getNumIsland(), results[2 * j]);
            }
           /* if (results[2 * j + 1] != 0) {
                assertEquals(FindCell.getNumUniqForm(), results[2 * j + 1]);
            }*/

        }
    }
/*
    @Test
    final void testMainNumTreads() {
        //~40sec each with iteration=25 for 5 files
        long temp = System.currentTimeMillis();
        int iteration = 25;
        for (int i = 0; i < iteration * 6; i++) {

            if (i % iteration == 0 & i != 0) {
                System.out.println("numThreads: " + Main.numObserverThreads + " time (sec): \t" + (double) (System.currentTimeMillis() - temp) / 1000);
                temp = System.currentTimeMillis();
            }
            Main.numObserverThreads = 1 + i / iteration;
            for (int j = 0; j < filename.length; j++) {
//				temp =  System.currentTimeMillis();
                Main.main(filename[j]);
                if (results[2 * j] != 0) {
                    assertEquals(Main.getMap().getNumIsland(), results[2 * j]);
                }
                if (results[2 * j + 1] != 0) {
                    assertEquals(Main.getMap().getNumUniqForm(), results[2 * j + 1]);
                }
            }
        }
        System.out.println("numThreads: " + Main.numObserverThreads + " time (sec): \t" + (double) (System.currentTimeMillis() - temp) / 1000);
        temp = System.currentTimeMillis();
        for (int i = 0; i < iteration * 6; i++) {

            if (i % iteration == 0 & i != 0) {
                System.out.println("numThreads: " + Main.numObserverThreads + " time (sec): \t" + (double) (System.currentTimeMillis() - temp) / 1000);
                temp = System.currentTimeMillis();
            }
            Main.numObserverThreads = 6 - i / iteration;
            for (int j = 0; j < filename.length; j++) {
//				temp =  System.currentTimeMillis();
                Main.main(filename[j]);
                if (results[2 * j] != 0) {
                    assertEquals(Main.getMap().getNumIsland(), results[2 * j]);
                }
                if (results[2 * j + 1] != 0) {
                    assertEquals(Main.getMap().getNumUniqForm(), results[2 * j + 1]);
                }
            }
        }
        System.out.println("numThreads: " + Main.numObserverThreads + " time (sec): \t" + (double) (System.currentTimeMillis() - temp) / 1000);
    }*/
/*
numThreads: 1 time (sec): 	46.291
﻿numThreads: 2 time (sec): 	45.057
﻿numThreads: 3 time (sec): 	43.12
﻿numThreads: 4 time (sec): 	43.867
﻿numThreads: 5 time (sec): 	46.796
﻿numThreads: 6 time (sec): 	45.192
﻿numThreads: 6 time (sec): 	46.046
﻿numThreads: 5 time (sec): 	44.024
﻿numThreads: 4 time (sec): 	44.822
﻿numThreads: 3 time (sec): 	43.908
﻿numThreads: 2 time (sec): 	45.009
﻿numThreads: 1 time (sec): 	46.035
*/

}