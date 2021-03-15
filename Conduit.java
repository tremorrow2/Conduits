public Class Conduit {
    double area;

    public Conduit() {
      area = 0.00;
    }

    public void add_wires(String type, int amount) {
      double wire_area = 0;
      double total_area = 0;
      if(type.equals("14")) {
        wire_area = .023;
      } else if (type.equals("12")) {
        wire_area = .029;
      } else if (type.equals("10")) {
        wire_area = .035;
      } else if (type.equals("8")) {
        wire_area = .062;
      } else if (type.equals("6")) {
        wire_area = .081;
      } else if (type.equals("4")) {
        wire_area = .108;
      } else if (type.equals("2")) {
        wire_area = .146;
      } else if (type.equals("1")) {
        wire_area = .197;
      } else if (type.equals("1/0")) {
        wire_area = .229;
      } else if (type.equals("2/0")) {
        wire_area = .274;
      } else if (type.equals("3/0")) {
        wire_area = .322;
      } else if (type.equals("4/0")) {
        wire_area = .385;
      } else if (type.equals("250 kcmil")) {
        wire_area = .478;
      } else if (type.equals("300 kcmil")) {
        wire_area = .555;
      } else {
        wire_area = 0;
        System.out.println("Wire is not within current inventory")
      }
      total_area = wire_area * amount;
      area += total_area;
    }

    public void add_multiCable(int type, int amount, int gauge) {
      double cable_area = 0;
      double total_area = 0;

      if(guage == 14) {
        if(type == 2) {
          cable_area = .091327;
        } else if(type == 3 ) {
          cable_area = .101223;
        } else if(type == 4 ) {
          cable_area = .120072;
        } else if(type == 5 ) {
          cable_area = .14522;
        } else if(type == 6 ) {
          cable_area = .169093;
        } else if(type == 7 ) {
          cable_area = .169093;
        } else if(type == 8 ) {
          cable_area = .201886;
        } else if(type == 9 ) {
          cable_area = .256072;
        } else if(type == 10 ) {
          cable_area = .275254;
        } else if(type == 12 ) {
          cable_area = .31769;
        } else if(type == 14 ) {
          cable_area = .350464;
        } else if(type == 15 ) {
          cable_area = .389256;
        } else if(type == 16 ) {
          cable_area = .389256;
        } else if(type == 19 ) {
          cable_area = .431247;
        } else if(type == 20 ) {
          cable_area = .476612;
        } else if(type == 21 ) {
          cable_area = .476612;
        } else if(type == 25 ) {
          cable_area = 0;
          System.out.println("No cable info for this size and guage");
        } else if(type == 26 ) {
          cable_area = .672006;
        } else {
          System.out.println("No info on give cable type");
        }
      } else if (guage == 12) {
        if(type == 2) {
          cable_area = .123786;
        } else if(type == 3 ) {
          cable_area = .138544;
        } else if(type == 4 ) {
          cable_area = .165468;
        } else if(type == 5 ) {
          cable_area = .198713;
        } else if(type == 6 ) {
          cable_area = 0;
          System.out.println("No cable info for this size and guage");
        } else if(type == 7 ) {
          cable_area = .263298;
        } else if(type == 8 ) {
          cable_area = 0;
          System.out.println("No cable info for this size and guage");
        } else if(type == 9 ) {
          cable_area = .359971;
        } else if(type == 10 ) {
          cable_area = .369605;
        } else if(type == 12 ) {
          cable_area = .445328;
        } else if(type == 14 ) {
          cable_area = 0;
          System.out.println("No cable info for this size and guage");
        } else if(type == 15 ) {
          cable_area = 0;
          System.out.println("No cable info for this size and guage");
        } else if(type == 16 ) {
          cable_area = .602696;
        } else if(type == 19 ) {
          cable_area = .677831;
        } else if(type == 20 ) {
          cable_area = .748151;
        } else if(type == 21 ) {
          cable_area = .748151;
        } else if(type == 25 ) {
          cable_area = .916088;
        } else if(type == 26 ) {
          cable_area = 0;
          System.out.println("No cable info for this size and guage");
        } else {
          System.out.println("No info on give cable type");
        }

        total_area = cable_area * amount;
        area += total_area;
    }

    public void add_fiberOptic(int type, int amount) {
      double optic_area = 0;
      double total_area = 0;
      if(type % 12 == 0) {
          if(type >= 12 && type <= 72) {
            optic_area = .134215;
          } else if(type >= 84 && type <= 96) {
            optic_area = .181193;
          } else if(type >= 108 && type <= 144) {
            optic_area = .303904;
          } else if(type >= 156 && type <= 216) {
            optic_area = .311646;
          } else if(type >= 228 && type <= 280) {
            optic_area = .403241;
          } else if(type == 432) {
            optic_area = .547135;
          } else {
            optic_area = 0;
            System.out.println("No info for given Fiber Optic Cable");
          }
      } else {
        System.out.println("No info for given Fiber Optic Cable");
      }

      total_area = area * amount;
      area += total_area;
    }

    public void add_circuitCable(String type, int amount) {
      double other_area;
      double total_area;
      if(type.equals("2cs")) {
        other_area = .074991;
      } else if(type.equals("3cs")) {
        other_area = .061575;
      }

      total_area = other_area * amount;
      area += total_area;

    }

    public int size() {
      return area;
    }

    public String trade_size(String type, int schedule) {
      String trade = "";
      if(schedule == 40) {
        if(type.equalsIgnoreCase("old")) {
          if(area <= .114) {
            trade = "1/2";
          } else if(area <= .2032) {
            trade = "3/4";
          } else if(area <= .3328) {
            trade = "1";
          } else if(area <= .5812) {
            trade = "1 1/4";
          } else if(area <= .7944) {
            trade = "1 1/2";
          }  else if(area <= 1.3164) {
            trade = "2";
          }  else if(area <= 1.878) {
            trade = "2 1/2";
          }  else if(area <= 2.9072) {
            trade = "3";
          }  else if(area <= 3.8948) {
            trade = "3 1/2";
          }  else if(area <= 5.0216) {
            trade = "4";
          }  else if(area <= 7.9044) {
            trade = "5";
          }  else if(area <= 11.4268) {
            trade = "6";
          }  else {
            trade = "There is no conduit that fits this area."
          }
        } else {
          if(area <= .0741) {
            trade = "1/2";
          } else if(area <= .13208) {
            trade = "3/4";
          } else if(area <= .21632) {
            trade = "1";
          } else if(area <= .37778) {
            trade = "1 1/4";
          } else if(area <= .51636) {
            trade = "1 1/2";
          }  else if(area <= .85566) {
            trade = "2";
          }  else if(area <= 1.2207) {
            trade = "2 1/2";
          }  else if(area <= 1.88968) {
            trade = "3";
          }  else if(area <= 2.53162) {
            trade = "3 1/2";
          }  else if(area <= 3.26404) {
            trade = "4";
          }  else if(area <= 5.13786) {
            trade = "5";
          }  else if(area <= 7.42742) {
            trade = "6";
          }  else {
            trade = "There is no conduit that fits this area."
          }
        }
      } else {
        if(type.equalsIgnoreCase("old")) {
          if(area <= .0868) {
            trade = "1/2";
          } else if(area <= .1636) {
            trade = "3/4";
          } else if(area <= .2752) {
            trade = "1";
          } else if(area <= .4948) {
            trade = "1 1/4";
          } else if(area <= .6844) {
            trade = "1 1/2";
          }  else if(area <= 1.1496) {
            trade = "2";
          }  else if(area <= 1.6476) {
            trade = "2 1/2";
          }  else if(area <= 2.5768) {
            trade = "3";
          }  else if(area <= 3.4752) {
            trade = "3 1/2";
          }  else if(area <= 4.5032) {
            trade = "4";
          }  else if(area <= 7.142) {
            trade = "5";
          }  else if(area <= 10.2392) {
            trade = "6";
          }  else {
            trade = "There is no conduit that fits this area."
          }
        } else {
          if(area <= .05642) {
            trade = "1/2";
          } else if(area <= .10634) {
            trade = "3/4";
          } else if(area <= .17888) {
            trade = "1";
          } else if(area <= .32162) {
            trade = "1 1/4";
          } else if(area <= .44486) {
            trade = "1 1/2";
          }  else if(area <= .74724) {
            trade = "2";
          }  else if(area <= 1.07094) {
            trade = "2 1/2";
          }  else if(area <= 1.67492) {
            trade = "3";
          }  else if(area <= 2.25888) {
            trade = "3 1/2";
          }  else if(area <= 2.92708) {
            trade = "4";
          }  else if(area <= 4.6423) {
            trade = "5";
          }  else if(area <= 6.65548) {
            trade = "6";
          }  else {
            trade = "There is no conduit that fits this area."
          }
        }
      }
    }

}
