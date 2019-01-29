package com.company;

import java.util.ArrayList;

public class Solver {


    // collects all digits
    private static ArrayList<Integer> collectDigits(ArrayList<Cell> cellGroup){

        ArrayList<Integer> collectedDigits = new ArrayList<>();

        for(Cell cell: cellGroup){
            collectedDigits.add(cell.getDigit());
        }

        return collectedDigits;
    }



    // fills all naked cells
    private static void fillAllNakedCells(Board boardToSolve){

        for(int row = 0; row < boardToSolve.getCells()[1].length; row++){
            for(int col = 0; col < boardToSolve.getCells().length; col++){
                boardToSolve.getCells()[row][col].solveIfNaked();
            }
        }
    }



    // trims candidates from a cell group (row, column or box)
    private static void trimCandidatesFromCellGroup(ArrayList<Cell> cellGroup){

        ArrayList<Integer> candidatesToDiscard = collectDigits(cellGroup);

        for(Cell cell: cellGroup){
            for(Integer candidate: candidatesToDiscard){
                cell.trimCandidates(candidate);
            }
        }

    }

    private  static void trimCandidatesFromAllRows(Board boardToSolve){
        for(int row = 0; row < boardToSolve.getCells()[1].length; row++){
            trimCandidatesFromCellGroup(boardToSolve.collectRow(row));
        }
    }
    private  static void trimCandidatesFromAllColumns(Board boardToSolve){
        for(int col = 0; col < boardToSolve.getCells().length; col++){
            trimCandidatesFromCellGroup(boardToSolve.collectColumn(col));
        }
    }
    private  static void trimCandidatesFromAllBoxes(Board boardToSolve){
        for(Board.BoardBox box : Board.BoardBox.values()){
            trimCandidatesFromCellGroup(boardToSolve.collectBox(box));
        }
    }

    private static void trimCandidates(Board boardToSolve){
        trimCandidatesFromAllRows(boardToSolve);
        trimCandidatesFromAllColumns(boardToSolve);
        trimCandidatesFromAllBoxes(boardToSolve);
    }



    // in a given group, looks for a cell with unique candidate. Fills all found
    private static void testForUniqueCandidatesInCellGroup(ArrayList<Cell> cellGroup, Board boardToSolve){

        // candidates:                      x/1/2/3/4/5/6/7/8/9
        int[]candidateFrequency = new int[]{0,0,0,0,0,0,0,0,0,0};

        // fill the candidateFrequency Array
        for(Cell cell: cellGroup){
            for(Integer integer: cell.getCandidates()){
                candidateFrequency[integer] += 1;
            }
        }

        // solves all (if any were found) cells with singleton candidates
        for(int digit = 1; digit <= 9; digit++){
            if(candidateFrequency[digit] == 1){
                for(Cell cell: cellGroup){
                    if(cell.getCandidates().contains(digit)){
                        cell.setDigit(digit);
                        trimCandidates(boardToSolve);
                    }
                }
            }
        }


    }

    private static void testForUniqueCandidatesInAllRows(Board boardToSolve){
        for(int row = 0; row < boardToSolve.getCells()[1].length; row++){
            testForUniqueCandidatesInCellGroup(boardToSolve.collectRow(row), boardToSolve);
        }
    }
    private static void testForUniqueCandidatesInAllColumns(Board boardToSolve){
        for(int col = 0; col < boardToSolve.getCells().length; col++){
            testForUniqueCandidatesInCellGroup(boardToSolve.collectColumn(col), boardToSolve);
        }
    }
    private static void testForUniqueCandidatesInAllBoxes(Board boardToSolve){
        for(Board.BoardBox box: Board.BoardBox.values()){
            testForUniqueCandidatesInCellGroup(boardToSolve.collectBox(box), boardToSolve);
        }
    }

    private static void testForUniqueCandidates(Board boardToSolve){
        testForUniqueCandidatesInAllRows(boardToSolve);
        testForUniqueCandidatesInAllColumns(boardToSolve);
        testForUniqueCandidatesInAllBoxes(boardToSolve);
    }


    // choose a cell with the lowest amount of candidates
    // choose one of them and insert it, try to solve the board
    // if the solving fails -> the number was incorrect
    // otherwise -> board solved
    private static Cell findCellWithLowestAmountOfCandidates(Board boardToSolve){

        Cell wantedCell = new Cell();

        for(int row = 0; row < boardToSolve.getCells()[1].length; row++){
            for(int col = 0; col < boardToSolve.getCells().length; col++){

                if(!boardToSolve.getCells()[row][col].isFilled() && boardToSolve.getCells()[row][col].getCandidates().size() < wantedCell.getCandidates().size()){
                    wantedCell = boardToSolve.getCells()[row][col];
                }

            }
        }

        return wantedCell;
    }

    private static void solveByInserting(Board boardToSolve){

        Board boardCopy = boardToSolve.copy();

        Cell manipulatedCell = findCellWithLowestAmountOfCandidates(boardToSolve);
        Cell manipulatedCellReflection = findCellWithLowestAmountOfCandidates(boardCopy);

        int insertedDigit = manipulatedCell.getCandidates().get(0);

        manipulatedCell.setDigit(insertedDigit);
        manipulatedCell.getCandidates().removeAll(manipulatedCell.getCandidates());

        solve(boardToSolve);

        if (!boardToSolve.isLegal() || !boardToSolve.isSolved()){
            manipulatedCellReflection.getCandidates().remove((Integer)insertedDigit);
            boardToSolve = boardCopy.copy();
            solve(boardToSolve);
        }

    }



















    // Solve the board
    public static void solve(Board board) {

        trimCandidates(board);

        Board testForChangesBoard = new Board();

        System.out.println("in");

        while (!board.isSolved() && !board.hasUnsolvableCells() && board.isLegal()){


            // TODO delet coment
            //board.printCandidateAmount();
            //board.printBoardState();

            if(board.theSameAs(testForChangesBoard)){
               solveByInserting(board);
            }

            testForChangesBoard = board.copy();

            trimCandidates(board);
            testForUniqueCandidates(board);
            fillAllNakedCells(board);
            trimCandidates(board);

        }

        System.out.println("out");

    }



    // Constructor
    public Solver(){


    }
}
