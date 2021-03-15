import java.util.*;
public class Conduit_Calculator {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    Intro();
    String ans = scan.next();
    while(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y")) {
      Calculate(scan);
      System.out.println("Do you want to calculate another one?");
      System.out.print("Yes/No? ");
      ans = scan.next();
    }

  }

  public static void Intro() {
    System.out.println("Welcome to your conduit sizing calculator.");
    //System.out.println("Using this calculator we will be able to find the smallest conduit allowed.")
    System.out.println("Do you want to calculate the minimum conduit size?");
    System.out.print("Yes/No? ");
  }

  public static void Calculate(Scanner scan) {

    Conduit con1 = new Conduit();
    int area = 0;
    String run = "";
    System.out.println("Do you have any wires?");
    System.out.print("Yes/No? ");
    String ans = scan.next();
    while(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y")) {
      System.out.print("What type of wire do you have? ");
      String type = scan.next();
      System.out.print("How many of this type of wire? ");
      int amount = scan.nextInt();
      con1.add_wires(type,amount);
      System.out.println("Do you have any more wire?");
      System.out.print("Yes/No? ");
      ans = scan.next();
    }

    System.out.println("Do you have any multi cables?");
    System.out.print("Yes/No? ");
    String ans = scan.next();
    while(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y")) {
      System.out.print("What type of multi cables do you have? ");
      int type2 = scan.nextInt();
      System.out.print("What is its guage? ");
      int guage = scan.nextInt();
      System.out.print("How many of this type of cable? ");
      int amount2 = scan.nextInt();
      con1.add_multiCable(type2, guage, amount2);
      System.out.println("Do you have any more multi cables?");
      System.out.print("Yes/No? ");
      ans = scan.next();
    }

    System.out.println("Do you have any fiber optic cables?");
    System.out.print("Yes/No? ");
    String ans = scan.next();
    while(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y")) {
      System.out.print("What type of fiber optic cable do you have? ");
      int type3 = scan.nextInt();
      System.out.print("How many of this type of fiber optic cable? ");
      int amount3 = scan.nextInt();
      con1.add_fiberOptic(type3,amount3);
      System.out.println("Do you have any more fiber optic cables?");
      System.out.print("Yes/No? ");
      ans = scan.next();
    }

    System.out.println("Do you have any circuit cables?");
    System.out.print("Yes/No? ");
    String ans = scan.next();
    while(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y")) {
      System.out.print("What type of circuit cable do you have? ");
      int type4 = scan.nextInt();
      System.out.print("How many of this type of circuit cable? ");
      int amount4 = scan.nextInt();
      con1.add_fiberOptic(type4,amount4);
      System.out.println("Do you have any more circuit cables?");
      System.out.print("Yes/No? ");
      ans = scan.next();
    }

    Sytem.out.println("What schedule is your conduit?");
    System.out.print("40/80?" )
    int sched = scan.nextInt();
    System.out.println("Is your conduit old or new? ");
    String age = scan.next();
    run = con1.trade_size(age,sched);
    area = con1.size();
    System.out.println("The mine size of your conduit is " + run);
    System.out.println("The combined area of the wires is " + area + "in squared");
  }
}
