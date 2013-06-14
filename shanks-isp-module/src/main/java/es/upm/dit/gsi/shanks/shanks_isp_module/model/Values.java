package es.upm.dit.gsi.shanks.shanks_isp_module.model;

import es.upm.dit.gsi.shanks.ShanksSimulation;

public class Values extends es.upm.dit.gsi.shanks.networkattacks.util.Values {

	public Values(ShanksSimulation sim) {
		super(sim);
	}
	
	// HANS values. 
	public static final String HAN_SUSCRIBER_LINK = "HanSuscription";
	
	// Enterpirse Values
	public static final String ENTERPRISE_SUSCRIBER_LINK_ID = "MegaCorpSuscription";

	public static final int deltaX = 20;
	public static final int deltaY = 20;

	public static final String ISP_SCENARIO_ID = "ISPScenario";

	public static final String ACTION_BLOCK = "Blocking";
	public static final String ACTION_UNBLOCK = "Unblocking";



}
