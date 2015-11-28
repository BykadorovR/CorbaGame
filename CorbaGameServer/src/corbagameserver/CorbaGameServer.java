/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corbagameserver;
import corbagameserver.GameServer;
/**
 *
 * @author akokoshn
 */
public class CorbaGameServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String[] converted_args = new String[2];
        converted_args[0] = "-ORBInitialPort 900";
        converted_args[1] = "-ORBInitialHost dnapc";
        GameServer server = new GameServer();
        server.main(converted_args);
        // TODO code application logic here
    }
    
}
