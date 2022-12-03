package racingcar;

public class GameMachine {
    private InputView inputView = new InputView();
    private OutputView outputView = new OutputView();

    private static final int LIMIT = 1_000_000;
    private int tolerance = 0;

    public interface UnCheckedCallable<V> {
        V call() throws RuntimeException;
    }

    public void run() {
        Cars cars = new Cars(readTemplate(() -> inputView.readCarNames()));
        play(
                cars,
                readTemplate(() -> inputView.readCoins())
        );
        outputView.printWinners(cars.findWinners());
    }

    private void play(Cars cars, Coin coin) {
        outputView.printGameStart();
        while (coin.isRemain()) {
            coin.use();
            String result = cars.move();
            outputView.printProgress(result);
        }
    }

    private <T> T readTemplate(UnCheckedCallable<T> function) {
        try {
            return function.call();
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
            plusGameTimes();
            return function.call();
        }
    }

    private void plusGameTimes() {
        tolerance += 1;
        if (tolerance > LIMIT) {
            throw new IllegalArgumentException("[ERROR] 게임 진행 횟수 한도가 초과되었습니다. 악성 접근으로 판단되어 게임이 종료됩니다.");
        }
    }
}
