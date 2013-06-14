package es.upm.dit.gsi.shanks.networkattacks.util.networkelements;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.networkattacks.util.Values;

/**
 * This is a simple implementation of Computer as a device of the simulation. On
 * this simulation the computer device has only three possible states: 
 * - OFF 
 * - OK 
 * - NOK 
 * 
 * OK and NOK which implies that the device is turned on.
 * 
 * 
 * En este escenario los computadores son utilizados por el equipo técnico como terminales
 * de acceso para el manejo, el mantenimiento y la administración de la red de la empresa.
 * 
 * 
 * @author darofar
 * 
 */
public class Computer extends Device {

	// It is simplified at there is a failure or not. Not in detail what type of
	// failure.
	public static final String STATUS_OFF = "OFF";
	public static final String STATUS_OK = "OK";
	public static final String STATUS_NOK = "NOK";
	public static final String STATUS_DISCONNECTED = "Disconnected";
	public static final String STATUS_NO_ISP_SERVICE = "NoISPService";

	public static final String PROPERTY_CPUFREQ = "CPU Frequency"; // In %
	public static final String PROPERTY_TEMPERATURE = "Temperature"; // In ªC
	public static final String PROPERTY_POWER = "Power";
	public static final String PROPERTY_ETHERNET_CONNECTION = "Connection";

	public Computer(String id) throws UnsupportedNetworkElementFieldException {
		super(id, Computer.STATUS_OK, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
	 */
	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementFieldException {
		HashMap<String, Boolean> status = this.getStatus();
		if (status.get(Computer.STATUS_OFF)) {
			this.shutdown();
		} else {
			this.updatePropertyTo(Computer.PROPERTY_POWER, Values.ON);
			if (status.get(Computer.STATUS_NOK)
					|| status.get(Computer.STATUS_DISCONNECTED)
					|| status.get(Computer.STATUS_NO_ISP_SERVICE)){
				
				if (status.get(Computer.STATUS_NOK)) {
					this.updatePropertyTo(Computer.PROPERTY_CPUFREQ, 99.0);
					this.updatePropertyTo(Computer.PROPERTY_TEMPERATURE, 70.0);
				} else {
					this.updatePropertyTo(Computer.PROPERTY_CPUFREQ, 10.0);
					this.updatePropertyTo(Computer.PROPERTY_TEMPERATURE, 10.0);
				}
				if (status.get(Computer.STATUS_DISCONNECTED)) {
					this.updatePropertyTo(Computer.PROPERTY_ETHERNET_CONNECTION,
							Values.DISCONNECTED);
				} else if (status.get(Computer.STATUS_NO_ISP_SERVICE)) {
					this.updatePropertyTo(Computer.PROPERTY_ETHERNET_CONNECTION,
							Values.NO_IP);
				} else {
					this.updatePropertyTo(Computer.PROPERTY_ETHERNET_CONNECTION,
							Values.CONNECTED);
				}
			} else if (status.get(Computer.STATUS_OK)) {
				this.updatePropertyTo(Computer.PROPERTY_ETHERNET_CONNECTION,
						Values.CONNECTED);
				this.updatePropertyTo(Computer.PROPERTY_CPUFREQ, 10.0);
				this.updatePropertyTo(Computer.PROPERTY_TEMPERATURE, 10.0);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
	 */
	@Override
	public void checkStatus() throws UnsupportedNetworkElementFieldException {
		String power = (String) this.getProperty(Computer.PROPERTY_POWER);
		double temp = (Double) this.getProperty(Computer.PROPERTY_TEMPERATURE);
		double freq = (Double) this.getProperty(Computer.PROPERTY_CPUFREQ);
		String connection = (String) this
				.getProperty(Computer.PROPERTY_ETHERNET_CONNECTION);

		if (power.equals(Values.OFF)) {
			this.updateStatusTo(Computer.STATUS_OFF, true);
			this.shutdown();
		} else {
			this.updateStatusTo(Computer.STATUS_OFF, false);
			if ((temp >= 80) || (freq > 90)|| !(connection.equals(Values.CONNECTED))) {
				this.updateStatusTo(Computer.STATUS_OK, false);
				if ((temp >= 80) || (freq > 90) ){
					this.updateStatusTo(Computer.STATUS_NOK, true);
				} else {
					this.updateStatusTo(Computer.STATUS_NOK, false);
				}
				if (connection.equals(Values.DISCONNECTED)) {
					this.updateStatusTo(Computer.STATUS_DISCONNECTED, true);
					this.updateStatusTo(Computer.STATUS_NO_ISP_SERVICE, false);
				} else if (connection.equals(Values.NO_IP)){
					this.updateStatusTo(Computer.STATUS_DISCONNECTED, false);
					this.updateStatusTo(Computer.STATUS_NO_ISP_SERVICE, true);
				} else {
					this.updateStatusTo(Computer.STATUS_DISCONNECTED, false);
					this.updateStatusTo(Computer.STATUS_NO_ISP_SERVICE, false);
				}
			} else {
				this.updateStatusTo(Computer.STATUS_OK, true);
				this.updateStatusTo(Computer.STATUS_NOK, false);
				this.updateStatusTo(Computer.STATUS_DISCONNECTED, false);
				this.updateStatusTo(Computer.STATUS_NO_ISP_SERVICE, false);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.element.NetworkElement#fillIntialProperties()
	 */
	@Override
	public void fillIntialProperties() {
		this.addProperty(Computer.PROPERTY_CPUFREQ, 50.0);
		this.addProperty(Computer.PROPERTY_TEMPERATURE, 30.0);
		this.addProperty(Computer.PROPERTY_POWER, Values.ON);
		this.addProperty(Computer.PROPERTY_ETHERNET_CONNECTION,
				Values.CONNECTED);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
	 */
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(Computer.STATUS_NOK);
		this.addPossibleStatus(Computer.STATUS_OK);
		this.addPossibleStatus(Computer.STATUS_OFF);
		this.addPossibleStatus(Computer.STATUS_DISCONNECTED);
		this.addPossibleStatus(Computer.STATUS_NO_ISP_SERVICE);
	}

	/**
	 * Accessory method that configures the device on shut down state.
	 * 
	 * @throws UnsupportedNetworkElementFieldException
	 */
	private void shutdown() throws UnsupportedNetworkElementFieldException {
		this.updatePropertyTo(Computer.PROPERTY_CPUFREQ, 0.0);
		this.updatePropertyTo(Computer.PROPERTY_TEMPERATURE, 0.0);
		this.updatePropertyTo(Computer.PROPERTY_POWER, Values.OFF);
		this.updatePropertyTo(Computer.PROPERTY_ETHERNET_CONNECTION,
				Values.DISCONNECTED);
		this.updateStatusTo(Computer.STATUS_OK, false);
		this.updateStatusTo(Computer.STATUS_NOK, false);
		this.updateStatusTo(Computer.STATUS_DISCONNECTED, false);
		this.updateStatusTo(Computer.STATUS_NO_ISP_SERVICE, false);
	}
}