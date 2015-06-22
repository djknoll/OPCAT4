//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.05.23 at 12:36:13 PM IDT 
//

package extensionTools.opl2.generated.impl.runtime;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import com.sun.xml.bind.Messages;

/**
 * Keeps the information about the grammar as a whole.
 * 
 * This object is immutable and thread-safe.
 *
 * @author
 *  <a href="mailto:kohsuke.kawaguchi@sun.com">Kohsuke KAWAGUCHI</a>
 */
public class GrammarInfoImpl implements GrammarInfo
{
    /**
     * Map from {@link QName}s (root tag names) to {@link Class}es of the
     * content interface that should be instanciated.
     */
    private final Map rootTagMap;
    
    /**
     * Enclosing ObjectFactory class. Used to load resources.
     */
    private final Class objectFactoryClass;
    
    /**
     * Map from {@link Class}es that represent content interfaces
     * to {@link String}s that represent names of the corresponding
     * implementation classes.
     */
    private final Map defaultImplementationMap;
    
    /**
     * ClassLoader that should be used to load impl classes.
     */
    private final ClassLoader classLoader;
    
    public GrammarInfoImpl( Map _rootTagMap, Map _defaultImplementationMap, Class _objectFactoryClass ) {
        this.rootTagMap = _rootTagMap;
        this.defaultImplementationMap = _defaultImplementationMap;
        this.objectFactoryClass = _objectFactoryClass;
        // the assumption is that the content interfaces and their impls
        // are loaded from the same class loader. 
        this.classLoader = this.objectFactoryClass.getClassLoader(); 
    }
    
    /**
     * @return the name of the content interface that is registered with
     * the specified element name.
     */
    private final Class lookupRootMap( String nsUri, String localName ) {
        // note that the value of rootTagMap could be null.
        QName qn;
        
        qn = new QName(nsUri,localName);
        if(this.rootTagMap.containsKey(qn)) {
			return (Class)this.rootTagMap.get(qn);
		}

        qn = new QName(nsUri,"*");
        if(this.rootTagMap.containsKey(qn)) {
			return (Class)this.rootTagMap.get(qn);
		}

        qn = new QName("*","*");
        return (Class)this.rootTagMap.get(qn);
    }
    
    public final Class getRootElement(String namespaceUri, String localName) {
        Class intfCls = this.lookupRootMap(namespaceUri,localName);
        if(intfCls==null) {
			return null;
		} else {
			return this.getDefaultImplementation(intfCls);
		}
    }

    public final UnmarshallingEventHandler createUnmarshaller(
        String namespaceUri, String localName, UnmarshallingContext context ) {
        
        Class impl = this.getRootElement(namespaceUri,localName);
        if(impl==null) {
			return null;
		}
        
        try {
            return ((UnmarshallableObject)impl.newInstance()).createUnmarshaller(context);
        } catch (InstantiationException e) {
            throw new InstantiationError(e.toString());
        } catch (IllegalAccessException e) {
            throw new IllegalAccessError(e.toString());
        }
    }
    
    public final String[] getProbePoints() {
        List r = new ArrayList();
        for (Iterator itr = this.rootTagMap.keySet().iterator(); itr.hasNext();) {
            QName qn = (QName) itr.next();
            r.add(qn.getNamespaceURI());
            r.add(qn.getLocalPart());
        }
        return (String[]) r.toArray(new String[r.size()]);
    }
    
    public final boolean recognize( String nsUri, String localName ) {
        return this.lookupRootMap(nsUri,localName)!=null;
    }
    
    public final Class getDefaultImplementation( Class javaContentInterface ) {
        try {
            // by caching the obtained Class objects.
            return Class.forName((String)this.defaultImplementationMap.get(javaContentInterface), true, this.classLoader );
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.toString());
        }
    }

    /**
     * Gets the MSV AGM which can be used to validate XML during
     * marshalling/unmarshalling.
     */
    public final com.sun.msv.grammar.Grammar getGrammar() throws JAXBException {
        try {
            InputStream is = this.objectFactoryClass.getResourceAsStream("bgm.ser");
            
            if( is==null ) {
                // unable to find bgm.ser
                String name = this.objectFactoryClass.getName();
                int idx = name.lastIndexOf('.');
                name = '/'+name.substring(0,idx+1).replace('.','/')+"bgm.ser";
                throw new JAXBException(
                    Messages.format( Messages.NO_BGM, name ) );
            }
            
            // deserialize the bgm
            ObjectInputStream ois = new ObjectInputStream( is );
            com.sun.xml.bind.GrammarImpl g = (com.sun.xml.bind.GrammarImpl)ois.readObject();
            ois.close();
            
            g.connect(new com.sun.msv.grammar.Grammar[]{g});    // connect to itself
            
            return g;
        } catch( Exception e ) {
            throw new JAXBException( 
                Messages.format( Messages.UNABLE_TO_READ_BGM ), 
                e );
        }
    }
    
    /**
     * @see com.sun.tools.xjc.runtime.GrammarInfo#castToXMLSerializable(java.lang.Object)
     */
    public XMLSerializable castToXMLSerializable(Object o) {
        if( o instanceof XMLSerializable ) {
             return (XMLSerializable)o;
        } else {
            return null;
        }
    }
    
    /**
     * @see com.sun.tools.xjc.runtime.GrammarInfo#castToValidatableObject(java.lang.Object)
     */
    public ValidatableObject castToValidatableObject(Object o) {
        if( o instanceof ValidatableObject ) {
             return (ValidatableObject)o;
        } else {
            return null;
        }
    }
}
