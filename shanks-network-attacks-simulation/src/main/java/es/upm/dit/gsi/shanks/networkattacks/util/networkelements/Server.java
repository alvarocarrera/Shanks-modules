/**
 * es.upm.dit.gsi
 * 08/05/2012 
 * 
 */
package es.upm.dit.gsi.shanks.networkattacks.util.networkelements;

import java.util.ArrayList;
import java.util.HashMap;

import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.networkattacks.util.Values;

/**
 * A server is a computer in which a user can be signed up and ask for services.
 * 
 * @author darofar
 * 
 */
public class Server extends Computer {

	public static final String PROPERTY_VULNERABILITY = "Vulnerability";
	public static final String PROPERTY_LOAD = "load";
	
	public static final String PROPERTY_HACKED = "hacked";
	public static final String PROPERTY_LOG = "log";
	
	/**
	 * List of registered authentications.
	 */
	private ArrayList<String> passwords;
	/**
	 * A HashMap with services referenced by a serviceName.
	 */
	private HashMap<String, HashMap<String, Object>> services;
	
	/**
	 * A List with the "access" logs
	 */
	private HashMap<String, Integer> logs;

	/**
	 * 
	 * @param id
	 * @throws UnsupportedNetworkElementFieldException
	 */
	public Server(String id) throws UnsupportedNetworkElementFieldException {
		super(id);
		this.passwords = new ArrayList<String>();
		this.services = new HashMap<String, HashMap<String, Object>>();
		this.logs = new HashMap<String, Integer>();
	}

	/**
	 * An user must be registered first to have access to services.
	 * 
	 * @param userPassword
	 *            The user-password to register.
	 * @return true or false if the register operation was successful or not
	 *         respectively.
	 */
	public boolean registerClient(String userPassword) {
		if (!passwords.contains(userPassword)) {
			passwords.add(userPassword);
			return true;
		}
		return false;
	}

	/**
	 * This method is supposed to be used on the start up of the simulation and
	 * not on simulation time. It is used to define the services that the Server
	 * has to provide.
	 * 
	 * @param serviceName
	 *            String. Name of the service to be defined.
	 * @param serviceMap
	 *            HashMap <String Action, Object Value>. A map of the functions
	 *            that the service will provide.
	 * 
	 * @return true or false if the define operation was successful or not
	 *         respectively.
	 */
	public boolean defineService(String serviceName,
			HashMap<String, Object> serviceMap) {
		if (!this.services.containsKey(serviceName)) {
			this.services.put(serviceName, serviceMap);
			return true;
		}
		return false;
	}

	/**
	 * This is the interface that the final user will use to access the
	 * services. The user needs to provide an authentication, the name of the
	 * services and the action of that service he or she wants to invoke.
	 * 
	 * @param autentication
	 *            String. The user-password provided before on the system
	 *            register.
	 * @param serviceName
	 *            String. The name of the service the user wants to invoke.
	 * @param serviceAction
	 *            String. The action of the service the user wants to invoke.
	 * 
	 * @return Object. The value returned for the pair ServiceName-Action data
	 *         inputs.
	 */
	public Object getService(String autentication, String serviceName,
			String serviceAction) {
		if (!this.passwords.contains(autentication)) {
			return null;
		} else if (!this.services.containsKey(serviceName)) {
			return Values.SERVICE_NOT_FOUND;
		} else if (!this.services.get(serviceName).containsKey(serviceAction)) {
			return Values.ACTION_NOT_AVAILABLE;
		} else {
			return this.services.get(serviceName).get(serviceAction);
		}
	}
	
	public void logAccess(String ID){
		if (this.logs.containsKey(ID)) {
			this.logs.put(ID, this.logs.get(ID) + 1);
		} else {
			this.logs.put(ID, 1);
		}
		
	}
	
	public HashMap<String, Integer> getLogs(){
		return logs;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.element.NetworkElement#fillIntialProperties()
	 */
	@Override
	public void fillIntialProperties() {
		this.addProperty(Server.PROPERTY_CPUFREQ, 50.0);
		this.addProperty(Server.PROPERTY_TEMPERATURE, 30.0);
		this.addProperty(Server.PROPERTY_POWER, Values.ON);
		this.addProperty(Server.PROPERTY_ETHERNET_CONNECTION,
				Values.CONNECTED);
		this.addProperty(Server.PROPERTY_LOAD, 10);
		this.addProperty(Server.PROPERTY_LOG, Values.SERVER_LOG_OK);
		this.addProperty(Server.PROPERTY_HACKED, false);
		this.addProperty(Server.PROPERTY_VULNERABILITY, 0.1);
	}

}