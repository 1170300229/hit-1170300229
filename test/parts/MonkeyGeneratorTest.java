package parts;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class MonkeyGeneratorTest {
  /*
   * Testing strategy
   *
   * Partition the inputs as follows:
   * number of ladders:
   *                ladders < 4
   *                4 <= ladders <= 6
   *                6 <= ladders <= 10
   * number of monkeys
   *                monkeys <= 50
   *                monkeys > 50
   * strategy       OneSideStrategy
   *                ExtremeStrategy 
   *                PartitionStrategy
   * 
   * Include even- and odd-length reversals because
   * only odd has a middle element that doesn't move.
   *
   * Exhaustive Cartesian coverage of partitions.
   */

  /**
   * test OneSideStrategy.
   */
  @Test
  public void testMonkeyGeneratorCase1() {
    List<Ladder> ladders = new ArrayList<>();
    for (int i = 1;i <= 3; ++i)
      ladders.add(new Ladder(20));
    RiverPanel panel = new RiverPanel(ladders, 20);
    MonkeyGenerator generator = new MonkeyGenerator(20, 1, 50, 10, 10, ladders, panel);
    generator.run();
  }
  
  /**
   * test ExtremeStrategy.
   */
  @Test
  public void testMonkeyGeneratorCase2() {
    List<Ladder> ladders = new ArrayList<>();
    for (int i = 1;i <= 10; ++i)
      ladders.add(new Ladder(10));
    RiverPanel panel = new RiverPanel(ladders, 10);
    MonkeyGenerator generator = new MonkeyGenerator(10, 1, 51, 50, 10, ladders, panel);
    generator.run();
  }
  
  /**
   * test PartitionStrategy.
   */
  @Test
  public void testMonkeyGeneratorCase3() {
    List<Ladder> ladders = new ArrayList<>();
    for (int i = 1;i <= 6; ++i)
      ladders.add(new Ladder(10));
    RiverPanel panel = new RiverPanel(ladders, 10);
    MonkeyGenerator generator = new MonkeyGenerator(10, 1, 51, 50, 10, ladders, panel);
    generator.run();
  }
}
