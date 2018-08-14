package io.transwarp.framework.salon.proj11.jpq.rule.operator.impl;

import io.transwarp.framework.salon.proj11.jpq.rule.operator.TerminalExpression;

import java.io.Serializable;

/**
 * Created by qijianpeng on 2018/8/12.
 * mail: jianpengqi@126.com
 */
public class LpOperator extends TerminalExpression implements Serializable {
    private static final long serialVersionUID = 1L;

    public LpOperator(){
        super("(");
    }

}
