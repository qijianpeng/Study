package io.transwarp.framework.salon.proj11.jpq.parser.token;

import java.io.Serializable;

/**
 * Created by qijianpeng on 2018/8/12.
 * mail: jianpengqi@126.com
 */
public abstract class Token implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String val;
    protected int pos;
    public Token(String val){
        this(val, -1);
    }
    public Token(String val, int pos){
        this.val = val;
        this.pos = pos;
    }
    public String toString(){
        return "[" + this.val +", pos: " + this.pos +"]";
    }
}
