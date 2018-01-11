package xin.yiliya.baseInterface;

import com.aliyun.oss.event.ProgressListener;

public interface BaseProgress extends ProgressListener {
    boolean isSucceed();
}
