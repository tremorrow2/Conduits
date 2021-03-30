import java.util.*;
import java.io.*;

/*Trevor Morrow
This class is called Conduit_Calculator its main function is to increase
effiency of minimum allowed size of a new conduit or to telll user if existing conduit is already filled to capacity
This class uses the Conduit Class to store information about individual conduits*/

public class Electrical_Calculator {
  public static void main(String[] args) throws FileNotFoundException {
    Scanner scan = new Scanner(System.in);
    System.out.println("Create a name for the file that you want to export your calculations to.");
    String name = scan.next();
    String file = name + ".txt";
    PrintStream output = new PrintStream(new File(file));
    output.println("                        " + name + " Calculations");
    Intro(scan,output);
  }

  //Explains the meaning of the program
  public static void Intro(Scanner scan, PrintStream output) {
    int runs = 0;
    int branches = 0;
    System.out.println("Welcome to your Electrical Calculator.");
    System.out.println("Do you want to calculate the minimum conduit size?");
    System.out.println("Yes/No? ");
    String ans = scan.next();
    if(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y")) {
       System.out.println("This calculator will ask you information about wires, multicables, fiberoptic cables and cs cables. \nIf a particlar wire or cable is not listed, after you finish the cs cables it will ask if you need to add any miscellaneous wire. \nGround wire is automatically included and calculated so you do no need to worry about it.");
       output.println("Conduit Sizing");
       output.println();
    }
    while(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y")) {
      runs++;
      Calculate(scan, output, runs);
      System.out.println("Do you want to calculate another one?");
      System.out.println("Yes/No? ");
      ans = scan.next();
    }
    System.out.println("Do you want to calculate line loss percentage for different branches?\nYes/No?");
    ans = scan.next();
    if(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y")) {
      System.out.println("This calculator will ask you information about your total voltage allowed, the type of wire in a segment, the length of the segment, and the amp load of that segment.");
      output.println("LINE LOSS BRANCHES");
      output.println();
    }
    while(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y")) {
      branches++;
      Lines(scan, output, branches);
      System.out.println("Do you want to calculate another one?");
      System.out.println("Yes/No? ");
      ans = scan.next();
    }
  }

  //Main Calculator Function
  public static void Calculate(Scanner scan, PrintStream output, int runs) {
    Conduit con1 = new Conduit();
    Trade(scan, output, con1, runs);
    Wire(scan,con1);
    MultiCable(scan,con1);
    FiberOptic(scan,con1);
    CS(scan,con1);
    Misc(scan,con1);
    con1.trade_size(output);
  }

  //This fucntion names your current run, asked if its exsiting or new, ask the schedule and creates your conduit.
  public static void Trade(Scanner scan,PrintStream output, Conduit con1, int runs){
    String age = "";
    int sched = 0;
    String size = "";
    String run = "";
    String ans = "";
    System.out.println("What is the name of this run?");
    run = scan.next();
    System.out.println("Is your conduit a new 40 schedule?");
    ans = scan.next();
    if(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y")){
      sched = 40;
      age = "New";
    } else {
      System.out.println("Is your conduit an existing 40 schedule?");
      ans = scan.next();
      if(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y")){
        sched = 40;
        age = "Existing";
      } else {
        System.out.println("Is your conduit a new 80 schedule?");
        if(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y")){
          sched = 80;
          age = "New";
        } else {
          System.out.println("Is your conduit an existing 80 schedule?");
          if(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y")){
            sched = 80;
            age = "Existing";
          }
        }
      }
    }


    /*
    while(!(sched == 40 || sched == 80)) {
      System.out.println("What schedule is your conduit?");
      System.out.println("40/80? " );
      sched = scan.nextInt();
    }
    while(!(age.equalsIgnoreCase("existing") || age.equalsIgnoreCase("new"))){
      System.out.println("Is your conduit existing or new? ");
      age = scan.next();
    }*/

    if(age.equalsIgnoreCase("existing")) {
      while(!con1.contain_trade(size)){
        System.out.println("What size is your existing conduit? ");
        size = scan.nextLine();

      }
      output.println(runs + ".  " + age + " run " + run + " of schedule " + sched + " and initial size is a " + size + ".");
      con1.existence(sched, size, age);
    } else {
      output.println(runs + ".  " + age + " run " + run + " of schedule " + sched + ".");
      con1.existence(sched, age);
    }

  }

  //Adds necessary wires to your conduit
  public static void Wire(Scanner scan, Conduit con1){
    String wires = con1.print_wires();
    String ans = "";
    while(!(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y") || ans.equalsIgnoreCase("no") || ans.equalsIgnoreCase("n"))) {
      System.out.println("Do you have any wires?");
      System.out.println("Your options are\n" + wires);
      System.out.println("Yes/No? ");
      ans = scan.next();
    }
    while(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y")) {
      System.out.println("What type of wire do you have? ");
      String type = scan.nextLine();
      while(!con1.contain_wire(type)) {
        type = scan.nextLine();
      }
      System.out.println("How many of this type of wire? ");
      int amount = scan.nextInt();
      con1.add_wires(type,amount);
      System.out.println("Do you have any more wire?");
      System.out.println("Yes/No? ");
      ans = scan.next();
    }
  }

  //Adds neccessary multicables to your conduit
  public static void MultiCable(Scanner scan, Conduit con1) {
    String multicables = con1.print_multicables();
    String ans = "";
    while(!(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y") || ans.equalsIgnoreCase("no") || ans.equalsIgnoreCase("n"))) {
      System.out.println("Do you have any multi cables?\nYes/No? ");
      System.out.println("Your options are\n" + multicables + " " );
      ans = scan.next();
    }
    while(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y")) {
      System.out.println("What type of multi cables do you have?");
      int type2 = scan.nextInt();
      System.out.println("What is its gauge? 12/14? ");
      int guage = scan.nextInt();
      while(!con1.contain_multicables(guage,type2)) {
        System.out.println("Answer not contained in list, please try again.");
        type2 = scan.nextInt();
        System.out.println("What is its guage? 12/14? ");
        guage = scan.nextInt();
      }
      System.out.println("How many of this type of cable? ");
      int amount2 = scan.nextInt();
      con1.add_multiCable(type2, guage, amount2);
      System.out.println("Do you have any more multi cables?");
      System.out.println("Yes/No? ");
      ans = scan.next();
    }

  }

  //Adds neccessary fiberoptics to conduit
  public static void FiberOptic(Scanner scan, Conduit con1) {
    String fiberoptics = con1.print_fiberoptics();
    String ans = "";
    while(!(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y") || ans.equalsIgnoreCase("no") || ans.equalsIgnoreCase("n"))) {
      System.out.println("Do you have any fiber optic cables?");
      System.out.println("Your options are\n" + fiberoptics + " ");
      System.out.println("Yes/No? ");
      ans = scan.next();
    }
    while(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y")) {
      System.out.println("What type of fiber optic cable do you have? ");
      int type3 = scan.nextInt();
      while(!con1.contain_fiberoptic(type3)) {
        System.out.println("Answer not contained in list, please try again.");
        type3 = scan.nextInt();
      }
      System.out.println("How many of this type of fiber optic cable? ");
      int amount3 = scan.nextInt();
      con1.add_fiberOptic(type3,amount3);
      System.out.println("Do you have any more fiber optic cables?");
      System.out.println("Yes/No? ");
      ans = scan.next();
    }
  }

  //Adds necessary circuit cables to conduit
  public static void CS(Scanner scan, Conduit con1) {
    String cs = con1.print_cs();
    String ans = "";
    while(!(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y") || ans.equalsIgnoreCase("no") || ans.equalsIgnoreCase("n"))) {
      System.out.println("Do you have any cs cables?");
      System.out.println("Your options are\n" + cs + " ");
      System.out.println("Yes/No? ");
      ans = scan.next();
    }
    while(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y")) {
      System.out.println("What type of circuit cable do you have? ");
      String type4 = scan.next();
      while(!con1.contain_cs(type4)) {
        System.out.println("Answer not contained in list, please try again. ");
        type4 = scan.next();
      }
      System.out.println("How many of this type of circuit cable? ");
      int amount4 = scan.nextInt();
      con1.add_cs(type4,amount4);
      System.out.println("Do you have any more circuit cables?");
      System.out.println("Yes/No? ");
      ans = scan.next();
    }
  }

  //Adds any other miscellaneous wires or cables to the conduit
  public static void Misc(Scanner scan, Conduit con1) {
    String ans = "";
    while(!(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y") || ans.equalsIgnoreCase("no") || ans.equalsIgnoreCase("n"))) {
      System.out.println("Is there a wire or cable missing that you wish to add?");
      System.out.println("Yes/No? ");
      ans = scan.next();
    }
    while(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y")) {
      System.out.print("Please provide the area of this wire or cable? ");
      Double type5 = scan.nextDouble();
      con1.add(type5);
      System.out.println("Do you have any other miscellaneous wire/cables");
      System.out.println("Yes/No? ");
      ans = scan.next();
    }
  }

  public static void Lines(Scanner scan, PrintStream output, int branches) {
    System.out.println("What is the name of this branch?");
    String name = scan.next();
    System.out.println("What is the total voltage for this branch?");
    int volt = scan.nextInt();
    Line_Loss bran = new Line_Loss(volt);
    String wires = bran.print_wires();
    String ans = "";
    while(!(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y") || ans.equalsIgnoreCase("no") || ans.equalsIgnoreCase("n"))) {
      System.out.println("Do you want to add a segment to this branch?");
      ans = scan.next();
    }
    while(ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y")) {
      System.out.println("What is the length of this segment in feet?");
      int len = scan.nextInt();
      System.out.println("What is the amp load of this segment?");

      double load = scan.nextDouble();
      scan.nextLine();
      System.out.println("What type of wire is being used in your segment?");
      System.out.println("Your options are\n" + wires);
      String type = scan.nextLine();
      while(!bran.contain_wire(type)) {
        System.out.println("Answer not contained in list.");
        type = scan.nextLine();
      }
      bran.new_line(len,load,type);
      System.out.println("Do you want to add another segment?");
      ans = scan.next();
    }
    System.out.println(branches + ".  Branch " + name + " has a total voltage of " + volt + "V has " + bran.size() + " segments.");
    output.println(branches + ".  Branch " + name + " has a total voltage of " + volt + "V has " + bran.size() + " segments.");
    output.println();
    bran.loss_percentages(output);
    output.println();
  }
}
