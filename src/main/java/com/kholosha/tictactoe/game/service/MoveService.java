package com.kholosha.tictactoe.game.service;

import com.kholosha.tictactoe.game.model.GameStateManager;
import com.kholosha.tictactoe.game.model.Move;
import org.springframework.stereotype.Service;

import static com.kholosha.tictactoe.game.constants.Constants.EMPTY_CELL;

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
