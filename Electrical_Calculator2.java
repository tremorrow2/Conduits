import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

public class Electrical_Calculator2 {
  public static void main(String[] args) throws FileNotFoundException {
    try {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    } catch (Exception e) {
        e.printStackTrace();
    }
    Scanner scan = new Scanner(System.in);
    System.out.println("Create a name for the file that you want to export your calculations to.");
    String name = scan.nextLine();
    scan.nextLine();
    String file = name + ".txt";
    PrintStream output = new PrintStream(new File(file));
    output.println("                        " + name + " Calculations");
    SwingUtilities.invokeLater(() -> {
      Intro(output);
    });
  }

  public static void Intro(PrintStream output) {
    JFrame frame = set_frame();
    JButton CONBT = new JButton("CONDUIT");
    JButton LINBT = new JButton("LINE LOSS");

    JPanel ECControlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel ConControl = new JPanel(new FlowLayout(FlowLayout.LEFT));
    ECControlPanel.add(CONBT);
    ECControlPanel.add(LINBT);
    JPanel ECTextPanel = new JPanel();
    ECTextPanel.setLayout(new BorderLayout());
    ECTextPanel.add(ECControlPanel, BorderLayout.NORTH);

    JTextArea infoTextArea = new JTextArea();
    infoTextArea.setLineWrap(true);
    infoTextArea.setWrapStyleWord(true);
    infoTextArea.setText("What do you want to measure today?");
    infoTextArea.setBackground(new Color(241,241,241));
    infoTextArea.setEditable(false);
    infoTextArea.setMargin(new Insets(5, 5, 5,5));
    JPanel infoPanel = new JPanel(new BorderLayout());
    infoPanel.add(infoTextArea, BorderLayout.CENTER);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(infoPanel, BorderLayout.NORTH);
    mainPanel.add(ECTextPanel, BorderLayout.CENTER);
    frame.add(mainPanel, BorderLayout.CENTER);

    CONBT.addActionListener((e) -> {
      Con_frame(output);
    });
    LINBT.addActionListener((e) -> {
      Line_frame(output);
    });

  }

  public static JFrame set_frame() {
    JFrame frame = new JFrame();
    frame.setTitle("Electrical Calculator by Trevor Morrow");
    frame.setLayout(new BorderLayout());
    frame.setSize(new Dimension(800, 650));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);
    frame.setVisible(true);
    return frame;
  }

  public static void Con_frame(PrintStream output) {
    JFrame frame = set_frame();
    JTextArea nameArea = new JTextArea(2,20);
    nameArea.setLineWrap(true);
    nameArea.setWrapStyleWord(true);
    nameArea.setMargin(new Insets(5, 5, 5,5));
    JLabel nameLabel = new JLabel("Give your run name below.");
    nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
    JTextArea exArea = new JTextArea(2,20);
    exArea.setLineWrap(true);
    exArea.setWrapStyleWord(true);
    exArea.setMargin(new Insets(5, 5, 5,5));
    JLabel exLabel = new JLabel("If existing put the trade size below.");
    exLabel.setHorizontalAlignment(SwingConstants.CENTER);
    JButton NBT40 = new JButton("New 40");
    JButton EBT40 = new JButton("Existing 40");
    JButton NBT80 = new JButton("New 80");
    JButton EBT80 = new JButton("Existing 80");
    JPanel ECControlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    ECControlPanel.add(NBT40);
    ECControlPanel.add(NBT80);
    ECControlPanel.add(EBT40);
    ECControlPanel.add(EBT80);
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
    JTextArea infoTextArea = new JTextArea();
    infoTextArea.setLineWrap(true);
    infoTextArea.setWrapStyleWord(true);
    infoTextArea.setText("Give the name of the run in the box below and if it is exisiting give the exisiting size in the box below.\n Then click the button for the type of conduit you want.");
    infoTextArea.setBackground(new Color(241,241,241));
    infoTextArea.setEditable(false);
    infoTextArea.setMargin(new Insets(5, 5, 5,5));
    JPanel infoPanel = new JPanel(new BorderLayout());
    infoPanel.add(infoTextArea, BorderLayout.CENTER);
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(infoPanel, BorderLayout.NORTH);
    mainPanel.add(txtPanel, BorderLayout.CENTER);
    mainPanel.add(ECControlPanel, BorderLayout.SOUTH);
    frame.add(mainPanel, BorderLayout.CENTER);

    NBT40.addActionListener((e) -> {
      String name = nameArea.getText().trim();
      Run_frame(null,output,name,"New",40);
    });

    EBT40.addActionListener((e) -> {
      String name = nameArea.getText().trim();
      String size = exArea.getText().trim();
      Run_frame(size,output,name,"Existing",40);
    });

    NBT80.addActionListener((e) -> {
      String name = nameArea.getText().trim();
      Run_frame(null,output,name,"New",80);
    });

    EBT80.addActionListener((e) -> {
      String name = nameArea.getText().trim();
      String size = exArea.getText().trim();
      Run_frame(size,output,name,"Existing",80);
    });
  }

  public static void Run_frame(String size, PrintStream output, String name, String age, int sched) {
    JFrame frame = set_frame();
    Conduit con1 = new Conduit();

    if(age.equalsIgnoreCase("new")) {
      con1.existence(sched,age);
      output.println(age + " run " + name + " of schedule " + sched + ".");
    } else {
      output.println(age + " run " + name + " of schedule " + sched + " and initial size is a " + size + ".");
      con1.existence(sched, size, age);
    }


    JButton AddWire = new JButton("Add Wire");
    JButton AddMCable = new JButton("Add MultiCable");
    JButton AddFiberOptic = new JButton("Add FiberOptic");
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
    JPanel type = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel amount = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel awg = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel control = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel info = new JPanel(new FlowLayout(FlowLayout.LEFT));
    control.add(AddWire);
    control.add(AddMCable);
    control.add(AddFiberOptic);
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
    infoTextArea.setText("Here you will put the components into your conduit, in the type box choose a value from one of the list below and for the amount type an integer. If it is a signal cable also input the correct AWG. After you are done click the correct add buttons to add the component. Press done when you are done with this run.\nWires:\n" + con1.print_wires() + "\nSignal Cables:\n" + con1.print_multicables() + "\nFiber Optics:\n" + con1.print_fiberoptics());
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
      int g2 = Integer.parseInt(a);
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


    Done.addActionListener((e) -> {
      con1.trade_size(output);
      frame.setVisible(false);
    });
  }

  public static void Line_frame(PrintStream output){
    JFrame frame = set_frame();
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
    mainPanel.add(next, BorderLayout.SOUTH);
    frame.add(mainPanel, BorderLayout.CENTER);

    next.addActionListener((e) -> {
      String name = nameArea.getText().trim();
      String vt = exArea.getText().trim();
      int volt = Integer.parseInt(vt);
      branch_frame(output,name,volt);
    });

  }

  public static void branch_frame(PrintStream output, String name, int volt){
    JFrame frame = set_frame();
    Line_Loss bran = new Line_Loss(volt);
    JButton Add = new JButton("ADD");
    JButton Done = new JButton("DONE");
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
    JPanel type = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel length = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel amp = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel info = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel control = new JPanel(new FlowLayout(FlowLayout.LEFT));
    control.add(Add);
    control.add(Done);
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
    infoTextArea.setText("Give the type of wire, the length of the wire, and the amp load ofthis segment below.");
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
    });

    Done.addActionListener((e) -> {
      output.println("Branch " + name + " has a total voltage of " + volt + "V has " + bran.size() + " segments.");
      output.println();
      bran.loss_percentages(output);
      output.println();
      frame.setVisible(false);
    });
  }
}
