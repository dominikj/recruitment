package pl.task.evaluator;

import pl.task.enums.OperatorType;

/**
 * Created by dominik on 12.03.21.
 */
public class Operator extends Symbol {

    private OperatorType operatorType;

    public Operator(OperatorType operatorType) {
        this.operatorType = operatorType;
    }

    @Override
    public boolean isOperator() {
        return true;
    }

    public boolean isLogicalOperator() {
        return operatorType.isLogicalOperator();
    }

    public OperatorType getType() {
        return operatorType;
    }

    public int getPrecedence() {
        return operatorType.getPrecedence();
    }

    public boolean apply(boolean op1, boolean op2) {
        return operatorType.apply(op1, op2);
    }

    public boolean apply(int op1, int op2) {
        return operatorType.apply(op1, op2);
    }
}
