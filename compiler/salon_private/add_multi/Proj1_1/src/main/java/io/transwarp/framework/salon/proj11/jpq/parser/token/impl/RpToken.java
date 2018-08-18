package io.transwarp.framework.salon.proj11.jpq.parser.token.impl;

import io.transwarp.framework.salon.proj11.jpq.parser.token.OperatorToken;
import io.transwarp.framework.salon.proj11.jpq.parser.token.Token;
import io.transwarp.framework.salon.proj11.jpq.rule.operator.BinaryOperator;

import java.io.Serializable;

/**
 * Created by qijianpeng on 2018/8/12.
 * mail: jianpengqi@126.com
 */
public class RpToken extends OperatorToken implements Serializable {
    private static final long serialVersionUID = 1L;
    public RpToken(){
        super(")");
    }

    @Override
    public BinaryOperator getOperator() {
        throw new UnsupportedOperationException();
    }
}
