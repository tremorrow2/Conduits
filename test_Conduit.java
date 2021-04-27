import java.io.*;
import java.util.*;
public class test_Conduit {
  public static void main(String[] args) throws FileNotFoundException {
    Conduit test = new Conduit();
    System.out.println(test.print_wires());
    System.out.println(test.print_multicables());
    System.out.println(test.print_fiberoptics());
    System.out.println(test.print_misc());
  }
}
