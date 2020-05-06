import data.GameState;
import game.TicTacToe;

import java.util.Scanner;

public class GameEntry {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TicTacToe ticTacToe = new TicTacToe();
        ticTacToe.initialize();
        for (; ; ) {
            clearScreen();
            GameState gameState = ticTacToe.getCurrentGameState();
            if (gameState instanceof GameState.EnterNameOfPlayer) {
                System.out.println("Добро пожаловать в игру крестики нолики!");
                System.out.print("Укажите имя игрока (крестики) - ");
                String player1 = scanner.nextLine();
                System.out.print("Укажите имя игрока (нолики) - ");
                String player2 = scanner.nextLine();
                ticTacToe.setPlayersName(player1, player2);
            } else if (gameState instanceof GameState.OrderToPlay) {
                GameState.OrderToPlay orderToPlayState = ((GameState.OrderToPlay) gameState);
                drawGameField(orderToPlayState.getGameField());
                if (orderToPlayState.getMessage() != null) {
                    System.out.println("Игровое замечание: " + orderToPlayState.getMessage());
                }
                System.out.print(orderToPlayState.getPlayerName() + ", ваш ход(1-9): ");
                int move = scanner.nextInt();
                ticTacToe.playerMove(move);
            }
            else if (gameState instanceof GameState.Winner){
                GameState.Winner winnerState = ((GameState.Winner)gameState);
                drawGameField(winnerState.getGameField());
                System.out.println("Игрок - " + winnerState.getPlayerName() + " победитель");
                break;
            }
            else if (gameState instanceof GameState.NoWinner){
                GameState.NoWinner noWinnerState = ((GameState.NoWinner)gameState);
                drawGameField(noWinnerState.getGameField());
                System.out.println("Ничья");
                break;
            }
        }
    }

    public static void drawGameField(int[][] gameField) {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {
                System.out.print(gameField[i][j] == 0 ? "_" : gameField[i][j] == 1 ? "x" : "o" + "\t");
            }
            System.out.println();
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}