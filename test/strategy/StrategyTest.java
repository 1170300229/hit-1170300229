package strategy;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import parts.Ladder;

public class StrategyTest {
  /*
   * Testing strategy
   *
   * Partition the inputs as follows:
   * number of ladders      = 6
   * 
   * Include even- and odd-length reversals because
   * only odd has a middle element that doesn't move.
   *
   * Exhaustive Cartesian coverage of partitions.
   */

  /**
   * test get method.
   */
  @Test
  public void testStrategyCase1() {
    List<Ladder> ladders = new ArrayList<Ladder>();
    for (int i = 1;i <= 6; ++i)
      ladders.add(new Ladder(20));
    Strategy strategy = new PartitionStrategy(ladders, 6, 10, 51, 40);
    assertEquals(2, ladders.indexOf(strategy.get(9)));
  }
}
