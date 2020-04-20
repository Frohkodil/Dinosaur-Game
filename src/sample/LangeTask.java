package sample;

import javafx.concurrent.Task;

public class LangeTask extends Task {

    @Override
    protected Object call() throws Exception {
        return null;
    }
    @Override
    public void run() {
        for(int i = 0; i < 100; i++){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.updateProgress(i,100);
            updateMessage(String.valueOf(i/10 + 1));
        }
        updateMessage("Countdown");
        this.updateProgress(0,10);
    }


}
