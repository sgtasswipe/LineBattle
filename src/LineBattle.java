import java.util.Random;
import java.util.Scanner;

public class LineBattle {
    Scanner in = new Scanner(System.in);
    int playerPosition = 11 -rollDice() ;
    int computerPosition = -11 + rollDice();
    int playerSoldiers = 25;
    int computerSoldiers = 25;
    int playerFirepower  = 2500;
    int computerFirepower = 2500;
    public static void main(String[] args) {
        new LineBattle().run() ;
    }

    private void run() {
        startGame();
        do {

            if (playerChoice() == 'f') {
                moveForward();
            }
            if (playerChoice() == 'r') {
                moveBack();
            }
            if (playerChoice()== 's') {
                showstats();
            }
        }  while (playerSoldiers < 30); // temp
    }
        // if value of choice is x, do y:
       // computerChoice();


    private void computerChoice() {
        System.out.println("Figure it out");
    }
    void attack () {

    }

    private char playerChoice() {
        System.out.println("Enter your move: f, r, a, s");
        char choice = in.nextLine().charAt(0);
        return choice;
    }
    public void showstats () {
        System.out.println("position is: " + playerPosition);
        System.out.println("Amount of solider " + playerSoldiers);
        System.out.println("Amount of firepower: " + playerFirepower);
        System.out.println("Enemy position: "+ computerPosition);
        System.out.println("Enemy amount of soldier: " + computerFirepower);
    }
   public int moveForward () {
        int distanceMoved;

        if (rollDice() % 2 ==0) {
           playerPosition-=2;

            System.out.println("Your postion is: " + playerPosition);
            return  playerPosition;
        } else
            playerPosition --;
        System.out.println("Your position is: " + playerPosition);
        return playerPosition;
    }

    public int moveBack () {

        if (rollDice() ==1 || rollDice() ==2) {
            playerPosition++;
            System.out.println("Your postion is: " + playerPosition);
            return playerPosition;

        } else if (rollDice()== 3 || rollDice()==4) {
            playerPosition += 2;
        System.out.println("Your position is: " + playerPosition);
        return playerPosition;
        }   else
            playerPosition+=3;
            return playerPosition;
    }


    private void startGame() {
        System.out.println("WELCOME TO LINE BATTLE");
        System.out.println("LETS BEGIN");

    }
    private int rollDice () {
        Random random = new Random();
        int dice = random.nextInt(6) +1;
        return dice;
    }
}
