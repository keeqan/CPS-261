public class MySelectionSort {
    public static <T extends Comparable<T>> void sort(T[] array) {
        //Iterates Aarray
        for (int i = 0; i < array.length - 1; i++) {
            //assuming min is current
            int minIndex = i;
            //find the minimum
            for (int j = i + 1; j < array.length; j++) {
                //if smaller update min
                if (array[j].compareTo(array[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            //swap min with current index
            T temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }
    }
}