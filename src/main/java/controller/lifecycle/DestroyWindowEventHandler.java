package controller.lifecycle;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DestroyWindowEventHandler extends WindowAdapter {
    Closeable toClose;

    public DestroyWindowEventHandler(Closeable toClose) {
        this.toClose = toClose;
    }

    public void windowClosing(WindowEvent e) {
        toClose.close();
    }
}
