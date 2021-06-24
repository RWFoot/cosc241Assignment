package week10;

import java.util.*;
import java.util.Scanner;
import java.lang.*;

/**RPN calculator with special and modified inputs.
 *@author Ryan Foot.
 */
public class RPNCalc{
    
    public static void main(String[] args){

    }

    /**Stack with Long values*/
    public static Stack<Long> stack = new Stack<Long>();

    /**String has individual elements that go through the process*/
    public static StringTokenizer tokens;

    /**Stops the process when needed*/
    public static boolean quit = false;

    /**Reads user input*/
    public static Scanner scan = new Scanner(System.in);

    /**next value in the input*/
    public static String input;

    /**When user inputs a "(" this becomes true*/
    public static boolean openParentheses = false;

    /**Number of times input in parentheses needs to be repeated*/
    public static long repeatInParentheses;

    /**All values inside the parentheses*/
    public static String strng = "";


    /**Main method that processes the RPN calculator.
     *@param args the command line arguements.
     */
    public static void main(String[] args){
        input = scan.nextLine();
        try{
            while(!quit){
                tokens = new StringTokenizer(input);
                while(tokens.hasMoreElements()){
                    String str = tokens.nextElement().toString();
                    RPNFunction(str);
                }
                if(!stack.isEmpty()){
                    System.out.println(stack);
                }
                strng = "";
                stack.clear();
                input = scan.nextLine();
            }
        }catch(NoSuchElementException e){
        }
    }

        /**pushes numbers onto the stack and if operand the correct calculation is done and pushed on stack.
     *@param token is the input from the user.
     */
    public static void RPNFunction(String token){
        Long firstPop = 0L;
        if(openParentheses == true){
            strng += token;
        }
        try{
                switch(token){
                    case "+":
                        stack.push(stack.pop() + stack.pop());
                        break;
                    case "-":
                        firstPop = stack.pop();
                        stack.push(stack.pop() - firstPop);
                        break;
                    case "*":
                        stack.push(stack.pop() * stack.pop());
                        break;
                    case "/":
                        firstPop = stack.pop();
                        stack.push(stack.pop() / firstPop);
                        break;
                    case "%":
                        firstPop = stack.pop();
                        stack.push(stack.pop() % firstPop);
                        break;
                    case "+!":
                        while(stack.size() > 1){
                            stack.push(stack.pop() + stack.pop());
                        }
                        break;
                    case "*!":
                        while(stack.size() > 1){
                            stack.push(stack.pop() * stack.pop());
                        }
                        break;
                    case "-!":
                        while(stack.size() > 1){
                            stack.push(stack.pop() - stack.pop());
                        }
                        break;
                    case "d":
                        stack.push(stack.peek());
                        break;
                    case "o":
                        System.out.print(stack.peek() + " ");
                        break;
                    case "c":
                        long t = stack.pop();
                        for(int i = 1; i < t; i++){
                            stack.push(stack.peek());
                        }
                        break;
                    case "r":
                        rotateStack();
                        break;
                    case "(":
                        openParentheses = true;
                        repeatInParentheses = stack.pop();
                        break;
                    case ")":
                        closeParentheses();
                        break;
                    case "q":
                        quit = true;
                        break;
                    default: stack.push(Long.parseLong(token));
                        break;
                }
        }catch(NumberFormatException e){
            System.out.println("Error: bad token '" + token + "'");
            stack.clear();
        }catch(EmptyStackException e){
            System.out.println("Error: too few operands");
        }catch(ArithmeticException e){
            System.out.println("Error: division by 0");
        }
    }

    /**
     * Moves top element down the stack k - 1 times.
     */
    public static void rotateStack(){
        int rotateNum = Math.toIntExact(stack.pop()) - 1;
        long numToMove = stack.pop();
        long[] arr = new long[rotateNum];
        for(int i = 0; i < rotateNum; i++){
            arr[i] = stack.pop();
        }
        stack.push(numToMove);
        int j = arr.length - 1;
        while(j != 0){
            stack.push(arr[j]);
            j--;
        }
        stack.push(arr[0]);
    }

    /**
     * adds values inside Parentheses into a string
     * then puts it back onto stack k - 1 times.
     */
    public static void closeParentheses(){
        openParentheses = false;
        String fixedString = strng.substring(0, strng.length() - 1);
        String strngMultiplied = "";
        String strngSpaced = "";
        String str;
        for(int i = 0; i < repeatInParentheses - 1; i++){
            strngMultiplied += fixedString;
        }
        strngSpaced = strngMultiplied.replace("", " ").trim();
        StringTokenizer s = new StringTokenizer(strngSpaced);
        while(s.hasMoreElements()){
            str = s.nextElement().toString();
            RPNFunction(str);
        }

    }



}