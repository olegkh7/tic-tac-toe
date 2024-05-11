package com.kholosha.tictactoe.game.api;

import com.kholosha.tictactoe.game.core.action.Action;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class GameController {

    private final GameStateManager gameStateManager;
    private final List<Action> gameActions;

    @Autowired
    public GameController(GameStateManager gameStateManager, List<Action> gameActions) {
        this.gameStateManager = gameStateManager;
        this.gameActions = gameActions;
    }

    public ActionPayload init() {
        log.info("Initializing game");
        gameStateManager.reset();
        gameStateManager.initRandom();
        return ActionPayload.builder()
                .actionType(ActionType.INIT_MOVE_ORDER)
                .random(gameStateManager.getRandom())
                .build();
    }

    public ActionPayload handleAction(ActionPayload actionPayload) {
        var gameAction = gameActions.stream()
                .filter(action -> action.accept(actionPayload.getActionType()))
                .findFirst()
                .orElseThrow();
        return gameAction.proceed(actionPayload, gameStateManager);
    }

}
