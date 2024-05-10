package com.kholosha.tictactoe.game.model;

import com.kholosha.tictactoe.game.constants.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameStateRepresentation {
    private char[][] board;
    private GameStatus status;
}
