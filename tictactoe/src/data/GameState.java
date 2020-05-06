package data;

public abstract class GameState {

    public static class EnterNameOfPlayer extends GameState {}

    public static class NoWinner extends GameState {
        private int[][] gameField;

        public NoWinner(int[][] gameField) {
            this.gameField = gameField;
        }

        public int[][] getGameField() {
            return gameField;
        }
    }

    public static class Winner extends GameState {
        private int[][] gameField;
        private String playerName;

        public Winner(int[][] gameField, String playerName) {
            this.gameField = gameField;
            this.playerName = playerName;
        }

        public int[][] getGameField() {
            return gameField;
        }

        public String getPlayerName() {
            return playerName;
        }
    }

    public static class OrderToPlay extends GameState {
        private int[][] gameField;
        private String playerName;
        private String message;

        public OrderToPlay(int[][] gameField, String playerName) {
            this.gameField = gameField;
            this.playerName = playerName;
            this.message = null;
        }

        public OrderToPlay(int[][] gameField, String playerName, String message) {
            this.gameField = gameField;
            this.playerName = playerName;
            this.message = message;
        }

        public int[][] getGameField() {
            return gameField;
        }

        public String getPlayerName() {
            return playerName;
        }

        public String getMessage() {
            return message;
        }
    }
}
