public class Main {
    public static void main(String[] args) {
        MySum sharedMySum = new MySum();
        
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {

               // sharedMySum.increaseSum();
               sharedMySum.increaseSumSyncBlock();
              //   sharedMySum.increaseSumSync();

            }).start();
        }
    }
}