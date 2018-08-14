package io.transwarp.framework.salon.proj11.jpq.parser.token.impl;

import io.transwarp.framework.salon.proj11.jpq.parser.token.Token;

import java.io.Serializable;

/**
 * Created by qijianpeng on 2018/8/12.
 * mail: jianpengqi@126.com
 */
public class NumberToken extends Token implements Serializable {
    private static final long serialVersionUID = 1L;
    public NumberToken(String val, Integer pos){
        super(val, pos);
    }

    public NumberToken(Integer val, Integer pos){
        this(val.toString(), pos);
    }
    public Integer getNumber(){
        return Integer.valueOf(val);
    }


}
