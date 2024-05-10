package com.kholosha.tictactoe.controller;

import com.kholosha.tictactoe.game.model.GameStateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class BoardController {

    private final GameStateManager gameStateManager;

    @Autowired
    public BoardController(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @GetMapping("/board")
    public char[][] getGameSate() {
        return gameStateManager.getCopy();
    }
}
