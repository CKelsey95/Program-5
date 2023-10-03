import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/* Colton Kelsey
   CS 2050 Program 3
*/
public class Program5 {
    public static void main(String[] args) {
        DefaultStackClass<Character> stackSetup = new DefaultStackClass<>();  // creates new stack with 32 elements
        String input;

        try { // reads from one file and writes to another
            BufferedReader reader = new BufferedReader(new FileReader("src\\Program5.txt")); // reads
            FileWriter writer = new FileWriter("src\\Program5.out.txt"); //

            while ((input = reader.readLine()) != null) { // only performs when file is not empty
                String infixExpression = input.trim();  // removes leading and trailing whitespace
                String postfixExpression = InfixToPostfix(stackSetup, infixExpression); // calls the infixToPostfix

                writer.write(postfixExpression); // writes postfix to program3.out
                writer.write("\n"); // creates newline in between expressions
                System.out.println("Infix Expression: " + infixExpression);        // prints starting expression to console
                System.out.println("Postfix Expression: " + postfixExpression);    // prints postfix to console
            }
            reader.close();  // closes reader
            writer.close();  // closes writer
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());  //testing this as I haven't used it before
        }
    }

    public static String InfixToPostfix(DefaultStackClass<Character> stack, String InfixExpression) {
        StringBuilder postfix = new StringBuilder();
        DefaultStackClass<Character> operatorStack = new DefaultStackClass<>();
        boolean isValid = true;
        int openParanCount = 0;
        int closeParanCount = 0;

        for (int i = 0; i < InfixExpression.length(); i++) {
            char currentChar = InfixExpression.charAt(i);

            if (currentChar == ' ') {
                // Skip spaces
                continue;
            } else if (currentChar == '(') {
                operatorStack.push(currentChar);
                openParanCount++;
            } else if (currentChar == ')') {
                closeParanCount++;
                while (!operatorStack.empty() && operatorStack.peek() != '(') {
                    postfix.append(operatorStack.pop()).append(' ');
                }
                if (!operatorStack.empty() && operatorStack.peek() == '(') {
                    operatorStack.pop();
                } else {
                    isValid = false;
                    break;
                }
            } else if (isOperator(currentChar)) {
                while (!operatorStack.empty() && operatorStack.peek() != '(' && precedence(currentChar) <= precedence(operatorStack.peek())) {
                    postfix.append(operatorStack.pop()).append(' ');
                }
                operatorStack.push(currentChar);
            } else if (Character.isDigit(currentChar) || currentChar == '.') {
                StringBuilder operand = new StringBuilder();
                while (i < InfixExpression.length() && (Character.isDigit(currentChar) || currentChar == '.')) {
                    operand.append(currentChar);
                    i++;
                    if (i < InfixExpression.length()) {
                        currentChar = InfixExpression.charAt(i);
                    }
                }
                i--;
                postfix.append(operand.toString()).append(' ');
            } else {
                isValid = false; // Invalid character found
                break;
            }
        }

        while (!operatorStack.empty()) {
            if (operatorStack.peek() == '(') {
                isValid = false;
                break;
            }
            postfix.append(operatorStack.pop()).append(' ');
        }

        if (!isValid || openParanCount != closeParanCount) {
            return "Invalid expression";
        }
        return postfix.toString().trim(); // Trim any trailing spaces
    }

    public static boolean isValidCharacter(char c, char prevChar) {
        // Allow digits, operators, decimal points, parentheses, and spaces
        if ((c == '-' || c == '–') && (prevChar == '(' || prevChar == ' ')) {
            // Treat both '-' and '–' as unary operators (negative sign)
            return false;
        }
        return Character.isDigit(c) || isOperator(c) || c == '.' || c == '(' || c == ')' || c == ' ';
    }



    public static boolean isOperator(char c) {
        // allowable operators
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '–';
    }

    public static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            default:
                return 0;
        }
    }

}