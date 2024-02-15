import java.util.Scanner;

public class BattleShip {
    // Declare player and computer boards
    String playerBoardArrayTop[][] = new String[10][10];
    String playerBoardArrayBottom[][] = new String[10][10];

    String computerBoardArrayTop[][] = new String[10][10];
    String computerBoardArrayBottom[][] = new String[10][10];

    Scanner userInput = new Scanner(System.in);

    // Constructor
    public BattleShip() {

        System.out.println("Let's Play Battleship");
        System.out.println("Place your Boats!");

        // Initializes all boards to be filled with dashes
        for (int i = 0; i < playerBoardArrayTop.length; i++) {
            for (int j = 0; j < playerBoardArrayTop[0].length; j++) {
                playerBoardArrayTop[i][j] = "-";
            }
        }

        for (int i = 0; i < playerBoardArrayBottom.length; i++) {
            for (int j = 0; j < playerBoardArrayBottom[0].length; j++) {
                playerBoardArrayBottom[i][j] = "-";
            }
        }

        for (int i = 0; i < computerBoardArrayTop.length; i++) {
            for (int j = 0; j < computerBoardArrayTop[0].length; j++) {
                computerBoardArrayTop[i][j] = "-";
            }
        }

        for (int i = 0; i < computerBoardArrayBottom.length; i++) {
            for (int j = 0; j < computerBoardArrayBottom[0].length; j++) {
                computerBoardArrayBottom[i][j] = "-";
            }
        }

        // Print initial boards
        printBoards();

        // Place ships on the board
        placeShip("Carrier", 5);
        printBoards();
        placeShip("Destroyer", 4);
        printBoards();
        placeShip("Submarine", 3);
        printBoards();
        placeShip("TSubmarine", 3);
        printBoards();
        placeShip("U-Boat", 2);
    }

    // Method to place a ship on the board
    public void placeShip(String shipName, int shipLength) {
        // Extract the first letter of the shipName as a shipSubName
        String shipSubName = shipName.substring(0, 1);

        int directionPlaced;
        int xCoordinate;
        int yCoordinate;

        // Loop to get valid coordinates from the user
        do {
            do {
                System.out.println("Input " + shipName + " X coordinate");
                xCoordinate = userInput.nextInt();
                while (xCoordinate > 10 || xCoordinate < 1) {
                    System.out.println("Invalid input. Please enter a valid X coordinate");
                    System.out.println("Input " + shipName + " X coordinate");
                    xCoordinate = userInput.nextInt();
                }

                System.out.println("Input " + shipName + " Y coordinate");
                yCoordinate = userInput.nextInt();
                while (yCoordinate > 10 || yCoordinate < 1) {
                    System.out.println("Invalid input. Please enter a valid Y coordinate");
                    System.out.println("Input " + shipName + " Y coordinate");
                    yCoordinate = userInput.nextInt();
                }
            } while ((playerBoardArrayTop[yCoordinate - 1][xCoordinate - 1].equals("C")) ||
                    (playerBoardArrayTop[yCoordinate - 1][xCoordinate - 1].equals("D")) ||
                    (playerBoardArrayTop[yCoordinate - 1][xCoordinate - 1].equals("S")) ||
                    (playerBoardArrayTop[yCoordinate - 1][xCoordinate - 1].equals("T")) ||
                    (playerBoardArrayTop[yCoordinate - 1][xCoordinate - 1].equals("U")));

            // Loop to get a valid direction for placing the ship
            do {
                System.out.println("What direction would you like to go?");
                System.out.println("up = 1 \ndown = 2\nleft = 3\nright = 4");
                directionPlaced = userInput.nextInt();
                if (isOutOfBound(yCoordinate, xCoordinate, shipLength, directionPlaced)) {
                    System.out.println("Invalid direction. This will cause an out-of-bounds error. Please choose another direction.");
                }
            } while (isOutOfBound(yCoordinate, xCoordinate, shipLength, directionPlaced));

        } while (doesOverlap(yCoordinate - 1, xCoordinate - 1, shipLength, directionPlaced));

        // Update the corresponding player board based on the ship placement
        updatePlayerBoard(shipSubName, shipLength, yCoordinate - 1, xCoordinate - 1, directionPlaced);
    }

    // Method to check if the ship overlaps with existing ships on the board
    private boolean doesOverlap(int y, int x, int shipLength, int direction) {
        for (int i = 0; i < shipLength; i++) {
            if (direction == 1 && !playerBoardArrayTop[y - i][x].equals("-")) {
                return true; // overlap upward
            } else if (direction == 2 && !playerBoardArrayTop[y + i][x].equals("-")) {
                return true; // overlap downward
            } else if (direction == 3 && !playerBoardArrayTop[y][x - i].equals("-")) {
                return true; // overlap leftward
            } else if (direction == 4 && !playerBoardArrayTop[y][x + i].equals("-")) {
                return true; // overlap rightward
            }
        }
        return false;
    }

    // Method to check if the ship placement is out of bounds
    private boolean isOutOfBound(int y, int x, int shipLength, int direction) {
        if (direction == 1 && y - shipLength < 0) {
            return true; // out of bounds upward
        } else if (direction == 2 && y + shipLength > 10) {
            return true; // out of bounds downward
        } else if (direction == 3 && x - shipLength < 0) {
            return true; // out of bounds leftward
        } else if (direction == 4 && x + shipLength > 10) {
            return true; // out of bounds rightward
        }
        return false;
    }

    // Method to update the player board and bottom computer board after ship placement
    private void updatePlayerBoard(String shipSubName, int shipLength, int yCoordinate, int xCoordinate, int direction) {
        for (int i = 0; i < shipLength; i++) {
            if (direction == 1) {  // up
                playerBoardArrayTop[yCoordinate - i][xCoordinate] = shipSubName;
                computerBoardArrayBottom[yCoordinate - i][xCoordinate] = shipSubName;
            } else if (direction == 2) {  // down
                playerBoardArrayTop[yCoordinate + i][xCoordinate] = shipSubName;
                computerBoardArrayBottom[yCoordinate + i][xCoordinate] = shipSubName;
            } else if (direction == 3) {  // left
                playerBoardArrayTop[yCoordinate][xCoordinate - i] = shipSubName;
                computerBoardArrayBottom[yCoordinate][xCoordinate - i] = shipSubName;
            } else if (direction == 4) {  // right
                playerBoardArrayTop[yCoordinate][xCoordinate + i] = shipSubName;
                computerBoardArrayBottom[yCoordinate][xCoordinate + i] = shipSubName;
            }
        }
    }

    // Method to print the player and computer boards
    public void printBoards() {

        System.out.print("    ");
        for (int i = 1; i <= 10; i++) {
            System.out.print(i + " ");
        }
        System.out.print("      ");
        for (int i = 1; i <= 10; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.print("  ---------TopGB---------");
        System.out.println("    ---------BottomGB---------");
        System.out.println();

        // Print player board
        for (int r = 0; r < playerBoardArrayTop.length; r++) {
            if (r < 9) {
                System.out.print((r + 1) + " | ");
            } else {
                System.out.print((r + 1) + "| ");
            }

            for (int c = 0; c < playerBoardArrayTop.length; c++) {

                System.out.print(playerBoardArrayTop[r][c]);
                System.out.print(" ");

            }
            System.out.print("  ");
            if (r < 9) {
                System.out.print((r + 1) + " | ");
            } else {
                System.out.print((r + 1) + "| ");
            }
            for (int c = 0; c < playerBoardArrayBottom.length; c++) {
                System.out.print(" ");
                System.out.print(playerBoardArrayBottom[r][c]);
            }
            System.out.print("    ");
            System.out.println();
        }
        System.out.println();
        for (int i = 0; i < playerBoardArrayTop[0].length; i++) {

        }

        // COMPUTER BOARD
        System.out.print("    ");
        for (int i = 1; i <= 10; i++) {
            System.out.print(i + " ");
        }
        System.out.print("      ");
        for (int i = 1; i <= 10; i++) {
            System.out.print(i + " ");
        }
        // computerBoardArrayTop
        System.out.println();
        System.out.print("  --------CTopGB---------");
        System.out.println("    --------CBottomGB---------");
        System.out.println();

        // Print computer board
        for (int r = 0; r < playerBoardArrayTop.length; r++) {
            if (r < 9) {
                System.out.print((r + 1) + " | ");
            } else {
                System.out.print((r + 1) + "| ");
            }

            for (int c = 0; c < playerBoardArrayTop.length; c++) {

                System.out.print(computerBoardArrayTop[r][c]);
                System.out.print(" ");

            }
            System.out.print("  ");
            if (r < 9) {
                System.out.print((r + 1) + " | ");
            } else {
                System.out.print((r + 1) + "| ");
            }
            for (int c = 0; c < playerBoardArrayBottom.length; c++) {
                System.out.print(" ");
                System.out.print(computerBoardArrayBottom[r][c]);
            }
            System.out.print("    ");
            System.out.println();
        }
        System.out.println();
        for (int i = 0; i < playerBoardArrayTop[0].length; i++) {

        }
    }

    // Method to simulate an attack
    public String attack(int row, int column, String boardArray[][]) {
        row -= 1;
        column -= 1;
        return "";
    }
}
