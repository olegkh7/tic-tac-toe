package com.kholosha.tictactoe.game.core.service;

import com.kholosha.tictactoe.game.core.constants.GameStatus;
import com.kholosha.tictactoe.game.api.GameStateManager;
import com.kholosha.tictactoe.game.core.model.Move;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TicTacToeService {

    private final GameValidationService gameValidationService;
    private final MoveService moveService;
    private final int delay;

    public TicTacToeService(GameValidationService gameValidationService,
                            MoveService moveService,
                            @Value("${game.answerDelayMillis}") int delay) {
        this.gameValidationService = gameValidationService;
        this.moveService = moveService;
        this.delay = delay;
    }

    public Move makeFirstMove(GameStateManager gameStateManager) {
        var myMove = moveService.getRandomMove(gameStateManager);
        moveAndCheckForWin(gameStateManager, myMove, gameStateManager.getMySymbol());
        return myMove;
    }

    public Move makeMoveAndAnswer(Move move, GameStateManager gameStateManager) {
        if (!moveAndCheckForWin(gameStateManager, move, gameStateManager.getOponentSymbol())) {
            return null;
        }
        delayMove();
        var myMove = moveService.getRandomMove(gameStateManager);
        if (!moveAndCheckForWin(gameStateManager, myMove, gameStateManager.getMySymbol())) {
            return null;
        }
        return myMove;
    }

    @SneakyThrows
    void delayMove() {
        Thread.sleep(delay);
    }

    private boolean moveAndCheckForWin(GameStateManager gameStateManager, Move move, char symbol) {
        if (gameStateManager.getStatus() != GameStatus.IN_PROGRESS && gameStateManager.getStatus() != GameStatus.NEW) {
            return false;
        }
        if (!gameValidationService.isValidMove(gameStateManager, move)) {
            throw new IllegalArgumentException("Invalid move");
        }
        gameStateManager.setCell(move.getRow(), move.getCol(), symbol);
        gameStateManager.setStatus(GameStatus.IN_PROGRESS);
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
