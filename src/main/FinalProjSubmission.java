//Lance Nelkin and Kat Hoadley - 8 Queens Project

package main;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FinalProjSubmission {

    public static Random random = new Random();

//    public static boolean[][] currentQueenMap;
//    public static boolean[][] bestQueenMap;

//    public static int[] currentQueenMap;
//    public static int[] bestQueenMap;

    public static List<int[]> boardList = new ArrayList<>();

//    public static int bestQueenMapValue = Integer.MAX_VALUE;
//    public static boolean solvedConflict = false;

    public static int MAX_RESTARTS = 1000;
    public static int totalSuccessorsGenerated;
    public static int totalRestarts;
    public static int totalFailures;

    public static int randomBoardStates;

    public static void main(String[] args){
        greetUser();
    }

    public static void greetUser(){
        Scanner input = new Scanner(System.in);
        System.out.println("Hello! How many random board states should be initialized?");
        randomBoardStates = input.nextInt();
        initializer();
    }

    public static void initializer(){
        generateBoards();
        randomRestart();
        firstChoice();

        if (randomBoardStates == 1){
            visualizer();
        }
    }

    public static void generateBoards() {
        for (int i = 0; i < randomBoardStates; i++) {
            boardList.add(randomizeQueenMap());
        }
    }

    //TODO complete this function - reference instructions!
    public static void visualizer(){

    }

    //TODO complete this function - alg for random restart
    public static void randomRestart(){

    }

    //TODO complete this function - alg for first choice
    public static void firstChoice(){

    }

    public static int calculateHeuristic(int[] board){
        int conflicts = 0;
        for (int i = 0; i < board.length; i++){
            for (int j = i + 1; j < board.length; j++){
                //row conflict
                if (board[i] == board[j]){
                    conflicts++;
                }
                //diagonal conflict
                else if (Math.abs(board[i] - board[j]) == Math.abs(i-j)){
                    conflicts++;
                }
            }
        }
        return conflicts;
    }

    public static List<int[]> getSuccessors(int[] board){
        List<int[]> successors = new ArrayList<>();

        for (int col = 0; col < 8; col++){
            for (int row = 0; row < 8; row++){
                if (row != board[col]){
                    int[] successor = board.clone();
                    successor[col] = row;
                    successors.add(successor);
                }
            }
        }
        return successors;
    }

    //TODO track and display stats
    public static void statistics(){

    }

    public static int[] randomizeQueenMap() {
        int[] queenMap = new int[8];
        for (int col = 0; col < 8; col++) {
            queenMap[col] = random.nextInt(8);
        }
        return queenMap;
    }

//    public static boolean[][] randomizeQueenMap(){
//        boolean[][] queenMap = new boolean[8][8];
//
//        for (int i = 0; i < 8; i++){
//            queenMap[i][random.nextInt(8)] = true;
//        }
//
//        return queenMap;
//    }
//
//    public static void solveQueens(){
//        int currentQueenMapValue;
//
//        while (!solvedConflict){
//
//            currentQueenMap = randomizeQueenMap();
//
//            currentQueenMapValue = calculateQueenMapValue(currentQueenMap);
//
//            if (currentQueenMapValue == 0){
//                solvedConflict = true;
//            }
//
//            if (bestQueenMapValue >= currentQueenMapValue){
//                //why does cloning 2D arrays suck...
//                bestQueenMapValue = currentQueenMapValue;
//                bestQueenMap = new boolean[8][8];
//                for (int row = 0; row < 8; row++) {
//                    bestQueenMap[row] = currentQueenMap[row].clone();
//                }
//            }
//        }
//    }

    //I tried so hard to make this better than the connect 4 version but oh well its ugly
//    private static int calculateQueenMapValue(boolean[][] queenMap) {
//        int conflicts = 0;
//
//        for (int row1 = 0; row1 < 8; row1++) {
//            for (int col1 = 0; col1 < 8; col1++) {
//
//                if (!queenMap[row1][col1]) {
//                    continue;
//                }
//
//                for (int row2 = row1; row2 < 8; row2++) {
//                    for (int col2 = 0; col2 < 8; col2++) {
//                        if (row2 == row1 && col2 <= col1) {
//                            continue;
//                        }
//
//                        if (!queenMap[row2][col2]) {
//                            continue;
//                        }
//
//                        //row
//                        if (row1 == row2) {
//                            conflicts++;
//                        }
//                        //column
//                        else if (col1 == col2) {
//                            conflicts++;
//                        }
//                        //diagonal
//                        else if (Math.abs(row1 - row2) == Math.abs(col1 - col2)) {
//                            conflicts++;
//                        }
//                    }
//                }
//            }
//        }
//
//        return conflicts;
//    }


}