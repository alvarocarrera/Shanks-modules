package es.upm.dit.gsi.shanks.tutorial.model.han.scenario.portrayal;

import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.tutorial.model.han.element.device.Computer;
import es.upm.dit.gsi.shanks.tutorial.model.han.element.device.EthernetRouter;
import es.upm.dit.gsi.shanks.tutorial.model.han.element.device.ModemADSL;
import es.upm.dit.gsi.shanks.tutorial.model.han.element.device.Smartphone;
import es.upm.dit.gsi.shanks.tutorial.model.han.element.device.WifiAccessPoint;
import es.upm.dit.gsi.shanks.tutorial.model.han.element.device.portrayal.Computer2DPortrayal;
import es.upm.dit.gsi.shanks.tutorial.model.han.element.device.portrayal.EthernetRouter2DPortrayal;
import es.upm.dit.gsi.shanks.tutorial.model.han.element.device.portrayal.ModemADSL2DPortrayal;
import es.upm.dit.gsi.shanks.tutorial.model.han.element.device.portrayal.Smartphone2DPortrayal;
import es.upm.dit.gsi.shanks.tutorial.model.han.element.device.portrayal.WifiAccessPoint2DPortrayal;
import es.upm.dit.gsi.shanks.tutorial.model.han.element.link.EthernetCable;
import es.upm.dit.gsi.shanks.tutorial.model.han.element.link.InternalBus;
import es.upm.dit.gsi.shanks.tutorial.model.han.element.link.protrayal.EthernetCable2DPortrayal;
import es.upm.dit.gsi.shanks.tutorial.model.han.element.link.protrayal.InternalBus2DPortrayal;

public class HANScenario2DPortrayal extends Scenario2DPortrayal {
	

	/**
	 * @param scenario
	 * @param width
	 * @param height
	 * @throws DuplicatedPortrayalIDException
	 */
	public HANScenario2DPortrayal(Scenario scenario, int width, int height)
			throws DuplicatedPortrayalIDException {
		super(scenario, width, height);
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal#placeElements()
	 */
	@Override
	public void placeElements() {
		this.situateDevice((Device)this.getScenario().getNetworkElement("PC"), 5, 20);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Router"), 30, 30);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Modem"), 40, 30);
		this.situateDevice((Device)this.getScenario().getNetworkElement("WifiAccessPoint"), 35, 25);
		this.situateDevice((Device)this.getScenario().getNetworkElement("iPhone"), 15, 5);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Android"), 25, 5);
		
		this.drawLink((Link)this.getScenario().getNetworkElement("Ethernet: PC-Router"));
		this.drawLink((Link)this.getScenario().getNetworkElement("InternalBus_MRW"));
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal#setupPortrayals()
	 */
	@Override
	public void setupPortrayals() {
        ContinuousPortrayal2D devicesPortrayal = (ContinuousPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
        NetworkPortrayal2D linksPortrayal = (NetworkPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);
                
        devicesPortrayal.setPortrayalForClass(Computer.class, new Computer2DPortrayal());
        devicesPortrayal.setPortrayalForClass(EthernetRouter.class, new EthernetRouter2DPortrayal());
        devicesPortrayal.setPortrayalForClass(ModemADSL.class, new ModemADSL2DPortrayal());
        devicesPortrayal.setPortrayalForClass(WifiAccessPoint.class, new WifiAccessPoint2DPortrayal());
        devicesPortrayal.setPortrayalForClass(Smartphone.class, new Smartphone2DPortrayal());
                
        linksPortrayal.setPortrayalForClass(EthernetCable.class, new EthernetCable2DPortrayal());
        linksPortrayal.setPortrayalForClass(InternalBus.class, new InternalBus2DPortrayal());
        
	}

	@Override
	public void addPortrayals() {
		// TODO Auto-generated method stub
	}
}
