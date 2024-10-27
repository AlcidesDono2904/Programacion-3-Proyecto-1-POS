package pos;

import pos.logic.Server;

public class Application {
    public static void main(String[] args) {
        Server s=new Server();
        s.run();
    }
}
