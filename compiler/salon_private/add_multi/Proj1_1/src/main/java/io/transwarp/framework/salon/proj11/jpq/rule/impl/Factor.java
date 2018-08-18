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

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

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
        //Token token = tokensQueue.peek();
        //TOK_NUMBER
        if (tokensQueue.peek() instanceof NumberToken) {
            tokensQueue.pop();
            return;
        }
        //TOK_LP EXP TOK_RP
        fastSkipLpExpRp(tokensQueue);
        /*//TOK_LP
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
        }*/
    }

    static Byte LP = (byte) -1;
    static Byte RP = (byte) 1;

    public void fastSkipLpExpRp(Deque<Token> tokensQueue) throws SemanticException{
        if(tokensQueue.peek() instanceof LpToken){
            Stack<Byte> stack = new Stack<>();
            tokensQueue.pop();
            stack.push(LP);
            while (!stack.empty()){
                matchP(stack, tokensQueue.peek());
                //others like '+', '#', '*'
                tokensQueue.pop();
                if (tokensQueue.isEmpty() && !stack.empty()){
                    throw new SemanticException("Missing ')'");
                }
                /*//This step should belong to semantic analyze.
                if (stack.empty() && (tokensQueue.peek() instanceof RpToken)){
                    throw new SemanticException("Missing '('");
                }*/
            }
        }else return;
    }

    private void matchP(Stack<Byte> stack, Token token){
        if (token instanceof LpToken){
            stack.push(LP);
        }else if (token instanceof RpToken){
            if (!stack.empty() && stack.peek().equals(LP))stack.pop();
        }
    }

    /**
     * 提取以'('开头的最外层'(EXPR)'中的EXPR。
     *
     * (1+2) --> 1+2
     * ((1+2)) --> (1+2)
     * (1+2)+3 --> 1+2
     * 1+2+(3+4) --> NULL/""
     *
     * @param tokensQueue
     * @return
     * @throws SemanticException
     */
    public Deque<Token> extractExpOfLpExpRp(Deque<Token> tokensQueue) throws SemanticException{
        Deque<Token> deque = null;
        if(tokensQueue.peek() instanceof LpToken){
            deque = new ArrayDeque();
            Stack<Byte> stack = new Stack<>();
            tokensQueue.pop();
            stack.push(LP);
            while (!stack.empty()){
                matchP(stack, tokensQueue.peek());
                //others like '+', '#', '*'
                deque.add(tokensQueue.pop());
                if (tokensQueue.isEmpty() && !stack.empty()){
                    throw new SemanticException("Missing ')'");
                }
                /*//This step should belong to semantic analyze.
                if (stack.empty() && (tokensQueue.peek() instanceof RpToken)){
                    throw new SemanticException("Missing '('");
                }*/
            }
            deque.pollLast();//pop ')'
        }
        return deque;
    }
}
