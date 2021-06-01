import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class GameBoard {
  private Dice dice;
  // For deciding the order of play.
  // Once a player finishes his turn, he will be added at the last.
  private Queue<Player> playersQueue;
  private List<Jumper> snakes;
  private List<Jumper> ladders;
  // Contains the current position of the player in the board
  private Map<Player, Integer> playersPosition;
  int boardSize;

  GameBoard(Dice dice, Queue<Player> playersQueue, List<Jumper> snakes, List<Jumper> ladders, int boardSize) {
    this.dice = dice;
    this.playersQueue = playersQueue;
    this.snakes = snakes;
    this.ladders = ladders;
    this.boardSize = boardSize;
    this.playersPosition = new HashMap<>();

    // Handle players empty case
    Player firstPlayer = playersQueue.remove();
    this.playersPosition.put(firstPlayer, 0);
    playersQueue.add(firstPlayer);
    // Setting initial position for all the players as 0 or it can be 1
    // Error handling is needed
    while(!playersQueue.peek().equals(firstPlayer)) {
      Player player = playersQueue.remove();
      this.playersPosition.put(player, 0);
      playersQueue.add(player);
    }
  }

  // Refractor this method so that cognitive complexity of it comes below 15.
  public void startGame() {
    // To get onbly one winner, break this after one player won.
    while(playersQueue.size() > 1) {
      Player player = playersQueue.poll();
      // Rolls the dice input
      // The game will continue till all players except one to reach the end of the board.
      int currentPosition = playersPosition.get(player);
      int diceValue = dice.rollDice();

      // Calculate next position
      int nextCell = currentPosition + diceValue;
      // Only if the player reaches last cell, he wins.
      // If the next cell > boardSize, then he must play in his next turn like ludo.
      if (nextCell > boardSize) {
        playersQueue.add(player);
      }
      else if (nextCell == boardSize) {
        System.out.println(player.getPlayerName() + " wont the game");
      }
      else {
        // Declared as an array because of lamda expression used below.
        // TODO: Learn more about lamda
        int[] nextPosition = new int[1];
        // TODO: Optimise so that boolean is not required
        boolean[] ladderClaim = new boolean[1];
        nextPosition[0] = nextCell;
        // Three scenarios
        // 1. There can be a snake at the new position
        // 2. There can be a ladder at the new position
        // 3. There is neither snake nor ladder at the new position
        // This solution assumes that there can a snake at the top of any ladder
        // and there can be a ladder at the bottom of any snake.
        snakes.forEach(snakeEntry -> {
          if (snakeEntry.startPoint == nextCell) {
            nextPosition[0] = snakeEntry.endPoint;
          }
        });
        if (nextPosition[0] != nextCell) {
          System.out.println(player.getPlayerName() + " got bitten by a snake at " + nextCell);
        }
        ladders.forEach(entry -> {
          if (entry.startPoint == nextCell) {
            nextPosition[0] = entry.endPoint;
            ladderClaim[0] = true;
          }
        });
        // TODO: Recursively handle snake bite and ladder claim
        if (nextPosition[0] != nextCell && ladderClaim[0]) {
          System.out.println(player.getPlayerName() + " claimed the ladder at " + nextCell);
        }
        if (nextPosition[0] == boardSize) {
          System.out.println(player.getPlayerName() + " wont the game");
        }
        else {
          playersPosition.put(player, nextPosition[0]);
          playersQueue.add(player);
          System.out.println(player.getPlayerName() + " is at position " + nextPosition[0]);
        }
      }
    }
  }
}
