package com.company;

import java.util.ArrayList;

public class Board {

    // Board Size
    private final static int BOARD_SIDE_LENGTH = 9;


    // Board Alignment Box
    public enum BoardBox {
        LEFT_TOP, CENTER_TOP, RIGHT_TOP,
        LEFT_CENTER, TRUE_CENTER, RIGHT_CENTER,
        LEFT_BOTTOM, CENTER_BOTTOM, RIGHT_BOTTOM
    }


    // All Fields
    private Cell[][] cells = new Cell[9][9];


    // Getters and Setters
    public Cell[][] getCells() {
        return cells;
    }
    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }


    // Checks if the board is solved
    public boolean isSolved(){

        for(int row = 0; row < BOARD_SIDE_LENGTH; row++){
            for(int col = 0; col < BOARD_SIDE_LENGTH; col++){
                if(!getCells()[row][col].isFilled()){
                    return false;
                }
            }
        }

        return true;

    }



    // return Row / Column / Box
    public ArrayList<Cell> collectRow(int row) {

        ArrayList<Cell> rowCells = new ArrayList<>();

        for (int i = 0; i < BOARD_SIDE_LENGTH; i++) {
            rowCells.add(getCells()[row][i]);
        }

        return rowCells;

    }
    public ArrayList<Cell> collectColumn(int column) {

        ArrayList<Cell> columnCells = new ArrayList<>();

        for (int i = 0; i < BOARD_SIDE_LENGTH; i++) {
            columnCells.add(getCells()[i][column]);
        }

        return columnCells;

    }
    public ArrayList<Cell> collectBox(BoardBox boardBox){

        ArrayList<Cell> boxCells = new ArrayList<>();

        switch (boardBox){

            case LEFT_TOP:
                for(int row = 0; row < 3; row++){
                    for(int col = 0; col < 3; col++){
                        boxCells.add(getCells()[row][col]);
                    }
                }
                break;

            case CENTER_TOP:
                for(int row = 0; row < 3; row++){
                    for(int col = 3; col < 6; col++){
                        boxCells.add(getCells()[row][col]);
                    }
                }
                break;

            case RIGHT_TOP:
                for(int row = 0; row < 3; row++){
                    for(int col = 6; col < 9; col++){
                        boxCells.add(getCells()[row][col]);
                    }
                }
                break;

            case LEFT_CENTER:
                for(int row = 3; row < 6; row++){
                    for(int col = 0; col < 3; col++){
                        boxCells.add(getCells()[row][col]);
                    }
                }
                break;

            case TRUE_CENTER:
                for(int row = 3; row < 6; row++){
                    for(int col = 3; col < 6; col++){
                        boxCells.add(getCells()[row][col]);
                    }
                }
                break;

            case RIGHT_CENTER:
                for(int row = 3; row < 6; row++){
                    for(int col = 6; col < 9; col++){
                        boxCells.add(getCells()[row][col]);
                    }
                }
                break;

            case LEFT_BOTTOM:
                for(int row = 6; row < 9; row++){
                    for(int col = 0; col < 3; col++){
                        boxCells.add(getCells()[row][col]);
                    }
                }
                break;

            case CENTER_BOTTOM:
                for(int row = 6; row < 9; row++){
                    for(int col = 3; col < 6; col++){
                        boxCells.add(getCells()[row][col]);
                    }
                }
                break;

            case RIGHT_BOTTOM:
                for(int row = 6; row < 9; row++){
                    for(int col = 6; col < 9; col++){
                        boxCells.add(getCells()[row][col]);
                    }
                }
                break;

        }

        return boxCells;
    }



    // tests if the board is legal
    private boolean testLegalityOfCellGroup(ArrayList<Cell> cellGroup){

        ArrayList<Integer> presentDigits = new ArrayList<>();

        for(Cell cell: cellGroup){
            if(cell.isFilled()){
                if(presentDigits.contains(cell.getDigit())){
                    return false; // returns false in a case of a repeated digit value
                } else {
                    presentDigits.add(cell.getDigit());
                }
            }
        }

        return true; // returns true otherwise
    }

    private boolean testLegalityOfAllRows(){

        for(int row = 0; row < BOARD_SIDE_LENGTH; row++){
            if(!testLegalityOfCellGroup(collectRow(row))){
                return false;
            }
        }

        return true;
    }
    private boolean testLegalityOfAllColumns(){

        for(int col = 0; col < BOARD_SIDE_LENGTH; col++){
            if(!testLegalityOfCellGroup(collectColumn(col))){
                return false;
            }
        }

        return true;
    }
    private boolean testLegalityOfAllBoxes(){

        for(BoardBox box: BoardBox.values()){
            if(!testLegalityOfCellGroup(collectBox(box))){
                return false;
            }
        }

        return true;



    }

    public boolean isLegal(){
        return testLegalityOfAllRows() && testLegalityOfAllColumns() && testLegalityOfAllBoxes();
    }


    // loos for a not filled cell with 0 candidates
    public boolean hasUnsolvableCells(){
        for (int row = 0; row < BOARD_SIDE_LENGTH; row++) {
            for (int col = 0; col < BOARD_SIDE_LENGTH; col++) {
                if(!getCells()[row][col].isFilled() && getCells()[row][col].getCandidates().isEmpty()){
                    return true;
                }
            }
        }

        return false;
    }



    // Copying Method
    public Board copy(){

        Board copiedBoard = new Board();

        for (int row = 0; row < BOARD_SIDE_LENGTH; row++) {
            for (int col = 0; col < BOARD_SIDE_LENGTH; col++) {
                copiedBoard.cells[row][col] = this.cells[row][col].copy();
            }
        }

        return copiedBoard;

    }

    // Compares 2 board states
    public boolean theSameAs(Board otherBoard){

        for(int row = 0; row < BOARD_SIDE_LENGTH; row++){
            for(int col = 0; col < BOARD_SIDE_LENGTH; col++){

                if(this.getCells()[row][col].getDigit() != otherBoard.getCells()[row][col].getDigit()){
                    return false;
                }

                if(!this.getCells()[row][col].isFilled() &&
                    (!this.getCells()[row][col].getCandidates().containsAll(otherBoard.getCells()[row][col].getCandidates()) ||
                    !otherBoard.getCells()[row][col].getCandidates().containsAll(this.getCells()[row][col].getCandidates())  ||
                    this.getCells()[row][col].getCandidates().size() != otherBoard.getCells()[row][col].getCandidates().size() ) ) {
                    return false;
                }

            }
        }

        return true;
    }

    // Checks if a cell is a part of this board
    public boolean containsCell(Cell cell){
        for (int row = 0; row < BOARD_SIDE_LENGTH; row++) {
            for (int col = 0; col < BOARD_SIDE_LENGTH; col++) {
                if(getCells()[row][col].equals(cell)){
                    return true;
                }
            }
        }
        return false;
    }


    // Board state printing method
    public void printBoardState(){

        System.out.println("\n Board state:");

        for(int row = 0; row < BOARD_SIDE_LENGTH; row++){

            if (row % 3 == 0){
                System.out.println();
            }


            for(int col = 0; col < BOARD_SIDE_LENGTH; col++){

                if (col % 3 == 0){
                    System.out.print(" ");
                }

                System.out.print(getCells()[row][col].getDigit() + " ");

            }


            System.out.println();

        }

    }

    // Board state printing method
    public void printCandidateAmount(){

        System.out.println("\n Amount of candidates:");

        for(int row = 0; row < BOARD_SIDE_LENGTH; row++){

            if (row % 3 == 0){
                System.out.println();
            }


            for(int col = 0; col < BOARD_SIDE_LENGTH; col++){

                if (col % 3 == 0){
                    System.out.print(" ");
                }

                if (getCells()[row][col].getCandidates().size() > 0){
                    System.out.print(getCells()[row][col].getCandidates().size() + " ");
                } else {
                    System.out.print("S ");
                }

            }


            System.out.println();

        }

    }


    // Constructor

    // Empty
    public Board(){

        for (int row = 0; row < BOARD_SIDE_LENGTH; row++) {
            for (int col = 0; col < BOARD_SIDE_LENGTH; col++) {

                cells[row][col] = new Cell();

            }
        }

    }

    // From int[][]
    public Board(int[][] board) {

        for (int row = 0; row < BOARD_SIDE_LENGTH; row++) {
            for (int col = 0; col < BOARD_SIDE_LENGTH; col++) {

                cells[row][col] = new Cell(board[row][col]);

            }
        }

    }




}