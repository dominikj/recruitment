package pl.task;

import pl.task.enums.Field;
import pl.task.enums.OperatorType;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by dominik on 12.03.21.
 */
public class Util {
    private static final Map<String, OperatorType> operatorSymbolMap = Arrays.stream(OperatorType.values())
            .collect(Collectors.toMap(OperatorType::getSymbol, operator -> operator));

    private static final Map<String, Field> fieldSymbolMap = Arrays.stream(Field.values())
            .collect(Collectors.toMap(Field::getStringVal, field -> field));

    public static OperatorType getOperatorForSymbol(String symbol) {
        return operatorSymbolMap.get(symbol);
    }

    public static boolean isOperator(String symbol) {
        return operatorSymbolMap.keySet().contains(symbol);
    }

    public static Field getFieldForSymbol(String symbol) {
        return fieldSymbolMap.get(symbol);
    }

    public static boolean isField(String symbol) {
        return fieldSymbolMap.keySet().contains(symbol);
    }

    public static boolean isNumber(String symbol) {
        try {
            Integer.parseInt(symbol);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

}
