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
    Term term = new Term();
    Factor factor = new Factor();
    {
        expr.setTermRule(term);
        term.setFactorRule(factor);
        factor.setExpRule(expr);
    }
    public Result execute(Deque<Token> tokens) throws SemanticException {
        int result = expr.execute(tokens);
        if (!tokens.isEmpty()){
            return new Result(Integer.MIN_VALUE, true);
        }
        return new Result(result, false);
    }
}
