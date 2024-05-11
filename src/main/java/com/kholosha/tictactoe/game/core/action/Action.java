package com.kholosha.tictactoe.game.core.action;

import com.kholosha.tictactoe.game.api.ActionType;
import com.kholosha.tictactoe.game.api.ActionPayload;
import com.kholosha.tictactoe.game.api.GameStateManager;

public interface Action {

    boolean accept(ActionType actionType);

    ActionPayload proceed(ActionPayload actionPayload, GameStateManager stateManager);
}
