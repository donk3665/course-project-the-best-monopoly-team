//package Logic.MainTreeLogic;
//
//import Entities.Game.Board;
//import Entities.Game.Cell;
//import Entities.Game.Player;
//import Entities.Game.Property;
//import Entities.InternalDataTransfer.State;
//import Logic.GameLogic;
//import Logic.MainTreeNodeLogic.TradingBranch.PickItemOpUseCase;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class PickItemOpUseCaseTest {
//
//    @Test
//    public void testPickItemOpCreateState(){
//        Player playerOne = new Player("Player One");
//        Player playerTwo = new Player("Player Two");
//        List<Player> players = new ArrayList<>();
//        players.add(playerOne);
//        Property test_property1 = new Property("Property One", "Blue", 100, 100, new int[6],
//                playerOne, 50, 0, false);
//        Property test_property2 = new Property("Property One", "Blue", 100, 100, new int[6],
//                playerTwo, 50, 0, false);
//        playerOne.addProperty(test_property1);
//        playerTwo.addProperty(test_property2);
//        List<Cell> cells = new ArrayList<>();
//        cells.add(test_property1);
//        cells.add(test_property2);
//        Board board = new Board(players, cells);
//        new GameLogic(playerOne, board);
//        PickItemOpUseCase pickItemOpUseCase = new PickItemOpUseCase();
//        State actual = pickItemOpUseCase.create_state(0);
//        ArrayList<String> options = new ArrayList<>();
//        options.add("Property One");
//        Assertions.assertTrue(actual.isBackEnable());
//        Assertions.assertEquals(actual.getId(), "Pick Item Of Opponent");
//        Assertions.assertEquals(actual.getOptions(), options);
//    }
//
//}
