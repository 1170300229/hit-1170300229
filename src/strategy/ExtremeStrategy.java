package strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import parts.Ladder;

/**
 * extreme strategy, immutable type.
 * @author 1170300229
 *
 */
public class ExtremeStrategy implements Strategy{
  @SuppressWarnings("unused")
  private static int counter = 0;
  private static boolean counter1 = false;
  private static boolean counter2 = false;
  private static boolean stageTime = true;
  private final List<Ladder> ladders;
  private final Map<Integer, Integer> chanel = new HashMap<>();
  private final int n;
  @SuppressWarnings("unused")
  private final int N;
  private final int MV;
  private int[] extremeArray;
  //private final Strategy laterStrategy;
  
  //Abstract Function:
  //      AF(counter) = count the number of monkeys
  //      AF(stageTime) = if it's on early stage
  //      AF(ladders) = all laders
  //      AF(chanel) = mapping the velocity to ladders
  //      AF(n) = number of ladders
  //      AF(N) = number of monkeys
  //      AF(MV) = max velocity of monkey
  //      AF(extremeArray) = the number of velocity on ladders
  // Representation invariant:
  //      RI(counter) = a non-negative integer
  //      RI(stageTime) = a valid boolean
  //      RI(ladders) = ladders.size() == n
  //      RI(chanel) = chanel.keySet().size() == MV
  //      RI(n) = 1 <= n <= 10, integer
  //      RI(N) = 2 <= N <= 1000, integer
  //      RI(MV) = 5 <= MV <= 10, integer
  //      RI(extremeArray) = extremaArray.length == n
  // Safety from rep exposure:
  //      use defensive copy

  /**
   * construct a extreme strategy.
   * @param inputLadders - ladders
   * @param inputN - n
   * @param inputMV - MV
   * @param inputNN - N
   */
  public ExtremeStrategy(List<Ladder> inputLadders, int inputN, int inputMV, int inputNN) {
    ladders = inputLadders;
    n = inputN;
    N = inputNN;
    MV = inputMV;

    extremeArray = new int[inputN];
    for (int i = 2; i < n; ++i)
      extremeArray[i] = (MV - 1) / (n - 2);
    for (int i = 1; i <= ((MV - 1) % (n - 2)); ++i)
      extremeArray[n - i]++;
    int sum = 1;
    chanel.put(1, 0);
    chanel.put(-1, 1);
    for (int i = 2; i < n; ++i) {
      if (extremeArray[i] > 0) {
        for (int j = 1; j <= extremeArray[i]; ++j) {
          chanel.put(sum + j, i);
          chanel.put(-(sum + j), i);
        }
      }
      sum += extremeArray[i];
    }
  
    /*for (int i = 1;i <= MV; ++i)
      System.out.println(i + " + " + chanel.get(i) + " - " + chanel.get(-i));*/
    
    /*if (n < 6)
      throw new RuntimeException("should be larger or equal than 6");*/
  }
  
  /**
   * get the ladder according to ladders.
   * @param v - velocity
   * @return null means wait, or a ladder
   */
  private Ladder getEarlyStage(int v) {
    if (v == 1) {
      if (ladders.get(0).isPointEmpty(0)) {
        ++counter;
        counter1 = true;
        return ladders.get(0);
      } else
        return null;
    }
    if (v == -1) {
      if (ladders.get(1).isPointEmpty(1)) {
        ++counter;
        counter2 = true;
        return ladders.get(1);
      } else
        return null;
    }

    Ladder ladder = ladders.get(chanel.get(v));
    if (ladder.isEmpty()) {
      return ladder;
    }
    if (v > 0 && ladder.getDirection() == 0 && ladder.isPointEmpty(0)) {
      return ladder;
    }
    if (v < 0 && ladder.getDirection() == 1 && ladder.isPointEmpty(1)) {
      return ladder;
    }
    return null;
  }

  @Override
  public Ladder get(int v) {
    if (stageTime
        && ((!counter1 || !counter2) || (!ladders.get(0).isEmpty() && !ladders.get(1).isEmpty()))) {
      return getEarlyStage(v);
    } else {
      stageTime = false;
      for (int i = 0; i < n; ++i) {
        Ladder ladder = ladders.get(i);
        if (ladder.isEmpty())
          return ladder;
        if ((i == 0 || i == 1) && (ladder.getNearVelocity() == 1 || ladder.getNearVelocity() == -1))
          continue;
        if (v > 0 && ladder.getDirection() == 0 && ladder.isPointEmpty(0))
          return ladder;
        if (v < 0 && ladder.getDirection() == 1 && ladder.isPointEmpty(1))
          return ladder;
      }
      return null;
    }
  }
}
