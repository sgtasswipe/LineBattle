import java.util.Random;
import java.util.Scanner;

public class LineBattle {
    Scanner in = new Scanner(System.in);
    int playerPosition = 11 - rollDice();
    int computerPosition = -11 + rollDice();
    int playerSoldiers = 25;
    int computerSoldiers = 25;
    int playerFirepower = 2500;
    int computerFirepower = 2500;
    char playerDecision;
    int playerBombPosition;
    int playerBomb = 1;
    int computerBomb = 1;
    boolean bombDetonated;

    public static void main(String[] args) {
        new LineBattle().run();
    }

    private void run() {
        startGame();
        boolean gameOver=false;
        computerChoice();
        int turnCounter = 0;

        do {
            if (turnCounter % 2 == 0) {
                playerDecision = playerChoice();

                if (playerDecision == 'f') {
                    playerMoveForward();
                }
                if (playerDecision == 'r') {
                    playerMoveBack();
                }
                if (playerDecision == 's') {
                    showstats();
                }
                if (playerDecision == 'a') {
                    playerAttack();
                }
                if (playerDecision == 'q') {
                    surrender();
                }
                if (playerDecision == 'b') {
                    plantBomb();
                }
                if (playerDecision == 'd') {
                    detonateBomb();
                }
            } else {
                computerChoice();
            }
            gameOver = determineWinningConditions();
            turnCounter++;
        } while (!gameOver);
    }

    private void computerChoice() {
        System.out.println("ENEMY CHOOSES: ");
        Random random = new Random();
        int num = random.nextInt(3);
        if (num == 0) {
            System.out.println("Enemy moves forward");
        }
        if (num == 1) {
            System.out.println("Enemy retreats");
        }
        if (num == 2) {
            System.out.println("Enemy attacks!");
        }

        switch (num) {
            case 0 -> computerMoveForward();
            case 1 -> computerMoveBack();
            case 2 -> computerAttack();
        }
    }

    private void computerAttack() {

            int diceResult = rollDice();
            int amountOfDmg = diceResult * 100;
            computerFirepower -= amountOfDmg;
            int distance = Math.abs(playerPosition - computerPosition);

            if (distance < 6) {
                System.out.println("You are in range");
                playerSoldiers -= (6 - distance);
                System.out.println("The enemy has killed " + (6 - distance) + " of your soldiers");
            } else {
                System.out.println("MISS!");
            }


        }


    private int computerMoveBack() {
        int distanceMoved = 0;
        int diceResult = rollDice();

        if (diceResult == 1 || diceResult == 2) {
            distanceMoved = 1;


        } else if (diceResult == 3 || diceResult == 4) {
            distanceMoved = 2;

        } else {
            distanceMoved = 3;
        }
        if (computerPosition - distanceMoved < -10) {
            computerPosition = -10;
        } else {
            computerPosition -= distanceMoved;

        }
        return computerPosition;
    }


    private int computerMoveForward() {
        int distanceMoved = 0;
        int diceResult = rollDice();
        if (diceResult % 2 == 0) {
            distanceMoved = 2;

        } else {
            distanceMoved = 1;

        }
        if (computerPosition + distanceMoved > 10) {
            computerPosition = 10;
        } else {
            computerPosition += distanceMoved;
        }
        return computerPosition;


    }

    private char playerChoice() {

            System.out.println("Enter your move: \n (f) Forward,    (r) Retreat, \n (a) Attack, (q) Surrender \n (s) Show stats ");

            if (playerPosition < 0 && playerBomb == 1) {
                System.out.println("(b) Bomb");
            }

            if (!bombDetonated &&playerBomb == 0 && playerPosition >= playerBombPosition + 6 && playerPosition > 0) {
                System.out.println("(d) Detonate bomb");
                char choice = in.nextLine().charAt(0);

                if(choice=='d') {
                    bombDetonated=true;
                }

                return choice;
            }

            return in.nextLine().charAt(0);
        }



        private void surrender() {
        char playerResult = playerChoice();
        if (playerResult == 'q') {
            System.out.println("YOU HAVE SURRENDERED YOU COWARD");
            playerSoldiers = 0;
        }
    }

    void playerAttack() {
        int diceResult = rollDice();
        int amountOfDmg = diceResult * 100;
        playerFirepower = playerFirepower - amountOfDmg;
        int distance = Math.abs(playerPosition - computerPosition);
        if (distance < 6) {
            System.out.println("Enemy is in range");
            computerSoldiers -= (6 - distance);
            System.out.println("You have killed " + (6 - distance) + " Enemy soldiers");
        } else {
            System.out.println("MISS!");
        }
        // man kan kun skade når distance er 5 eller under
        // skader altid 6 - distance

    }


    public void plantBomb() {
        if (playerPosition <= -1 && playerBomb == 1) {
            System.out.println("Bomb has been planted");
            playerBombPosition = playerPosition;
            int distance = Math.abs(playerBombPosition - computerPosition);
            playerBomb--;


        } else {
            System.out.println("You can't plant a bomb at this position!");
        }
    }

    public void scoutReport() {
        //todo
    }

    public void detonateBomb() {

        if (playerBomb == 0 && playerPosition >= playerBombPosition + 6 && playerPosition > 0) {
            System.out.println("KABOOOM");

            if (computerPosition == playerBombPosition && playerBombPosition != -10) {
                System.out.println("Enemy lost 10 soldiers");
            } else {
                System.out.println("Enemy lost 2 soldiers");
                computerSoldiers -= 2;
            }
        } else {
            System.out.println("You can't detonate the bomb at this position!");
        }
    }


        public void showstats() {
        System.out.println("Your position is: " + playerPosition);
        System.out.println("Amount of solider " + playerSoldiers);
        System.out.println("Amount of firepower: " + playerFirepower);
        System.out.println("Amount of bombs: " + playerBomb);
        System.out.println("Bomb is planted at position: " + playerBombPosition);
        System.out.println("Enemy position: " + computerPosition);
        System.out.println("Enemy amount of soldier: " + computerFirepower);
        System.out.println("Enemy soldiers: " + computerSoldiers);
    }

    public int playerMoveForward() {
        int distanceMoved = 0;
        int diceResult = rollDice();
        if (diceResult % 2 == 0) {
            distanceMoved = 2;
        } else {
            distanceMoved = 1;
        }
        if (playerPosition - distanceMoved < -10) {
            playerPosition = -10;
            System.out.println("Youre at the enemy's base. You can't go further");
        } else {
            playerPosition -= distanceMoved;
        }
        System.out.println("Your Position is: " + playerPosition);
        return playerPosition;

    }

    public int playerMoveBack() {
        int distanceMoved = 0;
        int diceResult = rollDice();

        if (diceResult == 1 || diceResult == 2) {
            distanceMoved = 1;


        } else if (diceResult == 3 || diceResult == 4) {
            distanceMoved = 2;

        } else {
            distanceMoved = 3;
        }
        if (playerPosition + distanceMoved > 10) {
            playerPosition = 10;
            System.out.println("You're at your own base. You will get a new bomb\nif you dont have one");
        } else {
            playerPosition += distanceMoved;
        }
        System.out.println("Your position is: " + playerPosition);
        return playerPosition;

    }


    private void startGame() {
        System.out.println("WELCOME TO LINE BATTLE");
        System.out.println("LETS BEGIN");

    }



    private int rollDice() {
        Random random = new Random();
        int dice = random.nextInt(6) + 1;
        return dice;
    }

    private boolean determineWinningConditions() {

        if (computerSoldiers <= 0) {
            System.out.println("Congratulations! You've won by eliminating all enemy soldiers!");
            return true;
        }


        if (playerSoldiers <= 0) {
            System.out.println("The computer has won by eliminating all your soldiers. Better luck next time!");
            return true;
        }



        return false; // No winner yet
    }
}

