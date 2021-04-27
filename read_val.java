import java.io.*;
import java.util.*;
public class read_val {
  public static void main(String[] args) throws FileNotFoundException {
    Scanner scan = new Scanner(new File("val.txt"));
    LinkedHashMap<String, Double> wires = new LinkedHashMap<String, Double>() ;
    Map<Integer, LinkedHashMap<String,Double>> multicables = new TreeMap<Integer, LinkedHashMap<String,Double>>();
    Map<Integer, Double> fiberoptics = new TreeMap<Integer, Double>();
    Map<String, Double> miscs = new TreeMap<String, Double>();
    Map<Integer,Map<String,Double>> trades = new TreeMap<Integer,Map<String,Double>>();
    //Wires
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
    String wire = wires.entrySet().toString();
    System.out.println(wire);
    scan.nextLine();
    //Multicables
    line = scan.nextLine();
    while(!line.equals("FiberOptic")){
      String[] awg = line.split("=",2);
      LinkedHashMap<String, Double> cables = new LinkedHashMap<String, Double>();
      String list = awg[1].substring(1,awg[1].length()-1);
      String[] pair = list.split(", ");
      for(int i = 0; i < pair.length; i++) {
        String[]val = pair[i].split("=");
        String type = val[0];
        double area = Double.parseDouble(val[1]);
        cables.put(type,area);
      }
      int gauge = Integer.parseInt(awg[0]);
      multicables.put(gauge,cables);
      line = scan.nextLine();
    }
    String cable = multicables.entrySet().toString();
    System.out.println(cable);
    //FiberOptic
    line = scan.nextLine();
    line = line.substring(1,line.length()-1);
    pairs = line.split(", ");
    for(int i = 0; i < pairs.length; i++) {
      String[]val = pairs[i].split("=");
      int type = Integer.parseInt(val[0]);
      double area = Double.parseDouble(val[1]);
      fiberoptics.put(type,area);
    }
    String fiberoptic = fiberoptics.entrySet().toString();
    System.out.println(fiberoptic);
    scan.nextLine();
    //FiberOptic
    line = scan.nextLine();
    line = line.substring(1,line.length()-1);
    pairs = line.split(", ");
    for(int i = 0; i < pairs.length; i++) {
      String[]val = pairs[i].split("=");
      String type = val[0];
      double area = Double.parseDouble(val[1]);
      miscs.put(type,area);
    }
    String misc = miscs.entrySet().toString();
    System.out.println(misc);
    scan.nextLine();
    line = scan.nextLine();
    while(scan.hasNextLine()) {
      String[] trade = line.split("=",2);
      LinkedHashMap<String, Double> tr = new LinkedHashMap<String, Double>();
      String list = trade[1].substring(1,trade[1].length()-1);
      String[] pair = list.split(", ");
      for(int i = 0; i < pair.length; i++) {
        String[]val = pair[i].split("=");
        String type = val[0];
        double area = Double.parseDouble(val[1]);
        tr.put(type,area);
      }
      int sched = Integer.parseInt(trade[0]);
      trades.put(sched,tr);
      line = scan.nextLine();
    }
    String tra = trades.entrySet().toString();
    System.out.println(tra);
    /*
    while(scan.hasNextLine()){
      System.out.println(line);
      line = scan.nextLine();
    }
    */

  }
}
