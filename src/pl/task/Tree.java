package pl.task;

import pl.task.evaluator.Operand;
import pl.task.evaluator.Operator;
import pl.task.evaluator.Symbol;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * Created by dominik on 13.03.21.
 */
public class Tree {

    private Node rootOfTree;

    public void buildTree(List<Symbol> expressionInRPN) {
        Deque<Node> nodes = new ArrayDeque<>();

        for (Symbol symbol : expressionInRPN) {

            // Push argument as nodes on stack
            if (!symbol.isOperator()) {

                nodes.push(new Node(getValue((Operand) symbol)));

                // When operator appears, create new node, take two nodes from the stack and set them as children
            } else {

                Node node = new Node(((Operator) symbol).getType().getSymbol());
                node.setChildren(nodes.pop(), nodes.pop());
                nodes.push(node);
            }
        }

        // Last node is a root
        rootOfTree = nodes.pop();

    }

    public void printTree() {

        if (rootOfTree != null) {

            rootOfTree.print("");
        }
    }

    private String getValue(Operand operand) {

        if (operand.isBoolean()) {
            return String.valueOf(operand.getBoolean());
        }

        if (operand.isField()) {
            return String.valueOf(operand.getField());
        }

        if (operand.isNumber()) {
            return String.valueOf(operand.getNumber());
        }

        throw new IllegalArgumentException("Operand hasn't got a value");
    }

    private class Node {

        private String value;
        private Node leftChild;
        private Node rightChild;

        public Node(String value) {
            this.value = value;
        }

        public void setChildren(Node rightChild, Node leftChild) {
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }

        public void print(String prefix) {

            if ("|".equals(value)) {
                System.out.println("or");

            } else if ("&".equals(value)) {
                System.out.println("and");

            } else {

                System.out.println(value);
            }

            if (leftChild != null) {
                if (rightChild != null) {

                    System.out.print(prefix + "├── ");
                    leftChild.print(prefix + "│   ");

                } else {

                    System.out.print(prefix + "└── ");
                    leftChild.print(prefix + "    ");
                }
            }

            if (rightChild != null) {

                System.out.print(prefix + "└── ");
                rightChild.print(prefix + "    ");
            }
        }
    }
}
