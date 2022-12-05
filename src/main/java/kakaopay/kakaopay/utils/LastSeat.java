package kakaopay.kakaopay.utils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class LastSeat {
    public static  void main (String [] args) {
        Scanner in = new Scanner(System.in);
        int personCount = in.nextInt();
        int len = in.nextInt();

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= personCount; i++) {
            queue.offer(i);
        }

        while(!queue.isEmpty()) {
            for (int i = 1 ; i < len; i++) {
                queue.offer(queue.poll());
            }
            queue.poll();
            if (queue.size() == 1) {
                System.out.println(queue.poll());
            }
        }
    }
}