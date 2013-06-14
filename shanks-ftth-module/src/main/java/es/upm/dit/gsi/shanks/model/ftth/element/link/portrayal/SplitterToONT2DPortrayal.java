package es.upm.dit.gsi.shanks.model.ftth.element.link.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;

import sim.field.network.Edge;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.network.EdgeDrawInfo2D;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.portrayal.Link2DPortrayal;
import es.upm.dit.gsi.shanks.model.ftth.element.link.LinkDefinitions;

public class SplitterToONT2DPortrayal extends Link2DPortrayal{
		

    /**
	 * 
	 */
	private static final long serialVersionUID = 6936426131699154189L;

	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.link.portrayal.Link2DPortrayal#draw(java.lang.Object, java.awt.Graphics2D, sim.portrayal.DrawInfo2D)
     */
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
        Edge e = (Edge) object;

        Link link = (Link) e.getInfo();
        this.drawLink(link, object, graphics, info);
    }

    /**
     * @param link
     * @param object
     * @param graphics
     * @param info
     */
    private void drawLink(Link link, Object object, Graphics2D graphics,
            DrawInfo2D info) {

        EdgeDrawInfo2D ei = (EdgeDrawInfo2D) info;
        final double width = 20;
        final double height = 20;

        String status = link.getCurrentStatus();

        graphics.setColor(Color.black);
        if (status.equals(LinkDefinitions.OK_STATUS)) {
            graphics.setColor(Color.green);
        } else if (status.equals(LinkDefinitions.BROKEN_STATUS)) {
            graphics.setColor(Color.red);
        }


        final int startX = (int) ei.draw.x;
        final int startY = (int) ei.draw.y;
        final int endX = (int) ei.secondPoint.x;
        final int endY = (int) ei.secondPoint.y;
        final int midX = (int) (ei.draw.x + ei.secondPoint.x) / 2;
        final int midY = (int) (ei.draw.y + ei.secondPoint.y) / 2;
        final int w = (int) (width);
        final int h = (int) (height);
        graphics.drawLine(startX, startY, endX, endY);
        graphics.fillRect(midX-(int)(width/2), midY-(int)(height/2), w, h);

        // Draw the devices ID ID
        graphics.setColor(Color.black);
        graphics.drawString(link.getID(), midX - 5, midY);
    }

}
