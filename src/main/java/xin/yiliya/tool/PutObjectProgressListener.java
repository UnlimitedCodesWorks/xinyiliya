package xin.yiliya.tool;

import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import xin.yiliya.baseInterface.BaseProgress;

public class PutObjectProgressListener implements BaseProgress {
    private long bytesWritten = 0;
    private long totalBytes = -1;
    private boolean succeed = false;
    private ProgressBar progressBar;
    private Task copyWorker;
    public void progressChanged(ProgressEvent progressEvent) {
        long bytes = progressEvent.getBytes();
        //进度条状态
        ProgressEventType eventType = progressEvent.getEventType();
        switch (eventType) {
            case TRANSFER_STARTED_EVENT:
                System.out.println("Start to upload......");
                break;
            case REQUEST_CONTENT_LENGTH_EVENT:
                this.totalBytes = bytes;
                System.out.println(this.totalBytes + " bytes in total will be uploaded to OSS");
                break;
            case REQUEST_BYTE_TRANSFER_EVENT:
                this.bytesWritten += bytes;
                if (this.totalBytes != -1) {
                    int percent = (int)(this.bytesWritten * 100.0 / this.totalBytes);
                    System.out.println(bytes + " bytes have been written at this time, upload progress: " + percent + "%(" + this.bytesWritten + "/" + this.totalBytes + ")");
                    final double bytesWrite = this.bytesWritten;
                    final double total = this.totalBytes;
                    copyWorker = new Task() {
                        @Override
                        protected Object call() throws Exception {
                            updateProgress(bytesWrite,total);
                            return true;
                        }
                    };
                    progressBar.progressProperty().unbind();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            //更新JavaFX的主线程的代码放在此处
                            progressBar.progressProperty().bind(copyWorker.progressProperty());
                        }
                    });
                    new Thread(copyWorker).start();
                } else {
                    System.out.println(bytes + " bytes have been written at this time, upload ratio: unknown" + "(" + this.bytesWritten + "/...)");
                }
                break;
            case TRANSFER_COMPLETED_EVENT:
                this.succeed = true;
                System.out.println("Succeed to upload, " + this.bytesWritten + " bytes have been transferred in total");
                break;
            case TRANSFER_FAILED_EVENT:
                System.out.println("Failed to upload, " + this.bytesWritten + " bytes have been transferred");
                break;
            default:
                break;
        }
    }
    public boolean isSucceed() {
        return succeed;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }
}
