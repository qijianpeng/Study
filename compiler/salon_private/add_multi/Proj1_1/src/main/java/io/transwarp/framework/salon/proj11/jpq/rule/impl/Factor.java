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
    public Expression execute(Deque<Token> tokensQueue)  throws SemanticException {
        //TOK_EOF
        Token token = tokensQueue.peek();
        //TOK_NUMBER
        if (token instanceof NumberToken){
            tokensQueue.pop();
            return new Digit(((NumberToken) token).getNumber());
        }else if (token instanceof LpToken){ //TOK_LP EXP TOK_RP
            //TOK_LP
            tokensQueue.pop();
            Expression expression = expRule.execute(tokensQueue);//EXP
            if (tokensQueue.isEmpty()) {
                throw new SemanticException("Missing ')'.");
            }
            Token rp = tokensQueue.peek();
            if (rp instanceof RpToken) {//TOK_RP
                tokensQueue.pop();
                return expression;
            }
        }else {
            throw new SemanticException("Redundant operator after " +  tokensQueue.peek().toString());
        }
        return null;
    }
}
