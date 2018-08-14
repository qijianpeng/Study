package io.transwarp.framework.salon.proj11.jpq.parser.token.impl;

import io.transwarp.framework.salon.proj11.jpq.rule.operator.BinaryOperator;
import io.transwarp.framework.salon.proj11.jpq.rule.operator.impl.MultiOperator;
import io.transwarp.framework.salon.proj11.jpq.parser.token.OperatorToken;

import java.io.Serializable;

/**
 * Created by qijianpeng on 2018/8/12.
 * mail: jianpengqi@126.com
 */
public class MultiToken extends OperatorToken implements Serializable {
    private static final long serialVersionUID = 1L;
    public MultiToken(int pos){
        super("*", pos);
    }

    @Override
    public BinaryOperator getOperator() {
        return new MultiOperator();
    }
}
