package io.transwarp.framework.salon.proj11.jpq.parser.token.impl;

import io.transwarp.framework.salon.proj11.jpq.operator.impl.AddOperator;
import io.transwarp.framework.salon.proj11.jpq.operator.impl.BinaryOperator;

import java.io.Serializable;

/**
 * Created by qijianpeng on 2018/8/12.
 * mail: jianpengqi@126.com
 */
public class AddToken extends OperatorToken implements Serializable {
    private static final long serialVersionUID = 1L;
    public AddToken(int pos){
        super("+", pos);
    }

    @Override
    public BinaryOperator getOperator() {
        return new AddOperator();
    }
}
