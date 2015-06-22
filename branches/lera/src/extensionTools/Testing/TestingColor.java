package extensionTools.Testing;

import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ProcessEntry;
import gui.projectStructure.StateEntry;
import gui.projectStructure.StateInstance;
import gui.projectStructure.ThingEntry;
import gui.projectStructure.ThingInstance;

import java.awt.Color;
import java.util.Collections;
import java.util.Iterator;

public class TestingColor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String value = " ";

	Color color = null;

	public TestingColor(ThingEntry entry) {

		// super(((ThingInstance) entry.getInstances().nextElement())
		// .getBackgroundColor().getRed(), ((ThingInstance) entry
		// .getInstances().nextElement()).getBackgroundColor().getGreen(),
		// ((ThingInstance) entry.getInstances().nextElement())
		// .getBackgroundColor().getBlue());

		ThingInstance ins = (ThingInstance) entry.getInstances().nextElement();

		color = new Color(230, 230, 230);

		if (ins.isAnimated() || ins.isAnimatedDown() || ins.isAnimatedUp()) {
			if (entry instanceof ObjectEntry) {
				color = new Color(127, 182, 127);
			}
			if (entry instanceof ProcessEntry) {
				color = new Color(127, 127, 212);
				// 233, 191, 191
				// 212, 127, 127
				// color = new Color(ins.getBackgroundColor().getRed(), ins
				// .getBackgroundColor().getGreen(), ins
				// .getBackgroundColor().getBlue());
			}
		}

		if (entry instanceof ObjectEntry) {
			ObjectEntry obj = (ObjectEntry) entry;
			Iterator iter = Collections.list(obj.getStates()).iterator();
			while (iter.hasNext()) {
				StateEntry state = (StateEntry) iter.next();
				StateInstance stateIns = (StateInstance) state.getInstances()
						.nextElement();
				if (stateIns.isAnimated()) {
					value = state.getName();
				}
			}
			if (!obj.getStates().hasMoreElements() && ins.isAnimated()) {
				value = "object exists without states";
			}
		} else {
			if (ins.isAnimated()) {
				value = "active";
			} else {
				value = "not active";
			}
		}
	}

	public String toString() {

		return value;
	}

	public Color getColor() {
		return color;
	}

}
