package com.kholosha.tictactoe.game.api;

import com.kholosha.tictactoe.game.core.constants.GameStatus;
import com.kholosha.tictactoe.game.core.model.Move;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ActionPayload {
    private ActionType actionType;
    private GameStatus status;
    private Move move;
    private Integer random;
}
