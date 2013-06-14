package es.upm.dit.gsi.shanks.tutorial.model.han.element.device.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;

import sim.portrayal.DrawInfo2D;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal;
import es.upm.dit.gsi.shanks.tutorial.model.han.element.device.ModemADSL;

public class ModemADSL2DPortrayal extends Device2DPortrayal {

	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal#draw(java.lang.Object, java.awt.Graphics2D, sim.portrayal.DrawInfo2D)
     */
    @Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

        Device device = (Device) object;
        final double width = 10;
        final double height = 10;

        String status = device.getCurrentStatus();
        if (status.equals(ModemADSL.STATUS_OK)) {
            graphics.setColor(Color.green);
        } else if (status.equals(ModemADSL.STATUS_OFF)) {
            graphics.setColor(Color.black);
        } else if (status.equals(ModemADSL.STATUS_NO_ADSL_CONNECTION)) {
            graphics.setColor(Color.red);
        }

        // Draw the devices
        final int x = (int) (info.draw.x - width / 2.0);
        final int y = (int) (info.draw.y - height / 2.0);
        final int w = (int) (width);
        final int h = (int) (height);
        graphics.fillOval(x, y, w+5, h+5);

        // Draw the devices ID
        graphics.setColor(Color.black);
        graphics.drawString(device.getID(), x - 3, y);

    }

	
	private static final long serialVersionUID = 7135327893048920339L;
}
