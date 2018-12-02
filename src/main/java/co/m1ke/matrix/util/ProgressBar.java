package co.m1ke.matrix.util;

import co.m1ke.matrix.callback.Callback;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProgressBar {

    private String name;
    private int threshold;
    private Callback task;

    public ProgressBar(String name, int threshold, Callback task) {
        this.name = name;
        this.threshold = threshold;
        this.task = task;

        ExecutorService service = Executors.newCachedThreadPool();
        service.submit(new Thread(() -> {
            int i = 0;

            LegacyTimings lt = new LegacyTimings();

            while (i++ < this.threshold) {
                System.out.print(Lang.WHITE + "[");
                int j = 0;
                while (j++ < i) {
                    System.out.print("*");
                }
                while (j++ <= this.threshold) {
                    System.out.print(" ");
                }

                task.complete();

                System.out.print("]" + Lang.YELLOW + " " + this.name + ": " + Lang.RESET + (i == this.threshold ? Lang.GREEN + "Done" + Lang.RESET + " (in " + new DecimalFormat("#.##").format((lt.stop() / 1000)) + "s)" : i + "%"));
                if (i == this.threshold) {
                    System.out.println();
                }
                System.out.print("\r");
            }
        }));
        service.shutdown();
        try {
            service.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException ignored) {}
    }

}
