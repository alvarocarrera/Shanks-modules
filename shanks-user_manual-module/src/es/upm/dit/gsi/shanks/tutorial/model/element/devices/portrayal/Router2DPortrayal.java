package es.upm.dit.gsi.shanks.tutorial.model.element.devices.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sim.portrayal.DrawInfo2D;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal;

/**
 * @author Daniel Lara
 * 
 * This class is used to represent graphically the routers
 * 
 */

public class Router2DPortrayal extends Device2DPortrayal{

	
	private static final long serialVersionUID = 2560868713505503360L;

	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal#draw(java.lang.Object, java.awt.Graphics2D, sim.portrayal.DrawInfo2D)
     */
    @Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

        Device device = (Device) object;
        final double width = 20;
        final double height = 20;

        HashMap<String, Boolean> status = device.getStatus();
        List<String> okStatus = new ArrayList<String>();
        List<String> nokStatus = new ArrayList<String>();
        
        for(String s : status.keySet()){
            if(status.get(s)){
                nokStatus.add(s);
            }else{
                okStatus.add(s);
            }
        }
        //if the device is OK we represents it in green, 2 altered status blue, and 3 or more red
        if (nokStatus.size() == 1) {
            graphics.setColor(Color.green);
        } else if (nokStatus.size() >= 3) {
            graphics.setColor(Color.red);
        } else{
            graphics.setColor(Color.blue);
        }

        // Draw the devices
        final int x = (int) (info.draw.x - width / 2.0);
        final int y = (int) (info.draw.y - height / 2.0);
        final int w = (int) (width);
        final int h = (int) (height);
        graphics.fillOval(x, y, w, h);
        
        //If you want put and image use this method, and comment the previous five lines 
        //this.putImage(path, x, y, w, h, graphics);
        
        // Draw the devices ID ID
        graphics.setColor(Color.black);
        graphics.drawString(device.getID(), x - 3, y);
    }
	
}
