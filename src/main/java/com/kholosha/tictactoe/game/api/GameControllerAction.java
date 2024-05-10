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
public class GameControllerAction {
    private Action action;
    private GameStatus status;
    private Move move;
    private Integer random;
}
