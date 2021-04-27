import java.util.*;
import java.io.*;

public class Line_Loss {

  int tot_volt;
  int segments;
  int ft;
  Map<String, Double> wires;
  Map<Integer, Double> branch;
  Map<Integer,Integer> length;
  Map<Integer,String> type;
  Scanner scan;

  public Line_Loss(int voltage) throws FileNotFoundException {

    tot_volt = voltage;
    segments = 0;
    ft = 0;
    branch = new TreeMap<Integer,Double>();
    length = new TreeMap<Integer,Integer>();
    type = new TreeMap<Integer,String>();
    scan = new Scanner(new File("catalog2.txt"));
    create_wires();
  }

  //Creates the new line with its length and voltage loss
  public void new_line(int len, double load, String wire) {
    double line_loss = 0.0;
    double resistance = wires.get(wire);
    ft += len;
    segments++;
    line_loss = resistance * load * len * 2;
    branch.put(segments,line_loss);
    length.put(segments,len);
    type.put(segments,wire);
  }

  //Prints loss percentage for each individual line
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
    for(int i = 1; i < segments + 1; i++) {
      loss = branch.get(i);
      ln = Math.round(loss*100.0)/100.0;
      percent = (1-((tot_volt-loss)/tot_volt))*100;
      perc = Math.round(percent*100.0)/100.0;
      tot_percentage += percent;
      tot_perc = Math.round(tot_percentage*1000.0)/1000.0;
      print_value(output,i);
      print_value(output,type.get(i));
      print_value(output,length.get(i));
      print_value(output,ln);
      print_value(output,perc);
      print_value(output,tot_perc);
      output.println();

    }
    output.println("Segments:" + this.size() + "     Total Length:" + ft + "ft     Total Percentage:" + tot_perc + "%");
  }

  //Creates map for individual wire and their sizes
  public void create_wires() {
    wires = new TreeMap<String,Double>();
    wires = new LinkedHashMap<>();
    scan.nextLine();
    String line = scan.nextLine();
    line = line.substring(1,line.length()-1);
    String[]pairs = line.split(", ");
    for(int i = 0; i < pairs.length; i++) {
      String[]val = pairs[i].split("=");
      String type = val[0];
      Double area = Double.parseDouble(val[1]);
      wires.put(type,area);
    }
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

  //Number of segments
  public int size(){
    return segments;
  }

  //To print table
  private static void print_value(PrintStream output, String wd) {
    output.print(wd);
    for(int i = 0; i < 10 - wd.length(); i++) {
      output.print(" ");
    }
  }

  //To print table
  private static void print_value(PrintStream output, int wd) {
    output.print(wd);
    String w = String.valueOf(wd);
    for(int i = 0; i < 10 - w.length(); i++) {
      output.print(" ");
    }
  }

  //To print table
  private static void print_value(PrintStream output, double wd) {
    output.print(wd);
    String w = String.valueOf(wd);
    for(int i = 0; i < 10 - w.length(); i++) {
      output.print(" ");
    }
  }

}
