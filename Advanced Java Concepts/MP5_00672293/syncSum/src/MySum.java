public class MySum {
    private int sum = 0;

    /* 

    // Method without synchronization
    public void increaseSum() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sum++;
        System.out.println(Thread.currentThread().getName() + " sum is: " + sum);
    }

   */
    /* 

    // Method with synchronization
    public synchronized void increaseSumSync() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sum++;
        System.out.println(Thread.currentThread().getName() + " sum is: " + sum);
    }

    */
   // /* 

    // Method with block synchronization
    public void increaseSumSyncBlock() {
        synchronized(this) {
             try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sum++;
        System.out.println(Thread.currentThread().getName() + " sum is: " + sum);
        }
    }

   // */

}

