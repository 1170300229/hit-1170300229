package parts;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import strategy.Strategy;
import strategy.WaitStrategy;

public class MonkeyTest {
  /*
   * Testing strategy
   *
   * Partition the inputs as follows:
   * ladder         null
   *                a valid ladder
   * 
   * Include even- and odd-length reversals because
   * only odd has a middle element that doesn't move.
   *
   * Exhaustive Cartesian coverage of partitions.
   */

  /**
   * test strategy.get, panel.next.
   */
  @Test
  public void testMonkeyCase1() {
    List<Ladder> ladders = new ArrayList<>();
    for (int i = 1;i <= 3; ++i)
      ladders.add(new Ladder(10));
    Strategy strategy = new WaitStrategy(ladders);
    RiverPanel panel = new RiverPanel(ladders, 10);
    Monkey monkey = new Monkey(1, 1, 10, strategy, panel);
    new Thread(monkey).start();
    try {
      Thread.sleep(2200);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    String[] strings = new String[10];
    Monkey.main(strings);
  }
}
