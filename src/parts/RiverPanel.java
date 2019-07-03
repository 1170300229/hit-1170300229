package parts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * a panel simulate the whole structure, mutable type.
 * @author 1170300229
 *
 */
public class RiverPanel extends JPanel {
  private static final long serialVersionUID = 1L;
  
  private final int ladderX0 = 450;
  private final int ladderY0;
  private final int ladderNumber;
  private final int ladderGap;
  private final int footBoardNumber;
  private final int footBoardLength = 800;
  private final int footBoardGap;
  
  private final Image image = Toolkit.getDefaultToolkit().getImage("pic/monkey.png");
  private final Map<Integer, Position> objMap = new HashMap<>();
  private final Map<Ladder, Position> ladderMap = new HashMap<Ladder, Position>();
  private final Map<Integer, Ladder> objLadder = new HashMap<Integer, Ladder>();
  private final Map<Integer, Long> startMap = new HashMap<Integer, Long>();
  private final Map<Integer, Long> endMap = new HashMap<Integer, Long>();
  
  private final int frameNumber = 50;
  private final long time;
  private int totalNumber = 0;
  //Abstract Function:
  //      AF(ladderX0) = x coordinate of ladders 
  //      AF(ladderY0) = y coordinate of ladders
  //      AF(ladderNumber) = number of ladders
  //      AF(ladderGap) = gap distance between ladders
  //      AF(footBoardNumber) = number of foot board
  //      AF(footBoardLength) = length of foot board 
  //      AF(footBoardGap) = gap distance of foot board
  //      AF(image) = a image file of monkey
  //      AF(objMap) = mapping the monkey to their positions
  //      AF(ladderMap) = mapping the ladder to their positions
  //      AF(objLadder) = mapping the monkey to the ladders their belong to
  //      AF(startMap) = mapping the monkey to the second they enter
  //      AF(endMap) = mapping the monkey to the second they finish
  //      AF(frameNumber) = number of frames
  //      AF(time) = the beginning time
  //      AF(totalNumber) = a counter count the number of monkeys
  // Representation invariant:
  //      RI(ladderX0) = a positive integer 
  //      RI(ladderY0) = a positive integer
  //      RI(ladderNumber) = 1 <= ladder number <= 10, integer
  //      RI(ladderGap) = a positive integer
  //      RI(footBoardNumber) = 1 <= foot board number <= 20, integer
  //      RI(footBoardLength) = a positive integer
  //      RI(footBoardGap) = a positive integer
  //      RI(image) = a valid image file
  //      RI(objMap) = objMap.keySet() == the set of monkeys
  //      RI(ladderMap) = ladderMap.keySet() == the set of ladders
  //      RI(objLadder) = objLadder.keySet() == the set of monkeys
  //      RI(startMap) = startMap.keySet() == the set of monkeys
  //      RI(endMap) = endMap.keySet() == the set of monkeys
  //      RI(frameNumber) = a positive integer
  //      RI(time) = a positive integer
  //      RI(totalNumber) = a positive integer
  // Safety from rep exposure:
  //      use defensive copy
  
  /**
   * construct a river panel.
   * @param list - the list of ladders
   * @param length - the number of foot boards
   */
  public RiverPanel(List<Ladder> list, int length) {
    ladderNumber = list.size();
    ladderY0 = 450 - 40 * ladderNumber;
    ladderGap = 150 - 6 * ladderNumber;
    footBoardNumber = length;
    footBoardGap = footBoardLength / (length + 1);
    time = new Date().getTime();
    
    for (int i = 0; i < ladderNumber; ++i) {
      int nowY0 = ladderY0 + i * ladderGap;
      ladderMap.put(list.get(i), new Position(ladderX0, nowY0));
    }
  }
  
  /**
   * draw back ground.
   * @param g - graphics
   */
  private void drawBackGround(Graphics g) {
    ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, 1800, 1000);
    g.drawImage(image, 100, 400, this);
    g.drawImage(image, 1600, 400, this);
    g.setColor(Color.BLACK);
    g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
    double seconds = (double) ((new Date().getTime()) - time) / 1000.0;
    String throughput = String.format("%.4f", ((double) totalNumber) / seconds);
    g.drawString("Throughput " + throughput + " per sec", 50, 100);
    synchronized (startMap) {
      if (totalNumber == startMap.keySet().size()) {
        int totalFairness = 0;
        for (int i = 1;i < totalNumber; ++i)
          for (int j = i + 1; j <= totalNumber; ++j) {
            // if (endMap.get(j) != null && endMap.get(i) != null)
            if (startMap.get(j) == null)
              System.out.println("start map " + j);
            if (startMap.get(i) == null)
              System.out.println("start map " + i);
            if (endMap.get(j) == null)
              System.out.println("end map " + j);
            if (endMap.get(i) == null)
              System.out.println("end map " + i);
            totalFairness +=
                ((startMap.get(j) - startMap.get(i)) * (endMap.get(j) - endMap.get(i)) >= 0 ? 1
                    : -1);
          }
        String fairnessString = String.format("%.4f",
            (((double) totalFairness) * 2.0 / ((double) (totalNumber * (totalNumber - 1)))));
        g.drawString("Fairness: " + fairnessString, 50, 120);
      }      
    }
  }
  
  /**
   * draw ladders.
   * @param g - graphics
   */
  private void drawLadders(Graphics g) {
    g.setColor(new Color(64, 96, 152));
    for (int i = 0; i < ladderNumber; ++i) {
      ((Graphics2D) g).setStroke(new BasicStroke(3.0f));
      int nowY0 = ladderY0 + i * ladderGap;
      g.drawLine(ladderX0, nowY0, ladderX0 + footBoardLength, nowY0);
      int nowY1 = nowY0 + (ladderGap * 5 / 9);
      g.drawLine(ladderX0, nowY1, ladderX0 + footBoardLength, nowY1);
      ((Graphics2D) g).setStroke(new BasicStroke(1.0f));
      for (int j = 1;j <= footBoardNumber; ++j)
        g.drawLine(ladderX0 + j * footBoardGap, nowY0, ladderX0 + j * footBoardGap, nowY1);
    }
  }
  
  /**
   * draw monkeys.
   * @param g - graphics
   */
  private void drawObj(Graphics g) {
    synchronized (objMap) {
      Iterator<Map.Entry<Integer, Position>> iterator = objMap.entrySet().iterator();
      for (; iterator.hasNext();) {
        Position po = null;
        /*try {
          po = iterator.next().getValue();
        } catch (Exception e) {
          e.printStackTrace();
        }*/
        po = iterator.next().getValue();
        g.drawImage(image, po.getX(), po.getY(), this);
      }
    }
  }
  
  /**
   * a animation of update position.
   * @param id - the id of monkey
   * @param x0 - the initial x coordinate
   * @param y0 - the initial y coordinate
   * @param x1 - the aim x coordinate
   * @param y1 - the aim y coordinate
   */
  private void updPosition(int id, int x0, int y0, int x1, int y1) {
    new Thread(new Runnable() {
      
      @Override
      public void run() {
        int xGap = (x1 - x0) / frameNumber;
        int yGap = (y1 - y0) / frameNumber;
        int xmod = (x1 - x0) % frameNumber;
        int ymod = (y1 - y0) % frameNumber;
        //System.out.println("x mod " + xmod + ", y mod " + ymod);
        xmod = (xmod < 0 ? -xmod : xmod);
        ymod = (ymod < 0 ? -ymod : ymod);
        int xRemander = (x1 - x0) >= 0 ? 1 : -1;
        int yRemander = (y1 - y0) >= 0 ? 1 : -1;
        //System.out.println("x mod " + xmod + ", y mod " + ymod);
        int x2 = x0;
        int y2 = y0;
        for (int i = 1;i <= frameNumber; ++i) {
          try {
            Thread.sleep(500 / frameNumber);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          x2 += xGap;
          y2 += yGap;
          if (i <= xmod)
            x2 += xRemander;
          if (i <= ymod)
            y2 += yRemander;
          synchronized (objMap) {
            objMap.put(id, new Position(x2, y2));
          }
          repaint();
        }
        //System.out.println((objMap.get(id).getX() == x1) + "," + (objMap.get(id).getY() == y1));
      }
    }).start();
  }
  
  /**
   * get the position of foot board on one ladder.
   * @param ladder - ladder
   * @param footBorad - foot board number
   * @return the position of foot board on one ladder
   */
  private Position getPosition(Ladder ladder, int footBorad) {
    Position po = ladderMap.get(ladder);
    return new Position(po.getX() + footBorad * footBoardGap - 30, po.getY() + 10);
  }
  
  /**
   * add a monkey to river panel.
   * @param id - the id of monkey, should not exist in this river panel
   * @param ladder - the ladder the monkey belongs to, should exist in this river panel
   * @param footBorad - the initial foot board the monkey is on
   */
  public void add(int id, Ladder ladder, int footBorad) {
    Position po = getPosition(ladder, footBorad);
    objLadder.put(id, ladder);
    //ladder.add(id, footBorad);
    synchronized (startMap) {
      startMap.put(id, new Date().getTime());
    }
    if (footBorad == 1)
      updPosition(id, 100, 400, po.getX(), po.getY());
    else
      updPosition(id, 1600, 400, po.getX(), po.getY());
  }
  
  /**
   * remove a monkey from river panel.
   * @param id - the id of the monkey
   * @param v - velocity
   */
  private void rem(int id, int v) {
    Position nowPosition = null;
    synchronized (objMap) {
      nowPosition = objMap.get(id);
    }
    synchronized (startMap) {
      endMap.put(id, new Date().getTime());
      ++ totalNumber;
    }
    if (v > 0) {
      updPosition(id, nowPosition.getX(), nowPosition.getY(), 1600, 400);
    } else {
      updPosition(id, nowPosition.getX(), nowPosition.getY(), 100, 400);
    }
    /*synchronized (objMap) {
      objMap.remove(id);
    }*/
    objLadder.remove(id);
  }
  
  /**
   * move a monkey.
   * @param id - the id of the monkey
   * @param v - velocity
   * @return if the monkey finishes
   */
  public boolean mov(int id, int v) {
    Ladder ladder = objLadder.get(id);
    int nextFootBorad = ladder.next(id, v);
    if (nextFootBorad > footBoardNumber || nextFootBorad < 1) {
      rem(id, v);
      return false;
    }
    else {
      Position po = getPosition(ladder, nextFootBorad);
      Position nowPosition = null;
      synchronized (objMap) {
        nowPosition = objMap.get(id);
      }
      if (nowPosition == null)
        System.out.println("now position id " + id);
      updPosition(id, nowPosition.getX(), nowPosition.getY(), po.getX(), po.getY());
      return true;
    }
  }
  
  @Override
  public void paint(Graphics g) {
    drawBackGround(g);
    drawLadders(g);
    drawObj(g);
  }
  
  /**
   * test river panel
   * @param args - strings
   */
  public static void main(String[] args) {
    int ladderNumber = 20;
    List<Ladder> list = new ArrayList<Ladder>();
    Ladder[] ladders = new Ladder[11];
    for (int i = 0;i <= 10; ++i)
      ladders[i] = new Ladder(ladderNumber);
    for (int i = 1;i <= 10; ++i)
      list.add(ladders[i]);
    JFrame frame = new JFrame();
    RiverPanel panel = new RiverPanel(list, ladderNumber);
    
    frame.add(panel);
    frame.setSize(1800, 1000);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    /*panel.add(1, ladders[1], 1);
    panel.add(2, ladders[2], ladderNumber);
    panel.add(3, ladders[3], 1);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    panel.mov(2, -1);
    panel.mov(1, 3);
    panel.mov(3, 10);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    panel.mov(2, -4);
    panel.mov(1, 3);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    panel.mov(2, -6);
    panel.mov(1, 5);*/
    for (int i = 1;i <= 10; ++i)
      panel.add(i, ladders[i], 1);
    for (int j = 1; j <= 20; ++j) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      for (int k = 0;k < 20; ++k) {
        if (k < j)
          for (int i = 1;i <= 10; ++i)
            panel.mov(i + k * 10, 1);
        else if (k == j)
          for (int i = 1;i <= 10; ++i)
            panel.add(i + k * 10, ladders[i], 1);
      }
    }
  }
}

class Position {
  int x, y;
  public Position(int X, int Y) {
    x = X;
    y = Y;
  }
  public int getX() {
    return x;
  }
  public int getY() {
    return y;
  }
  
  @Override
  public boolean equals(Object arg0) {
    if (!(arg0 instanceof Position))
      return false;
    Position arg1 = (Position) arg0;
    return x == arg1.getX() && y == arg1.getY();
  }
  
  @Override
  public int hashCode() {
    return x * 100000 + y;
  }
}