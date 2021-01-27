public class Main {

    public static void main(String[] args) {
        if ("server".equals(args[0])) new GameServer();
        new GameClient();
    }
}
