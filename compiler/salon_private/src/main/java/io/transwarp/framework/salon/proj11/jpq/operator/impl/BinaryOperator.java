package io.transwarp.framework.salon.proj11.jpq.operator.impl;

import io.transwarp.framework.salon.proj11.jpq.operator.Expression;

import java.io.Serializable;

/**
 * Created by qijianpeng on 2018/8/12.
 * mail: jianpengqi@126.com
 */
public abstract class BinaryOperator extends TerminalExpression implements Serializable {
    private static final long serialVersionUID = 1L;

    public BinaryOperator(Object val) {
        super(val);
    }

    public abstract Object execute(Expression left, Expression right);
}
