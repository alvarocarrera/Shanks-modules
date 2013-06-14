/**
 * 
 */
package es.upm.dit.gsi.shanks.hackerhan.model.element.device;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.hackerhan.model.Values;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;

/**
 * This is a simple implementation of a Wireless device like a Smartphone or Tablet. On this simulation
 * the wireless device has only three possible states: 
 * 		- OFF
		- OK 
		- NOK 
 * OK and NOK implies that the device is turned on.
 * 
 * HANs usually have wireless devices in addition to PCs. Wireless device has his own tracking id so
 * an can connect to other nets to perform attacks. 
 * 
 * @author darofar
 *
 */
public class WirelessDevice extends Device{

	public static final String STATUS_OFF = "OFF";
	public static final String STATUS_OK = "OK";
	public static final String STATUS_NOK = "NOK";
	public static final String STATUS_DISCONNECTED = "Disconnected";
	public static final String STATUS_NO_ISP_CONNECTION= "NoISPConnection";
	public static final String STATUS_OUT_OF_RANGE = "Out of range";
	public static final String STATUS_DISCHARGED = "Discharged";
//	public static final String STATUS_STEALING_CONNECTION = "StealingConnection";

	public static final String PROPERTY_SIGNAL = "Signal"; // In %
	public static final String PROPERTY_BATTERY_CAPACITY = "Battery Power"; //In %
	public static final String PROPERTY_CONNECTION = "Connection";
	public static final String PROPERTY_CONNECTION_TYPE = "ConnectionType";
	public static final String PROPERTY_POWER = "Power";
//	private static final Object CONNECTION_TYPE_STEAL = "steal";
//	private static final Object CONNECTION_TYPE_ISP = "isp";

	
	public WirelessDevice(String id)
			throws UnsupportedNetworkElementFieldException {
		super(id, WirelessDevice.STATUS_OK, false);
	}
	
	/*
	 * (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkProperties()
	 */
	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementFieldException {
        HashMap<String, Boolean> status = this.getStatus();
		if(status.get(WirelessDevice.STATUS_OFF)) {
			this.shutdown();
		} else { 
			this.updatePropertyTo(WirelessDevice.PROPERTY_POWER, Values.ON);
//			if (status.get(WirelessDevice.STATUS_STEALING_CONNECTION))
//				this.updatePropertyTo(WirelessDevice.PROPERTY_CONNECTION_TYPE, WirelessDevice.CONNECTION_TYPE_STEAL);
//			else 
//				this.updatePropertyTo(WirelessDevice.PROPERTY_CONNECTION_TYPE, WirelessDevice.CONNECTION_TYPE_ISP);
			if(status.get(WirelessDevice.STATUS_NOK)) {
				this.updatePropertyTo(WirelessDevice.PROPERTY_BATTERY_CAPACITY, 1.0);
				this.updatePropertyTo(WirelessDevice.PROPERTY_SIGNAL, 1.0);
			}
			if(status.get(WirelessDevice.STATUS_DISCONNECTED)){
				this.updatePropertyTo(WirelessDevice.PROPERTY_CONNECTION, Values.DISCONNECTED);
			} 
			if(status.get(WirelessDevice.STATUS_OK)){
				this.updatePropertyTo(WirelessDevice.PROPERTY_BATTERY_CAPACITY,
						50 + Math.random() * 50);
				this.updatePropertyTo(WirelessDevice.PROPERTY_SIGNAL,
						70 + Math.random() * 30);
				this.updatePropertyTo(WirelessDevice.PROPERTY_CONNECTION, Values.CONNECTED);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#checkStatus()
	 */
	@Override
	public void checkStatus() throws UnsupportedNetworkElementFieldException {

		double battery = (Double) this.getProperty(WirelessDevice.PROPERTY_BATTERY_CAPACITY);
		double signal = (Double) this.getProperty(WirelessDevice.PROPERTY_SIGNAL);
		String connection = (String) this.getProperty(WirelessDevice.PROPERTY_CONNECTION);
		String power = (String) this.getProperty(WirelessDevice.PROPERTY_POWER);
//		String ctype= (String) this.getProperty(WirelessDevice.PROPERTY_CONNECTION_TYPE);

		if (power.equals(Values.OFF)) {
			this.updateStatusTo(WirelessDevice.STATUS_OFF, true);
			this.shutdown();
		} else {
//			if (ctype.equals(WirelessDevice.CONNECTION_TYPE_STEAL)) {
//				this.updateStatusTo(WirelessDevice.STATUS_STEALING_CONNECTION, true);
//			} else {
//				this.updateStatusTo(WirelessDevice.STATUS_STEALING_CONNECTION, false);
//			}
			if ((battery <= 5)||(signal <= 20)||!(connection.equals(Values.CONNECTED))) {
				this.updateStatusTo(WirelessDevice.STATUS_OK, false);
				if((battery <= 5)||(signal <= 20)){
					this.updateStatusTo(WirelessDevice.STATUS_NOK, true);
				} else {
					this.updateStatusTo(WirelessDevice.STATUS_NOK, false);
				}
				if(connection.equals(Values.DISCONNECTED)){
					this.updateStatusTo(WirelessDevice.STATUS_DISCONNECTED, true);
					this.updateStatusTo(WirelessDevice.STATUS_NO_ISP_CONNECTION, false);
				} else if(connection.equals(Values.NO_IP)){
					this.updateStatusTo(WirelessDevice.STATUS_DISCONNECTED, false);
					this.updateStatusTo(WirelessDevice.STATUS_NO_ISP_CONNECTION, true);
				} else {
					this.updateStatusTo(WirelessDevice.STATUS_DISCONNECTED, false);
					this.updateStatusTo(WirelessDevice.STATUS_NO_ISP_CONNECTION, false);
				}
			} else {
				this.updateStatusTo(WirelessDevice.STATUS_NOK, false);
				this.updateStatusTo(WirelessDevice.STATUS_OK, true);
				this.updateStatusTo(WirelessDevice.STATUS_DISCONNECTED, false);
				this.updateStatusTo(WirelessDevice.STATUS_NO_ISP_CONNECTION, false);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#fillIntialProperties()
	 */
	@Override
	public void fillIntialProperties() {
		this.addProperty(PROPERTY_CONNECTION, Values.CONNECTED);
		this.addProperty(PROPERTY_POWER, Values.ON);
		this.addProperty(PROPERTY_SIGNAL, 70.0+Math.random()*10.0);
		this.addProperty(PROPERTY_BATTERY_CAPACITY, 50.0+Math.random()*10.0);
	}

	/*
	 * (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.element.NetworkElement#setPossibleStates()
	 */
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(WirelessDevice.STATUS_OFF);
		this.addPossibleStatus(WirelessDevice.STATUS_OK);
		this.addPossibleStatus(WirelessDevice.STATUS_NOK);
		this.addPossibleStatus(WirelessDevice.STATUS_DISCONNECTED);
		this.addPossibleStatus(WirelessDevice.STATUS_NO_ISP_CONNECTION);
	}
	
	/**
	 * Accessory method that configures the device on shut down state. 
	 * 
	 * @throws UnsupportedNetworkElementFieldException
	 */
	private void shutdown() throws UnsupportedNetworkElementFieldException {
		this.updatePropertyTo(WirelessDevice.PROPERTY_CONNECTION, Values.DISCONNECTED);
		this.updatePropertyTo(WirelessDevice.PROPERTY_SIGNAL, 0.0);
		this.updatePropertyTo(WirelessDevice.PROPERTY_POWER, Values.OFF);
		this.updatePropertyTo(WirelessDevice.PROPERTY_CONNECTION_TYPE, Values.NA);
		this.updateStatusTo(WirelessDevice.STATUS_OK, false);
		this.updateStatusTo(WirelessDevice.STATUS_NOK, false);
//		this.updateStatusTo(WirelessDevice.STATUS_STEALING_CONNECTION, false);
		this.updateStatusTo(WirelessDevice.STATUS_DISCONNECTED, false);
		this.updateStatusTo(WirelessDevice.STATUS_NO_ISP_CONNECTION, false);
	}
}
