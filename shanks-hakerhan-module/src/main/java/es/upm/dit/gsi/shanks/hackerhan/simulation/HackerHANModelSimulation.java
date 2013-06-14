package es.upm.dit.gsi.shanks.hackerhan.simulation;

/**
 * 
 */

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import sim.engine.Schedule;
import sim.engine.SimState;
import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.exception.DuplicatedActionIDException;
import es.upm.dit.gsi.shanks.exception.DuplicatedAgentIDException;
import es.upm.dit.gsi.shanks.hackerhan.model.scenario.HackerHANScenario;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

/**
 * @author a.carrera
 * 
 */
public class HackerHANModelSimulation extends ShanksSimulation {

	public HackerHANModelSimulation(long seed,
			Class<? extends Scenario> scenarioClass, String scenarioID,
			String initialState, Properties properties)
			throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			UnsupportedNetworkElementFieldException,
			TooManyConnectionException, UnsupportedScenarioStatusException,
			DuplicatedIDException, DuplicatedPortrayalIDException,
			ScenarioNotFoundException, DuplicatedAgentIDException,
			DuplicatedActionIDException {
		super(seed, scenarioClass, scenarioID, initialState, properties);
	}

	public static void main(String[] args) {
		try {
			Properties scenarioProperties = new Properties();
			scenarioProperties.put(Scenario.SIMULATION_GUI, Scenario.NO_GUI);
			HackerHANModelSimulation tut = new HackerHANModelSimulation(
					System.currentTimeMillis(), HackerHANScenario.class,
					"HackerHomeAreaNetwork", HackerHANScenario.STATUS_ATTACKING,
					scenarioProperties);
			tut.start();
			do
				if (!tut.schedule.step(tut))
					break;
			while (tut.schedule.getSteps() < 8001);
			tut.finish();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addSteppables() {
		Steppable steppable = new Steppable() {

			private static final long serialVersionUID = 2669002521740395423L;

			@Override
			public void step(SimState sim) {
				ShanksSimulation simulation = (ShanksSimulation) sim;
				logger.info("Resolved Failures :"
						+ simulation.getNumOfResolvedFailures() + " in step "
						+ sim.schedule.getSteps());
			}
		};
		schedule.scheduleRepeating(Schedule.EPOCH, 3, steppable, 500);
	}

	@Override
	public void registerShanksAgents() throws DuplicatedAgentIDException,
			DuplicatedActionIDException {
//		Hacker hacker = new Hacker("Hacker", "src/main/java/es/upm/dit/gsi/shanks/hackerhan/agent/Hacker.net", null);
//		// Shame on me. Again.
//
//		this.registerShanksAgent(hacker);
	}
	private static final long serialVersionUID = -2546286073637681966L;
}
