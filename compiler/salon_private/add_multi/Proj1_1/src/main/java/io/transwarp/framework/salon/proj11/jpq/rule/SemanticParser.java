package io.transwarp.framework.salon.proj11.jpq.rule;

import io.transwarp.framework.salon.proj11.Result;
import io.transwarp.framework.salon.proj11.jpq.err.ErrorSets;
import io.transwarp.framework.salon.proj11.jpq.err.SemanticException;
import io.transwarp.framework.salon.proj11.jpq.rule.impl.Factor;
import io.transwarp.framework.salon.proj11.jpq.rule.impl.Term;
import io.transwarp.framework.salon.proj11.jpq.rule.operator.Expression;
import io.transwarp.framework.salon.proj11.jpq.parser.token.Token;
import io.transwarp.framework.salon.proj11.jpq.rule.impl.Expr;

import java.io.Serializable;
import java.util.Deque;

/**
 * Created by qijianpeng on 2018/8/12.
 * mail: jianpengqi@126.com
 */
public class SemanticParser implements Serializable {
    private static final long serialVersionUID = 1L;
    Expr expr = new Expr();
    Term term = null;
    Factor factor = null;
    public Result execute(Deque<Token> tokens) throws SemanticException {
        if (null == expr.getTermRule()){
            term = new Term();
            expr.setTermRule(term);
        }
        if (null == term.getFactorRule()){
            factor = new Factor();
            term.setFactorRule(factor);
        }
        if (null == factor.getExpRule()){
            factor.setExpRule(expr);
        }
        Expression result = expr.execute(tokens);
        if (null == result || !tokens.isEmpty()){
            return new Result(Integer.MIN_VALUE, true);
        }
        return new Result(Integer.valueOf(result.execute().toString()), false);
    }
}
