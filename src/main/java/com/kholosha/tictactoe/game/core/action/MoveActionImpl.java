package com.kholosha.tictactoe.game.core.action;

import com.kholosha.tictactoe.game.api.ActionPayload;
import com.kholosha.tictactoe.game.api.ActionType;
import com.kholosha.tictactoe.game.api.GameStateManager;
import com.kholosha.tictactoe.game.core.constants.GameStatus;
import com.kholosha.tictactoe.game.core.service.TicTacToeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MoveActionImpl implements Action {

    private final TicTacToeService ticTacToeService;

    @Autowired
    public MoveActionImpl(TicTacToeService ticTacToeService) {
        this.ticTacToeService = ticTacToeService;
    }

    @Override
    public boolean accept(ActionType actionType) {
        return actionType == ActionType.MOVE;
    }

    @Override
    public ActionPayload proceed(ActionPayload actionPayload, GameStateManager gameStateManager) {
        if (gameStateManager.getStatus() == GameStatus.NEW && actionPayload.getMove() == null) {
            if (gameStateManager.isFirstMove()) {
                var move = ticTacToeService.makeFirstMove(gameStateManager);
                return ActionPayload.builder()
                        .actionType(ActionType.MOVE)
                        .move(move)
                        .build();
            }
            return ActionPayload.builder()
                    .actionType(ActionType.MOVE)
                    .build();
        } else {
            var move = ticTacToeService.makeMoveAndAnswer(actionPayload.getMove(), gameStateManager);
            if (gameStateManager.getStatus() != GameStatus.IN_PROGRESS && gameStateManager.getStatus() != GameStatus.NEW) {
                if (move != null) {
                    return ActionPayload.builder()
                            .actionType(ActionType.MOVE)
                            .status(gameStateManager.getStatus())
                            .move(move)
                            .build();
                }
                return ActionPayload.builder()
                        .actionType(ActionType.COMPLETE)
                        .status(gameStateManager.getStatus())
                        .build();
            } else {
                return ActionPayload.builder()
                        .actionType(ActionType.MOVE)
                        .move(move)
                        .build();
            }
        }
    }
}
