package com.kholosha.tictactoe.game.service;

import com.kholosha.tictactoe.game.constants.GameStatus;
import com.kholosha.tictactoe.game.model.GameStateManager;
import com.kholosha.tictactoe.game.model.Move;
import org.springframework.stereotype.Service;

@Service
public class TicTacToeService {

    private final GameValidationService gameValidationService;
    private final MoveService moveService;

    public TicTacToeService(GameValidationService gameValidationService, MoveService moveService) {
        this.gameValidationService = gameValidationService;
        this.moveService = moveService;
    }

    public Move makeFirstMove(GameStateManager gameStateManager) {
        var myMove = moveService.getRandomMove(gameStateManager);
        moveAndCheckForWin(gameStateManager, myMove, gameStateManager.getMySymbol());
        gameStateManager.setStatus(GameStatus.IN_PROGRESS);
        return myMove;
    }

    public Move makeMove(Move move, GameStateManager gameStateManager) {
        if (!moveAndCheckForWin(gameStateManager, move, gameStateManager.getOponentSymbol())) {
            return null;
        }

        var myMove = moveService.getRandomMove(gameStateManager);
        if (!moveAndCheckForWin(gameStateManager, myMove, gameStateManager.getMySymbol())) {
            return null;
        }
        return myMove;
    }

    private boolean moveAndCheckForWin(GameStateManager gameStateManager, Move move, char symbol) {
        if (gameStateManager.getStatus() != GameStatus.IN_PROGRESS && gameStateManager.getStatus() != GameStatus.NEW) {
            return false;
        }
        if (!gameValidationService.isValidMove(gameStateManager, move)) {
            throw new IllegalArgumentException("Invalid move");
        }
        gameStateManager.setCell(move.getRow(), move.getCol(), symbol);
        if (gameStateManager.getFreeCells() == 0) {
            gameStateManager.setStatus(GameStatus.DRAW);
        }
        if (gameValidationService.isWinner(gameStateManager, symbol)) {
            var gameStatus = symbol == gameStateManager.getMySymbol() ? GameStatus.WON : GameStatus.LOST;
            gameStateManager.setStatus(gameStatus);
        }
        return true;
    }


}
