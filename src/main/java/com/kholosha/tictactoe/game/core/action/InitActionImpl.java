package com.kholosha.tictactoe.game.core.action;

import com.kholosha.tictactoe.game.api.ActionPayload;
import com.kholosha.tictactoe.game.api.ActionType;
import com.kholosha.tictactoe.game.api.GameStateManager;
import org.springframework.stereotype.Component;

@Component
public class InitActionImpl implements Action {

    @Override
    public boolean accept(ActionType actionType) {
        return actionType == ActionType.INIT_MOVE_ORDER;
    }

    @Override
    public ActionPayload proceed(ActionPayload actionPayload, GameStateManager gameStateManager) {
        if (gameStateManager.getRandom() == null) {
            gameStateManager.reset();
            gameStateManager.initRandom();
            gameStateManager.initSymbols(actionPayload.getRandom());
            return ActionPayload.builder()
                    .actionType(ActionType.INIT_MOVE_ORDER)
                    .random(gameStateManager.getRandom())
                    .build();
        }
        gameStateManager.initSymbols(actionPayload.getRandom());
        return ActionPayload.builder()
                .actionType(ActionType.MOVE)
                .build();
    }
}
