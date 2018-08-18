package io.transwarp.framework.salon.proj11.jpq.parser.token.impl;

import io.transwarp.framework.salon.proj11.jpq.rule.operator.impl.AddOperator;
import io.transwarp.framework.salon.proj11.jpq.rule.operator.BinaryOperator;
import io.transwarp.framework.salon.proj11.jpq.parser.token.OperatorToken;

import java.io.Serializable;

/**
 * Created by qijianpeng on 2018/8/12.
 * mail: jianpengqi@126.com
 */
public class AddToken extends OperatorToken implements Serializable {
    private static final long serialVersionUID = 1L;
    public AddToken(){
        super("+");
    }

    @Override
    public BinaryOperator getOperator() {
        return new AddOperator();
    }

    @Override
    public String toString() {
        return "+";
    }
}
