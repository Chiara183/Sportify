package selenium;

import java.util.Timer;
import java.util.TimerTask;

public abstract class testBaseSelenium {

    protected static void sleep(long duration) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {}
        }, duration);
    }

}
