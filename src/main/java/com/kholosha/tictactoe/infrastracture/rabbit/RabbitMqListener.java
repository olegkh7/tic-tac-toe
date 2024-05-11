package com.kholosha.tictactoe.infrastracture.rabbit;

import com.kholosha.tictactoe.game.api.ActionPayload;
import com.kholosha.tictactoe.game.api.GameController;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqListener {

    private final GameController gameController;
    private final RabbitTemplate rabbitTemplate;
    private final String destinationQueueName;

    @Autowired
    public RabbitMqListener(GameController gameController, RabbitTemplate rabbitTemplate, @Value("${message.destinationQueue}") String destinationQueueName) {
        this.gameController = gameController;
        this.rabbitTemplate = rabbitTemplate;
        this.destinationQueueName = destinationQueueName;
    }

    @RabbitListener(queues = "${message.listenerQueue}")
    public void listen(@Payload ActionPayload actionPayload) {
        if (actionPayload == null) {
            return;
        }
        var response = gameController.handleAction(actionPayload);
        if (response != null) {
            rabbitTemplate.convertAndSend(destinationQueueName, response);
        }
    }

}
