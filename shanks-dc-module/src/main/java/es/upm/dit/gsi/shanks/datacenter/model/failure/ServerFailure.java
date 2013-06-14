package es.upm.dit.gsi.shanks.datacenter.model.failure;

import es.upm.dit.gsi.shanks.datacenter.model.Values;
import es.upm.dit.gsi.shanks.networkattacks.util.failures.ComputerFailure;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.Server;

public class ServerFailure extends ComputerFailure {

	/**
	 * 
	 */
	public ServerFailure() {
		super(ServerFailure.class.getName()+System.currentTimeMillis(),
				Values.SERVER_FAILURE_PROB);
	}

	/**
	 * @param id
	 * @param occurrenceProbability
	 */
	public ServerFailure(String id, double occurrenceProbability) {
		super(id, occurrenceProbability);
	}

	/**
	 * @param id
	 */
	public ServerFailure(String id) {
		super(id, Values.SERVER_FAILURE_PROB);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.failure.Failure#addPossibleAffectedElements()
	 */
	@Override
	public void addPossibleAffectedElements() {
		this.addPossibleAffectedProperties(Server.class, Server.PROPERTY_CPUFREQ, 99.9);
	}

	
}
