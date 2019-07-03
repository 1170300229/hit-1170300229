package applications;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import parts.Ladder;
import parts.Monkey;
import parts.MonkeyGenerator;
import parts.RiverPanel;
import strategy.ExtremeStrategy;
import strategy.LargeStrategy;
import strategy.SmallStrategy;
import strategy.Strategy;
import strategy.WaitStrategy;

/**
 * application of monkey crossing river simulator, mutable type.
 * @author 1170300229
 *
 */
public class Applications {
  public static int n = 5;
  public static int h = 10;
  public static int t = 1;
  public static int N = 20;
  public static int k = 5;
  public static int MV = 5;
  public static Map<Integer, Set<Monkey>> map = new TreeMap<>();
  //Abstract Function:
  //      AF(n, h, t, N, k, MV) = args to simulate 
  //      AF(n) = number of ladders
  //      AF(h) = number of foot board
  //      AF(t) = time gap
  //      AF(N) = number of monkeys
  //      AF(k) = number of monkeys per second
  //      AF(MV) = max velocity of monkey
  // Representation invariant:
  //      RI(n) = 1 <= n <= 10, integer
  //      RI(h) = 1 <= h <= 20, integer
  //      RI(t) = 1 <= t <= 5, integer
  //      RI(N) = 2 <= N <= 1000, integer
  //      RI(k) = 1 <= k <= 50, integer
  //      RI(MV) = 5 <= MV <= 10, integer
  // Safety from rep exposure:
  //      use defensive copy

  /**
   * parse the file to get args
   * configuration.txt is in /txt
   */
  public static void parseFile() {
    Pattern pattern;
    Matcher matcher;
    try {
      BufferedReader reader = new BufferedReader(new FileReader(new File("txt/configuration.txt")));
      
      pattern = Pattern.compile("n=((\\d)+)");
      matcher = pattern.matcher(reader.readLine());
      matcher.find();
      n = Integer.parseInt(matcher.group(1));
      
      pattern = Pattern.compile("h=((\\d)+)");
      matcher = pattern.matcher(reader.readLine());
      matcher.find();
      h = Integer.parseInt(matcher.group(1));
      
      pattern = Pattern.compile("t=((\\d)+)");
      matcher = pattern.matcher(reader.readLine());
      matcher.find();
      t = Integer.parseInt(matcher.group(1));

      pattern = Pattern.compile("N=((\\d)+)");
      matcher = pattern.matcher(reader.readLine());
      matcher.find();
      N = Integer.parseInt(matcher.group(1));

      pattern = Pattern.compile("k=((\\d)+)");
      matcher = pattern.matcher(reader.readLine());
      matcher.find();
      k = Integer.parseInt(matcher.group(1));

      pattern = Pattern.compile("MV=((\\d)+)");
      matcher = pattern.matcher(reader.readLine());
      matcher.find();
      MV = Integer.parseInt(matcher.group(1));
      
      reader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * normal simulate, use monkey generator
   */
  public static void normal() {
    parseFile();
    List<Ladder> list = new ArrayList<>();
    for (int i = 1;i <= n; ++i)
      list.add(new Ladder(h));

    JFrame frame = new JFrame();
    RiverPanel panel = new RiverPanel(list, h);
    
    frame.add(panel);
    frame.setSize(1800, 1000);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    MonkeyGenerator generator = new MonkeyGenerator(h, t, N, k, MV, list, panel);
    generator.run();
    
  }
  
  /**
   * competition simulate, read file
   */
  public static void competition() {
    Pattern pattern;
    Matcher matcher;
    try {
      BufferedReader reader = new BufferedReader(new FileReader(new File("txt/competition.txt")));
      String tmpString = "";
      
      pattern = Pattern.compile("n=((\\d)+)");
      matcher = pattern.matcher(reader.readLine());
      matcher.find();
      n = Integer.parseInt(matcher.group(1));
      
      pattern = Pattern.compile("h=((\\d)+)");
      matcher = pattern.matcher(reader.readLine());
      matcher.find();
      h = Integer.parseInt(matcher.group(1));

      List<Ladder> list = new ArrayList<>();
      for (int i = 1;i <= n; ++i)
        list.add(new Ladder(h));

      JFrame frame = new JFrame();
      RiverPanel panel = new RiverPanel(list, h);
      
      frame.add(panel);
      frame.setSize(1800, 1000);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      Strategy strategy = new WaitStrategy(list);
      if (n > 6)
        strategy = new LargeStrategy(list, 10, 8);
      else if (n >= 4)
        strategy = new ExtremeStrategy(list, list.size(), 5, 300);
      else
        strategy = new SmallStrategy(list, 4, 100);
      
      pattern = Pattern.compile("monkey=<(\\d+),(\\d+),((R->L)|(L->R)),(\\d+)>");
      for (; (tmpString = reader.readLine()) != null;) {
        tmpString = tmpString.replaceAll(" ", "");
        matcher = pattern.matcher(tmpString);
        matcher.find();
        
        int time = Integer.parseInt(matcher.group(1));
        int id = Integer.parseInt(matcher.group(2));
        int initFootBoard = matcher.group(4) == null ? 1 : h;
        int v = Integer.parseInt(matcher.group(6));
        v = (initFootBoard == 1 ? v : -v);
        if (!map.containsKey(time))
          map.put(time, new HashSet<Monkey>());
        map.get(time).add(new Monkey(id, initFootBoard, v, strategy, panel));
      }
      
      Iterator<Map.Entry<Integer, Set<Monkey>>> iterator = map.entrySet().iterator();
      long lastTime = 0;
      for (; iterator.hasNext();) {
        Map.Entry<Integer, Set<Monkey>> entry = iterator.next();
        long nowTime = ((long) entry.getKey()) * 1000;
        try {
          Thread.sleep(nowTime - lastTime);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        Iterator<Monkey> setIterator = entry.getValue().iterator();
        for (; setIterator.hasNext();) {
          new Thread(setIterator.next()).start();
        }
        lastTime = nowTime;
      }
      
      reader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * simulate the monkey crossing the river
   * @param args - strings
   */
  public static void main(String[] args) {
    //normal();
    competition();
  }
}
