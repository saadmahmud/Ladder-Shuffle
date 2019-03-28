package com.saadmahmud.laddershuffle;

import android.util.Log;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Util {


    /*
     * @param startValue    start position of parent view
     * @param endValue      end position of parent view
     * @param count         amount of margins to be returned
     * @param minGap        minimum gap between margins
     *
     * @return int[]        count amount of margins (or less, depending on random values, if there is 2 same value is generated)
     *
     * */
    // 0, 1000, 4
    public static Integer[] getMargins(int startValue, int endValue, int count, int minGap) {
        if (count <= 0) {
            return new Integer[0];
        }

        int possibleValueCount = (endValue - startValue) / minGap; // (1000-0)/100= 1000/100= 10

        possibleValueCount -= 2; // as we don't include last (max) and 2nd last value

        int[] possibleValues = new int[possibleValueCount];

        showLog("getMargins-> possibleValueCount " + possibleValueCount);

        for (int j = 0; j < possibleValueCount; j++) {

            possibleValues[j] = minGap * (j + 1);
            /* breakdown of possible values
             possibleValues[]= [100, 200, 300, 400, 500, 600, 700, 800] for a 1000 dp line*/
        }

        Random random = new Random();

        Set<Integer> valueIndexSet = new HashSet<>();
        for (int i = 0; i < count; i++) {
             /*create "count" number of margin values with random amount of (gap) space between
             minGap is the Gap unit*/

            // returns int between 0 to possibleValueCount
            int index = random.nextInt(possibleValueCount);

            valueIndexSet.add(index);
            /*We're using value set because it */
        }

        Integer[] valueIndex = new Integer[valueIndexSet.size()];

        valueIndexSet.toArray(valueIndex);
        Arrays.sort(valueIndex);

        Integer[] margins = new Integer[valueIndex.length];

        showLog("getMargins-> valueIndex.length " + valueIndex.length);

        for (int i = 0; i < valueIndex.length; i++) {
            /* create "valueIndex.length" number of margin values with random amount of (gap ) space between them.
             minGap is the Gap unit */

            showLog("valueIndex " + i + "= " + valueIndex[i]);

            int prevValue = 0;
            if (i > 0) {
                prevValue = possibleValues[valueIndex[i - 1]];
            }
            int curValue = possibleValues[valueIndex[i]];

            /*We'll take difference between two adjacent values*/

            int margin;
            margin = prevValue != curValue ? Math.abs(curValue - prevValue) : curValue;

            margins[i] = margin;

            showLog("margins " + i + "= " + margin + " , prevValue= " + prevValue + " , curValue= " + curValue);
        }
        return margins;
    }

    public static void showLog(String s) {
        Log.d("ANIMFi", s);
    }
}
