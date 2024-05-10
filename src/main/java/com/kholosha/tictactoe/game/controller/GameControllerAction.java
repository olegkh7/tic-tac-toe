package com.kholosha.tictactoe.game.controller;

import com.kholosha.tictactoe.game.constants.GameStatus;
import com.kholosha.tictactoe.game.model.Move;
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
