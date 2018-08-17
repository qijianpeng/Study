package io.transwarp.framework.salon.proj11.jpq.rule.impl;

import io.transwarp.framework.salon.proj11.jpq.err.SemanticException;
import io.transwarp.framework.salon.proj11.jpq.rule.operator.Expression;
import io.transwarp.framework.salon.proj11.jpq.rule.operator.TerminalExpression;
import io.transwarp.framework.salon.proj11.jpq.parser.token.Token;
import io.transwarp.framework.salon.proj11.jpq.parser.token.impl.MultiToken;
import io.transwarp.framework.salon.proj11.jpq.rule.Rule;

import java.util.Deque;

/**
 * Created by jianpeng.qi on 18-8-13.
 * e: jianpeng.qi@transwarp.io
 *
 *
 * TERM: FACTOR (TOK_MUL TERM)
 */
public class Term implements Rule {
    Factor factorRule = null;

    public Factor getFactorRule() {
        return factorRule;
    }

    public void setFactorRule(Factor factorRule) {
        this.factorRule = factorRule;
    }

    @Override
    public Expression execute(Deque<Token> tokensQueue) throws SemanticException {
        TerminalExpression factor = (TerminalExpression)factorRule.execute(tokensQueue);
        while (null != factor && !tokensQueue.isEmpty()) {
            if (Integer.valueOf(factor.getValue().toString()) == 0){
                Token token = tokensQueue.peek();
                if (token instanceof MultiToken) { //TOK_MUL TERM
                    tokensQueue.pop();
                    factorRule.skipFactor(tokensQueue);
                }
            }
            Token token = tokensQueue.peek();
            if (token instanceof MultiToken) { //TOK_MUL TERM
                tokensQueue.pop();
                TerminalExpression term = (TerminalExpression) execute(tokensQueue);
                factor = (TerminalExpression) ((MultiToken) token).getOperator().execute(factor, term);
            }else {
                break;
            }
        }
        return factor;
    }
}
