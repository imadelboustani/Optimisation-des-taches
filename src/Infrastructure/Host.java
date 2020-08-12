package Infrastructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by t460p on 12/01/2018.
 */
public class Host {
    private ArrayList<Process> processes = new ArrayList<Process>();
    private Server server;
    private double qtRessource;
    private double qtCommunication;

    public double getQtCommunication() {
		return qtCommunication;
	}

	public void setQtCommunication(double qtCommunication) {
		this.qtCommunication = qtCommunication;
	}

	public ArrayList<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(ArrayList<Process> processes) {
        this.processes = processes;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

	public double getQtRessource() {
		return qtRessource;
	}

	public void setQtRessource(double qtRessource) {
		this.qtRessource = qtRessource;
	}

	public Host(Server server) {
		super();
		
		this.server = server;
		this.qtRessource = server.getRessources();
		this.qtCommunication = server.getQtcommunication();
		
	}
    
}
