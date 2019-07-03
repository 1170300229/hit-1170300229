package strategy;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import parts.Ladder;

public class ExtremeStrategyTest {
  /*
   * Testing strategy
   *
   * Partition the inputs as follows:
   * number of ladders      = 8
   * 
   * Include even- and odd-length reversals because
   * only odd has a middle element that doesn't move.
   *
   * Exhaustive Cartesian coverage of partitions.
   */

  /**
   * test get method
   */
  @Test
  public void testExtremeStrategyCase1() {
    List<Ladder> ladders = new ArrayList<Ladder>();
    for (int i = 1;i <= 8; ++i)
      ladders.add(new Ladder(20));
    Strategy strategy = new ExtremeStrategy(ladders, 8, 10, 50);
    assertEquals(7, ladders.indexOf(strategy.get(10)));
  }
}
