package io.transwarp.framework.salon.proj11.jpq.parser.token.impl;

import io.transwarp.framework.salon.proj11.jpq.parser.token.Token;

import java.io.Serializable;

/**
 * Created by qijianpeng on 2018/8/12.
 * mail: jianpengqi@126.com
 */
public class NumberToken extends Token implements Serializable {
    private static final long serialVersionUID = 1L;
    private int val;
    public NumberToken(int val){
        this.val = val;
    }

    public int getNumber(){
        return val;
    }


    @Override
    public String toString() {
        return ""+val;
    }
}
