package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Cell{


    // EMPTY value
    private final static int EMPTY = 0;

    // Digit, default: empty
    private int digit = EMPTY;

    // Candidates
    private ArrayList<Integer> candidates = new ArrayList<>();




    // Getters and Setters
    public int getDigit() {
        return digit;
    }
    public void setDigit(int digit) {
        this.digit = digit;
    }
    public ArrayList<Integer> getCandidates() {
        return candidates;
    }
    public void setCandidates(ArrayList<Integer> candidates) {
        this.candidates = candidates;
    }




    // is Filled ( == is not empty )
    public boolean isFilled(){
        return getDigit() != EMPTY;
    }

    // is Naked ( == only one candidate left )
    public boolean isNaked(){
        return getCandidates().size() == 1;
    }




    // Solves Naked Field ( if the field is naked then it gets filled, otherwise nothing happens )
    public void solveIfNaked(){
        if(isNaked()){
            setDigit(getCandidates().get(0));
            getCandidates().removeAll(getCandidates());
        }
    }




    // Trims Candidates ( removes given values form candidates list)
    public void trimCandidates(int ... candidatesToTrim){
        for (int candidate : candidatesToTrim){
            if (getCandidates().contains(candidate)){
                getCandidates().remove((Integer)candidate);
            }
        }

        // TODO necessary?
        if(isFilled()){
            getCandidates().removeAll(getCandidates());
        }
    }




    // Copying method
    protected Cell copy(){

        // Superclass
        Cell clonedCell = new Cell();

        // Digit
        clonedCell.setDigit(this.getDigit());

        // Candidates
        clonedCell.getCandidates().removeAll(clonedCell.getCandidates());

        for (int candidate : this.getCandidates()){
            clonedCell.getCandidates().add(candidate);
        }

        // Return
        return clonedCell;
    }



    // Constructors

    // Empty Cell
    public Cell(){

        // empty -> initially given all candidates
        getCandidates().addAll(Arrays.asList(1,2,3,4,5,6,7,8,9));

    }

    // Filled Cell
    public Cell(int digit){
        setDigit(digit);

        if(!isFilled()){
            getCandidates().addAll(Arrays.asList(1,2,3,4,5,6,7,8,9));
        }

    }


}
