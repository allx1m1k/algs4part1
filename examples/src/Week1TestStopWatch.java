/**
 * Created by dima on 06.02.2015.
 */

public class Week1TestStopWatch {

    public static void main(String[] args) {
        Stopwatch i = new Stopwatch();
        for(int j = 0; j < 65365; j++) {
            System.out.println(j);
        }
        System.out.println(i.elapsedTime());

    }
}
