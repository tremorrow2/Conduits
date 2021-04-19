import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.*;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;


/*
  The CFLL or Conduit Fill and Line Loss calculator was developed to help in calculations for Traffic Engineering projects.
  The Conduit Fill section allows the user to calculate the percentage filled for a new or exisitng Conduit and if the conduit is new it
  will give the minimum sized conduit allowed for the given area, this is purely for user to know the most cost effective size they can have.
  The Line Loss sections allows the user to calculate percentage loss for indvidual branches.
  This tool also prints out all the information calculated into a text file that can be used in future reports or designs and makes all the
  information easily accessible.
*/

public class CFLL{
  public static File file;
  //Main Function sets the initial UI look and starts the program
  public static void main(String[] args) throws FileNotFoundException {
    setNimbus();
    PrintStream output;
    SwingUtilities.invokeLater(() -> {
      Intro();
    });
  }

  //Prompts the user to create their new file
  public static void Intro() {
    JFrame frame = set_small_frame(800,100);
    JButton SUB = new JButton("Create your file");
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(SUB, BorderLayout.NORTH);
    frame.add(mainPanel, BorderLayout.CENTER);
    SUB.addActionListener((e) -> {
      get_file();
      String name = file.getName();
      name = name.substring(0,name.length()-4);
      try
      {
        PrintStream  output = new PrintStream(file);
        FirstQ(name,output);
        frame.setVisible(false);
      }
      catch(FileNotFoundException b)
      {
        b.printStackTrace();
      }
    });
  }

  //Ask the user if they want to calculate conduit sizing
  public static void FirstQ(String name,PrintStream output) {

    String date = get_date();
    output.println("                                                            " + date);
    output.println("                            " + name + " Calculations");
    output.println();
    JFrame frame = set_small_frame(800,150);
    JButton YESBT = new JButton("YES");
    JButton NOBT = new JButton("NO");
    JPanel ECControlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel ConControl = new JPanel(new FlowLayout(FlowLayout.CENTER));
    ECControlPanel.add(YESBT);
    ECControlPanel.add(NOBT);
    JPanel ECTextPanel = new JPanel();
    ECTextPanel.setLayout(new BorderLayout());
    ECTextPanel.add(ECControlPanel, BorderLayout.NORTH);
    JTextArea infoTextArea = new JTextArea();
    infoTextArea.setLineWrap(true);
    infoTextArea.setWrapStyleWord(true);
    infoTextArea.setText("Do you want to measure conduit sizes?");
    infoTextArea.setBackground(new Color(241,241,241));
    infoTextArea.setEditable(false);
    infoTextArea.setMargin(new Insets(5, 5, 5,5));
    JPanel infoPanel = new JPanel(new BorderLayout());
    infoPanel.add(infoTextArea, BorderLayout.CENTER);
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(infoPanel, BorderLayout.NORTH);
    mainPanel.add(ECTextPanel, BorderLayout.CENTER);
    frame.add(mainPanel, BorderLayout.CENTER);

    YESBT.addActionListener((e) -> {
      output.println("CONDUIT SIZING");
      output.println();
      output.println("Run       New/Exis  Sched     Size(in)  Area(in2) Fill(%)   Min(in)   MFill(%)");

      Con_frame(output);
      frame.setVisible(false);
    });

    NOBT.addActionListener((e) -> {
      LineQ_frame(output);
      frame.setVisible(false);
    });

  }

  //Has the user name the run and select the type of run it is.
  public static void Con_frame(PrintStream output) {

    JFrame frame = set_small_frame(800,200);
    JTextArea nameArea = new JTextArea(2,20);
    nameArea.setLineWrap(true);
    nameArea.setWrapStyleWord(true);
    nameArea.setMargin(new Insets(5, 5, 5,5));
    JLabel nameLabel = new JLabel("Give your run name below.");
    nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
    JButton NBT40 = new JButton("New 40");
    JButton EBT40 = new JButton("Existing 40");
    JButton NBT80 = new JButton("New 80");
    JButton EBT80 = new JButton("Existing 80");
    JButton Done = new JButton("Done");
    JPanel ECControlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    ECControlPanel.add(NBT40);
    ECControlPanel.add(NBT80);
    ECControlPanel.add(EBT40);
    ECControlPanel.add(EBT80);
    ECControlPanel.add(Done);
    JPanel ECTextPanel = new JPanel();
    ECTextPanel.setLayout(new BorderLayout());
    ECTextPanel.add(nameLabel, BorderLayout.NORTH);
    ECTextPanel.add(nameArea, BorderLayout.CENTER);
    JTextArea infoTextArea = new JTextArea();
    infoTextArea.setLineWrap(true);
    infoTextArea.setWrapStyleWord(true);
    infoTextArea.setText("Give the name of the run in the box below then select the type of trade it is.");
    infoTextArea.setBackground(new Color(241,241,241));
    infoTextArea.setEditable(false);
    infoTextArea.setMargin(new Insets(5, 5, 5,5));
    JPanel infoPanel = new JPanel(new BorderLayout());
    infoPanel.add(infoTextArea, BorderLayout.CENTER);
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(infoPanel, BorderLayout.NORTH);
    mainPanel.add(ECTextPanel, BorderLayout.CENTER);
    mainPanel.add(ECControlPanel, BorderLayout.SOUTH);
    frame.add(mainPanel, BorderLayout.CENTER);

    NBT40.addActionListener((e) -> {
      String name = nameArea.getText().trim();
      Size_frame(output,name,"New",40);
      frame.setVisible(false);
    });

    EBT40.addActionListener((e) -> {
      String name = nameArea.getText().trim();
      Size_frame(output,name,"Existing",40);
      frame.setVisible(false);
    });

    NBT80.addActionListener((e) -> {
      String name = nameArea.getText().trim();
      Size_frame(output,name,"New",80);
      frame.setVisible(false);
    });

    EBT80.addActionListener((e) -> {
      String name = nameArea.getText().trim();
      Size_frame(output,name,"Existing",80);
      frame.setVisible(false);
    });

    Done.addActionListener((e) -> {
      LineQ_frame(output);
      frame.setVisible(false);
    });
  }

  //Prompts the user to select a desired size conduit for calculations
  public static void Size_frame(PrintStream output, String name, String age, int sched) {

    JFrame frame = set_small_frame(800,150);
    JButton One = new JButton("1");
    JButton OneQuat = new JButton("1 1/4");
    JButton OneHalf = new JButton("1 1/2");
    JButton Two = new JButton("2");
    JButton TwoHalf = new JButton("2 1/2");
    JButton Three = new JButton("3");
    JButton ThreeHalf = new JButton("3 1/2");
    JButton Four = new JButton("4");
    JPanel ControlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    ControlPanel.add(One);
    ControlPanel.add(OneQuat);
    ControlPanel.add(OneHalf);
    ControlPanel.add(Two);
    ControlPanel.add(TwoHalf);
    ControlPanel.add(Three);
    ControlPanel.add(ThreeHalf);
    ControlPanel.add(Four);
    JTextArea infoTextArea = new JTextArea();
    infoTextArea.setLineWrap(true);
    infoTextArea.setWrapStyleWord(true);
    infoTextArea.setText("Pick your desired Trade Size");
    infoTextArea.setBackground(new Color(241,241,241));
    infoTextArea.setEditable(false);
    infoTextArea.setMargin(new Insets(5, 5, 5,5));
    JPanel infoPanel = new JPanel(new BorderLayout());
    infoPanel.add(infoTextArea, BorderLayout.CENTER);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(infoPanel, BorderLayout.NORTH);
    mainPanel.add(ControlPanel, BorderLayout.CENTER);
    frame.add(mainPanel, BorderLayout.CENTER);

    One.addActionListener((e) -> {
      Run_frame("1",output,name,age,sched);
      frame.setVisible(false);
    });

    OneQuat.addActionListener((e) -> {
      Run_frame("1 1/4",output,name,age,sched);
      frame.setVisible(false);
    });

    OneHalf.addActionListener((e) -> {
      Run_frame("1 1/2",output,name,age,sched);
      frame.setVisible(false);
    });

    Two.addActionListener((e) -> {
      Run_frame("2",output,name,age,sched);
      frame.setVisible(false);
    });

    TwoHalf.addActionListener((e) -> {
      Run_frame("2 1/2",output,name,age,sched);
      frame.setVisible(false);
    });

    Three.addActionListener((e) -> {
      Run_frame("3",output,name,age,sched);
      frame.setVisible(false);
    });

    ThreeHalf.addActionListener((e) -> {
      Run_frame("3 1/2",output,name,age,sched);
      frame.setVisible(false);
    });

    Four.addActionListener((e) -> {
      Run_frame("4",output,name,age,sched);
      frame.setVisible(false);
    });


  }

  //This allows user to add components(Wire, Signal Cables, Fiber Optic, Miscellaneous) to the conduit
  public static void Run_frame(String size, PrintStream output, String name, String age, int sched) {

    JFrame frame = set_small_frame(800,550);
    Conduit con1 = new Conduit();

    if(age.equalsIgnoreCase("new")) {
      con1.existence(name,sched,size,age);
      //output.println("    The run labled " + name + " is a proposed new run with a schedule of " + sched + " \n    and proposed size of " + size + " in.");
    } else {
      //output.println("    The run labled " + name + " is an existing run with a schedule of " + sched + " \n     and its initial size is " + size + " in.");
      con1.existence(name,sched, size, age);
    }


    JButton AddWire = new JButton("Add Wire");
    JButton AddMCable = new JButton("Add Signal Cable");
    JButton AddFiberOptic = new JButton("Add FiberOptic");
    JButton AddMisc = new JButton("Add Misc");
    JButton Done = new JButton("Done");
    JTextArea typeArea = new JTextArea(2,20);
    typeArea.setLineWrap(true);
    typeArea.setWrapStyleWord(true);
    typeArea.setMargin(new Insets(5, 5, 5,5));
    JLabel typeLabel = new JLabel("Type");
    typeLabel.setHorizontalAlignment(SwingConstants.CENTER);
    JTextArea amountArea = new JTextArea(2,20);
    amountArea.setLineWrap(true);
    amountArea.setWrapStyleWord(true);
    amountArea.setMargin(new Insets(5, 5, 5,5));
    JLabel amountLabel = new JLabel("Amount");
    amountLabel.setHorizontalAlignment(SwingConstants.CENTER);
    JTextArea awgArea = new JTextArea(2,20);
    awgArea.setLineWrap(true);
    awgArea.setWrapStyleWord(true);
    awgArea.setMargin(new Insets(5, 5, 5,5));
    JLabel awgLabel = new JLabel("AWG");
    awgLabel.setHorizontalAlignment(SwingConstants.CENTER);
    JPanel type = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel amount = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel awg = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel control = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel info = new JPanel(new FlowLayout(FlowLayout.CENTER));
    control.add(AddWire);
    control.add(AddMCable);
    control.add(AddFiberOptic);
    control.add(AddMisc);
    control.add(Done);
    type.add(typeLabel, BorderLayout.NORTH);
    type.add(typeArea,BorderLayout.CENTER);
    amount.add(amountLabel, BorderLayout.NORTH);
    amount.add(amountArea,BorderLayout.CENTER);
    awg.add(awgLabel, BorderLayout.NORTH);
    awg.add(awgArea,BorderLayout.CENTER);
    info.add(type,BorderLayout.NORTH);
    info.add(amount,BorderLayout.CENTER);
    info.add(awg,BorderLayout.SOUTH);
    JTextArea infoTextArea = new JTextArea();
    infoTextArea.setLineWrap(true);
    infoTextArea.setWrapStyleWord(true);
    infoTextArea.setText("Here you will put the components into your conduit, in the type box choose a value from one of the list below and for the amount type an integer. If it is a signal cable also input the correct AWG. After you are done click the correct add buttons to add the component. Press done when you are done with this run.\n\nElectrical Power Wires:\n" + con1.print_wires() + "\n\nSignal Cables:\n" + con1.print_multicables() + "\n\nFiber Optics:\n" + con1.print_fiberoptics() + "\n\nMiscellaneous:\n" + con1.print_misc());
    infoTextArea.setBackground(new Color(241,241,241));
    infoTextArea.setEditable(false);
    infoTextArea.setMargin(new Insets(5, 5, 5,5));
    JPanel infoPanel = new JPanel(new BorderLayout());
    infoPanel.add(infoTextArea, BorderLayout.CENTER);
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(infoPanel, BorderLayout.NORTH);
    mainPanel.add(info, BorderLayout.CENTER);
    mainPanel.add(control, BorderLayout.SOUTH);
    frame.add(mainPanel, BorderLayout.CENTER);

    AddWire.addActionListener((e) -> {
      String w = typeArea.getText().trim();
      String a = amountArea.getText().trim();
      int a2 = Integer.parseInt(a);
      con1.add_wires(w,a2);
      typeArea.setText(null);
      amountArea.setText(null);
      awgArea.setText(null);
    });

    AddMCable.addActionListener((e) -> {
      String w = typeArea.getText().trim();
      String a = amountArea.getText().trim();
      int a2 = Integer.parseInt(a);
      String g = awgArea.getText().trim();
      int g2 = Integer.parseInt(g);
      con1.add_multiCable(w,g2,a2);
      typeArea.setText(null);
      amountArea.setText(null);
      awgArea.setText(null);
    });

    AddFiberOptic.addActionListener((e) -> {
      String w = typeArea.getText().trim();
      int w2 = Integer.parseInt(w);
      String a = amountArea.getText().trim();
      int a2 = Integer.parseInt(a);
      con1.add_fiberOptic(w2,a2);
      typeArea.setText(null);
      amountArea.setText(null);
      awgArea.setText(null);
    });

    AddMisc.addActionListener((e) -> {
      String w = typeArea.getText().trim();
      String a = amountArea.getText().trim();
      int a2 = Integer.parseInt(a);
      con1.add_misc(w,a2);
      typeArea.setText(null);
      amountArea.setText(null);
      awgArea.setText(null);
    });

    Done.addActionListener((e) -> {
      con1.trade_size(output);
      frame.setVisible(false);
      Con_frame(output);
    });
  }

  //Ask the user if they want to calculate Line Loss
  public static void LineQ_frame(PrintStream output) {

    JFrame frame = set_small_frame(800,150);
    JButton YESBT = new JButton("YES");
    JButton NOBT = new JButton("NO");
    JPanel ECControlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel ConControl = new JPanel(new FlowLayout(FlowLayout.CENTER));
    ECControlPanel.add(YESBT);
    ECControlPanel.add(NOBT);
    JPanel ECTextPanel = new JPanel();
    ECTextPanel.setLayout(new BorderLayout());
    ECTextPanel.add(ECControlPanel, BorderLayout.NORTH);
    JTextArea infoTextArea = new JTextArea();
    infoTextArea.setLineWrap(true);
    infoTextArea.setWrapStyleWord(true);
    infoTextArea.setText("Do you want to measure line loss?");
    infoTextArea.setBackground(new Color(241,241,241));
    infoTextArea.setEditable(false);
    infoTextArea.setMargin(new Insets(5, 5, 5,5));
    JPanel infoPanel = new JPanel(new BorderLayout());
    infoPanel.add(infoTextArea, BorderLayout.CENTER);
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(infoPanel, BorderLayout.NORTH);
    mainPanel.add(ECTextPanel, BorderLayout.CENTER);
    frame.add(mainPanel, BorderLayout.CENTER);

    YESBT.addActionListener((e) -> {
      output.println("LINE LOSS");
      output.println();
      Branch_frame(output);
      frame.setVisible(false);
    });

    NOBT.addActionListener((e) -> {
      openFile();
      System.exit(0);
    });

  }

  //Prompts the user to give the name of the branch and the total voltage for this branch
  public static void Branch_frame(PrintStream output) {

    JFrame frame = set_small_frame(800,275);
    JTextArea nameArea = new JTextArea(2,20);
    nameArea.setLineWrap(true);
    nameArea.setWrapStyleWord(true);
    nameArea.setMargin(new Insets(5, 5, 5,5));
    JLabel nameLabel = new JLabel("Give the branch name below.");
    nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
    JTextArea exArea = new JTextArea(2,20);
    exArea.setLineWrap(true);
    exArea.setWrapStyleWord(true);
    exArea.setMargin(new Insets(5, 5, 5,5));
    JLabel exLabel = new JLabel("Put the total voltage of the brach below.");
    exLabel.setHorizontalAlignment(SwingConstants.CENTER);
    JButton next = new JButton("NEXT");
    JButton Done = new JButton("DONE");
    JPanel Panel = new JPanel();
    Panel.setLayout(new BorderLayout());
    Panel.add(nameLabel, BorderLayout.NORTH);
    Panel.add(nameArea, BorderLayout.CENTER);
    JPanel ECTextPanel = new JPanel();
    ECTextPanel.setLayout(new BorderLayout());
    ECTextPanel.add(nameLabel, BorderLayout.NORTH);
    ECTextPanel.add(nameArea, BorderLayout.CENTER);
    JPanel exTextPanel = new JPanel();
    exTextPanel.setLayout(new BorderLayout());
    exTextPanel.add(exLabel, BorderLayout.NORTH);
    exTextPanel.add(exArea, BorderLayout.CENTER);
    JPanel txtPanel = new JPanel();
    txtPanel.setLayout(new BorderLayout());
    txtPanel.add(ECTextPanel, BorderLayout.NORTH);
    txtPanel.add(exTextPanel, BorderLayout.CENTER);
    JPanel control = new JPanel(new FlowLayout(FlowLayout.CENTER));
    control.add(next);
    control.add(Done);
    JTextArea infoTextArea = new JTextArea();
    infoTextArea.setLineWrap(true);
    infoTextArea.setWrapStyleWord(true);
    infoTextArea.setText("Give the name of the branch in the box below and the total voltage of the branch in the box below that.\n Then click the next button.");
    infoTextArea.setBackground(new Color(241,241,241));
    infoTextArea.setEditable(false);
    infoTextArea.setMargin(new Insets(5, 5, 5,5));
    JPanel infoPanel = new JPanel(new BorderLayout());
    infoPanel.add(infoTextArea, BorderLayout.CENTER);
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(infoPanel, BorderLayout.NORTH);
    mainPanel.add(txtPanel, BorderLayout.CENTER);
    mainPanel.add(control, BorderLayout.SOUTH);
    frame.add(mainPanel, BorderLayout.CENTER);

    next.addActionListener((e) -> {
      String name = nameArea.getText().trim();
      String vt = exArea.getText().trim();
      int volt = Integer.parseInt(vt);
      Seg_frame(output,name,volt);
      frame.setVisible(false);
    });

    Done.addActionListener((e) -> {
      openFile();
      System.exit(0);
    });

  }

  //Allows the user to add segments to the branch
  public static void Seg_frame(PrintStream output, String name, int volt) {

    JFrame frame = set_small_frame(800,300);
    Line_Loss bran = new Line_Loss(volt);
    JButton Add = new JButton("ADD");
    JButton Next = new JButton("DONE");
    JTextArea typeArea = new JTextArea(2,20);
    typeArea.setLineWrap(true);
    typeArea.setWrapStyleWord(true);
    typeArea.setMargin(new Insets(5, 5, 5,5));
    JLabel typeLabel = new JLabel("Type");
    typeLabel.setHorizontalAlignment(SwingConstants.CENTER);
    JTextArea lengthArea = new JTextArea(2,20);
    lengthArea.setLineWrap(true);
    lengthArea.setWrapStyleWord(true);
    lengthArea.setMargin(new Insets(5, 5, 5,5));
    JLabel lengthLabel = new JLabel("Length");
    lengthLabel.setHorizontalAlignment(SwingConstants.CENTER);
    JTextArea ampArea = new JTextArea(2,20);
    ampArea.setLineWrap(true);
    ampArea.setWrapStyleWord(true);
    ampArea.setMargin(new Insets(5, 5, 5,5));
    JLabel ampLabel = new JLabel("AMP LOAD");
    ampLabel.setHorizontalAlignment(SwingConstants.CENTER);
    JPanel type = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel length = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel amp = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel info = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel control = new JPanel(new FlowLayout(FlowLayout.CENTER));
    control.add(Add);
    control.add(Next);
    type.add(typeLabel, BorderLayout.NORTH);
    type.add(typeArea,BorderLayout.CENTER);
    length.add(lengthLabel, BorderLayout.NORTH);
    length.add(lengthArea,BorderLayout.CENTER);
    amp.add(ampLabel, BorderLayout.NORTH);
    amp.add(ampArea,BorderLayout.CENTER);
    info.add(type,BorderLayout.NORTH);
    info.add(length,BorderLayout.CENTER);
    info.add(amp,BorderLayout.SOUTH);
    JTextArea infoTextArea = new JTextArea();
    infoTextArea.setLineWrap(true);
    infoTextArea.setWrapStyleWord(true);
    infoTextArea.setText("Give the type of wire, the length of the wire, and the amp load of this segment below.");
    infoTextArea.setBackground(new Color(241,241,241));
    infoTextArea.setEditable(false);
    infoTextArea.setMargin(new Insets(5, 5, 5,5));
    JPanel infoPanel = new JPanel(new BorderLayout());
    infoPanel.add(infoTextArea, BorderLayout.CENTER);
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(infoPanel, BorderLayout.NORTH);
    mainPanel.add(info, BorderLayout.CENTER);
    mainPanel.add(control, BorderLayout.SOUTH);
    frame.add(mainPanel, BorderLayout.CENTER);

    Add.addActionListener((e) -> {
      String ln = lengthArea.getText().trim();
      String ld = ampArea.getText().trim();
      String tp = typeArea.getText().trim();
      int len = Integer.parseInt(ln);
      double lod = Double.parseDouble(ld);
      bran.new_line(len,lod,tp);
      lengthArea.setText(null);
      ampArea.setText(null);
    });

    Next.addActionListener((e) -> {
      output.println("    Branch " + name + " has a total voltage of " + volt + "V has " + bran.size() + " segments.");
      output.println();
      bran.loss_percentages(output);
      output.println();
      Branch_frame(output);
      frame.setVisible(false);
    });
  }

  //Sets the frame size and information
  public static JFrame set_small_frame(int x, int y) {
    JFrame frame = new JFrame();
    BufferedImage img = null;
    try {
      img = ImageIO.read(new File("LOGO ONLY PNG.png"));
    } catch (IOException e) {
    }
    frame.setTitle("PH Consulting Conduit Fill and Line Loss Calculator (Proprietary PH Consulting use only)");
    frame.setLayout(new BorderLayout());
    JLabel rules = new JLabel(" Unlicensed use prohibited, contact admin@phtraffic.com for licensing");
    frame.add(rules,BorderLayout.PAGE_END);
    frame.setSize(new Dimension(x, y));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);
    frame.setIconImage(img);
    frame.setVisible(true);
    return frame;
  }

  //Gets current date
  public static String get_date() {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    return dtf.format(now);
  }

  //Allows user to pick a save spot for their file
  public static void get_file(){
    setWindows();
    PrintStream output;
    JFileChooser chooser = new JFileChooser();
		chooser.showSaveDialog(null);
		file = chooser.getSelectedFile();
    file = new File(file.getPath()+".txt");
    setNimbus();
  }

  //Sets look of UI
  public static void setNimbus() {
    try {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    } catch (Exception e) {
        e.printStackTrace();
    }
  }

  //Sets look of UI
  public static void setWindows() {
    try {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    } catch (Exception e) {
        e.printStackTrace();
    }
  }

  public static void openFile() {
    try
    {
      //constructor of file class having file as argument

      if(!Desktop.isDesktopSupported())//check if Desktop is supported by Platform or not
      {
        System.out.println("not supported");
        return;
      }
      Desktop desktop = Desktop.getDesktop();
      if(file.exists())
      //setupPrinter();        //checks file exists or not
      desktop.open(file);              //opens the specified file
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  /*
  public static void setupPrinter() throws Exception {
    PrinterJob pjob = PrinterJob.getPrinterJob();
    PageFormat pf = pjob.defaultPage();
    pjob.setPrintable(null, pf);
    pjob.printDialog();
    /*
    if (pjob.printDialog()) {
      pjob.print();
    }

  }*/
}
