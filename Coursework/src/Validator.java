import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    static Scanner input = new Scanner(System.in);
    public static int intValidator(String prompt){

        System.out.println(prompt);
        while (true){
            try {
                int intInput = input.nextInt();
                if(intInput>0){
                    return intInput;
                }
                else {
                    System.out.println("Input should be greater than zero.");
                }
            } catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("Invalid input. Please re-enter");
            }
        }
    }
    public  static  int intValidatorLimit(String prompt, int min, int max){
        System.out.println(prompt);
        while (true){
            try {
                int intInput =input.nextInt();
                if (intInput<=max && intInput>=min){
                    return intInput;
                }
                System.out.println("Input should be between "+min+" and "+max);
            } catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("Invalid input. Please re-enter");

            }
        }
    }
    public static double doubleValidator(String prompt){

        System.out.println(prompt);
        while (true){
            try {
                double doubleInput = input.nextDouble();
                if(doubleInput>0){
                    return doubleInput;
                }
                else {
                    System.out.println("Input should be greater than zero.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please re-enter");
                input.next();
            }
        }
    }
    public static String stringValidator(String prompt,String pattern){
        while (true){
            Pattern stringPattern = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
            System.out.println(prompt);
            String textInput = input.next();
            Matcher matcher = stringPattern.matcher(textInput);
            if (matcher.matches()) {
                return textInput;
            }
            System.out.println("Invalid Input. Please re-enter.");
        }
    }
    public static String productIDGenerator(boolean isElectronic){
        StringBuilder productID = new StringBuilder();
        if(isElectronic){
            productID.append("E-");
        }
        else {
            productID.append("C-");
        }
        Random random = new Random();
        for(int i=0;i<5;i++){
            productID.append(random.nextInt(10));
        }
        return productID.toString();
    }
}
