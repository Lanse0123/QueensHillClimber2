//Lance Nelkin and Kat Hoadley - 8 Queens Project

package main;

import java.util.*;

public class FinalProjSubmission {

    public static Random random = new Random();

    public static List<int[]> boardList = new ArrayList<>();
    public static int[] finalBoard = new int[8];

    public static int MAX_RESTARTS = 1000;
    public static int totalSuccessorsGenerated;
    public static int totalRestarts;
    public static int totalFailures;
    public static int totalSteps;
    public static int randomBoardStates;

    public static void main(String[] args){
        greetUser();
    }

    public static void greetUser(){
        boolean inputIsValid = false;

        while (!inputIsValid) {
            try {
                Scanner input = new Scanner(System.in);
                System.out.println("Hello! How many random board states should be initialized?");
                randomBoardStates = input.nextInt();

                //I managed to get a divide by 0 error, so this check is here now
                if (randomBoardStates < 1) {
                    System.out.println("WRONG, TRY AGAIN.");
                    continue;
                }

                inputIsValid = true;

                //I managed to get inputMismatchException
            } catch (Exception e){
                System.out.println("WRONG, TRY AGAIN.");
            }
        }

        initializer();
    }

    public static void initializer(){
        generateBoards();

        solveWithRandomRestart();
        outputStatistics(totalSuccessorsGenerated, totalRestarts, totalFailures, "Random Restart");
        if (randomBoardStates == 1){
            visualizer("Random Restart");
        }

        solveWithFirstChoice();
        outputStatistics(totalSuccessorsGenerated, totalRestarts, totalFailures, "First Choice");
        if (randomBoardStates == 1){
            visualizer("First Choice");
        }
    }

    public static void generateBoards() {
        for (int i = 0; i < randomBoardStates; i++) {
            boardList.add(randomBoard());
        }
    }

    public static void visualizer(String algorithmName){
        if (totalRestarts >= MAX_RESTARTS){
            System.out.println("We failed to find a solution using the " + algorithmName + " algorithm.");

        } else {

            int[] board = boardList.getFirst();

            int startingHeuristic = calculateHeuristic(board);

            System.out.println("We used the " + algorithmName + " algorithm.");
            System.out.println("This is the initial placement of the queens \uD83D\uDC85\uD83D\uDC85\uD83D\uDC85");
            System.out.println("It has " + startingHeuristic + " conflicts");
            displayBoard(board);

            System.out.println("\nWe have found a solution in " + totalSteps + " steps.");
            System.out.println("This required " + totalRestarts + " random restarts.");
            System.out.println("The solution is: ");
            displayBoard(finalBoard);
        }
    }

    public static void displayBoard(int[] board){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++) {
                //weirdness checking -1 and equal to 7 because off by 1 and board was upside down
                if (board[j] - 1 == 7 - i) {
                    System.out.print("Q");
                } else {
                    System.out.print("-");
                }
            }
            System.out.print("\n");
        }
    }

    public static void solveWithRandomRestart(){
        totalSuccessorsGenerated = 0;
        totalRestarts = 0;
        totalFailures = 0;
        totalSteps = 0;

        for (int[] ints : boardList) {
            int[] currentBoard = ints.clone();
            int currentHeuristic = calculateHeuristic(currentBoard);

            int boardRestarts = 0;
            int boardSuccessors = 0;
            boolean solved = false;

            while (boardRestarts < MAX_RESTARTS) {
                if (currentHeuristic == 0) {
                    solved = true;
                    break;
                }

                List<int[]> successors = getSuccessors(currentBoard);
                boardSuccessors += successors.size();

                int bestSuccessorHeuristic = Integer.MAX_VALUE;
                int[] bestSuccessor = null;

                for (int[] successor : successors) {
                    int heuristic = calculateHeuristic(successor);
                    if (heuristic < bestSuccessorHeuristic) {
                        bestSuccessorHeuristic = heuristic;
                        bestSuccessor = successor;
                    }
                }

                if (bestSuccessorHeuristic < currentHeuristic) {
                    currentBoard = bestSuccessor;
                    currentHeuristic = bestSuccessorHeuristic;
                    totalSteps++;

                } else {
                    boardRestarts++;
                    currentBoard = randomBoard();
                    currentHeuristic = calculateHeuristic(currentBoard);
                }

                if (bestSuccessor != null) {
                    finalBoard = bestSuccessor.clone();
                }
            }

            if (!solved) {
                totalFailures++;
            }

            totalSuccessorsGenerated += boardSuccessors;
            totalRestarts += boardRestarts;
        }
    }

    public static void solveWithFirstChoice(){
        totalSuccessorsGenerated = 0;
        totalRestarts = 0;
        totalFailures = 0;
        totalSteps = 0;

        for (int[] ints : boardList) {
            int[] currentBoard = ints.clone();
            int currentHeuristic = calculateHeuristic(currentBoard);

            int boardRestarts = 0;
            int boardSuccessors = 0;
            boolean solved = false;

            while (boardRestarts < MAX_RESTARTS) {
                if (currentHeuristic == 0) {
                    solved = true;
                    break;
                }

                List<int[]> successors = getSuccessors(currentBoard);
                boardSuccessors += successors.size();

                int bestSuccessorHeuristic = Integer.MAX_VALUE;
                int[] bestSuccessor = null;

                for (int[] successor : successors) {
                    int heuristic = calculateHeuristic(successor);
                    if (heuristic < bestSuccessorHeuristic) {
                        bestSuccessorHeuristic = heuristic;
                        bestSuccessor = successor;
                        break;
                    }
                }

                if (bestSuccessorHeuristic < currentHeuristic) {
                    currentBoard = bestSuccessor;
                    currentHeuristic = bestSuccessorHeuristic;
                    totalSteps++;

                } else {
                    boardRestarts++;
                    currentBoard = randomBoard();
                    currentHeuristic = calculateHeuristic(currentBoard);
                }

                if (bestSuccessor != null) {
                    finalBoard = bestSuccessor.clone();
                }
            }

            if (!solved) {
                totalFailures++;
            }

            totalSuccessorsGenerated += boardSuccessors;
            totalRestarts += boardRestarts;
        }
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

        for (int col = 1; col < 8; col++){
            for (int row = 1; row < 8; row++){
                if (row != board[col]){
                    int[] successor = board.clone();
                    successor[col] = row;
                    successors.add(successor);
                }
            }
        }

        return successors;
    }

    public static void outputStatistics(int totalSuccessors, int totalRestarts, int totalFailures, String algorithmName){
        System.out.println(randomBoardStates + " puzzles.");
        System.out.println(MAX_RESTARTS + " restarts allowed per search.");

        int avgSuccessors = totalSuccessors / randomBoardStates;
        int avgRestarts = totalRestarts / randomBoardStates;

        System.out.println("Hill-climbing with " + algorithmName + ": average search cost/number of " +
                "successors: " + avgSuccessors + ", average number of restarts required: " +
                avgRestarts + ", total number of failed searches: " + totalFailures + "\n");
    }

    public static int[] randomBoard() {
        int[] newBoard = new int[8];

        for (int col = 0; col < 8; col++) {
            newBoard[col] = random.nextInt(8) + 1;
        }

        return newBoard;
    }
}