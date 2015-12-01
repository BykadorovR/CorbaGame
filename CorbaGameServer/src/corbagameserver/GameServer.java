package corbagameserver;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.WrongAdapter;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import Game.ICell;
import Game.ICellHelper;
import Game.ICellPOA;
import Game.IGame;
import Game.IGameHelper;
import Game.IGamePOA;

class gameImpl extends IGamePOA {

	private ICell[] _grid = new ICell[100];
	private String[] _ID = new String[2];

	public gameImpl() {
		for (int i = 0; i < 2; i++) {
			_ID[i] = "";
		}
	}

	public ICell[] getGrid() {
		for (int i = 0; i < 100; i++) {
			if (_grid[i] == null) {
				cellImpl cellServant = new cellImpl(0, "");
				try {
					_grid[i] = ICellHelper.narrow(_default_POA()
							.servant_to_reference(cellServant));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return _grid;
	}

	public void connect(String ID) {
		if (_ID[0].equalsIgnoreCase("")) {
			System.out.println("Hello, I am client :" + ID + " my number is 0");
			_ID[0] = ID;
		} else {
			System.out.println("Hello, I am client :" + ID + " my number is 1");
			_ID[1] = ID;
		}
	}

	public void disconnect(String ID) {
		System.out.println("DISCONNECTED");
		if (_ID[0].equalsIgnoreCase(ID)) {
			_ID[0] = "";
		} else {
			_ID[1] = "";
		}
	}

	public String[] getID() {
		return _ID;
	}

	public int gameIsReady() {
		if (!_ID[0].equalsIgnoreCase("") && !_ID[1].equalsIgnoreCase("")) {
			return 1;
		} else {
			return 0;
		}
	}

}

class cellImpl extends ICellPOA {
	private int value;
	private String ID;

	public cellImpl(int value, String ID) {
		this.value = value;
		this.ID = ID;

	}

	public int getCell() {
		return value;
	}

	public String getID() {
		return ID;
	}

	public void setCell(int value, String ID) {
		this.value = value;
		this.ID = ID;

	}

}

public class GameServer {
	public static void main(String args[]) {
		try {
			ORB orb = ORB.init(args, null);

			POA rootpoa = POAHelper.narrow(orb
					.resolve_initial_references("RootPOA"));

			rootpoa.the_POAManager().activate();
			gameImpl game = new gameImpl();

			Object ref = rootpoa.servant_to_reference(game);
			IGame href = IGameHelper.narrow(ref);

			Object objRef = orb.resolve_initial_references("NameService");

			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			String name = "Game";
			NameComponent path[] = ncRef.to_name(name);
			ncRef.rebind(path, href);
			System.out.println("GameServer ready and waiting ...");
			orb.run();
		} catch (Exception e) {
			System.err.println("ERROR: " + e);
			e.printStackTrace(System.out);
		}
		System.out.println("GameServer Exiting ...");
	}
}
