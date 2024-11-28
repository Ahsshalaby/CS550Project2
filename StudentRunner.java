

import java.util.Scanner; 

public class StudentRunner {

    public static void main(String[] args) {
    System.out.println("hello world");

    Scanner myScanner = new Scanner(System.in);

        System.out.println("Enter your user name for Oracle: ");
        String userName = myScanner.nextLine();
        System.out.println("Enter your password for Oracle: ");
        String userPassword = myScanner.nextLine();


        System.out.println("username " + userName + " password " + userPassword);



    }
}