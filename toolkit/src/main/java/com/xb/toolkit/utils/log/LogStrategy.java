package com.xb.toolkit.utils.log;

/**
 * Created by admin on 2018/9/6.
 */

public interface LogStrategy {
    void log(int priority, String tag, String message);
}
