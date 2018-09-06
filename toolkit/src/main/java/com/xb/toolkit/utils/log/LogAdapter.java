package com.xb.toolkit.utils.log;

/**
 * Created by admin on 2018/9/6.
 */

public interface LogAdapter {
    boolean isLoggable(int priority, String tag);

    void log(int priority, String tag, String message);
}
