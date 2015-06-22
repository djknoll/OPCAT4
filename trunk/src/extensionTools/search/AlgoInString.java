package extensionTools.search;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXObjectEntry;
import exportedAPI.opcatAPIx.IXProcessEntry;
import exportedAPI.opcatAPIx.IXStateEntry;
import exportedAPI.opcatAPIx.IXSystem;
import gui.projectStructure.Entry;
import util.Configuration;


/**
 * An In string implemantation of a search algorithem.
 *
 * @author raanan
 * @return Vector which holds the found {@link  IXEntry}.
 */
public class AlgoInString implements AlgoInterface {
    private OptionsInString options;

    public AlgoInString(OptionsInString searchOptions) {
        this.options = searchOptions;
    }

    public Vector PreformSearch(Entry parent, IXSystem opcat) {
        Vector out = new Vector();
        boolean found = false;
        Enumeration entEnum = opcat.getIXSystemStructure().getElements();

        while (entEnum.hasMoreElements()) {
            Entry ent = (Entry) entEnum.nextElement();
            found = false;

            if ((ent.getName().replaceAll("\n", " ").toLowerCase().indexOf(this.options.getSearchText().toLowerCase()) > -1) && this.options.inName) {
                if ((ent instanceof IXProcessEntry) && this.options.forProcess) {
                    found = true;
                }

                if ((ent instanceof IXStateEntry) && this.options.forStates) {
                    found = true;
                }

                if ((ent instanceof IXObjectEntry) && this.options.forObjects) {
                    found = true;
                }

            }

            if ((ent.getDescription().replaceAll("\n", " ").toLowerCase().indexOf(this.options.getSearchText().toLowerCase()) > -1) && this.options.inDescription) {
                if ((ent instanceof IXProcessEntry) && this.options.forProcess) {
                    found = true;
                }

                if ((ent instanceof IXStateEntry) && this.options.forStates) {
                    found = true;
                }

                if ((ent instanceof IXObjectEntry) && this.options.forObjects) {
                    found = true;
                }

            }

            if (this.options.isInParams()) {
                StringTokenizer params = new StringTokenizer(Configuration.getInstance().getProperty("search.extra.parameters"), ";");
                while (params.hasMoreElements() && (!found)) {
                    String value = ent.getProperty(params.nextToken());
                    if ((value != null) && (value.replaceAll("\n", " ").toLowerCase().indexOf(this.options.getSearchText().toLowerCase()) > -1)) {
                        if ((ent instanceof IXProcessEntry) && this.options.forProcess) {
                            found = true;
                        }

                        if ((ent instanceof IXStateEntry) && this.options.forStates) {
                            found = true;
                        }

                        if ((ent instanceof IXObjectEntry) && this.options.forObjects) {
                            found = true;
                        }
                    }

                }
            }
            
            if (found) {
                out.add(ent);
            }
        }
        return out;
    }


}
