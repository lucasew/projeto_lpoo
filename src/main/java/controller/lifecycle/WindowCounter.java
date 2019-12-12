package controller.lifecycle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowCounter {
    private static int windowCount = 0;

    public static int getCounter() {
        return windowCount;
    }
    public static void increment() {
        System.out.println("Increment window counter");
        windowCount++;
    }

    public static void decrement() {
        System.out.println("Decrement window counter");
        windowCount--;
        if (windowCount == 0) {
            System.exit(0);
        }
    }
}
