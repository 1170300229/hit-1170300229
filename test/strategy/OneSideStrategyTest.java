package strategy;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import parts.Ladder;

public class OneSideStrategyTest {
  /*
   * Testing strategy
   *
   * Partition the inputs as follows:
   * number of ladders      = 3
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
  public void testOneSideStrategyCase1() {
    List<Ladder> ladders = new ArrayList<Ladder>();
    for (int i = 1;i <= 3; ++i)
      ladders.add(new Ladder(20));
    Strategy strategy = new OneSideStrategy(ladders);
    assertEquals(0, ladders.indexOf(strategy.get(10)));
  }
}
