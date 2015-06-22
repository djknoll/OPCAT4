/*
 * @(#)ExampleFileFilter.java	1.9 99/04/23
 *
 * Copyright (c) 1998, 1999 by Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */

package gui.util;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * A convenience implementation of FileFilter that filters out
 * all files except for those type extensions that it knows about.
 * <p/>
 * Extensions are of the type ".foo", which is typically found on
 * Windows and Unix boxes, but not on Macinthosh. Case is ignored.
 * <p/>
 * Example - create a new filter that filerts out all files
 * but gif and jpg image files:
 * <p/>
 * JFileChooser chooser = new JFileChooser();
 * ExampleFileFilter filter = new ExampleFileFilter(
 * new NAME{"gif", "jpg"}, "JPEG & GIF Images")
 * chooser.addChoosableFileFilter(filter);
 * chooser.showOpenDialog(this);
 *
 * @author Jeff Dinkins
 * @version 1.9 04/23/99
 */
public class CustomFileFilter extends FileFilter implements java.io.FileFilter {

    private Hashtable filters = null;
    private String description = null;
    private String fullDescription = null;
    private boolean useExtensionsInDescription = true;

    /**
     * Creates a file filter. If no filters are added, then all
     * files are accepted.
     *
     * @see #addExtension
     */
    public CustomFileFilter() {
        this.filters = new Hashtable();
    }

    /**
     * Creates a file filter that accepts files with the given extension.
     * Example: new ExampleFileFilter("jpg");
     *
     * @see #addExtension
     */
    public CustomFileFilter(String extension) {
        this(extension, null);
    }

    /**
     * Creates a file filter that accepts the given file type.
     * Example: new ExampleFileFilter("jpg", "JPEG Image Images");
     * <p/>
     * Note that the "." before the extension is not needed. If
     * provided, it will be ignored.
     *
     * @see #addExtension
     */
    public CustomFileFilter(String extension, String description) {
        this();
        if (extension != null) {
            this.addExtension(extension);
        }
        if (description != null) {
            this.setDescription(description);
        }
    }

    /**
     * Creates a file filter from the given string array.
     * Example: new ExampleFileFilter(NAME {"gif", "jpg"});
     * <p/>
     * Note that the "." before the extension is not needed adn
     * will be ignored.
     *
     * @see #addExtension
     */
    public CustomFileFilter(String[] filters) {
        this(filters, null);
    }

    /**
     * Creates a file filter from the given string array and description.
     * Example: new ExampleFileFilter(NAME {"gif", "jpg"}, "Gif and JPG Images");
     * <p/>
     * Note that the "." before the extension is not needed and will be ignored.
     *
     * @see #addExtension
     */
    public CustomFileFilter(String[] filters, String description) {
        this();
        for (int i = 0; i < filters.length; i++) {
            // add filters one by one
            this.addExtension(filters[i]);
        }
        if (description != null) {
            this.setDescription(description);
        }
    }

    /**
     * Return true if this file should be shown in the directory pane,
     * false if it shouldn't.
     * <p/>
     * Files that begin with "." are ignored.
     *
     * @see #getExtension
     * @see FileFilter#accepts
     */
    public boolean accept(File f) {
        if (f != null) {
            if (f.isDirectory()) {
                return true;
            }
            String extension = this.getExtension(f);
            if ((extension != null) && (this.filters.get(this.getExtension(f)) != null)) {
                return true;
            }
            ;
        }
        return false;
    }

    /**
     * Return the extension portion of the file's name .
     *
     * @see #getExtension
     * @see FileFilter#accept
     */
    public String getExtension(File f) {
        if (f != null) {
            String filename = f.getName();
            int i = filename.lastIndexOf('.');
            if ((i > 0) && (i < filename.length() - 1)) {
                return filename.substring(i + 1).toLowerCase();
            }
            ;
        }
        return null;
    }

    /**
     * Adds a filetype "dot" extension to filter against.
     * <p/>
     * For example: the following code will create a filter that filters
     * out all files except those that end in ".jpg" and ".tif":
     * <p/>
     * ExampleFileFilter filter = new ExampleFileFilter();
     * filter.addExtension("jpg");
     * filter.addExtension("tif");
     * <p/>
     * Note that the "." before the extension is not needed and will be ignored.
     */
    public void addExtension(String extension) {
        if (this.filters == null) {
            this.filters = new Hashtable();
        }
        this.filters.put(extension.toLowerCase(), this);
        this.fullDescription = null;
    }


    /**
     * Returns the human readable description of this filter. For
     * example: "JPEG and GIF Image Files (*.jpg, *.gif)"
     *
     * @see setDescription
     * @see setExtensionListInDescription
     * @see isExtensionListInDescription
     * @see FileFilter#getDescription
     */
    public String getDescription() {
        if (this.fullDescription == null) {
            if ((this.description == null) || this.isExtensionListInDescription()) {
                this.fullDescription = this.description == null ? "(" : this.description + " (";
                // build the description from the extension list
                Enumeration extensions = this.filters.keys();
                if (extensions != null) {
                    this.fullDescription += "." + (String) extensions.nextElement();
                    while (extensions.hasMoreElements()) {
                        this.fullDescription += ", " + (String) extensions.nextElement();
                    }
                }
                this.fullDescription += ")";
            } else {
                this.fullDescription = this.description;
            }
        }
        return this.fullDescription;
    }

    /**
     * Sets the human readable description of this filter. For
     * example: filter.setDescription("Gif and JPG Images");
     *
     * @see setDescription
     * @see setExtensionListInDescription
     * @see isExtensionListInDescription
     */
    public void setDescription(String description) {
        this.description = description;
        this.fullDescription = null;
    }

    /**
     * Determines whether the extension list (.jpg, .gif, etc) should
     * show up in the human readable description.
     * <p/>
     * Only relevent if a description was provided in the constructor
     * or using setDescription();
     *
     * @see getDescription
     * @see setDescription
     * @see isExtensionListInDescription
     */
    public void setExtensionListInDescription(boolean b) {
        this.useExtensionsInDescription = b;
        this.fullDescription = null;
    }

    /**
     * Returns whether the extension list (.jpg, .gif, etc) should
     * show up in the human readable description.
     * <p/>
     * Only relevent if a description was provided in the constructor
     * or using setDescription();
     *
     * @see getDescription
     * @see setDescription
     * @see setExtensionListInDescription
     */
    public boolean isExtensionListInDescription() {
        return this.useExtensionsInDescription;
    }
}