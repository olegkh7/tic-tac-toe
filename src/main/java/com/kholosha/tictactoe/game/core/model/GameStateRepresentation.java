package com.kholosha.tictactoe.game.core.model;

import com.kholosha.tictactoe.game.core.constants.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameStateRepresentation {
    private char[][] board;
    private GameStatus status;
}
