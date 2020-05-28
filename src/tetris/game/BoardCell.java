package tetris.game;

/*
bord is opgebouwd uit vakjes (boardcells);
bij initialisatie zijn ze allemaal leeg.
blokken kunnen boardcells vullen.
 */

import java.util.Arrays;

public class BoardCell {
    private final BlockType blockType;

    //lege boardcell zonder blok
    public BoardCell() {
        blockType = null;
    }

    //boardcell vullen met blok
    private BoardCell(BlockType type) {
        blockType = type;
    }

    //controleren of er een blok op dit vakje zit
    public boolean isEmpty() {
        return blockType == null;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public static BoardCell getCell(BlockType blockType) {
        return new BoardCell(blockType);
    }

    //leeg bord maken
    public static BoardCell[] getEmptyArray(int size) {
        BoardCell[] cells = new BoardCell[size];
        Arrays.fill(cells, new BoardCell());
        return cells;
    }
}
