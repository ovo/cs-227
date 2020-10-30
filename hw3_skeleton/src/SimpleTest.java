import api.Cell;
import api.Direction;
import api.MoveRecord;
import api.State;
import api.StringUtil;
import hw3.PearlUtil;
import hw3.Pearls;


/**
 * Sample tests from the pdf.  Uncomment each test when you get the
 * relevant methods implemented.
 */
public class SimpleTest
{
  public static void main(String[] args)
  {

//    String[] test = {
//      "##.###",
//      "##$..#",
//      "#.#..#",
//      "#.@@.#",
//      "#....#",
//      "##.###"};
//
//    // getStateSequence
//    Pearls game = new Pearls(test, new PearlUtil());
//    StringUtil.printGrid(game);
//    State[] states = game.getStateSequence(Direction.UP);
//    System.out.println();
//    StringUtil.printStateArray(states, 0);
//    System.out.println();
//
    String[] testPortals = {
      "######",
      "#Ooo$#",
      "# @@ #",
      "#    #",
      "#@O ##",
      "#  ###",
      "######"};
//
//    // getStateSequence with portals
//    game = new Pearls(testPortals, new PearlUtil());
//    StringUtil.printGrid(game);
//    states = game.getStateSequence(Direction.LEFT);
//    System.out.println();
//    StringUtil.printStateArray(states, 0);
//    System.out.println();
//
//    // examine portal offsets
//    Cell c = game.getCell(1, 1);
//    System.out.println(c.getRowOffset() + ", " + c.getColumnOffset());
//    c = game.getCell(4, 2);
//    System.out.println(c.getRowOffset() + ", " + c.getColumnOffset());
//    System.out.println();

    // setStateSequence
//    Pearls game = new Pearls(testPortals, new PearlUtil());
//    State[] states = StringUtil.createFromString(".xxOO.#");
//    game.setStateSequence(states, Direction.LEFT, 5);
//    System.out.println();
//    StringUtil.printGrid(game);

    // Using MoveRecords with movePlayer
//    PearlUtil util = new PearlUtil();
//    String s = "..@.o..@.#";
//    State[] states = StringUtil.createFromString(s);
//    MoveRecord[] records = new MoveRecord[states.length];
//    for (int i = 0; i < states.length; ++i)
//    {
//      records[i] = new MoveRecord(states[i], i);
//    }
//    StringUtil.printStateArray(states, 0);
//    System.out.println();
//    util.movePlayer(states, records, Direction.DOWN);
//    StringUtil.printStateArray(states, 8);
//    System.out.println();
//    for (int i = 0; i < records.length; ++i)
//    {
//      System.out.println(i + " " + records[i].toString());
//    }
//    System.out.println();

    // Using MoveRecords with moveBlocks
    PearlUtil util = new PearlUtil();
    String s = "..@.o+@+-+.o";
    State[] states = StringUtil.createFromString(s);
    MoveRecord[] records = new MoveRecord[states.length];
    for (int i = 0; i < states.length; ++i)
    {
      records[i] = new MoveRecord(states[i], i);
    }
    StringUtil.printStateArray(states, 0);
    System.out.println();
    util.moveBlocks(states, records);
    StringUtil.printStateArray(states, 8);
    System.out.println();
    for (int i = 0; i < records.length; ++i)
    {
      System.out.println(i + " " + records[i].toString());
    }

  }

}
