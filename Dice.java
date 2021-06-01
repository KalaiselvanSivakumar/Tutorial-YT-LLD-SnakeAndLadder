public class Dice {
  private int numberOfDices;

  Dice(int numberOfDices) {
    this.numberOfDices = numberOfDices;
  }

  int rollDice() {
    // Check this - Logic is wrong
    // TODO: See java.util.Random.nextInt() method
    return ((int) (
      Math.random() * ((6 - 1) * numberOfDices))
    ) + 1 * numberOfDices;
  }
}
