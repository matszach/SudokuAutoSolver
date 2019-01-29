package com.company;

public class Main {

    public static void main(String[] args) {

        int [][] sudokuPuzzle = new int[][]{

                { 5, 3, 0,  0, 7, 0,  0, 0, 0},
                { 6, 0, 0,  1, 9, 5,  0, 0, 0},
                { 0, 9, 8,  0, 0, 0,  0, 6, 0},

                { 8, 0, 0,  0, 6, 0,  0, 0, 3},
                { 4, 0, 0,  8, 0, 3,  0, 0, 1},
                { 7, 0, 0,  0, 2, 0,  0, 0, 6},

                { 0, 6, 0,  0, 0, 0,  2, 8, 0},
                { 0, 0, 0,  4, 1, 9,  0, 0, 5},
                { 0, 0, 0,  0, 8, 0,  0, 7, 9},

        };


        Board boardToSolve = new Board(sudokuPuzzle);


        Solver.solve(boardToSolve);


        boardToSolve.printBoardState();
        System.out.println("Board solved: " + boardToSolve.isSolved());
        System.out.println("Board state is legal :" + boardToSolve.isLegal());
        boardToSolve.printCandidateAmount();






    }
}
