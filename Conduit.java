import java.util.*;
public class Conduit {
    double area;
    Map<String, Double> wires;
    Map<Integer, Map<Integer,Double>> multicables;
    Map<Integer, Double> fiberoptic;
    Map<String, Double> cs;
    Map<Integer,Map<String,Double>> trades;

    public Conduit() {
      area = 0.00;
      create_wires();
      create_fiberoptic();
      create_multicables();
      create_cs();
      create_trades();
    }

    public void add_wires(String type, int amount) {
      double wire_area = 0;
      double total_area = 0;
      wire_area = wires.get(type);
      total_area = wire_area * amount;
      area += total_area;
    }

    public void add_multiCable(int type, int gauge, int amount) {
      double cable_area = 0;
      double total_area = 0;
      cable_area = multicables.get(gauge).get(type);
      total_area = cable_area * amount;
      area += total_area;
    }

    public void add_fiberOptic(int type, int amount) {
      double optic_area = 0;
      double total_area = 0;
      optic_area = fiberoptic.get(type);
      total_area = area * amount;
      area += total_area;
    }

    public void add_cs(String type, int amount) {
      double cs_area = 0;
      double total_area = 0;
      cs_area = cs.get(type);
      total_area = cs_area * amount;
      area += total_area;

    }

    public void trade_size(String type, int schedule) {
      double perc = 0.00;
      double trade_area = 0;
      double filled = 0;
      double temp_filled = 0;
      String trade = "";
      if(type.equalsIgnoreCase("old")) {
        perc = 0.40;
      } else {
        perc = 0.26;
      }
      Map<String, Double> temp = trades.get(schedule);
      Set<String> temp2 = temp.keySet();
      Iterator<String> look = temp2.iterator();
      trade = look.next();
      trade_area = temp.get(trade);
      while(area > (trade_area * perc)) {
        trade = look.next();
        trade_area = temp.get(trade);
      }
      temp_filled = (area/trade_area);
      double scale = Math.pow(10,4);
      filled = Math.round(temp_filled * 100);
      System.out.println("The mine size of your conduit is " + trade);
      System.out.println("The combined area of the wires is " + area + "in squared");
      System.out.println("The percentage filled is " + filled + "%");

    }

    public void create_wires() {
      wires = new TreeMap<String,Double>();
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
    }

    public void create_multicables() {
      Map<Integer, Double> multicables12 = new TreeMap<Integer,Double>();
      Map<Integer, Double> multicables14 = new TreeMap<Integer,Double>();
      multicables = new TreeMap<Integer,Map<Integer,Double>>();
      multicables12.put(2,.123786);
      multicables12.put(3,.138544);
      multicables12.put(4,.165468);
      multicables12.put(6,0.0);
      multicables12.put(5,.198713);
      multicables12.put(7,.263298);
      multicables12.put(8,0.0);
      multicables12.put(9,.359971);
      multicables12.put(10,.369605);
      multicables12.put(12,.445328);
      multicables12.put(14,0.0);
      multicables12.put(15,0.0);
      multicables12.put(16,.602696);
      multicables12.put(19,.677831);
      multicables12.put(20,.748151);
      multicables12.put(21,.748151);
      multicables12.put(25,.916088);
      multicables12.put(26,0.0);
      multicables14.put(2,.091327);
      multicables14.put(3,.101223);
      multicables14.put(4,.120072);
      multicables14.put(5,.14522);
      multicables14.put(6,.169093);
      multicables14.put(7,.169093);
      multicables14.put(8,.201886);
      multicables14.put(9,.256072);
      multicables14.put(10,.275254);
      multicables14.put(12,.31769);
      multicables14.put(14,.350464);
      multicables14.put(15,.389256);
      multicables14.put(16,.389256);
      multicables14.put(19,.431247);
      multicables14.put(20,.476612);
      multicables14.put(21,.476612);
      multicables14.put(25,0.0);
      multicables14.put(26,.672006);
      multicables.put(12,multicables12);
      multicables.put(14,multicables14);
    }

    public void create_trades() {

      Map<String, Double> TradeArea40 = new TreeMap<String, Double>();
      Map<String, Double> TradeArea80 = new TreeMap<String, Double>();
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

    public void create_cs(){
      cs = new TreeMap<String, Double>();
      cs.put("2cs",.074991);
      cs.put("3cs",.061575);
    }

    public String print_wires(){
      Set<String> wires2 = wires.keySet();
      return wires2.toString();
    }

    public String print_multicables(){
      Set<Integer> multicables2 = multicables.get(14).keySet();
      return multicables2.toString();
    }

    public String print_fiberoptics(){
      Set<Integer> fiberoptic2 = fiberoptic.keySet();
      return fiberoptic2.toString();
    }

    public String print_cs(){
      Set<String> cs2 = cs.keySet();
      return cs2.toString();
    }

    public boolean contain_wire(String s){
      return wires.containsKey(s);
    }

    public boolean contain_multicables(int gauge, int x){
      return multicables.get(gauge).containsKey(x);
    }

    public boolean contain_fiberoptic(int x){
      return fiberoptic.containsKey(x);
    }

    public boolean contain_cs(String s){
      return cs.containsKey(s);
    }

}
