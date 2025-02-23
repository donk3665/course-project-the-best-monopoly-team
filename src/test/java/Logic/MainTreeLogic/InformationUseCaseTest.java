//package Logic.MainTreeLogic;
//
//import Entities.Game.Board;
//import Entities.Game.Cell;
//import Entities.Game.Player;
//import Entities.InternalDataTransfer.State;
//import Logic.GameLogic;
//import Logic.MainTreeNodeLogic.InformationUseCase;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class InformationUseCaseTest {
//
//    @Test
//    public void testInformationUseCaseCreateState(){
//        Player playerOne = new Player("Player One");
//        Player playerTwo = new Player("Player Two");
//        List<Player> players = new ArrayList<>();
//        players.add(playerOne);
//        players.add(playerTwo);
//        List<Cell> cells = new ArrayList<>();
//        Board board = new Board(players, cells);
//        new GameLogic(playerOne, board);
//        InformationUseCase informationUseCase = new InformationUseCase();
//        State actual =  informationUseCase.create_state(0);
//        Assertions.assertEquals(actual.getId(), "main.Main Tree Parent Node");
//    }
//
//}
