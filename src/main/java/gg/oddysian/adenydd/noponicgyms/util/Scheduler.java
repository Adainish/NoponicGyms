package gg.oddysian.adenydd.noponicgyms.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Scheduler {
    private static final ExecutorService THREAD_POOL = Executors.newCachedThreadPool(Thread::new);

    public Scheduler() {
    }

    public static void runAsync(Runnable runnable) {
        THREAD_POOL.execute(runnable);
    }

    public static void runAsyncLater(Runnable runnable, long timeMillis) {
        THREAD_POOL.execute(() -> {
            try {
                Thread.sleep(timeMillis);
                runnable.run();
            } catch (InterruptedException var4) {
                var4.printStackTrace();
            }

        });
    }

    public static void runAsyncTimer(Runnable runnable, long delayMillis, long gapMillis) {
        THREAD_POOL.execute(() -> {
            try {
                Thread.sleep(delayMillis);
            } catch (InterruptedException var6) {
                var6.printStackTrace();
            }

            while(true) {
                while(true) {
                    try {
                        runnable.run();
                        Thread.sleep(gapMillis);
                    } catch (InterruptedException var7) {
                        var7.printStackTrace();
                    }
                }
            }
        });
    }
}
