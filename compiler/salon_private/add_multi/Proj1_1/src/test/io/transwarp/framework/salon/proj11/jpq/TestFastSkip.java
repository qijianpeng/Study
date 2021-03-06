package io.transwarp.framework.salon.proj11.jpq;

import io.transwarp.framework.salon.proj11.jpq.err.TokenParseException;
import io.transwarp.framework.salon.proj11.jpq.parser.TokenParser;
import io.transwarp.framework.salon.proj11.jpq.parser.token.Token;
import io.transwarp.framework.salon.proj11.jpq.rule.impl.Factor;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.Deque;

/**
 * Created by qijianpeng on 2018/8/18.
 * mail: jianpengqi@126.com
 */
public class TestFastSkip implements Serializable {
    private static final long serialVersionUID = 1L;
    Factor factor;
    @Before
    public void setUp(){
        factor = new Factor();
    }
    @Test
    public void testFastSkipLpExpRp(){
        assert fastSkipResultsStr("((((1+2)+1)))+1").equals("+1");
        assert fastSkipResultsStr("(1+2)+(2+3)").equals("+(2+3)");
        assert fastSkipResultsStr("3+(2+1)+1").equals("3+(2+1)+1");
        assert fastSkipResultsStr("(2+2))").equals(")");


    }

    @Test
    public void testFastSkipLpExpRpError(){
        assert fastSkipResultsStr("((2+1)+1").equals("Missing ')'");

    }


    @Test
    public void testExtractExp(){
        assert fastExtractExpr("(1+2)+2").equals("1+2");
        assert fastExtractExpr("1+(2)+2").equals("");
        assert fastExtractExpr("((1+2+2))").equals("(1+2+2)");
        assert fastExtractExpr("(1+(2)+2)").equals("1+2+2");//'(2)' --> '2' on token parser
    }

    private String fastSkipResultsStr(String expr){
        Deque<Token> tokens = null;
        try {
            tokens = TokenParser.tokenize(expr);
            factor.fastSkipLpExpRp(tokens);
            return Utils.queueToString(tokens);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String fastExtractExpr(String expr){
        Deque<Token> tokens = null;
        try {
            tokens = TokenParser.tokenize(expr);
            tokens = factor.extractExpOfLpExpRp(tokens);
            return Utils.queueToString(tokens);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
