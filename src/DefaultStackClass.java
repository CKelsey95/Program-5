import java.util.Stack;
import java.util.EmptyStackException;

public class DefaultStackClass<T> {
    private Stack<Character> stack;

    public DefaultStackClass() {
        stack = new Stack<>();
    }

    public void push(char element) {
        stack.push(element);  // Use push method from Stack class
    }

    public char pop() {
        if (empty()) {
            throw new EmptyStackException();
        } else {
            return stack.pop();  // Use pop method from Stack class
        }
    }

    public char peek() {
        if (empty()) {
            throw new EmptyStackException();
        } else {
            return stack.peek();  // Use peek method from Stack class
        }
    }

    public boolean empty() {
        return stack.isEmpty();  // Use isEmpty method from Stack class
    }

    public int size() {
        return stack.size();  // Use size method from Stack class
    }
}
