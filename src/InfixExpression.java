import java.util.Scanner;

/**
 * @author Sarah Kollins
 *
 */

//Work: all methods work
//Potential Problem: when user enters in two numbers in a row with spaces between
//			them, program deletes spaces between and makes one number
//Dont work: none
public class InfixExpression {

	private String expression;
	//Constructor
	public InfixExpression(String expression) {
		this.expression=expression;	
		if(isValid()) {
			clean();
		}
		else {
			System.out.println("Fail: Invalid Expression");
			throw new IllegalArgumentException();
		}
	}
	/**
	 * Override toString()
	 * Returns the infix expression
	 */
	public String toString() {
		return expression;
	}
	/**
	 * Checks to see if equation is balanced
	 * @return true if equation is balanced and false otherwise
	 */
	private boolean isBalanced() {
		ArrayStack<Character> paranStack = new ArrayStack<>();
		int charCount = expression.length();
		boolean checkBalanced= true;
		int index =0;
		char nextChar=' ';

		while(checkBalanced&&index<charCount) {
			nextChar= expression.charAt(index);
			switch(nextChar) {
			case '(': case '[': case '{':
				paranStack.push(nextChar);
				break;
			case ')': case ']': case'}':
				if(paranStack.isEmpty()) {
					checkBalanced = false;
				}
				else {
					char open = paranStack.pop();
					checkBalanced = isPaired(open, nextChar);
				}
				break;
			default: break;
			}
			index++;
		}
		if(!paranStack.isEmpty()) {
			checkBalanced = false;
		}
		return checkBalanced;
	}
	/**
	 * Checks to see if () are paired
	 * @param open takes in a char
	 * @param close takes in a char
	 * @return true if parenthesis is paired and false otherwise
	 */
	private boolean isPaired(char open, char close) {
		return(open =='(' && close == ')');
	}
	/**
	 * Checks to see if equation is valid
	 * @return true if equation is valid and false otherwise
	 */
	private boolean isValid() {
		if(singleNumExpression()) {
			return true;
		}
		if(!isBalanced()) {
			return false;
		}
		else {
			if(!containsOp()) {
				return false;
			}
			if(!lastCharAndFirstChar()) {
				return false;
			}
			if(!compareNumToChar()) {
				return false;
			}	
		}
		return true;
	}
	/**
	 * Checks to see if equation contains an operation
	 * @return true if contains operation and false otherwise
	 */
	private boolean containsOp() {
		char postfixEx=' ';
		for(int i =0; i<expression.length();i++) {
			if(expression.charAt(i)=='+'|expression.charAt(i)=='-'|
					expression.charAt(i)=='*'|expression.charAt(i)=='/'|
					expression.charAt(i)=='^'|expression.charAt(i)=='%') {
				return true;
			}
		}
		return false;
	}
	/**
	 * Checks to see if the equation is only a number
	 * @return true if it is only a number and false otherwise
	 */
	private boolean singleNumExpression() {
		if(expression.length()==1&&Character.isDigit(expression.charAt(0))) {
			return true;
		}

		return false;
	}
	/**
	 * Checks if the equation has the correct ratio of numbers to characters
	 * @return true if ratio is correct and false otherwise
	 */
	private boolean compareNumToChar() {
		int countInt=0;
		int countChar=0;
		clean();
		Scanner s = new Scanner(expression);
		String scan="";
		while(s.hasNext()) {
			scan=s.next();
			switch(scan) {
			case "+" : case"-":case"/":case"*":case "%":case"^":
				countChar++;
			default: break;
			}
			if(Character.isDigit(scan.charAt(0))){
				countInt++;
			}
		}
		if(countInt-countChar==1) {
			return true;
		}		
		return false;
	}
	/**
	 * Checks to see if an operation is the first or last character
	 * @return true if it is not the first or last character and false otherwise
	 */
	private boolean lastCharAndFirstChar() {
		if(expression.charAt(expression.length()-1)=='+'|expression.charAt(expression.length()-1)=='-'|
				expression.charAt(expression.length()-1)=='*'|expression.charAt(expression.length()-1)=='/'|
				expression.charAt(expression.length()-1)=='^'|expression.charAt(expression.length()-1)=='%') {
			return false;
		}
		if(expression.charAt(0)=='+'|expression.charAt(0)=='-'|
				expression.charAt(0)=='*'|expression.charAt(0)=='/'|
				expression.charAt(0)=='^'|expression.charAt(0)=='%') {
			return false;
		}
		return true;
	}

	/**
	 * Cleans the equation by putting a space between each character
	 */
	public void clean() {
		expression= expression.replaceAll(" ", "");
		expression = expression.replaceAll("\\+", " + ");
		expression = expression.replaceAll("\\-", " - ");
		expression = expression.replaceAll("\\*", " * ");
		expression = expression.replaceAll("\\/", " / ");
		expression = expression.replaceAll("\\%", " % ");
		expression = expression.replaceAll("\\^", " ^ ");
		expression = expression.replaceAll("\\(", " ( ");
		expression = expression.replaceAll("\\)", " ) ");
		expression = expression.trim();
	}
	/**
	 * Converts infix expression to postfix
	 * @return postfix expression
	 */
	public String getPostfixExpression() {
		Scanner exp = new Scanner(expression);
		ArrayStack<String> charStack = new ArrayStack<>();
		String postFix ="";
		String str="";
		while(exp.hasNext()) {
			str = exp.next();
			if(Character.isDigit(str.charAt(0))) {
				postFix=postFix +str+" ";
			}
			else {
				if(charStack.isEmpty()) {
					charStack.push(str);
				}
				else {
					switch(str) {
					case "^":
						charStack.push(str);
						break;
					case "+" : case "-" : case "*" : case "/": case"%":
						while(!charStack.isEmpty()&&
								precedenceLevel(str)<=precedenceLevel(charStack.peek())) {
							postFix=postFix+charStack.peek() + " ";
							charStack.pop();
						}
						charStack.push(str);
						break; 
					case "(":
						charStack.push(str);
						break; 
					case ")":
						String temp =charStack.pop();
						while(!temp.equals("(")&& !charStack.isEmpty()) {
							postFix=postFix+temp + " ";
							temp=charStack.pop();
						}
						break;
					default: break;
					}
				}
			}
		}
		while(!charStack.isEmpty()) {
			String temp2= charStack.pop();
			postFix=postFix+ temp2 + " ";
		}
		exp.close();
		return postFix;
	}
/**
 * Checks the precedence levels of operators
 * @param op type String
 * @return the precedence value of an operation
 */
	private int precedenceLevel(String op) {
		switch (op) {
		case"^":
			return 0;
		case "+":
		case "-":
			return 1;
		case "*":
		case "/":
		case "%":
			return 2;
		default:
			return -1;
		}
	}
/**
 * Solve the postfix expression
 * @return value for the solution for the postfix expression
 */
	public int evaluate() {
		int solution=0;
		ArrayStack<Integer> nums = new ArrayStack<>();
		String postF = getPostfixExpression();
		Scanner postFixExp = new Scanner(postF);
		String s = "";
		int op1 =0;
		int op2 = 0;

		while(postFixExp.hasNext()) {
			s = postFixExp.next();
			if(Character.isDigit(s.charAt(0))) {
				int num = Integer.parseInt(s);
				nums.push(num);
			}
			switch(s) {
			case "+" : 
				op2 = nums.pop();
				op1 = nums.pop();
				solution = op1 + op2;
				nums.push(solution);
				break;
			case "-" : 
				op2 = nums.pop();
				op1 = nums.pop();
				solution = op1 - op2;
				nums.push(solution);
				break;
			case "*" :
				op2 = nums.pop();
				op1 = nums.pop();
				solution = op1 * op2;
				nums.push(solution);
				break;
			case "/":
				op2 = nums.pop();
				op1 = nums.pop();
				solution = op1 / op2;
				nums.push(solution);
				break;
			case "%":
				op2 = nums.pop();
				op1 = nums.pop();
				solution = op1 % op2;
				nums.push(solution);
				break;
			case "^":
				op2 = nums.pop();
				op1 = nums.pop();
				solution = (int) Math.pow(op1,op2);
				nums.push(solution);
				break;
			default: break;
			}
		}
		return nums.peek();
	}
}
