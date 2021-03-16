package pl.task.enums;

import sun.plugin.dom.exception.InvalidStateException;

import java.util.function.BiFunction;

/**
 * Created by dominik on 12.03.21.
 */
public enum OperatorType {

    and("&", 2, true, (Boolean op1, Boolean op2) -> op1 && op2),
    or("|", 1, true, (Boolean op1, Boolean op2) -> op1 || op2),
    gt(">", 3, false, (Integer op1, Integer op2) -> op1 > op2),
    lt("<", 3, false, (Integer op1, Integer op2) -> op1 < op2),
    ge(">=", 3, false, (Integer op1, Integer op2) -> op1 >= op2),
    le("<=", 3, false, (Integer op1, Integer op2) -> op1 <= op2),
    eq("=", 3, false, (Integer op1, Integer op2) -> op1.equals(op2)),
    ne("!=", 3, false, (Integer op1, Integer op2) -> !op1.equals(op2)),
    right_bracket(")", 0, false, (op1, op2) -> {
        throw new InvalidStateException("Invalid operation");
    }),
    left_bracket("(", 0, false, (op1, op2) -> {
        throw new InvalidStateException("Invalid operation");
    });

    private int precedence;
    private String symbol;
    private BiFunction<?, ?, Boolean> function;
    private boolean logicalOperator;

    OperatorType(String symbol, int precedence, boolean logicalOperator, BiFunction<?, ?, Boolean> function) {
        this.symbol = symbol;
        this.precedence = precedence;
        this.function = function;
        this.logicalOperator = logicalOperator;
    }


    public boolean isLogicalOperator() {
        return logicalOperator;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getPrecedence() {
        return precedence;
    }

    public boolean apply(boolean op1, boolean op2) {

        if (!isLogicalOperator()) {
            throw new IllegalArgumentException("Illegal arguments for " + getSymbol());
        }

        return ((BiFunction<Boolean, Boolean, Boolean>) function).apply(op1, op2);
    }

    public boolean apply(int op1, int op2) {

        if (isLogicalOperator()) {
            throw new IllegalArgumentException("Illegal arguments for " + getSymbol());
        }

        return ((BiFunction<Integer, Integer, Boolean>) function).apply(op1, op2);
    }
}
