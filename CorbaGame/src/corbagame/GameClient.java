package corbagame;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.IOException;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import Game.ICell;
import Game.IGame;
import Game.IGameHelper;

public class GameClient extends Thread{
	int start;
        static IGame gameImpl;
	static String ID = "";
        VisualApp GUI;
        String[] args;
        ORB orb;
        public GameClient(VisualApp gui)
        {
            GUI = gui;
            start = 0;
        }
        public void main(String args[]) {
		
		try {
			orb = ORB.init(args, null);
			org.omg.CORBA.Object objRef = orb
					.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			String name = "Game";
			gameImpl = IGameHelper.narrow(ncRef.resolve_str(name));
			System.out.println("Obtained a handle on server object: "
					+ gameImpl);
			
			ID = new SimpleDateFormat("yyyyMMdd_HHmmss")
					.format(Calendar.getInstance().getTime());
			gameImpl.connect(ID);
			ICell[] grid = gameImpl.getGrid();
			//grid[5].setCell(2,ID);
                        //VisualApp GUI = new VisualApp();
                        while (true) {
				if (gameImpl.gameIsReady() == 1) {
					grid = gameImpl.getGrid();
                                        String[] IDAll = gameImpl.getID();
                                        //System.out.println("I AM CLIENT :" + IDAll[0] + " " + IDAll[1] + " ");
					if (grid != null) {
						//Because of client logic is similar the ID of cell is ID of last modify it client
						//But every sell contain ID of client so you can compare it ID and our for drawing
						//System.out.println( grid[5].getCell() + " cell of " + grid[5].getID() + " ID");
					}
                                        if (start == 0)
                                        {
                                            start = 1;
                                            GUI.SetParam(grid, IDAll, ID);
                                        }
                                        else
                                        {
                                            //gameImpl.wait();
                                            grid = gameImpl.getGrid();
                                            GUI.SetGrid(grid);
                                        }
                                        //GUI.main(args);
                                        //break;
                                }
                                else
                                {
                                    if (start == 1)
                                    {
                                        GUI.StopGame();
                                        start = 0;
                                    }
                                }
				Thread.sleep(500);
			}
                        			
		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}
		//WHEN ANY CLIENT IS LEFT WE NEED DISCONNECT HIM AND gameIsReady will be == 0 and need
		//to end game
		/*if (!ID.equalsIgnoreCase("")) {
			gameImpl.disconnect(ID);
			return;
		}*/
	}
    public void disconect() {
        gameImpl.disconnect(ID);
        start = 0;
    }
    
    
    @Override
    public void run() {
        try{
           main(args); 
        }catch (Exception e){
            System.out.println(e.getMessage() + "\n");
        }
    }
    
}
