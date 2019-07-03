package strategy;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import parts.Ladder;

public class VelocityStrategyTest {
  /*
   * Testing strategy
   *
   * Partition the inputs as follows:
   * number of ladders      = 6
   * direction              left -> right
   *                        left <- right
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
  public void testVelocityStrategyCase1() {
    List<Ladder> ladders = new ArrayList<Ladder>();
    for (int i = 1;i <= 6; ++i)
      ladders.add(new Ladder(20));
    Strategy strategy = new VelocityStrategy(ladders);
    assertEquals(0, ladders.indexOf(strategy.get(9)));
  }
  
  /**
   * test left -> right.
   */
  @Test
  public void testVelocityStrategyCase2() {
    List<Ladder> ladders = new ArrayList<Ladder>();
    for (int i = 1;i <= 3; ++i)
      ladders.add(new Ladder(20));
    Strategy strategy = new VelocityStrategy(ladders);
    assertEquals(0, ladders.indexOf(strategy.get(9)));
    ladders.get(0).add(1, 1, 10);
    ladders.get(0).next(1, 10);
    ladders.get(1).add(2, 1, 5);
    ladders.get(1).next(2, 5);
    ladders.get(2).add(3, 1, 9);
    ladders.get(2).next(3, 9);
    assertEquals(1, ladders.indexOf(strategy.get(9)));
  }
  
  /**
   * test left <- right.
   */
  @Test
  public void testVelocityStrategyCase3() {
    List<Ladder> ladders = new ArrayList<Ladder>();
    for (int i = 1;i <= 3; ++i)
      ladders.add(new Ladder(20));
    Strategy strategy = new VelocityStrategy(ladders);
    assertEquals(0, ladders.indexOf(strategy.get(9)));
    ladders.get(0).add(1, 20, -9);
    ladders.get(0).next(1, -9);
    ladders.get(1).add(2, 20, -5);
    ladders.get(1).next(2, -5);
    ladders.get(2).add(3, 20, -10);
    ladders.get(2).next(3, -10);
    assertEquals(2, ladders.indexOf(strategy.get(-9)));
  }
}
