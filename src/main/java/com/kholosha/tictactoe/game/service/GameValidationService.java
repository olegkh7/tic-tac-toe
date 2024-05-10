package com.kholosha.tictactoe.game.service;

import com.kholosha.tictactoe.game.model.GameStateManager;
import com.kholosha.tictactoe.game.model.Move;
import org.springframework.stereotype.Service;

import static com.kholosha.tictactoe.game.constants.Constants.EMPTY_CELL;

@Service
public class GameValidationService {

    public boolean isValidMove(GameStateManager state, Move move) {
        var row = move.getRow();
        var col = move.getCol();
        return isIndexValid(move.getCol(), state.getFieldSize()) &&
                isIndexValid(move.getRow(), state.getFieldSize())
                && state.getCell(row, col) == EMPTY_CELL;
    }

    private boolean isIndexValid(int index, int fieldSize) {
        return index >= 0 && index < fieldSize;
    }

    /**
     * Simplified algorithm for checking winner.
     * If all symbols in a row or column or diagonal are the same then return true
     */
    public boolean isWinner(GameStateManager state, char symbol) {
        var board = state.getBoard();
        var isWinner = false;
        for (int i = 0; i < board.length; i++) {
            isWinner = true;
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != symbol) {
                    isWinner = false;
                    break;
                }
            }
            if (isWinner) {
                return true;
            }
        }
        for (int i = 0; i < board.length; i++) {
            isWinner = true;
            for (int j = 0; j < board.length; j++) {
                if (board[j][i] != symbol) {
                    isWinner = false;
                    break;
                }
            }
            if (isWinner) {
                return true;
            }
        }

        //diagonal check
        isWinner = true;
        for (int i = 0; i < board.length; i++) {
            if (board[i][i] != symbol) {
                isWinner = false;
                break;
            }
        }
        if (isWinner) {
            return true;
        }

        isWinner = true;
        for (int i = 0, j = board.length - 1; i < board.length; i++, j--) {
            if (board[i][j] != symbol) {
                isWinner = false;
                break;
            }
        }
        return isWinner;
    }

}
