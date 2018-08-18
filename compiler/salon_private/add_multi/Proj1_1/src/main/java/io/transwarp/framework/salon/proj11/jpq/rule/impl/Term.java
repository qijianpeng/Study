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
    public int execute(Deque<Token> tokensQueue) throws SemanticException {
        int factor = factorRule.execute(tokensQueue);
        while (!tokensQueue.isEmpty()) {
            Token token = tokensQueue.peek();
            if (factor == 0 && (token instanceof MultiToken)){//fast skip
                 //TOK_MUL TERM
                tokensQueue.pop();
                System.out.println("Fast skipping..");
                fastSkipTerm(tokensQueue);
            }else if (token instanceof MultiToken) { //TOK_MUL TERM
                tokensQueue.pop();
                int term = execute(tokensQueue);
                factor *= term;
            }else {
                break;
            }
        }
        return factor;
    }

    public void fastSkipTerm(Deque<Token> tokensQueue) throws SemanticException {//TERM: FACTOR (TOK_MUL TERM)
        factorRule.fastSkipFactor(tokensQueue);
        while (!tokensQueue.isEmpty()) {
            Token token = tokensQueue.peek();
            if (token instanceof MultiToken){//fast skip
                //TOK_MUL TERM
                tokensQueue.pop();
                fastSkipTerm(tokensQueue);
            }else {
                break;
            }
        }
    }
}
