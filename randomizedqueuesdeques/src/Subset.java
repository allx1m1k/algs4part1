/**
 * Created by dima on 2/16/2015.
 */
public class Subset {

    public static void main(String[] args) {
        //int k = Integer.parseInt(args[0]);
        int N = StdIn.readInt();
        //Deque<String> queue = new Deque<String>();
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (StdIn.hasNextLine() && !StdIn.isEmpty()) {
            //queue.addLast(StdIn.readString());
            queue.enqueue(StdIn.readString());
        }
        for (int i = 0; i < N; i++) {
            StdOut.println(queue.iterator().next());
        }
    }

}
