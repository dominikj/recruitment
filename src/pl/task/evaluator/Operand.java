package pl.task.evaluator;

import pl.task.enums.Field;

/**
 * Created by dominik on 12.03.21.
 */
public class Operand extends Symbol {

    private Field field;
    private Boolean booleanVal;
    private Integer number;


    public Operand(Field field) {
        this.field = field;
    }

    public Operand(boolean booleanVal) {
        this.booleanVal = booleanVal;
    }

    public Operand(int number) {
        this.number = number;
    }

    @Override
    public boolean isOperator() {
        return false;
    }


    public boolean isBoolean() {
        return booleanVal != null;
    }

    public boolean isField() {
        return field != null;
    }

    public boolean isNumber() {
        return number != null;
    }

    public boolean getBoolean() {
        return booleanVal;
    }

    public Field getField() {
        return field;
    }

    public int getNumber() {
        return number;
    }
}
