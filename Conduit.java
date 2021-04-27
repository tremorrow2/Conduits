import java.util.*;
import java.io.*;
/*Trevor Morrow
This class creates the Conduit object, the object contains multiple maps to store sizes of wires,
multicables, fiberoptic cables and circuit cables, and trade sizes. The object keeps track of the total area
used based on the wires/cables added to the object and calculates the minimum size allowed for a new conduit,
or tells the user if it exceeds the standard limit for an existing conduit of that size*/

public class Conduit {
    double area;
    double ground_wire;
    String condition;
    String size;
    int sched;
    String name;
    //double allowed_area;
    double exist_area;
    LinkedHashMap<String, Double> wires;
    Map<Integer, LinkedHashMap<String,Double>> multicables;
    Map<Integer, Double> fiberoptics;
    Map<String, Double> misc;
    Map<Integer,Map<String,Double>> trades;
    Scanner scan;


    //creates an empty conduit and creates the maps for the wires, multicables, fiberoptics, circuit cables and trade sizes
    public Conduit() throws FileNotFoundException {
      area = 0.00;
      condition = "";
      size = "";
      name = "";
      exist_area = 0.0;
      sched = 0;
      scan = new Scanner(new File("val.txt"));
      create_wires();
      create_multicables();
      create_fiberoptic();
      create_misc();
      create_trades();
    }

    //For existing conduits sets the condition to "existing", sched to neccessry schedule, and fines the allowed area for given trade size
    public void existence(String nm, int sd, String sz, String con) {
      condition = con;
      size = sz;
      sched = sd;
      exist_area = trades.get(sched).get(size);
      name = nm;
    }

    //Adds total wire area to area of conduit
    public void add_wires(String type, int amount) {
      double wire_area = 0;
      double total_area = 0;
      wire_area = wires.get(type);
      if(wire_area>ground_wire) {
        ground_wire = wire_area;
      }
      total_area = wire_area * amount;
      area += total_area;
    }

    //Adds total multicable area to area of conduit
    public void add_multiCable(String type, int gauge, int amount) {
      double cable_area = 0;
      double total_area = 0;
      cable_area = multicables.get(gauge).get(type);
      total_area = cable_area * amount;
      area += total_area;
    }

    //Adds total fiberoptic area to area of conduit
    public void add_fiberOptic(int type, int amount) {
      double optic_area = 0;
      double total_area = 0;
      optic_area = fiberoptics.get(type);
      total_area = area * amount;
      area += total_area;
    }

    //Adds miscellaneous wires
    public void add_misc(String type, int amount) {
      double misc_area = 0;
      double total_area = 0;
      misc_area = misc.get(type);
      total_area = misc_area * amount;
      area += total_area;
    }

    //Adds miscellaneous area to area of conduit
    public void add(Double dub) {
      area += dub;
    }

    //Outputs the minimum trade size allowed for new conduit or outputs if the conduit is filled to capacity if existing
    public void trade_size(PrintStream output) {
      double perc = 0.00;
      double trade_area = 0;
      double filled = 0;
      double temp_filled = 0;
      double filled2 = 0;
      double temp_filled2 = 0;
      String trade = "";
      area+=ground_wire;
      if(condition.equalsIgnoreCase("existing")) {
        perc = .40;
        Map<String, Double> temp = trades.get(sched);
        Set<String> temp2 = temp.keySet();
        Iterator<String> look = temp2.iterator();
        trade = look.next();
        trade_area = temp.get(trade);
        temp_filled2 = (area/exist_area)*100;
        double scale = Math.pow(10,2);
        filled2 = Math.round(temp_filled2 * 100.0)/100.0;
        area = Math.round(area * scale)/scale;
        print_value(output,name);
        print_value(output,condition);
        print_value(output,sched);
        print_value(output,size);
        print_value(output,area);
        print_value(output,filled2);
        print_value(output,"-");
        output.print("-");
      } else {
        perc = 0.26;
        Map<String, Double> temp = trades.get(sched);
        Set<String> temp2 = temp.keySet();
        Iterator<String> look = temp2.iterator();
        trade = look.next();
        trade_area = temp.get(trade);
        temp_filled2 = (area/exist_area)*100;
        double scale = Math.pow(10,2);
        filled2 = Math.round(temp_filled2 * 100.0)/100.0;
        area = Math.round(area * scale)/scale;
        while(area > (trade_area * perc)) {
          trade = look.next();
          trade_area = temp.get(trade);
        }
        temp_filled = (area/trade_area)*100;
        filled = Math.round(temp_filled * 100.0)/100.0;
        print_value(output,name);
        print_value(output,condition);
        print_value(output,sched);
        print_value(output,size);
        print_value(output,area);
        print_value(output,filled2);
        print_value(output,trade);
        output.print(filled);
      }
      System.out.println();
      output.println();
    }

    //Creates map for individual wire and their sizes
    public void create_wires() {
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
      scan.nextLine();
      ground_wire = wires.get("8");
      System.err.println("Passed Wire");
    }

    //Creates maps for multicables and their sizes divided by allowed guages
    public void create_multicables() {
      multicables = new TreeMap<Integer,LinkedHashMap<String,Double>>();
      String line = scan.nextLine();
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
      System.err.println("Passed Multicable");
    }

    //Creates map for fiberoptics and their areas
    public void create_fiberoptic(){
      fiberoptics = new TreeMap<Integer,Double>();
      String line = scan.nextLine();
      line = line.substring(1,line.length()-1);
      String[] pairs = line.split(", ");
      for(int i = 0; i < pairs.length; i++) {
        String[]val = pairs[i].split("=");
        int type = Integer.parseInt(val[0]);
        double area = Double.parseDouble(val[1]);
        fiberoptics.put(type,area);
      }
      scan.nextLine();
      System.err.println("Passed FiberOptic");
    }

    //Creates miscellaneous items, is subject to change based on needs
    public void create_misc(){
      misc = new TreeMap<String,Double>();
      String line = scan.nextLine();
      line = line.substring(1,line.length()-1);
      String[] pairs = line.split(", ");
      for(int i = 0; i < pairs.length; i++) {
        String[]val = pairs[i].split("=");
        String type = val[0];
        double area = Double.parseDouble(val[1]);
        misc.put(type,area);
      }
      scan.nextLine();
      System.err.println("Passed Misc");
    }

    //Creates map for trades and their areas divided by their schedule
    public void create_trades() {
      trades = new TreeMap<Integer,Map<String, Double>>();
      String line = scan.nextLine();
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
      System.err.println("Passed Trades");
    }

    //Prints allowed wires
    public String print_wires(){
      Set<String> wires2 = wires.keySet();
      return wires2.toString();
    }

    //Prints allowed multicables
    public String print_multicables(){
      Set<Integer> gauges = multicables.keySet();
      Iterator<Integer> it = gauges.iterator();
      String ss = "";
      while (it.hasNext()) {
        int look = it.next();
        ss += look + ": " + multicables.get(look).keySet().toString();
        if(it.hasNext()){
          ss += "\n";
        }
      }
      return ss;
    }

    //Prints allowed fiberoptics
    public String print_fiberoptics(){
      Set<Integer> fiberoptic2 = fiberoptics.keySet();
      return fiberoptic2.toString();
    }

    //Prints allowed circuit cables
    public String print_misc(){
      Set<String> cs2 = misc.keySet();
      return cs2.toString();
    }

    //Determines if given wire is listed in map
    public boolean contain_wire(String s){
      return wires.containsKey(s);
    }

    //Determines if given multicable is listed in map
    public boolean contain_multicables(int gauge, String x){
      return multicables.get(gauge).containsKey(x);
    }

    //Determines if given fiberoptic is listed in map
    public boolean contain_fiberoptic(int x){
      return fiberoptics.containsKey(x);
    }

    //Determines if given circuit cable is listed in map
    public boolean contain_misc(String s){
      return misc.containsKey(s);
    }

    //Determines if given trade size is listed in map
    public boolean contain_trade(String s){
      return trades.get(40).containsKey(s);
    }

    private static void print_value(PrintStream output, String wd) {
      output.print(wd);
      for(int i = 0; i < 10 - wd.length(); i++) {
        output.print(" ");
      }
    }

    private static void print_value(PrintStream output, int wd) {
      output.print(wd);
      String w = String.valueOf(wd);
      for(int i = 0; i < 10 - w.length(); i++) {
        output.print(" ");
      }
    }

    private static void print_value(PrintStream output, double wd) {
      output.print(wd);
      String w = String.valueOf(wd);
      for(int i = 0; i < 10 - w.length(); i++) {
        output.print(" ");
      }
    }
}
