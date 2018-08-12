package io.transwarp.framework.salon.proj11.jpq.operator.impl;

import io.transwarp.framework.salon.proj11.jpq.operator.Expression;

import java.io.Serializable;

/**
 * Created by qijianpeng on 2018/8/12.
 * mail: jianpengqi@126.com
 */
public abstract class TerminalExpression implements Expression, Serializable {
    private static final long serialVersionUID = 1L;
    protected Object val;
    public TerminalExpression(Object val){
        this.val = val;
    }

    @Override
    public Object execute() {
        return null;
    }

    public String toString(){
        return val.toString();
    }
    public Object getValue(){
        return val;
    }
}
