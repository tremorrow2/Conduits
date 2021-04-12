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
    double allowed_area;
    double exist_area;
    LinkedHashMap<String, Double> wires;
    Map<Integer, LinkedHashMap<String,Double>> multicables;
    Map<Integer, Double> fiberoptic;
    Map<String, Double> misc;
    Map<Integer,Map<String,Double>> trades;


    //creates an empty conduit and creates the maps for the wires, multicables, fiberoptics, circuit cables and trade sizes
    public Conduit() {
      area = 0.00;
      condition = "";
      size = "";
      allowed_area = 0.0;
      exist_area = 0.0;
      sched = 0;
      create_wires();
      create_fiberoptic();
      create_multicables();
      create_misc();
      create_trades();
    }

    //For existing conduits sets the condition to "existing", sched to neccessry schedule, and fines the allowed area for given trade size
    public void existence(int sd, String sz, String con) {
      condition = con;
      size = sz;
      sched = sd;
      exist_area = trades.get(sched).get(size);
      allowed_area = exist_area * .40;

    }

    //For new conduits sets the condition to "new", and sched to neccessary schedule
    public void existence(int sd, String con) {
      condition = con;
      sched = sd;
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
      optic_area = fiberoptic.get(type);
      total_area = area * amount;
      area += total_area;
    }

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
        temp_filled = (area/exist_area);
        double scale = Math.pow(10,4);
        filled = Math.round(temp_filled * 100.00);
        area = Math.round(area * scale)/scale;
        if(temp_filled >= perc) {
            System.out.println("This " + size + " conduit is already filled to capacity with a an area of " + area + " in^2, and has " + filled + "% filled.");
            output.println("    This " + size + " conduit is already filled to capacity with a an area of " + area + " in^2,\n    and has " + filled + "% filled.");
        } else {
            System.out.println("This " + size + " conduit is not filled to capacity with a an area of " + area + " in^2, and perecent of " + filled + "% filled.");
            output.println("    This " + size + " conduit is not filled to capacity with a an area of " + area + " in^2,\n    and has " + filled + "% filled.");
        }
      } else {
        perc = 0.26;
        Map<String, Double> temp = trades.get(sched);
        Set<String> temp2 = temp.keySet();
        Iterator<String> look = temp2.iterator();
        trade = look.next();
        trade_area = temp.get(trade);
        if(area < (trades.get(sched).get("6"))*perc) {
          while(area > (trade_area * perc)) {
            trade = look.next();
            trade_area = temp.get(trade);
          }
          temp_filled = (area/trade_area);
          double scale = Math.pow(10,4);
          filled = Math.round(temp_filled * 100.00);
          area = Math.round(area * scale)/scale;
          System.out.println("The size of your conduit is a " + trade + ".");
          System.out.println("The combined area of the wires is " + area + " in^2.");
          System.out.println("The percentage filled is " + filled + "%.");
          output.println("    The size of your conduit is a " + trade + ".");
          output.println("    The combined area of the wires is " + area + " in^2.");
          output.println("    The percentage filled is " + filled + "%.");
        } else {
          output.println("    The calculated area exceeds any alllowed conduit fill under WSDOT regulations, please consider using multiple conduits.");
          System.out.println("The calculated area exceeds any alllowed conduit fill under WSDOT regulations, please consider using multiple conduits.");
        }
      }
      System.out.println();
      output.println();
    }

    //Creates map for individual wire and their sizes
    public void create_wires() {
      wires = new LinkedHashMap<>();

      wires.put("14",.023);
      wires.put("12",.029);
      wires.put("10",.035);
      wires.put("8",.062);
      wires.put("6",.081);
      wires.put("4",.108);
      wires.put("2",.146);
      wires.put("1",.197);
      wires.put("1/0",.229);
      wires.put("2/0",.274);
      wires.put("3/0",.322);
      wires.put("4/0",.385);
      wires.put("250 kcmil",.478);
      wires.put("300 kcmil",.555);
      ground_wire = wires.get("8");
    }

    //Creates maps for multicables and their sizes divided by allowed guages
    public void create_multicables() {
      LinkedHashMap<String, Double> multicables12 = new LinkedHashMap<String,Double>();
      LinkedHashMap<String, Double> multicables14 = new LinkedHashMap<String,Double>();
      LinkedHashMap<String, Double> multicables18 = new LinkedHashMap<String,Double>();
      LinkedHashMap<String, Double> multicables19 = new LinkedHashMap<String,Double>();
      LinkedHashMap<String, Double> multicables20 = new LinkedHashMap<String,Double>();
      //Map<String, Double> multicablesmisc = new TreeMap<String,Double>();
      multicables = new TreeMap<Integer,LinkedHashMap<String,Double>>();

      multicables12.put("2",.123786);
      multicables12.put("3",.138544);
      multicables12.put("4",.165468);
      multicables12.put("5",.198713);
      multicables12.put("7",.263298);
      multicables12.put("9",.359971);
      multicables12.put("10",.369605);
      multicables12.put("12",.445328);
      multicables12.put("16",.602696);
      multicables12.put("19",.677831);
      multicables12.put("20",.748151);
      multicables12.put("21",.748151);
      multicables12.put("25",.916088);

      multicables14.put("2",.091327);
      multicables14.put("3",.101223);
      multicables14.put("4",.120072);
      multicables14.put("5",.14522);
      multicables14.put("6",.169093);
      multicables14.put("7",.169093);
      multicables14.put("8",.201886);
      multicables14.put("9",.256072);
      multicables14.put("10",.275254);
      multicables14.put("12",.31769);
      multicables14.put("14",.350464);
      multicables14.put("15",.389256);
      multicables14.put("16",.389256);
      multicables14.put("19",.431247);
      multicables14.put("20",.476612);
      multicables14.put("21",.476612);
      multicables14.put("26",.672006);
      multicables14.put("2cs",.09);

      multicables18.put("4cs",.06);

      multicables19.put("6pcc",.32);

      multicables20.put("3cs",.07);

      multicables.put(12,multicables12);
      multicables.put(14,multicables14);
      multicables.put(18,multicables18);
      multicables.put(19,multicables19);
      multicables.put(20,multicables20);


    }

    //Creates map for fiberoptics and their areas
    public void create_fiberoptic(){
      fiberoptic = new TreeMap<Integer,Double>();

      fiberoptic.put(12,.134215);
      fiberoptic.put(24,.134215);
      fiberoptic.put(36,.134215);
      fiberoptic.put(48,.134215);
      fiberoptic.put(60,.134215);
      fiberoptic.put(72,.134215);
      fiberoptic.put(84,.181193);
      fiberoptic.put(96,.181193);
      fiberoptic.put(108,.303904);
      fiberoptic.put(120,.303904);
      fiberoptic.put(144,.303904);
      fiberoptic.put(156,.311646);
      fiberoptic.put(192,.311646);
      fiberoptic.put(216,.311646);
      fiberoptic.put(228,.403241);
      fiberoptic.put(240,.403241);
      fiberoptic.put(288,.403241);
      fiberoptic.put(432,.547135);
    }

    public void create_misc(){
      misc = new TreeMap<String,Double>();

      misc.put("orion",.132);
      misc.put("pull",.023);
    }

    //Creates map for trades and their areas divided by their schedule
    public void create_trades() {
      LinkedHashMap<String, Double> TradeArea40 = new LinkedHashMap<>();
      LinkedHashMap<String, Double> TradeArea80 = new LinkedHashMap<>();
      trades = new TreeMap<Integer,Map<String, Double>>();

      TradeArea40.put("1/2",.285);
      TradeArea40.put("3/4",.508);
      TradeArea40.put("1",.832);
      TradeArea40.put("1 1/4",1.453);
      TradeArea40.put("1 1/2",1.986);
      TradeArea40.put("2",3.291);
      TradeArea40.put("2 1/2",4.695);
      TradeArea40.put("3",7.268);
      TradeArea40.put("3 1/2",9.737);
      TradeArea40.put("4",12.554);
      TradeArea40.put("5",19.761);
      TradeArea40.put("6",28.567);

      TradeArea80.put("1/2",.217);
      TradeArea80.put("3/4",.409);
      TradeArea80.put("1",.688);
      TradeArea80.put("1 1/4",1.237);
      TradeArea80.put("1 1/2",1.711);
      TradeArea80.put("2",2.874);
      TradeArea80.put("2 1/2",4.119);
      TradeArea80.put("3",6.442);
      TradeArea80.put("3 1/2",8.688);
      TradeArea80.put("4",11.258);
      TradeArea80.put("5",17.855);
      TradeArea80.put("6",25.598);

      trades.put(40,TradeArea40);
      trades.put(80,TradeArea80);
    }

    //Prints allowed wires
    public String print_wires(){
      Set<String> wires2 = wires.keySet();
      return wires2.toString();
    }

    //Prints allowed multicables
    public String print_multicables(){
      Set<String> multicables2 = multicables.get(12).keySet();
      Set<String> multicables3 = multicables.get(14).keySet();
      Set<String> multicables4 = multicables.get(18).keySet();
      Set<String> multicables5 = multicables.get(19).keySet();
      Set<String> multicables6 = multicables.get(20).keySet();
      String ss = "";
      ss = "12: " + multicables2.toString() + "\n14: " + multicables3.toString() + "\n18: " + multicables4.toString() + "\n19: " + multicables5.toString() + "\n20: " + multicables6.toString();
      return ss;
    }

    //Prints allowed fiberoptics
    public String print_fiberoptics(){
      Set<Integer> fiberoptic2 = fiberoptic.keySet();
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
      return fiberoptic.containsKey(x);
    }

    //Determines if given circuit cable is listed in map
    public boolean contain_misc(String s){
      return misc.containsKey(s);
    }

    //Determines if given trade size is listed in map
    public boolean contain_trade(String s){
      return trades.get(40).containsKey(s);
    }
}
