import java.util.List;
import java.util.LinkedList;

class Board implements Cloneable {  // 盤面クラス
  public int size;               //盤面の配列の大きさ(4, 6, 8, 10)
  public int[][] square;         //盤面の配列　sizeに準拠して生成される
  public int count;              //ゲームの手数
  public static final int[] row = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};  // elevation
  public static final char[] column = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};  // vintage

  public Board(int boardSize) {  // コンストラクタ
    // 与えられた数字が4,6,10の場合はその大きさで、それ以外の数字の場合は全て8で盤面を作成
    switch (boardSize) {
      case 4 :
      case 6 :
      case 10 :
        this.size = boardSize;
      break;
      default:
        this.size = 8;
    }
    //  盤面を作成する
    this.square = new int[this.size][this.size];
    for (int i = 0; i < this.size; i++) {
      for (int j = 0; j < this.size; j++) {
        this.square[i][j] = 0;
      }
    }
    //  中央に初期配置の石を置く
    this.square[(this.size / 2 - 1)][(this.size / 2 - 1)] = 1;
    this.square[(this.size / 2)][(this.size / 2)] = 1;
    this.square[(this.size / 2 - 1)][(this.size / 2)] = -1;
    this.square[(this.size / 2)][(this.size / 2 - 1)] = -1;
    // 手数を0で初期化する
    this.count = 0;
  }

  @Override
  public Board clone() {     // 探索の際に一時的に作られるコピーを生成するメソッド
    Board b = null;
    // フィールドがコピーされる
    try {
      b = (Board)super.clone();  // 親クラスはObject
      // 配列は参照がコピーされないように中身をコピーする
      b.square = new int[b.size][b.size];
      for (int i = 0; i < b.size; i++) {
        System.arraycopy(this.square[i] , 0, b.square[i], 0, b.size);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return b;
  }

  public void printBoard() {       //コンソール上に盤面を表示するメソッド
    System.out.println("--------------------------------------------------");
    System.out.println("現在の手数は　" + this.count + "です。");
    System.out.print("  ");
    for (int i = 0; i < this.size; i++) {
      System.out.print(" " + column[i]);
    }
    System.out.println();
    for (int i = 0; i < this.size; i++) {
      System.out.println();
      System.out.printf("%2d", row[i]);
      for (int j = 0; j < this.size; j++) {
        if (this.square[i][j] == 1) {
          System.out.print(" ●");
        } else if (this.square[i][j] == -1) {
          System.out.print(" ○");
        } else {
          System.out.print("  ");
        }
      }
      System.out.println();
    }
    System.out.println("--------------------------------------------------");
  }

  //盤面に石を置いて相手の石を反転させる
  public int put(int h, int v, int color) {
    int reverse = 0;
    int n;
    int[][] direction = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, 1}, {1, 1}, {-1, -1}, {1, -1}};

    for (int[] d : direction) {
      n = 1;
      try {
        while (this.square[h + n * d[0]][v + n * d[1]] == -color) {  // 探索
          n++;
        }
      } catch (IndexOutOfBoundsException e) {
        continue;
      }
      if (this.square[h + n * d[0]][v + n * d[1]] == color) {
        reverse += n - 1;  // 色が同じ=挟まれているのでポイントを増やして間を反転させる
        this.square[h][v] = color;
        for (int j = 1; j < n; j++) {
          this.square[h + j * d[0]][v + j * d[1]] *= -1;
        }
      }
    }
    this.count++;  // 手数を１増やす　初期化時以外countはここでのみ変化する
    return reverse;    //戻り値は反転させた石の個数
  }

  public List<int[]> analyze(int color) {  //石を置ける場所を探してリストを返す
    List<int[]> position = new LinkedList<int[]>();  // 連結リスト
    // 0のマスを探して石を置けるか試す
    for (int i = 0; i < this.size; i++) {
      for (int j = 0; j < this.size; j++) {
        if (this.square[i][j] == 0) {
          int point = this.clone().put(i, j, color);
          if (point != 0) {
            int[] arr = {i, j, point};
            position.add(arr);
          }
        }
      }
    }
    return position;  // リストを返す
  }

  public int[] score() {  //得点計算
    int[] point = {0, 0, 0};
    // マスの数字に応じて点数を加算していく
    for (int i = 0; i < this.size; i++) {
      for (int j = 0; j < this.size; j++) {
        if (this.square[i][j] == 1) {
          point[0]++;
        } else if (this.square[i][j] == -1) {
          point[1]++;
        } else {
          point[2]++;
        }
      }
    }
    return point;  // 0・・・黒　1・・・白　2・・・空のマス
  }
}
