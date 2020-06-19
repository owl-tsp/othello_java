import java.util.Scanner;
import java.util.List;

class Human extends Player{  //人間のプレイヤークラス
  public Human(int color) {
    super(color);
  }

  @Override
  public boolean action(Board board) {  //入力された場所に石を置く
    Scanner scanner = new Scanner(System.in);
    int select = -1;  // 選択された位置
    List<int[]> position = board.analyze(this.color);  // ポジションリスト

    System.out.println((this.color == 1 ? "黒" : "白") + "の番です。");
    if (position.size() == 0) {
      System.out.println("Pass");
      return false;  //どこにも石を置けない場合、falseを返して終了
    } else if (position.size() == 1) {
      System.out.println("石が自動的に置かれました。");
      select = 0;           // 選択肢が１つしかない場合、自動的にそれが選ばれる。
    } else {
      int n = 0;      // 要素の番号
      System.out.println("石を置く場所を選んで下さい。");
      for (int[] p : position) {  //石を置くことができる場所を表示して番号で選ばせる
        System.out.printf("(%2d, %c) -> %d  ", board.row[p[0]], board.column[p[1]], n);
        n++;
      }
      System.out.println();
      //selectは0以上かつポジションリストの長さ未満のみ受け付ける
      while (select < 0 || select > position.size()) {
        select = scanner.nextInt();
      }
    }
    // 石を置く
    board.put(position.get(select)[0], position.get(select)[1], this.color);
    return true;  //石を置けたのでtrueを返して終了
  }
}
