package com.kholosha.tictactoe.game.api;

import com.kholosha.tictactoe.game.core.constants.Constants;
import com.kholosha.tictactoe.game.core.constants.GameStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Getter
@Setter
@Component
@ToString
public class GameStateManager {

    private char[][] board;
    private int fieldSize;
    private GameStatus status;
    private char mySymbol;
    private char oponentSymbol;
    private Integer random;
    private boolean isFirstMove;
    private int freeCells;
    private boolean finished;

    @Autowired
    public GameStateManager(@Value("${game.fieldSize}") int fieldSize) {
        this.fieldSize = fieldSize;
    }

    public void initRandom() {
        random = new Random().nextInt();
    }

    public void reset() {
        random = null;
        isFirstMove = false;
        finished = false;
        status = GameStatus.NEW;
        board = new char[fieldSize][fieldSize];
        freeCells = fieldSize * fieldSize;
        initBoard();
    }

    public void initSymbols(int oponentRandom) {
        if (random > oponentRandom) {
            isFirstMove = true;
            mySymbol = Constants.X_CELL;
            oponentSymbol = Constants.O_CELL;
        } else {
            isFirstMove = false;
            mySymbol = Constants.O_CELL;
            oponentSymbol = Constants.X_CELL;
        }
    }

    public char getCell(int row, int col) {
        return board[row][col];
    }

    public void setCell(int row, int col, char symbol) {
        board[row][col] = symbol;
        freeCells--;
    }

    private void initBoard() {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                board[i][j] = Constants.EMPTY_CELL;
            }
        }
    }

}
