import java.util.List;
import java.util.Random;

class Easy extends Player{  //人間のプレイヤークラス
  public Easy(int color) {
    super(color);
  }
  // 最大でどれだけの石を裏返せるかを調べる
  protected int maxScore(List<int[]> list) {
    int max = 0;      // 裏返す石の最大数
    for (int[] p : list) {
      if (max < p[2]) {
        max = p[2];
      }
    }
    return max;
  }
  // 最も多くの石を裏返す場所に置く(重複した場合はその中から乱数で決定)
  @Override
  public boolean action(Board board) {
    Random random = new Random();
    int select = -1;  // 選択された位置
    List<int[]> position = board.analyze(this.color);  // ポジションリスト

    System.out.println((this.color == 1 ? "黒" : "白") + "の番です。");
    if (position.size() == 0) {
      System.out.println("Pass");
      return false;  //どこにも石を置けない場合、falseを返して終了
    } else {
      System.out.println("石を置く場所を選んでいます・・・");
      final int max = maxScore(position);
      // ポジションリストから裏返せる石がlimit未満の要素を排除する
      position.removeIf(p -> p[2] < max);
      // 残った要素の中から石を置く場所をランダムに決定
      select = random.nextInt(position.size());
    }
    // 石を置く
    board.put(position.get(select)[0], position.get(select)[1], this.color);
    return true;  //石を置けたのでtrueを返して終了
  }
}
