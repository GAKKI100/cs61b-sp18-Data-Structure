/**
 * Class with 2 ways of doing Counting sort, one naive way and one "better" way
 *
 * @author Akhil Batra, Alexander Hwang
 *
 **/
public class CountingSort {
    /**
     * Counting sort on the given int array. Returns a sorted version of the array.
     * Does not touch original array (non-destructive method).
     * DISCLAIMER: this method does not always work, find a case where it fails
     *
     * @param arr int array that will be sorted
     * @return the sorted array
     */
    public static int[] naiveCountingSort(int[] arr) {
        // find max
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            max = max > i ? max : i;
        }

        // gather all the counts for each value
        int[] counts = new int[max + 1];
        for (int i : arr) {
            counts[i]++;
        }

        // when we're dealing with ints, we can just put each value
        // count number of times into the new array
        int[] sorted = new int[arr.length];
        int k = 0;
        for (int i = 0; i < counts.length; i += 1) {
            for (int j = 0; j < counts[i]; j += 1, k += 1) {
                sorted[k] = i;
            }
        }

        // however, below is a more proper, generalized implementation of
        // counting sort that uses start position calculation
        int[] starts = new int[max + 1];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += counts[i];
        }

        int[] sorted2 = new int[arr.length];
        for (int i = 0; i < arr.length; i += 1) {
            int item = arr[i];
            int place = starts[item];
            sorted2[place] = item;
            starts[item] += 1;
        }

        // return the sorted array
        return sorted;
    }

    /**
     * Counting sort on the given int array, must work even with negative numbers.
     * Note, this code does not need to work for ranges of numbers greater
     * than 2 billion.
     * Does not touch original array (non-destructive method).
     *
     * @param arr int array that will be sorted
     */
    public static int[] betterCountingSort(int[] arr) {
        // TODO make counting sort work with arrays containing negative numbers.
        int negativeSize = 0;
        int positiveSize = 0;
        int[] negative;
        int[] positive;
        for(int i = 0; i < arr.length;i++){
            if(arr[i] < 0){
                negativeSize++;
            } else{
                positiveSize++;
            }
        }
        if(negativeSize !=0){
            negative = new int[negativeSize];
            positive = new int[positiveSize];

            int x = 0;
            int y = 0;

            for(int i = 0; i < arr.length;i++){
                if(arr[i] < 0){
                    negative[x] = Math.abs(arr[i]);
                    x++;
                }else{
                    positive[y] = arr[i];
                    y++;
                }
            }
            int maxNegative = Integer.MIN_VALUE;
            for (int i : negative) {
                maxNegative = maxNegative > i ? maxNegative : i;
            }
            int maxPositive = Integer.MIN_VALUE;
            for (int i : positive) {
                maxPositive = maxPositive> i ? maxPositive : i;
            }
            int[] countNagative = new int[maxNegative+1];
            for(int i : negative){
                countNagative[i]++;
            }
            int[] countPositive = new int[maxPositive+1];
            for(int i : positive){
                countPositive[i]++;
            }
            int[] startsNegative = new int[maxNegative + 1];
            int posNagetive = 0;
            for(int i = 0; i < startsNegative.length; i++){
                startsNegative[i] = posNagetive;
                posNagetive = posNagetive + countNagative[i];
            }
            int[] startsPositive = new int[maxPositive + 1];
            int posPositive = 0;
            for(int i = 0; i < startsPositive.length; i++){
                startsPositive[i] = posPositive;
                posPositive = posPositive + countPositive[i];
            }
            int[] sortedNegative = new int[negativeSize];
            int[] sortedPositive = new int[positiveSize];

            for(int i = 0; i < negativeSize; i++){
                int itemNegative = negative[i];
                int placeNegative = startsNegative[itemNegative];
                sortedNegative[placeNegative] = itemNegative;
                startsNegative[itemNegative]++;
            }
            for(int i = 0; i < positiveSize; i++){
                int itemPositive = positive[i];
                int placePositive = startsPositive[itemPositive];
                sortedPositive[placePositive] = itemPositive;
                startsPositive[itemPositive]++;
            }
            for(int i = 0; i < sortedNegative.length / 2; i++)
            {
                int temp = sortedNegative[i];
                sortedNegative[i] = sortedNegative[sortedNegative.length - i - 1];
                sortedNegative[sortedNegative.length - i - 1] = temp;
            }
            for(int i = 0; i < sortedNegative.length; i++){
                sortedNegative[i] = -sortedNegative[i];
            }
            int[] array1and2 = new int[arr.length];
            System.arraycopy(sortedNegative, 0, array1and2, 0, sortedNegative.length);
            System.arraycopy(sortedPositive, 0, array1and2, sortedNegative.length, sortedPositive.length);
            return array1and2;
        }else{
            int max = Integer.MIN_VALUE;
            for (int i : arr) {
                max = max > i ? max : i;
            }

            // gather all the counts for each value
            int[] counts = new int[max + 1];
            for (int i : arr) {
                counts[i]++;
            }

            int[] starts = new int[max + 1];
            int pos = 0;
            for (int i = 0; i < starts.length; i += 1) {
                starts[i] = pos;
                pos += counts[i];
            }

            int[] sorted2 = new int[arr.length];
            for (int i = 0; i < arr.length; i += 1) {
                int item = arr[i];
                int place = starts[item];
                sorted2[place] = item;
                starts[item] += 1;
            }

            // return the sorted array
            return sorted2;
        }


    }
}
