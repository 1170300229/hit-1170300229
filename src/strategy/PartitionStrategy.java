package strategy;

import java.util.List;
import parts.Ladder;

/**
 * choose a ladder by partition of the velocity, immutable type.
 * @author 1170300229
 *
 */
public class PartitionStrategy implements Strategy {
  private static int counter = 0;
  private final List<Ladder> ladders;
  private final int n;
  @SuppressWarnings("unused")
  private final int N;
  private final int partitionN;
  private final int MV;
  private final int leftNumber;
  private final int rightNumber;
  private final Strategy laterStrategy;
  
  //Abstract Function:
  //      AF(counter) = count the number of monkeys
  //      AF(ladders) = all laders
  //      AF(n) = number of ladders
  //      AF(N) = number of monkeys
  //      AF(partitionN) = the partition number
  //      AF(MV) = max velocity of monkey
  //      AF(leftNumber) = the number of ladders from left to right
  //      AF(rightNumber) = the number of ladders from right to left
  //      AF(laterStrategy) = the strategy in the latter stage
  // Representation invariant:
  //      RI(counter) = a non-negative integer
  //      RI(ladders) = ladders.size() == n
  //      RI(n) = 1 <= n <= 10, integer
  //      RI(N) = 2 <= N <= 1000, integer
  //      RI(partitionN) = 0 <= partition number <= N, integer
  //      RI(MV) = 5 <= MV <= 10, integer
  //      RI(leftNumber) = 1 <= leftNumber <= n, integer
  //      RI(rightNumber) = 1 <= rightNumber <= n, integer
  //      RI(laterStrategy) = a valid strategy
  // Safety from rep exposure:
  //      use defensive copy

  /**
   * construct a partition strategy.
   * @param inputLadders - ladders
   * @param inputN - n
   * @param inputMV - MV
   * @param inputNN - N
   * @param inputPartitionN - partition number
   */
  public PartitionStrategy(List<Ladder> inputLadders, int inputN, int inputMV, int inputNN,
      int inputPartitionN) {
    ladders = inputLadders;
    n = inputN;
    N = inputNN;
    partitionN = inputPartitionN;
    MV = inputMV;
    leftNumber = n / 2;
    rightNumber = n - n / 2;
    laterStrategy = new OneSideStrategy(inputLadders);
    /*if (n < 4)
      throw new RuntimeException("should be larger or equal than 4");*/
  }

  /**
   * get the ladder in the early stage.
   * @param v
   * @return
   */
  private Ladder getEarlyStage(int v) {
    if (v > 0) {
      int partitionNumber = MV / leftNumber;
      for (int i = 1; i <= leftNumber; ++i)
        if (v > partitionNumber * (i - 1) && v <= partitionNumber * i) {
          if (ladders.get(i - 1).isPointEmpty(0)) {
            counter ++;
            return ladders.get(i - 1);
          }
          break;
        }
      if (v > partitionNumber * leftNumber) {
        if (ladders.get(leftNumber - 1).isPointEmpty(0)) {
          counter ++;
          return ladders.get(leftNumber - 1);
        }
      }
    } else {
      v = -v;
      int partitionNumber = MV / rightNumber;
      for (int i = 1; i <= rightNumber; ++i)
        if (v > partitionNumber * (i - 1) && v <= partitionNumber * i) {
          if (ladders.get(leftNumber + i - 1).isPointEmpty(1)) {
            counter++;
            return ladders.get(leftNumber + i - 1);
          }
          break;
        }
      if (v > partitionNumber * rightNumber) {
        if (ladders.get(leftNumber + rightNumber - 1).isPointEmpty(1)) {
          counter ++;
          return ladders.get(leftNumber + rightNumber - 1);
        }
      }
    }
    return null;
  }
  
  @Override
  public Ladder get(int v) {
    if (counter <= partitionN) {
      return getEarlyStage(v);
    }
    else {
      return laterStrategy.get(v);
    }
  }

}
