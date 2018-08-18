package io.transwarp.framework.salon.proj11.jpq.rule.operator.impl;

import io.transwarp.framework.salon.proj11.jpq.rule.operator.BinaryOperator;
import io.transwarp.framework.salon.proj11.jpq.rule.operator.Expression;

import java.io.Serializable;

/**
 * Created by qijianpeng on 2018/8/12.
 * mail: jianpengqi@126.com
 */
public class AddOperator extends BinaryOperator implements  Serializable {
    private static final long serialVersionUID = 1L;

    public AddOperator(){
        super("+");
    }

    @Override
    public Object execute(Expression left, Expression right) {
        return new Digit(Integer.valueOf(left.execute().toString()) +
                Integer.valueOf(right.execute().toString()));
    }
}
