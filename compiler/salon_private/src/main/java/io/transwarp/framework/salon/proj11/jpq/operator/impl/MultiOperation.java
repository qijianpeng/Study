package io.transwarp.framework.salon.proj11.jpq.operator.impl;


import io.transwarp.framework.salon.proj11.jpq.operator.Expression;

import java.io.Serializable;

/**
 * Created by qijianpeng on 2018/8/12.
 * mail: jianpengqi@126.com
 */
public class MultiOperation extends BinaryOperator implements Serializable {
    private static final long serialVersionUID = 1L;

    private Expression left;
    private Expression right;

    public MultiOperation(){
        super("*");
    }
    public MultiOperation(Expression left, Expression right){
        super("*");
        this.left = left;
        this.right = right;
    }

    @Override
    public Object execute() {
        return new Digit(Integer.valueOf(left.execute().toString()) *
                Integer.valueOf(right.execute().toString()));
    }

    @Override
    public Object execute(Expression left, Expression right) {
        return new Digit(Integer.valueOf(left.execute().toString()) *
                Integer.valueOf(right.execute().toString()));
    }
}
