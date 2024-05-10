package com.kholosha.tictactoe.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GameAspect {

    @Value("${game.answerDelayMillis}")
    private int delay;

    @Before("execution(* com.kholosha.tictactoe.game.controller.GameController.handleAction(..))")
    public void delayExecutionAdvice() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
