import java.util.Scanner;

public class Othello {
  static Player initPlayer(int color, int select) {
    Player player;
    switch (select) {
      case 1:
      player = new Human(color);
      break;
      case 2:
      player = new Easy(color);
      break;
      default:
      player = new Easy(color);
      break;
    }
    return player;
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int color = 1;
    int frag = 0;
    boolean success = true;

    System.out.println("盤面の大きさを選んで下さい (4, 6, 8, 10 ) : ");
    Board board = new Board(scanner.nextInt());
    System.out.println("先攻のプレイヤーを選んで下さい (1=human 2=easy 3=normal 4=hard ) : ");
    Player black = initPlayer(1, scanner.nextInt());
    System.out.println("後攻のプレイヤーを選んで下さい (1=human 2=easy 3=normal 4=hard ) : ");
    Player white = initPlayer(-1, scanner.nextInt());

    board.printBoard();
    while (frag <= 1) {
      if (color == 1) {
        success = black.action(board);
      } else {
        success = white.action(board);
      }
      if (success == true) {
        board.printBoard();
        frag = 0;
      } else {
        frag++;
      }
      color = -color;
    }

    int[] result = board.score();
    if (result[0] > result[1]) {
      System.out.println("黒の勝ち");
    } else if (result[0] < result[1]) {
      System.out.println("白の勝ち");
    } else {
      System.out.println("引き分け");
    }
  }
}
