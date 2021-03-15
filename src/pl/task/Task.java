package pl.task;

import pl.task.enums.Field;
import pl.task.evaluator.ExpressionEvaluator;
import pl.task.evaluator.Symbol;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Task {

    public static void main(String[] args) {

        if (args.length != 1) {

            showHelp();
            return;
        }

        new Task().evaluate(args[0]);
    }

    private static void showHelp() {

        System.out.println("Usage: Task <input_file>");
    }

    private void evaluate(String filename) {

        InputProcessor inputProcessor = new InputProcessor(filename);

        if (!inputProcessor.isfileOpened()) {

            return;
        }

        List<String> expression = inputProcessor.readExpression();
        List<Map<Field, Integer>> records = inputProcessor.readRecords();

        ExpressionEvaluator evaluator = new ExpressionEvaluator();

        List<Symbol> expressionInRPN = evaluator.convertExpressionToRPN(expression);

        Tree tree = new Tree();
        tree.buildTree(expressionInRPN);
        tree.printTree();

        System.out.println("Press enter to continue");
        new Scanner(System.in).nextLine();

        for (Map<Field, Integer> record : records) {

            if (evaluator.checkRecord(record, expressionInRPN)) {

                printRecord(record);
            }
        }
    }

    private void printRecord(Map<Field, Integer> record) {

        for (Field field : Field.values()) {
            System.out.print(new StringBuilder(field.getStringVal()).append("=").append(record.get(field)).append(" "));
        }

        System.out.println();
    }

}
