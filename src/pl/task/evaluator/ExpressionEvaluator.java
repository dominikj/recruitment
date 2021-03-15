package pl.task.evaluator;

import pl.task.Util;
import pl.task.enums.Field;
import pl.task.enums.OperatorType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by dominik on 13.03.21.
 */
public class ExpressionEvaluator {

    //http://math.uni.wroc.pl/~jagiella/p2python/skrypt_html/wyklad3-1.html
    // Shunting Yard Algorithm - convert expression to Reverse Polish Notation
    public List<Symbol> convertExpressionToRPN(List<String> expression) {

        Stack<Operator> operators = new Stack<>();
        List<Symbol> expressionInRPN = new ArrayList<>();

        for (String symbol : expression) {

            if (Util.isField(symbol)) {

                expressionInRPN.add(new Operand(Util.getFieldForSymbol(symbol)));

            } else if (Util.isNumber(symbol)) {

                expressionInRPN.add(new Operand(Integer.parseInt(symbol)));

            } else if (Util.isOperator(symbol)) {

                expressionInRPN.addAll(processOperator(symbol, operators));

            } else {
                throw new IllegalArgumentException("Unknown symbol in expression: " + symbol);
            }
        }

        // Put all remaining operators into the output
        while (!operators.empty()) {
            expressionInRPN.add(operators.pop());
        }

        return expressionInRPN;
    }

    private List<Symbol> processOperator(String symbol, Stack<Operator> operators) {

        List<Symbol> expressionInRPN = new ArrayList<>();

        OperatorType operator = Util.getOperatorForSymbol(symbol);

        if (isOpeningBracket(operator)) {

            operators.push(new Operator(operator));

            // Put an operators from stack into the output until an opening bracket appears
        } else if (isClosingBracket(operator)) {

            while (true) {
                Operator operatorFromStack = operators.pop();

                if (isOpeningBracket(operatorFromStack.getType())) {
                    break;
                }

                expressionInRPN.add(operatorFromStack);
            }

            // Put all operators from stack with higher priority into the output and then push current processed operator on stack
        } else {

            while (!operators.empty() && hasHigherPriority(operators.peek(), operator)) {

                expressionInRPN.add(operators.pop());
            }

            operators.push(new Operator(operator));

        }

        return expressionInRPN;
    }

    public boolean checkRecord(Map<Field, Integer> record, List<Symbol> expressionInRPN) {

        Stack<Operand> operands = new Stack<>();

        for (Symbol symbol : expressionInRPN) {

            // Push arguments on the stack
            if (!symbol.isOperator()) {

                operands.push((Operand) symbol);

                // When operator appears, take two arguments from the stack and apply operator to them. The result push on stack.
            } else {

                Operator operator = (Operator) symbol;

                // A logic operators (and, or) can take only boolean operands.
                if (operator.isLogicOperator()) {

                    operands.add(new Operand(operator.apply(operands.pop().getBoolean(), operands.pop().getBoolean())));

                } else {

                    operands.add(applyArithmeticOperands(operands.pop(), operands.pop(), operator, record));

                }
            }
        }

        // A last element on stack is the result of evaluation.
        return operands.pop().getBoolean();
    }

    private Operand applyArithmeticOperands(Operand operandRight, Operand operandLeft, Operator operator, Map<Field, Integer> record) {

        int leftOperandValue = getValue(operandLeft, record);
        int rightOperandValue = getValue(operandRight, record);

        return new Operand(operator.apply(leftOperandValue, rightOperandValue));
    }

    // An "numerical" operands can be a number or a letter. A Letter means field of record.
    // A letter have to be replaced by real field value of record before applying operator to it.
    private int getValue(Operand operand, Map<Field, Integer> record) {

        if (operand.isNumber()) {
            return operand.getNumber();
        }

        if (operand.isField()) {
            return record.get(operand.getField());
        }

        throw new IllegalArgumentException("Operand is not field or number");
    }

    private boolean isOpeningBracket(OperatorType operator) {
        return OperatorType.left_bracket.equals(operator);
    }

    private boolean isClosingBracket(OperatorType operator) {
        return OperatorType.right_bracket.equals(operator);
    }

    private boolean hasHigherPriority(Operator operator1, OperatorType operator2) {
        return operator1.getPrecedence() > operator2.getPrecedence();
    }
}
