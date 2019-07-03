package parts;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import strategy.Strategy;
import strategy.WaitStrategy;

/**
 * monkey, immutable type.
 * @author 1170300229
 *
 */
public class Monkey implements Runnable {
  private final int id;
  private final int initFootBoard;
  private final int v;
  private final Strategy strategy;
  private final RiverPanel panel;
  private Ladder ladder;
  //Abstract Function:
  //      AF(id) = the id of the monkey 
  //      AF(initFootBoard) = the initial foot board it is on
  //      AF(v) = the velocity of the monkey
  //      AF(strategy) = the ladder choosing strategy
  //      AF(panel) = a river panel
  //      AF(ladder) = the ladder it is on
  // Representation invariant:
  //      RI(id) = 1 <= id, integer
  //      RI(initFootBoard) = 1 <= initFootBoard <= 20, integer
  //      RI(v) = 1 <= v <= 10, integer
  //      RI(strategy) = a valid strategy
  //      RI(panel) = a valid panel
  //      RI(ladder) = null means waiting, or means the ladder it is on
  // Safety from rep exposure:
  //      use defensive copy

  /**
   * construct a monkey.
   * @param intputID - id
   * @param inputInitFootBorad - initial foot board
   * @param inputV - velocity
   * @param inputStrategy - strategy
   * @param inputPanel - river panel
   */
  public Monkey(int intputID, int inputInitFootBorad, int inputV, Strategy inputStrategy,
      RiverPanel inputPanel) {
    id = intputID;
    initFootBoard = inputInitFootBorad;
    v = inputV;
    strategy = inputStrategy;
    panel = inputPanel;
    ladder = null;
  }

  @Override
  public void run() {
    int cnt = 0;
    for (;; ++cnt) {
      if (ladder == null) {
        try {
          Thread.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        Log.logger.info("Monkey " + id + " : [Wait] " + " Time " + cnt + "s");
        synchronized (strategy) {
          ladder = strategy.get(v);
          if (ladder != null) {
            ladder.add(id, initFootBoard, v);
            panel.add(id, ladder, initFootBoard);
          }
        }
      } else {
        Log.logger.info("Monkey " + id + " : [Move] " + " Time " + cnt + "s");
        if (!panel.mov(id, v)) {
          Log.logger.info("Monkey " + id + " : [Arrive] " + " Time " + cnt + "s");
          break;
        }
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * test monkey.
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
    
    Strategy strategy = new WaitStrategy(list);
    
    for (int i = 1; i <= 2000; ++i) {
      if ((i & 1) == 1) {
        new Thread(new Monkey(i, ladderNumber, -((i % 5) + 1), strategy, panel)).start();
      } else {
        new Thread(new Monkey(i, 1, ((i % 5) + 1), strategy, panel)).start();
      }
    }
  }
}
