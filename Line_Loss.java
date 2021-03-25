import java.util.*;
import java.io.*;

public class Line_Loss {

  int tot_volt;
  int segments;
  Map<String, Double> wires;
  Map<String, Double> branch;
  public Line_Loss(int voltage) {

    tot_volt = voltage;
    segments = 0;
    branch = new TreeMap<String,Double>();
    create_wires();
  }

  public void new_line(String seg, int len, double load, String wire) {
    double line_loss = 0.0;
    double resistance = branch.get(wire);
    line_loss = resistance * load * len;
    branch.put(seg,line_loss);
    segment++;
  }

  public void loss_percentages() {
    double tot_percentage = 00.0;
    double percent = 0.00;
    double loss = 0;
    String line = "";
    Set<String> temp = branch.keySet();
    Iterator<String> look = temp.iterator();
    line = look.next();
    while(line != null) {
      loss = branch.get(line);
      percent = 1-((tot_volt-loss)/tot_volt);
      tot_percent += percent;
      System.out.println("Segment " + line + " has a line loss of " + loss + " for a loss percentage of " + percent + "%.");
    }
    System.out.println("The total percentage loss for this branch is " + tot_percent + "%.");
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

}
