package pl.task;

import pl.task.enums.Field;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by dominik on 14.03.21.
 */
public class InputProcessor {

    private static final String RECORD_FIELDS_SEPARATOR = ",";
    private static final String COMMENT_SYMBOL = "//";
    private static final String EXPRESSION_KEYWORD = "EXPR";

    private List<String> file;

    public InputProcessor(String filename) {

        file = readFile(filename);
    }

    public List<String> readExpression() {

        for (String line : file) {

            //Skip comment
            if (line.startsWith(COMMENT_SYMBOL)) {
                continue;
            }

            if (line.startsWith(EXPRESSION_KEYWORD)) {
                return prepareExpression(line.replace(EXPRESSION_KEYWORD, ""));
            }
        }

        return Collections.emptyList();
    }

    public List<Map<Field, Integer>> readRecords() {

        List<Map<Field, Integer>> records = new ArrayList<>();

        for (String line : file) {

            //Skip comment and expression
            if (line.startsWith(COMMENT_SYMBOL) || line.startsWith(EXPRESSION_KEYWORD)) {
                continue;
            }

            records.add(readRecord(line));
        }

        return records;
    }

    public boolean isfileOpened() {

        return !file.isEmpty();
    }


    private Map<Field, Integer> readRecord(String line) {

        Map<Field, Integer> record = new HashMap<>();
        String[] splittedLine = line.split(RECORD_FIELDS_SEPARATOR);

        Iterator<Field> fieldIterator = Arrays.asList(Field.values()).iterator();

        for (String fieldValue : splittedLine) {
            record.put(fieldIterator.next(), Integer.valueOf(fieldValue.trim()));
        }

        return record;
    }


    private List<String> readFile(String filename) {
        try {

            return Files.readAllLines(Paths.get(filename));

        } catch (NoSuchFileException e) {

            System.out.println("No such file!");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    private List<String> prepareExpression(String input) {

        List<String> preparedExpression = new ArrayList<>();

        // It simplifies processing
        String replacedInput = input.replace("and", "&").replace("or", "|");
        char[] charInput = replacedInput.toCharArray();

        for (int i = 0; i < charInput.length; ++i) {

            if (Character.isWhitespace(charInput[i])) {
                continue;
            }

            if (Character.isDigit(charInput[i])) {

                i = readNumber(preparedExpression, charInput, i);
                continue;
            }

            if ('>' == charInput[i] || '<' == charInput[i]) {

                i = readComprasionOperator(preparedExpression, charInput, i);
                continue;
            }

            if ('!' == charInput[i]) {

                preparedExpression.add(new StringBuilder(2).append(charInput[i]).append(charInput[i + 1]).toString());
                ++i;
                continue;
            }

            preparedExpression.add(String.valueOf(charInput[i]));

        }

        return preparedExpression;
    }

    // An appearing of characters < or > may mean operators: <, >  or <=, >=
    private int readComprasionOperator(List<String> expression, char[] input, int i) {

        if ('=' == input[i + 1]) {

            expression.add(new StringBuilder(2).append(input[i]).append(input[i + 1]).toString());
            ++i;

        } else {

            expression.add(String.valueOf(input[i]));
        }
        return i;
    }

    private int readNumber(List<String> expression, char[] input, int i) {

        StringBuilder number = new StringBuilder();

        do {
            number.append(input[i]);
            ++i;

        } while (i < input.length && Character.isDigit(input[i]));

        expression.add(number.toString());

        return --i;
    }


}
