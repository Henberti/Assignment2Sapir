package Task2;
import java.util.Scanner;
import Task2.games.*;


public class App {//HEN BERTI 201381407 && ELIRAN BALAISH 207598467
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

    System.out.println("For self game press->1");
    System.out.println("For user play press->2");
    int choose = s.nextInt();

    Game game;

    //choose one to play self game
    //choose 2 to play with the computer
    
    switch(choose){
        case 1:
            game = new SelfGame();
            break;
        case 2:
            game = new UserGame();
            break;
        default:
            game = new SelfGame();
    }
       game.start();
     
    }

    
}
