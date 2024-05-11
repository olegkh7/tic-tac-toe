package com.kholosha.tictactoe.game.core.action;

import com.kholosha.tictactoe.game.api.ActionPayload;
import com.kholosha.tictactoe.game.api.ActionType;
import com.kholosha.tictactoe.game.api.GameStateManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CompleteActionImpl implements Action {
    @Override
    public boolean accept(ActionType actionType) {
        return actionType == ActionType.COMPLETE;
    }

    @Override
    public ActionPayload proceed(ActionPayload actionPayload, GameStateManager gameStateManager) {
        if (gameStateManager.isFinished()) {
            return null;
        }
        var message = "It's a " + gameStateManager.getStatus();
        log.info(message);
        gameStateManager.setRandom(null);
        gameStateManager.setFinished(true);
        return ActionPayload.builder()
                .actionType(ActionType.COMPLETE)
                .status(gameStateManager.getStatus())
                .build();

    }
}
