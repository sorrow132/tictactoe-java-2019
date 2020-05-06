package game;

import data.GameState;

/**
 * 0 0 0
 * 0 0 0
 * 0 0 0
 * У нас есть девять полей куда юзер может походить
 * Есть два типа пользователей крестики и нолики.
 * Правило 1: На одной позиции может находится только крестик или нолик
 * Правило 2: Ходят по очереди, первые всегда крестики
 * Правило 3: Победитель тот кто соберет в ряд три одинаковых крестика или нолика
 * Правило 4: Если нет больше ходов и нет победителя, то ничья
 */
public class TicTacToe {
    private static final int CLEAR = 0;
    private static final int X = 1;
    private static final int ZERO = 2;

    private int[][] gameField;
    private int whichTurnOfPlaying = X;
    private String player1, player2;

    private GameState currentState;

    public TicTacToe() {
        gameField = new int[3][3];
    }

    public void initialize() {
        clear();
        changeState(new GameState.EnterNameOfPlayer());
    }

    public void setPlayersName(String playerOfX, String playerOfZero) {
        player1 = playerOfX;
        player2 = playerOfZero;
        changeState(new GameState.OrderToPlay(gameField.clone(), playerOfX));
    }

    /**
     * Player moves
     * All fields have number of 1 till 9, in a snake order from left to right
     *
     * @param number Number of move
     */
    public void playerMove(int number) {
        if (number < 1 || number > 9) {
            throw new IllegalArgumentException("Wrong number, it must be from 1 till 9.");
        }

        if (!(currentState instanceof GameState.OrderToPlay)) {
            throw new IllegalArgumentException("Wrong game state. When player moves, state should be in OrderToPlay");
        }

        String playerName = ((GameState.OrderToPlay) currentState).getPlayerName();
        int playerSymbol = getPlayerSymbol(playerName);

        // Check if player chooses free number
        int[] movePosition = getPositionByNumber(number);
        if (gameField[movePosition[0]][movePosition[1]] != CLEAR) {
            changeState(new GameState.OrderToPlay(gameField.clone(), playerName, "Хули ты не туда ходишь?"));
            return;
        } else {
            gameField[movePosition[0]][movePosition[1]] = playerSymbol;
        }
        // Проверка прямых
        for (int i = 0; i < 3; i++) {
            if (gameField[i][0] == X & gameField[i][1] == X & gameField[i][2] == X ||
                    gameField[0][i] == X & gameField[1][i] == X & gameField[2][i] == X) {
                changeState(new GameState.Winner(gameField, player1));
                return;
            } else if (gameField[i][0] == ZERO & gameField[i][1] == ZERO & gameField[i][2] == ZERO ||
                    gameField[0][i] == ZERO & gameField[1][i] == ZERO & gameField[2][i] == ZERO) {
                changeState(new GameState.Winner(gameField, player2));
                return;
            }
        }
        // Проверка диагоналей
        if (gameField[0][0] == X & gameField[1][1] == X & gameField[2][2] == X ||
                gameField[0][2] == X & gameField[1][1] == X & gameField[2][0] == X) {
            changeState(new GameState.Winner(gameField, player1));
            return;
        } else if (gameField[0][0] == ZERO & gameField[1][1] == ZERO & gameField[2][2] == ZERO ||
                gameField[0][2] == ZERO & gameField[1][1] == ZERO & gameField[2][0] == ZERO) {
            changeState(new GameState.Winner(gameField, player2));
            return;
        }

        // Проверка на ничью
        boolean isGamePlayable = false;
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {
                if (gameField[i][j] == CLEAR) {
                    isGamePlayable = true;
                }
            }
        }
        if (!isGamePlayable) {
            changeState(new GameState.NoWinner(gameField));
            return;
        }

        // Switch player move
        if (playerSymbol == X) {
            changeState(new GameState.OrderToPlay(gameField, player2));
        } else {
            changeState(new GameState.OrderToPlay(gameField, player1));
        }
    }

    private int getPlayerSymbol(String player) {
        if (player.equals(player1)) {
            return X;
        }
        return ZERO;
    }

    private int[] getPositionByNumber(int number) {
        switch (number) {
            case 1:
                return new int[]{0, 0};
            case 2:
                return new int[]{0, 1};
            case 3:
                return new int[]{0, 2};
            case 4:
                return new int[]{1, 0};
            case 5:
                return new int[]{1, 1};
            case 6:
                return new int[]{1, 2};
            case 7:
                return new int[]{2, 0};
            case 8:
                return new int[]{2, 1};
            case 9:
                return new int[]{2, 2};
        }
        return new int[]{0, 0};
    }

    private void changeState(GameState newState) {
        this.currentState = newState;
    }

    public GameState getCurrentGameState() {
        return currentState;
    }

    private void clear() {
        whichTurnOfPlaying = X;
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {
                gameField[i][j] = CLEAR;
            }
        }
    }
}
