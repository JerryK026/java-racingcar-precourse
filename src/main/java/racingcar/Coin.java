package racingcar;

public class Coin {
    private static final int LIMIT = 10_000;
    private int amount;

    public Coin(int amount) {
        validate(amount);
        this.amount = amount;
    }

    private void validate(int amount) {
        if (amount > LIMIT) {
            throw new IllegalArgumentException("[ERROR] 게임 실행 횟수는 " + LIMIT + "번 이상 수행될 수 없습니다");
        }
    }
}
