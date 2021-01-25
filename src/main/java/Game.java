public class Game {
    public static void main(String[] args) {
        if ("server".equals(args[0])) {
            new GameServer();
        } else {
            new GameClient();
        }
    }
}
