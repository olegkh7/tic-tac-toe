package com.kholosha.tictactoe.infrastracture.controller;

import com.kholosha.tictactoe.game.api.GameController;
import com.kholosha.tictactoe.game.api.GameStateManager;
import com.kholosha.tictactoe.game.core.model.GameStateRepresentation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class BoardController {

    private final GameStateManager gameStateManager;
    private final GameController gameController;
    private final RabbitTemplate rabbitTemplate;
    private final String destinationQueueName;

    @Autowired
    public BoardController(GameStateManager gameStateManager, GameController gameController, RabbitTemplate rabbitTemplate,
                           @Value("${message.destinationQueue}") String destinationQueueName) {
        this.gameStateManager = gameStateManager;
        this.gameController = gameController;
        this.rabbitTemplate = rabbitTemplate;
        this.destinationQueueName = destinationQueueName;
    }

    @GetMapping("/board")
    public GameStateRepresentation getGameSate() {
        return new GameStateRepresentation(gameStateManager.getBoard(), gameStateManager.getStatus());
    }

    @PostMapping("/start")
    public void start() {
        var action = gameController.init();
        rabbitTemplate.convertAndSend(destinationQueueName, action);
    }
}
