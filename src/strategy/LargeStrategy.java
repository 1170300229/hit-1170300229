package strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import parts.Ladder;

/**
 * strategy for large competition file, immutable type.
 * @author 1170300229
 *
 */
public class LargeStrategy implements Strategy{
  private static boolean counter1 = false;
  private static boolean counter2 = false;
  private static int counter3 = 0;
  private static int counter4 = 0;
  private static boolean stage1 = true;
  private static boolean stage2 = false;
  private static boolean stage3 = false;
  private static boolean stage4 = false;
  private final List<Ladder> ladders;
  private final Map<Integer, Integer> chanel = new HashMap<>();
  private final int n;
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
  public LargeStrategy(List<Ladder> inputLadders, int inputN, int inputMV) {
    ladders = inputLadders;
    n = inputN;
    MV = inputMV;
    
    extremeArray = new int[inputN];
    for (int i = 2;i < n; ++i)
      extremeArray[i] = (MV - 1) / (n - 2);
    for (int i = 1;i <= ((MV - 1) % (n - 2)); ++i)
      extremeArray[n - i] ++;
    int sum = 1;
    chanel.put(1, 0);
    chanel.put(-1, 1);
    for (int i = 2;i < n; ++i) {
      if (extremeArray[i] > 0) {
        for (int j = 1;j <= extremeArray[i]; ++j) {
          chanel.put(sum + j, i);
          chanel.put(-(sum + j), i);
        }
      }
      sum += extremeArray[i];
    }
  }
  
  /**
   * get the ladder according to ladders.
   * @param v - velocity
   * @return null means wait, or a ladder
   */
  private Ladder getEarlyStage(int v) {
    if (v == 1) {
      if (ladders.get(0).isPointEmpty(0)) {
        counter1 = true;
        return ladders.get(0);
      }
      else
        return null;
    }
    if (v == -1) {
      if (ladders.get(1).isPointEmpty(1)) {
        counter2 = true;
        return ladders.get(1);
      }
      else
        return null;
    }
    
    if (v == 2) {
      if (ladders.get(2).isPointEmpty(0)) {
        stage2 = true;
        ++counter3;
        return ladders.get(2);
      }
      else
        return null;
    }
    if (v == -2) {
      if (ladders.get(3).isPointEmpty(1)) {
        stage3 = true;
        ++counter4;
        return ladders.get(3);
      }
      else
        return null;
    }
    
    if (stage2 && counter3 >= 29) {
      stage2 = true;
      Ladder ladder = ladders.get(2);
      if (stage4 && ladder.isEmpty()) {
        stage1 = false;
        return ladder;
      }
      if (v > 0 && ladder.getDirection() == 0 && ladder.isPointEmpty(0)) {
        stage4 = true;
        return ladder;
      }
      if (v < 0 && ladder.getDirection() == 1 && ladder.isPointEmpty(1)) {
        return ladder;
      }
    }
    if (stage3 && counter4 >= 35) {
      stage3 = true;
      Ladder ladder = ladders.get(3);
      if (v > 0 && ladder.getDirection() == 0 && ladder.isPointEmpty(0)) {
        return ladder;
      }
      if (v < 0 && ladder.getDirection() == 1 && ladder.isPointEmpty(1)) {
        return ladder;
      }
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
    if (stage1
        && ((!counter1 || !counter2) || (!ladders.get(0).isEmpty() && !ladders.get(1).isEmpty()))) {
      return getEarlyStage(v);
    }
    else {
      stage1 = false;
      for (int i = n - 1;i >= 0; --i) {
        Ladder ladder = ladders.get(i);
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

}
