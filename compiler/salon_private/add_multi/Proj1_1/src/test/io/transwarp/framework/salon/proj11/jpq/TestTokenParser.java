package io.transwarp.framework.salon.proj11.jpq;

import io.transwarp.framework.salon.proj11.jpq.err.TokenParseException;
import io.transwarp.framework.salon.proj11.jpq.parser.TokenParser;
import org.junit.Test;

import java.io.Serializable;

/**
 * Created by qijianpeng on 2018/8/19.
 * mail: jianpengqi@126.com
 */
public class TestTokenParser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Test
    public void testLpRpReduce(){
        try {
            String reduceStr1 = Utils.queueToString(TokenParser.tokenize("(((((33)))))"));
            assert reduceStr1.equals("33");
        } catch (TokenParseException e) {
            e.printStackTrace();
        }
    }

}
