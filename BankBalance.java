import java.util.Scanner;
public class BankBalance {
   public static void main(String[] args){
      int monthCount = 0;
      System.out.println("Enter your initial balance: ");
      Scanner myBalance = new Scanner(System.in);
      int a = myBalance.nextInt();
      System.out.println("Enter the number of months: ");
      Scanner myMonths = new Scanner(System.in);
      int b = myMonths.nextInt();
      System.out.println("Enter the monthly interest rate for the account: ");
      Scanner myInterest = new Scanner(System.in);
      float c = myInterest.nextFloat();
      System.out.println("What will be withdrawn from the account each month: ");
      Scanner myWithdrawal = new Scanner(System.in);
      int d = myWithdrawal.nextInt();
      for(monthCount = 0; monthCount <= b; monthCount++){
         System.out.print("Month #");
         System.out.println(monthCount);
         System.out.print(" ");
         System.out.println("initial balance " + a);
         a *= c;
         System.out.println("Withdrawn amount " + d);
         a -= d;
         System.out.println("balance after withdrawal " + a);
         if( a < 100){
            System.out.println("What are you doing man, your account dropped below 100");
            return;
         }
         
         
         
         
      }
      
   
   
   
   
   }









}