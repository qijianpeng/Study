package io.transwarp.framework.salon.proj11.jpq.rule.impl;

import io.transwarp.framework.salon.proj11.jpq.err.ErrorSets;
import io.transwarp.framework.salon.proj11.jpq.err.SemanticException;
import io.transwarp.framework.salon.proj11.jpq.rule.operator.Expression;
import io.transwarp.framework.salon.proj11.jpq.rule.operator.impl.Digit;
import io.transwarp.framework.salon.proj11.jpq.parser.token.Token;
import io.transwarp.framework.salon.proj11.jpq.parser.token.impl.LpToken;
import io.transwarp.framework.salon.proj11.jpq.parser.token.impl.NumberToken;
import io.transwarp.framework.salon.proj11.jpq.parser.token.impl.RpToken;
import io.transwarp.framework.salon.proj11.jpq.rule.Rule;

import java.util.Deque;

/**
 * Created by jianpeng.qi on 18-8-13.
 * e: jianpeng.qi@transwarp.io
 *
 *
 * //FACTOR: TOK_EOF | TOK_NUMBER | TOK_LP EXP TOKRP
 */
public class Factor implements Rule {
    Expr expRule = null;

    public Expr getExpRule() {
        return expRule;
    }

    public void setExpRule(Expr expRule) {
        this.expRule = expRule;
    }

    @Override
    public int execute(Deque<Token> tokensQueue)  throws SemanticException {
        //TOK_EOF
        int lpCount = 0;
        Token token = tokensQueue.peek();
        //TOK_NUMBER
        if (token instanceof NumberToken){
            tokensQueue.pop();
            return ((NumberToken) token).getNumber();
        }
        //TOK_LP EXP TOK_RP
        //TOK_LP
        if (tokensQueue.peek() instanceof LpToken){
            tokensQueue.pop();
        }
        int expression = expRule.execute(tokensQueue);//EXP

        if (tokensQueue.isEmpty()) {
            throw new SemanticException("Missing ')'.");
        }
        if (tokensQueue.peek() instanceof RpToken) {
            tokensQueue.pop();
            return expression;
        }else {
            throw new SemanticException("Missing ')'.");
        }
    }

    //FACTOR: TOK_EOF | TOK_NUMBER | TOK_LP EXP TOKRP
    public void fastSkipFactor(Deque<Token> tokensQueue) throws SemanticException{
        //TOK_EOF
        Token token = tokensQueue.peek();
        //TOK_NUMBER
        if (token instanceof NumberToken) {
            tokensQueue.pop();
            return;
        }
        //TOK_LP EXP TOK_RP
        //TOK_LP
        if (tokensQueue.peek() instanceof LpToken){
            tokensQueue.pop();
        }
        expRule.fastSkipExp(tokensQueue);//EXP

        if (tokensQueue.isEmpty()) {
            throw new SemanticException("Missing ')'.");
        }
        if (tokensQueue.peek() instanceof RpToken) {
            tokensQueue.pop();
            return;
        }else {
            throw new SemanticException("Missing ')'.");
        }
    }
}
