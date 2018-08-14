package io.transwarp.framework.salon.proj11.jpq.rule;

import io.transwarp.framework.salon.proj11.Result;
import io.transwarp.framework.salon.proj11.jpq.err.ErrorSets;
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
    public Result execute(Deque<Token> tokens){
        Expr expr = new Expr();
        Expression result = expr.execute(tokens);
        if (null == result || !tokens.isEmpty() || ErrorSets.hasError()){
            return new Result(Integer.MIN_VALUE, true);
        }
        return new Result(Integer.valueOf(result.execute().toString()), false);
    }
}
