package com.kholosha.tictactoe.game.core.service;

import com.kholosha.tictactoe.game.api.GameStateManager;
import com.kholosha.tictactoe.game.core.constants.GameStatus;
import com.kholosha.tictactoe.game.core.model.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicTacToeServiceTest {

    private static final int FIELD_SIZE = 3;

    @Mock
    private GameValidationService gameValidationService;
    @Mock
    private MoveService moveService;
    private GameStateManager gameStateManager;
    private TicTacToeService ticTacToeService;

    @BeforeEach
    public void init() {
        gameStateManager = new GameStateManager(FIELD_SIZE);
        gameStateManager.reset();
        gameStateManager.initRandom();
        gameStateManager.initSymbols(gameStateManager.getRandom() - 1);
        ticTacToeService = new TicTacToeService(gameValidationService, moveService, 0);
    }

    @Test
    public void shouldMakeFirstMoveAndChangeStatusToInProgress() {
        var move = new Move(0, 0);

        when(moveService.getRandomMove(gameStateManager)).thenReturn(move);
        when(gameValidationService.isValidMove(gameStateManager, move)).thenReturn(true);

        var firstMove = ticTacToeService.makeFirstMove(gameStateManager);

        assertThat(firstMove).isEqualTo(move);
        assertThat(gameStateManager.getStatus()).isEqualTo(GameStatus.IN_PROGRESS);
    }

    @Test
    public void shouldMakeMoveAndChangeStatusToInProgress() {
        var move = new Move(0, 0);
        var answer = new Move(0, 1);

        when(moveService.getRandomMove(gameStateManager)).thenReturn(answer);
        when(gameValidationService.isValidMove(gameStateManager, move)).thenReturn(true);
        when(gameValidationService.isValidMove(gameStateManager, answer)).thenReturn(true);

        var answerMove = ticTacToeService.makeMoveAndAnswer(move, gameStateManager);

        assertThat(answerMove).isEqualTo(answer);
        assertThat(gameStateManager.getStatus()).isEqualTo(GameStatus.IN_PROGRESS);
    }

    @Test
    public void shouldApplyMoveAndChangeStatusToLost() {
        var move = new Move(0, 0);

        when(gameValidationService.isValidMove(gameStateManager, move)).thenReturn(true);
        when(gameValidationService.isWinner(gameStateManager, gameStateManager.getOponentSymbol())).thenReturn(true);

        var answerMove = ticTacToeService.makeMoveAndAnswer(move, gameStateManager);

        assertThat(answerMove).isNull();
        assertThat(gameStateManager.getStatus()).isEqualTo(GameStatus.LOST);
    }

    @Test
    public void shouldMakeAnswerMoveAndChangeStatusToWin() {
        var move = new Move(0, 0);
        var answer = new Move(0, 1);

        when(moveService.getRandomMove(gameStateManager)).thenReturn(answer);
        when(gameValidationService.isValidMove(gameStateManager, move)).thenReturn(true);
        when(gameValidationService.isValidMove(gameStateManager, answer)).thenReturn(true);
        when(gameValidationService.isWinner(gameStateManager, gameStateManager.getOponentSymbol())).thenReturn(false);
        when(gameValidationService.isWinner(gameStateManager, gameStateManager.getMySymbol())).thenReturn(true);

        var answerMove = ticTacToeService.makeMoveAndAnswer(move, gameStateManager);

        assertThat(answerMove).isEqualTo(answer);
        assertThat(gameStateManager.getStatus()).isEqualTo(GameStatus.WON);
    }

    @Test
    public void shouldMakeAnswerMoveAndChangeStatusToDraw() {
        var move = new Move(0, 0);
        var answer = new Move(0, 1);

        for (int i = 0; i < FIELD_SIZE - 1; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                gameStateManager.setCell(i, j, 'X');
            }
        }
        gameStateManager.setCell(FIELD_SIZE - 1, 0, 'X');

        when(moveService.getRandomMove(gameStateManager)).thenReturn(answer);
        when(gameValidationService.isValidMove(gameStateManager, move)).thenReturn(true);
        when(gameValidationService.isValidMove(gameStateManager, answer)).thenReturn(true);

        var answerMove = ticTacToeService.makeMoveAndAnswer(move, gameStateManager);

        assertThat(answerMove).isEqualTo(answer);
        assertThat(gameStateManager.getStatus()).isEqualTo(GameStatus.DRAW);
    }

    @Test
    public void shouldThrowExceptionIfMoveIsInvalid() {
        var move = new Move(0, 0);

        when(moveService.getRandomMove(gameStateManager)).thenReturn(move);
        when(gameValidationService.isValidMove(gameStateManager, move)).thenReturn(false);

        assertThatThrownBy(() -> ticTacToeService.makeFirstMove(gameStateManager))
                .isInstanceOf(IllegalArgumentException.class);
    }

}