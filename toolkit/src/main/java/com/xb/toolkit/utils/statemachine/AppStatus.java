package com.xb.toolkit.utils.statemachine;

/**
 * Created by zhangbw(biaoweizh@gmail.com) on 2017/12/27.
 * Description:
 */

public class AppStatus{
    private IState state;
    private int status;
    private int preStatus;

    public IState getState() {
        return state;
    }

    public void setState(IState state) {
        this.state = state;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPreStatus() {
        return preStatus;
    }

    public void setPreStatus(int preStatus) {
        this.preStatus = preStatus;
    }
}
