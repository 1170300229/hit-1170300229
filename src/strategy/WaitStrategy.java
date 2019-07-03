package strategy;

import java.util.Iterator;
import java.util.List;
import parts.Ladder;

public class WaitStrategy implements Strategy{
  private final List<Ladder> ladders;
  //Abstract Function:
  //      AF(ladders) = all laders
  // Representation invariant:
  //      RI(ladders) = ladders.size() == n
  // Safety from rep exposure:
  //      use defensive copy
  
  /**
   * construct a wait strategy
   * @param inputLadders - ladders
   */
  public WaitStrategy(List<Ladder> inputLadders) {
    ladders = inputLadders;
  }

  @Override
  public Ladder get(int v) {
    //synchronized (ladders) {
      Iterator<Ladder> iterator = ladders.iterator();
      for (; iterator.hasNext();) {
        Ladder ladder = iterator.next();
        if (ladder.isEmpty())
          return ladder;
      }
    //}
    return null;
  }

}
