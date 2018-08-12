package io.transwarp.framework.salon.proj11.jpq;

import java.io.Serializable;

/**
 * Created by qijianpeng on 2018/8/12.
 * mail: jianpengqi@126.com
 */
public class Result implements Serializable {
    private static final long serialVersionUID = 1L;
    protected int value;
    protected boolean status;
    public Result(int value, boolean status){
        this.value = value;
        this.status = status;
    }
}
