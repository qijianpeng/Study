package io.transwarp.framework.salon.proj11.jpq.parser.token;

import io.transwarp.framework.salon.proj11.jpq.rule.operator.BinaryOperator;

import java.io.Serializable;

/**
 * Created by qijianpeng on 2018/8/12.
 * mail: jianpengqi@126.com
 */
public abstract class OperatorToken extends Token implements Serializable {
    private static final long serialVersionUID = 1L;

    public OperatorToken(String val) {
        super(val);
    }

    public abstract BinaryOperator getOperator();
}
