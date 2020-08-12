package Infrastructure;

import java.util.Collection;

/**
 * Created by t460p on 12/01/2018.
 */
public class Process {
    private int id;
    private double workLoad;
    private double requiredRessources;
    private double requiredCommunication;

    public Process(int id, double workLoad, double requiredRessources,
			double requiredCommunication) {
		super();
		this.id = id;
		this.workLoad = workLoad;
		this.requiredRessources = requiredRessources;
		this.requiredCommunication = requiredCommunication;
	}

	public double getRequiredCommunication() {
		return requiredCommunication;
	}

	public void setRequiredCommunication(double requiredCommunication) {
		this.requiredCommunication = requiredCommunication;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Process(int id, double workLoad, double requiredRessources) {
        this.id = id;
        this.workLoad = workLoad;
        this.requiredRessources = requiredRessources;
    }

    public double getWorkLoad() {
        return workLoad;
    }

    public void setWorkLoad(double workLoad) {
        this.workLoad = workLoad;
    }

    public double getRequiredRessources() {
        return requiredRessources;
    }

    public void setRequiredRessources(double requiredRessources) {
        this.requiredRessources = requiredRessources;
    }

    public int getId() {
        return id;
    }
}
