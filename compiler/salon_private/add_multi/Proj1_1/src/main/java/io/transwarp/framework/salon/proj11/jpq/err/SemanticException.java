package io.transwarp.framework.salon.proj11.jpq.err;

import java.io.Serializable;

/**
 * Created by qijianpeng on 2018/8/12.
 * mail: jianpengqi@126.com
 */
public class SemanticException extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;
    public SemanticException(String errMsg){
        super(errMsg);
    }
}
