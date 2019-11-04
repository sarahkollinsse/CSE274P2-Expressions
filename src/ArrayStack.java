import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Stack;
public class ArrayStack<T> implements StackInterface<T>{


	
	private T[] stack;
	private int top;

	public ArrayStack(){
	stack = (T[]) new Object[10];
		
		top=-1;
	}

	@Override
	public void push(T newEntry) {
		if(top==stack.length-1) {
			stack = Arrays.copyOf(stack,stack.length*2);
			stack[top+1]=(T) newEntry;
			top++;
		}
		else {
			stack[top+1]=(T) newEntry;
			top++;
		}
	}

	@Override
	public T pop() {
		if(isEmpty()) {
			throw new EmptyStackException();
		}
		T temp = stack[top];
		stack[top]=null;
		top--;
		return temp;

	}

	@Override
	public T peek() {
		if(isEmpty()) {
			throw new EmptyStackException();
		}
		return stack[top];
	}

	@Override
	public boolean isEmpty() {
		return(top==-1);
	}

	@Override
	public void clear() {
		top=-1;
		for(int i =0;i<stack.length;i++) {
			stack[i]=null;
		}

	}

}
