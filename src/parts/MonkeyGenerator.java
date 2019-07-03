package parts;

import java.util.List;
import java.util.Random;
import strategy.ExtremeStrategy;
import strategy.OneSideStrategy;
import strategy.PartitionStrategy;
import strategy.Strategy;
import strategy.VelocityStrategy;
import strategy.WaitStrategy;

/**
 * a monkey generator, immutable type.
 * @author 1170300229
 *
 */
public class MonkeyGenerator {
  private final int n;
  private final int h;
  private final int t;
  private final int N;
  private final int k;
  private final int MV;
  private final List<Ladder> ladders;
  private final RiverPanel panel;
  //Abstract Function:
  //      AF(n, h, t, N, k, MV) = args to simulate 
  //      AF(n) = number of ladders
  //      AF(h) = number of foot board
  //      AF(t) = time gap
  //      AF(N) = number of monkeys
  //      AF(k) = number of monkeys per second
  //      AF(MV) = max velocity of monkey
  //      AF(ladders) = all laders
  //      AF(panel) = a river panel
  // Representation invariant:
  //      RI(n) = 1 <= n <= 10, integer
  //      RI(h) = 1 <= h <= 20, integer
  //      RI(t) = 1 <= t <= 5, integer
  //      RI(N) = 2 <= N <= 1000, integer
  //      RI(k) = 1 <= k <= 50, integer
  //      RI(MV) = 5 <= MV <= 10, integer
  //      RI(ladders) = ladders.size() == n
  //      RI(panel) = a valid river panel
  // Safety from rep exposure:
  //      use defensive copy

  /**
   * construct a monkey generator.
   * @param inputH - h
   * @param inputT - t
   * @param inputN - N
   * @param inputK - k
   * @param inputMV - MV
   * @param inputLadders - ladders
   * @param inputPanel - a river panel
   */
  public MonkeyGenerator(int inputH, int inputT, int inputN, int inputK, int inputMV,
      List<Ladder> inputLadders, RiverPanel inputPanel) {
    n = inputLadders.size();
    h = inputH;
    t = inputT;
    N = inputN;
    k = inputK;
    MV = inputMV;
    ladders = inputLadders;
    panel = inputPanel;
  }
  
  /**
   * run the monkey generator.
   */
  public void run() {
    Strategy strategy = new WaitStrategy(ladders);
    //Strategy strategy = new OneSideStrategy(ladders);
    //Strategy strategy = new PartitionStrategy(ladders, ladders.size(), MV, N, N * 4 / 5);
    //Strategy strategy = new VelocityStrategy(ladders);
    //Strategy strategy = new ExtremeStrategy(ladders, ladders.size(), MV, N);
    
    if (N <= 50)
      strategy = new OneSideStrategy(ladders);
    else if (n > 6 && MV >= n - 1)
      strategy = new ExtremeStrategy(ladders, ladders.size(), MV, N);
    else if (n >= 4)
      strategy = new PartitionStrategy(ladders, ladders.size(), MV, N, N * 4 / 5);
    else
      strategy = new VelocityStrategy(ladders);
    
    int number = 0;
    Random random = new Random();
    
    for (; number <= N;) {
      for (int i = 1;i <= k; ++i) {
        ++number;
        if (number > N) {
          break ;
        }
        int v = random.nextInt(MV) + 1;
        int direction = random.nextInt(2);
        v = (direction == 0 ? v : -v);
        direction = (direction == 0 ? 1 : h);
        new Thread(new Monkey(number, direction, v, strategy, panel)).start();
      }
      
      try {
        Thread.sleep(t * 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
