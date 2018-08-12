package io.transwarp.framework.salon.proj11.jpq.operator.impl;

import java.io.Serializable;

/**
 * Created by qijianpeng on 2018/8/12.
 * mail: jianpengqi@126.com
 */
public class Digit extends TerminalExpression implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * parses string to integer.
     * @param strVal
     */
    public Digit(String strVal){
        super(Integer.valueOf(strVal));
    }

    /**
     * an integer value.
     * Regarding this value is valid.
     * @param intVal
     */
    public Digit(Integer intVal){
        super(intVal);
    }

    @Override
    public Object execute() {
        return val;
    }
}
