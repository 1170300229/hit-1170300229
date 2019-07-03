package strategy;

import java.util.List;
import parts.Ladder;

/**
 * strategy for small competition file, immutable type.
 * @author 1170300229
 *
 */
public class SmallStrategy implements Strategy {
  private static int counter = 0;
  private Strategy earlyStrategy;
  private Strategy laterStrategy;
  //Abstract Function:
  //      AF(counter) = count the number of monkeys
  //      AF(earlyStrategy) = strategy in the early stage
  //      AF(laterStrategy) = strategy in the later stage
  // Representation invariant:
  //      RI(counter) = a non-negative integer
  //      RI(earlyStrategy) = a valid strategy
  //      RI(laterStrategy) = a valid strategy
  // Safety from rep exposure:

  /**
   * construct a small strategy.
   * @param ladders - ladders
   * @param MV - MV
   * @param N - N
   */
  public SmallStrategy(List<Ladder> ladders, int MV, int N) {
    earlyStrategy = new ExtremeStrategy(ladders, ladders.size(), MV, N);
    laterStrategy = new OneSideStrategy(ladders);
  }

  @Override
  public Ladder get(int v) {
    if (counter < 17) {
      Ladder ladder = earlyStrategy.get(v);
      if (v == -1 && ladder != null)
        ++counter;
      return ladder;
    }
    return laterStrategy.get(v);
  }

}
