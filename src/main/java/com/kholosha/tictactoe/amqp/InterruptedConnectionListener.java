package com.kholosha.tictactoe.amqp;

import com.kholosha.tictactoe.game.constants.GameStatus;
import com.kholosha.tictactoe.game.model.GameStateManager;
import com.rabbitmq.client.ShutdownSignalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InterruptedConnectionListener implements ConnectionListener {

    private final GameStateManager gameStateManager;

    @Autowired
    public InterruptedConnectionListener(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void onCreate(Connection connection) {
        // Connection created, do nothing
    }

    @Override
    public void onClose(Connection connection) {
        // Connection closed, do nothing
    }

    @Override
    public void onShutDown(ShutdownSignalException signal) {
        gameStateManager.setStatus(GameStatus.INVALID_STATE);
        log.error("RabbitMQ connection interrupted. Game status INVALID");
    }
}
