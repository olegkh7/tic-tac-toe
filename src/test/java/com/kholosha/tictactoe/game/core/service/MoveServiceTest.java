package com.kholosha.tictactoe.game.core.service;

import com.kholosha.tictactoe.game.api.GameStateManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MoveServiceTest {

    private static final Integer FIELD_SIZE = 3;

    private GameStateManager gameStateManager;
    private MoveService moveService;

    @BeforeEach
    public void init() {
        gameStateManager = new GameStateManager(FIELD_SIZE);
        gameStateManager.reset();
        moveService = new MoveService();
    }

    @Test
    void shouldGetRandomMoveIfFieldHasEmptyCells() {
        gameStateManager.setCell(0, 0, 'X');
        gameStateManager.setCell(0, 1, 'X');
        gameStateManager.setCell(0, 2, 'X');
        var move = moveService.getRandomMove(gameStateManager);

        assertNotEquals(move.getRow(), 0);
        assertEquals(move.getCol(), 0);

    }

    @Test
    void shouldGetNullIfFieldHasNoEmptyCells() {
        for (int i = 0; i < gameStateManager.getFieldSize(); i++) {
            for (int j = 0; j < gameStateManager.getFieldSize(); j++) {
                gameStateManager.setCell(i, j, 'X');
            }
        }
        var move = moveService.getRandomMove(gameStateManager);
        assertNull(move);
    }
}