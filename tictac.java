package tictactoe;

import java.util.Scanner;

class Field {
    public enum State {
        NOT_FINISHED("Game not finished"),
        DRAW("Draw"),
        X_WINS("X wins"),
        O_WINS("O wins");

        String text;
        State (String text){
            this.text = text;
        }
    }

    public State state;
    private int turn;
    private char[][] matrix;

    public Field() {
        this.matrix = new char[][]{
                {'_', '_', '_'},
                {'_', '_', '_'},
                {'_', '_', '_'}
        };
        this.state = State.NOT_FINISHED;
        this.turn = 1;
    }

    public Field(String input) {
        char[][] arr = new char[3][3];
        int cursor = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                arr[i][j] = input.charAt(cursor);
                cursor++;
            }
        }
        this.matrix = arr;
        this.state = State.NOT_FINISHED;
        this.turn = 1;
    }

    public boolean makeMove(String[] input) {
        int x,y;
        if (input[0].matches("\\d") && input[1].matches("\\d")) {
            x = Integer.parseInt(input[0]);
            y = Integer.parseInt(input[1]);

            if ( x > 3 || y > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
            }
            else {
                if (this.matrix[3-y][x-1] == '_') {
                    if (this.turn == 1) {
                        this.matrix[3-y][x-1] = 'X';
                        this.turn = 0;
                        return true;
                    }

                    if (this.turn == 0) {
                        this.matrix[3-y][x-1] = 'O';
                        this.turn = 1;
                        return true;
                    }
                    return true;
                }
                else {
                    System.out.println("This cell is occupied! Choose another one!");
                    return false;
                }
            }
        }
        else {
            System.out.println("You should enter numbers!");
            return false;
        }
        return false;
    }

    public void draw() {
        System.out.println("---------");
        for (char[] chars : matrix) {
            System.out.print("| ");
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(chars[j]);
                System.out.print(' ');
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    public void checkState() {
        int countXWins = 0; int countX = 0;
        int countOWins = 0; int countO = 0;
        int countE = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[i][j] == '_') {
                    countE++;
                }
                if (matrix[i][j] == 'X') {
                    countX++;
                }
                if (matrix[i][j] == 'O') {
                    countO++;
                }
            }
        }

        if (countX > countO) {
            this.turn = 0;
        }
        else {
            this.turn = 1;
        }

        for (int i = 0; i < 3; i++) {
            if (matrix[0][i] == 'X' && matrix[1][i] == 'X' && matrix[2][i] == 'X') {
                countXWins++;
            }
            if (matrix[i][0] == 'X' && matrix[i][1] == 'X' && matrix[i][2] == 'X') {
                countXWins++;
            }
            if (matrix[0][i] == 'O' && matrix[1][i] == 'O' && matrix[2][i] == 'O') {
                countOWins++;
            }
            if (matrix[i][0] == 'O' && matrix[i][1] == 'O' && matrix[i][2] == 'O') {
                countOWins++;
            }
        }

        if (matrix[0][0] == 'X' && matrix[1][1] == 'X' && matrix[2][2] == 'X') {
            countXWins++;
        }
        if (matrix[0][2] == 'X' && matrix[1][1] == 'X' && matrix[2][0] == 'X') {
            countXWins++;
        }
        if (matrix[0][0] == 'O' && matrix[1][1] == 'O' && matrix[2][2] == 'O') {
            countOWins++;
        }
        if (matrix[0][2] == 'O' && matrix[1][1] == 'O' && matrix[2][0] == 'O') {
            countOWins++;
        }

        if (countOWins == 0 && countXWins == 0 && countE == 0) {
            this.state = State.DRAW;
        }

        if (countXWins == 1) {
            this.state = State.X_WINS;
        }
        if (countOWins == 1) {
            this.state = State.O_WINS;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter cells: ");

        String input = sc.nextLine();
        Field field = new Field(input);
        field.draw();
        field.checkState();

        System.out.print("Enter the coordinates: ");
        while (true) {
            if (field.makeMove(sc.nextLine().split(" "))) {
                field.draw();
                field.checkState();
                System.out.println(field.state.text);
                break;
            }
        }
    }
}
