//package Logic.MainTreeLogic;
//
//import Entities.Game.Board;
//import Entities.Game.Cell;
//import Entities.Game.Player;
//import Entities.Game.Property;
//import Entities.InternalDataTransfer.State;
//import Logic.GameLogic;
//import Logic.MainTreeNodeLogic.ManagePropertyBranch.SelectActionPropertyUseCase;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class SelectPropertyUseCaseTest {
//
//    @Test
//    public void testSelectPropertyCreateCase(){
//        Player playerOne = new Player("Player One");
//        List<Player> players = new ArrayList<>();
//        players.add(playerOne);
//        Property test_property = new Property("Name", "Blue", 100, 100, new int[6],
//                null, 50, 0, false);
//        List<Cell> cells = new ArrayList<>();
//        cells.add(test_property);
//        Board board = new Board(players, cells);
//        new GameLogic(playerOne, board);
//        SelectActionPropertyUseCase selectPropertyUseCase = new SelectActionPropertyUseCase(null);
//        State actual = selectPropertyUseCase.create_state();
//        ArrayList<String> options = new ArrayList<>();
//        options.add("Mortgage");
//        options.add("Unmortgage");
//        options.add("Build a house");
//        Assertions.assertTrue(actual.isBackEnable());
//        Assertions.assertEquals(actual.getId(), "Select The Property (Manage Property)");
//        Assertions.assertEquals(actual.getOptions(), options);
//    }
//
//}
