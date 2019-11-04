import java.util.Scanner;

public class Tester {

	public static void main(String[]args) {
		Scanner in = new Scanner(System.in);
		String expression;
		System.out.println("Please enter in an expression: ");
		expression = in.nextLine();
		InfixExpression exp = new InfixExpression(expression);
		System.out.println("Infix Expression: "+exp.toString());
		System.out.println("Postfix Expression: "+exp.getPostfixExpression());
		System.out.println("Evaluation :" +exp.evaluate());
		

	}
}
