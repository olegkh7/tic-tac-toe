package com.kholosha.tictactoe.game.core.service;

import com.kholosha.tictactoe.game.api.GameStateManager;
import com.kholosha.tictactoe.game.core.constants.Constants;
import com.kholosha.tictactoe.game.core.model.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameValidationServiceTest {

    private static final Integer FIELD_SIZE = 3;

    private GameStateManager gameStateManager;
    private GameValidationService validationService;

    @BeforeEach
    public void init() {
        gameStateManager = new GameStateManager(FIELD_SIZE);
        gameStateManager.reset();
        validationService = new GameValidationService();
    }

    @Test
    void shouldReturnTrueIfMoveIsValid() {
        var move = new Move(0, 0);
        assertTrue(validationService.isValidMove(gameStateManager, move));
    }

    @Test
    void shouldReturnFalseIfMoveIsOutOfField() {
        var move = new Move(0, FIELD_SIZE + 1);
        assertFalse(validationService.isValidMove(gameStateManager, move));
    }

    @Test
    void shouldReturnFalseIfMoveIsOnBusyField() {
        var move = new Move(0, 0);
        gameStateManager.setCell(0, 0, Constants.X_CELL);
        assertFalse(validationService.isValidMove(gameStateManager, move));
    }

    @ParameterizedTest
    @MethodSource("provideOptions")
    void isWinner(char[][] board, boolean result) {
        gameStateManager.setBoard(board);
        assertEquals(validationService.isWinner(gameStateManager, 'X'), result);
    }

    private static Stream<Arguments> provideOptions() {
        var arrayHorizontal = new char[][]{
                {'X', 'O', '-'},
                {'X', 'X', 'X'},
                {'O', 'O', '-'}
        };
        var arrayVertical = new char[][]{
                {'X', 'O', '-'},
                {'X', 'X', 'O'},
                {'X', 'O', '-'}
        };

        var arrayDiagonal1 = new char[][]{
                {'X', 'O', '-'},
                {'O', 'X', 'O'},
                {'X', 'O', 'X'}
        };
        var arrayDiagonal2 = new char[][]{
                {'X', 'O', 'X'},
                {'O', 'X', 'O'},
                {'X', 'O', '-'}
        };

        var notWinnerArray = new char[][]{
                {'X', 'O', 'X'},
                {'O', '-', 'O'},
                {'X', 'O', '-'}
        };
        return Stream.of(
                Arguments.of(arrayHorizontal, true),
                Arguments.of(arrayVertical, true),
                Arguments.of(arrayDiagonal1, true),
                Arguments.of(arrayDiagonal2, true),
                Arguments.of(notWinnerArray, false)
        );
    }
}