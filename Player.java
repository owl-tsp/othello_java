abstract class Player {
  int color;  // 色（属性）黒なら1　白なら-1

  protected Player(int color) {
    this.color = color;
  }

  abstract boolean action(Board board);  // 自分の手番になった時の行動
}
