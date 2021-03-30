import java.util.*;
import java.io.*;

public class Line_Loss {

  int tot_volt;
  int segments;
  Map<String, Double> wires;
  Map<Integer, Double> branch;
  public Line_Loss(int voltage) {

    tot_volt = voltage;
    segments = 0;
    branch = new TreeMap<Integer,Double>();
    create_wires();
  }

  public void new_line(int len, double load, String wire) {
    double line_loss = 0.0;
    double resistance = wires.get(wire);
    segments++;
    line_loss = resistance * load * len * 2;
    branch.put(segments,line_loss);

  }

  public void loss_percentages(PrintStream output) {
    double tot_percentage = 0.00;
    double loss = 0;
    double percent = 0.00;
    double perc = 0.0;
    double ln = 0.0;
    double tot_perc = 0.0;
    int line = 0;
    Set<Integer> temp = branch.keySet();
    Iterator<Integer> look = temp.iterator();
    line = look.next();
    while(look.hasNext()) {
      loss = branch.get(line);
      ln = Math.round(loss*100.0)/100.0;
      percent = (1-((tot_volt-loss)/tot_volt))*100;
      perc = Math.round(percent*100.0)/100.0;
      tot_percentage += percent;
      tot_perc = Math.round(tot_percentage*1000.0)/1000.0;
      System.out.println("    Segment #" + line + " has a line loss of " + ln + " for a loss percentage of " + perc + "% and a cumulative drop percentage of " + tot_perc + "%.");
      output.println("    Segment #" + line + " has a line loss of " + ln + " for a loss percentage of " + perc + "%\n    and a cumulative drop percentage of " + tot_perc + "%.");
      line = look.next();
    }
    loss = branch.get(line);
    ln = Math.round(loss*100.0)/100.0;
    percent = (1-((tot_volt-loss)/tot_volt))*100;
    perc = Math.round(percent*100.0)/100.0;
    tot_percentage += percent;
    tot_perc = Math.round(tot_percentage*1000.0)/1000.0;
    System.out.println("    Segment #" + line + " has a line loss of " + ln + " for a loss percentage of " + perc + "% and a cumulative drop percentage of " + tot_perc + "%.");
    output.println("    Segment #" + line + " has a line loss of " + ln + " for a loss percentage of " + perc + "%\n    and a cumulative drop percentage of " + tot_perc + "%.");
    System.out.println("    The total percentage loss for this branch is " + tot_perc + "%.");
    output.println("    The total percentage loss for this branch is " + tot_perc + "%.");
  }

  //Creates map for individual wire and their sizes
  public void create_wires() {
    wires = new TreeMap<String,Double>();
    wires.put("14",.00326);
    wires.put("12",.00205);
    wires.put("10",.00129);
    wires.put("8",.000809);
    wires.put("6",.00051);
    wires.put("4",.000321);
    wires.put("2",.000201);
    wires.put("1",.00016);
    wires.put("1/0",.000127);
    wires.put("2/0",.000101);
    wires.put("3/0",.0000797);
    wires.put("4/0",.0000626);
    wires.put("250 kcmil",.0000535);
    wires.put("300 kcmil",.0000446);

  }

  //Prints allowed wires
  public String print_wires(){
    Set<String> wires2 = wires.keySet();
    return wires2.toString();
  }

  //Determines if given wire is listed in map
  public boolean contain_wire(String s){
    return wires.containsKey(s);
  }

  public int size(){
    return segments;
  }

  /*
  public double print_segs(int line,double tot, PrintStream output) {
   double tot_percentage = 0.00;
   double percent = 0.00;

   String perc = "";
   String ln = "";
   String tot_perc = "";
   loss = branch.get(line);
   ln = String.format("%.2g%n",loss);
   percent = (1-((tot_volt-loss)/tot_volt))*100;
   perc = String.format("%.2g%n",percent);
   tot_percentage += percent;
   tot_perc = String.format("%.2g%n",tot_percentage);
   System.out.println("Segment #" + line + " has a line loss of " + ln + " for a loss percentage of " + perc + "% and a cummalative drop percentage of " + tot_perc + "%.");
   output.println("Segment #" + line + " has a line loss of " + ln + " for a loss percentage of " + perc + "% and a cummalative drop percentage of " + tot_perc + "%.");
   return tot_percentage;

  }*/

}
