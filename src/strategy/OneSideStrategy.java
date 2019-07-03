package strategy;

import java.util.Iterator;
import java.util.List;
import parts.Ladder;

/**
 * choose a ladder if there is one satisfies the direction, immutable type.
 * @author 1170300229
 *
 */
public class OneSideStrategy implements Strategy{
  private final List<Ladder> ladders;
  //Abstract Function:
  //      AF(ladders) = all laders
  // Representation invariant:
  //      RI(ladders) = ladders.size() == n
  // Safety from rep exposure:
  //      use defensive copy

  /**
   * construct a one side strategy.
   * @param inputLadders - ladders
   */
  public OneSideStrategy(List<Ladder> inputLadders) {
    ladders = inputLadders;
  }

  @Override
  public Ladder get(int v) {
    Iterator<Ladder> iterator = ladders.iterator();
    for (; iterator.hasNext();) {
      Ladder ladder = iterator.next();
      if (ladder.isEmpty())
        return ladder;
      if (v > 0 && ladder.getDirection() == 0 && ladder.isPointEmpty(0))
        return ladder;
      if (v < 0 && ladder.getDirection() == 1 && ladder.isPointEmpty(1))
        return ladder;
    }
    return null;
  }
  

}
