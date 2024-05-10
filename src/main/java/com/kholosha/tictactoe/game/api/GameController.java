package com.kholosha.tictactoe.game.api;

import com.kholosha.tictactoe.game.core.constants.GameStatus;
import com.kholosha.tictactoe.game.core.service.TicTacToeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GameController {

    private final TicTacToeService ticTacToeService;
    private final GameStateManager gameStateManager;

    @Autowired
    public GameController(TicTacToeService ticTacToeService, GameStateManager gameStateManager) {
        this.ticTacToeService = ticTacToeService;
        this.gameStateManager = gameStateManager;
    }

    public GameControllerAction init() {
        log.info("Initializing game");
        gameStateManager.reset();
        gameStateManager.initRandom();
        return GameControllerAction.builder()
                .action(Action.INIT_MOVE_ORDER)
                .random(gameStateManager.getRandom())
                .build();
    }

    public GameControllerAction handleAction(GameControllerAction action) {
        return switch (action.getAction()) {
            case INIT_MOVE_ORDER -> init(action);
            case MOVE -> {
                if (gameStateManager.getStatus() == GameStatus.NEW && action.getMove() == null) {
                    if (gameStateManager.isFirstMove()) {
                        var move = ticTacToeService.makeFirstMove(gameStateManager);
                        yield GameControllerAction.builder()
                                .action(Action.MOVE)
                                .move(move)
                                .build();
                    }
                    yield GameControllerAction.builder()
                            .action(Action.MOVE)
                            .build();
                } else {
                    var move = ticTacToeService.makeMoveAndAnswer(action.getMove(), gameStateManager);
                    if (gameStateManager.getStatus() != GameStatus.IN_PROGRESS && gameStateManager.getStatus() != GameStatus.NEW) {
                        if (move != null) {
                            yield GameControllerAction.builder()
                                    .action(Action.MOVE)
                                    .status(gameStateManager.getStatus())
                                    .move(move)
                                    .build();
                        }
                        yield GameControllerAction.builder()
                                .action(Action.COMPLETE)
                                .status(gameStateManager.getStatus())
                                .build();
                    } else {
                        yield GameControllerAction.builder()
                                .action(Action.MOVE)
                                .move(move)
                                .build();
                    }
                }
            }

            case COMPLETE -> {
                if (gameStateManager.isFinished()) {
                    yield null;
                }
                var message = "It's a " + gameStateManager.getStatus();
                log.info(message);
                gameStateManager.setRandom(null);
                gameStateManager.setFinished(true);
                yield GameControllerAction.builder()
                        .action(Action.COMPLETE)
                        .status(gameStateManager.getStatus())
                        .build();

            }
        };
    }

    private GameControllerAction init(GameControllerAction action) {
        if (gameStateManager.getRandom() == null) {
            gameStateManager.reset();
            gameStateManager.initRandom();
            gameStateManager.initSymbols(action.getRandom());
            return GameControllerAction.builder()
                    .action(Action.INIT_MOVE_ORDER)
                    .random(gameStateManager.getRandom())
                    .build();
        }
        gameStateManager.initSymbols(action.getRandom());
        return GameControllerAction.builder()
                .action(Action.MOVE)
                .build();
    }
}