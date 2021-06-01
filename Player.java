public class Player {
  private String playerName;
  private int id;
  // Other player stats like gamesPlayed, won, loss, winPercentage, lastPlayedGameId

  Player(String playerName, int id) {
    this.playerName = playerName;
    this.id = id;
  }

  public String getPlayerName() {
    return playerName;
  }

  public int getId() {
    return id;
  }
}