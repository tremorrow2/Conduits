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
    System.out.println("Do you want to calculate the minimum conduit size?");
    System.out.print("Yes/No? ");
  }

  public static void Calculate(Scanner scan) {

    Conduit con1 = new Conduit();
    String wires = con1.print_wires();
    String multicables = con1.print_multicables();
    String fiberoptics = con1.print_fiberoptics();
    String cs = con1.print_cs();

    System.out.println("Do you have any wires?");
    System.out.print("Yes/No? ");
    String ans = scan.next();
    while(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y")) {
      System.out.println("What type of wire do you have? ");
      System.out.println("Your options are\n" + wires);
      String type = scan.next();
      while(!con1.contain_wire(type)) {
        System.out.println("Answer not contained in list, please try again.");
        type = scan.next();
      }
      System.out.print("How many of this type of wire? ");
      int amount = scan.nextInt();
      con1.add_wires(type,amount);
      System.out.println("Do you have any more wire?");
      System.out.print("Yes/No? ");
      ans = scan.next();
    }

    System.out.println("Do you have any multi cables?");
    System.out.print("Yes/No? ");
    ans = scan.next();

    while(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y")) {
      System.out.println("What type of multi cables do you have? 12/14?" );
      System.out.println("Your options are\n" + multicables);
      int type2 = scan.nextInt();
      System.out.print("What is its guage? ");
      int guage = scan.nextInt();
      while(!con1.contain_multicables(guage,type2)) {
        System.out.println("Answer not contained in list, please try again.");
        System.out.println("What type of multi cables do you have? ");
        System.out.println("Your options are\n" + multicables);
        type2 = scan.nextInt();
        System.out.print("What is its guage? 12/14?");
        guage = scan.nextInt();
      }
      System.out.print("How many of this type of cable? ");
      int amount2 = scan.nextInt();
      con1.add_multiCable(type2, guage, amount2);
      System.out.println("Do you have any more multi cables?");
      System.out.print("Yes/No? ");
      ans = scan.next();
    }

    System.out.println("Do you have any fiber optic cables?");
    System.out.print("Yes/No? ");
    ans = scan.next();
    while(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y")) {
      System.out.println("What type of fiber optic cable do you have? ");
      System.out.println("Your options are\n" + fiberoptics);
      int type3 = scan.nextInt();
      while(!con1.contain_fiberoptic(type3)) {
        System.out.println("Answer not contained in list, please try again.");
        type3 = scan.nextInt();
      }
      System.out.print("How many of this type of fiber optic cable? ");
      int amount3 = scan.nextInt();
      con1.add_fiberOptic(type3,amount3);
      System.out.println("Do you have any more fiber optic cables?");
      System.out.print("Yes/No? ");
      ans = scan.next();
    }

    System.out.println("Do you have any cs cables?");
    System.out.print("Yes/No? ");
    ans = scan.next();
    while(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y")) {
      System.out.println("What type of circuit cable do you have? ");
      System.out.println("Your options are\n" + cs);
      String type4 = scan.next();
      while(!con1.contain_cs(type4)) {
        System.out.println("Answer not contained in list, please try again.");
        type4 = scan.next();
      }
      System.out.print("How many of this type of circuit cable? ");
      int amount4 = scan.nextInt();
      con1.add_cs(type4,amount4);
      System.out.println("Do you have any more circuit cables?");
      System.out.print("Yes/No? ");
      ans = scan.next();
    }

    System.out.println("What schedule is your conduit?");
    System.out.print("40/80?" );
    int sched = scan.nextInt();
    System.out.print("Is your conduit old or new? ");
    String age = scan.next();
    con1.trade_size(age,sched);
  }
}
