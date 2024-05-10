package com.kholosha.tictactoe;

import com.kholosha.tictactoe.game.controller.GameController;
import com.kholosha.tictactoe.game.model.GameStateManager;
import com.kholosha.tictactoe.game.service.MoveService;
import com.kholosha.tictactoe.game.service.GameValidationService;
import com.kholosha.tictactoe.game.service.TicTacToeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class TicTacToeGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicTacToeGameApplication.class, args);
	}

//    public static void main(String[] args) throws InterruptedException {
//        var state1 = new GameStateManager(3);
//        var state2 = new GameStateManager(3);
//        var moveValidationService = new GameValidationService();
//        var moveService = new MoveService();
//        var ticTacService = new TicTacToeService(moveValidationService, moveService);
//        var controller1 = new GameController(ticTacService, state1);
//        var controller2 = new GameController(ticTacService, state2);
//        game(controller1, controller2, state1, state2);
//    }
//
//    public static void game(GameController controller1, GameController controller2, GameStateManager stateManager1, GameStateManager stateManager2) throws InterruptedException {
//
//        var action1 = controller1.init();
//        var action2 = controller2.init();
//        var t = true;
//        while (true) {
//            var action3 = controller1.handleAction(action2);
//            action2 = controller2.handleAction(t ? action1: action3);
//            action1 = action3;
//            t = false;
//            var table = stateManager1.getBoard();
//            for (int i = 0; i < 3; i++) {
//                for (int j = 0; j < 3; j++) {
//                    System.out.print(table[i][j] + " ");
//                }
//                System.out.println();
//            }
//            System.out.println(stateManager1);
//            System.out.println(stateManager2);
//            System.out.println("[[[[[[[[[[[[[[[[[[[[[[[[[[[");
////            Thread.sleep(5000);
//        }
//    }

}
