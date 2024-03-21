
import server.ServerFacade;
import ui.PreLoginMenu;


public class Main {

    private static final ServerFacade server = new ServerFacade();

    public static void main(String[] args) {
        System.out.println("♕ 240 Chess Client ♕\n");

        PreLoginMenu preLoginMenu = new PreLoginMenu(server);
        preLoginMenu.run();

    }
}