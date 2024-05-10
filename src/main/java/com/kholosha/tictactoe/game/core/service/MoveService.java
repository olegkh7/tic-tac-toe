package com.kholosha.tictactoe.game.core.service;

import com.kholosha.tictactoe.game.api.GameStateManager;
import com.kholosha.tictactoe.game.core.model.Move;
import org.springframework.stereotype.Service;

import static com.kholosha.tictactoe.game.core.constants.Constants.EMPTY_CELL;

@Service
public class MoveService {

    public Move getRandomMove(GameStateManager state) {
       for (int i = 0; i < state.getFieldSize(); i++) {
           for (int j = 0; j < state.getFieldSize(); j++) {
               if (state.getCell(i, j) == EMPTY_CELL) {
                   return new Move(i, j);
               }
           }
       }
        return null;
    }
}
