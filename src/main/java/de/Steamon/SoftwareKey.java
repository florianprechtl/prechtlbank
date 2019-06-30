
package de.Steamon;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for softwareKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="softwareKey">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.steamon.de/}longIdEntity">
 *       &lt;sequence>
 *         &lt;element name="software" type="{http://service.steamon.de/}software" minOccurs="0"/>
 *         &lt;element name="keyString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="activated" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="reserved" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "softwareKey", propOrder = {
    "software",
    "keyString",
    "activated",
    "reserved"
})
public class SoftwareKey
    extends LongIdEntity
{

    protected Software software;
    protected String keyString;
    protected boolean activated;
    protected boolean reserved;

    /**
     * Gets the value of the software property.
     * 
     * @return
     *     possible object is
     *     {@link Software }
     *     
     */
    public Software getSoftware() {
        return software;
    }

    /**
     * Sets the value of the software property.
     * 
     * @param value
     *     allowed object is
     *     {@link Software }
     *     
     */
    public void setSoftware(Software value) {
        this.software = value;
    }

    /**
     * Gets the value of the keyString property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeyString() {
        return keyString;
    }

    /**
     * Sets the value of the keyString property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeyString(String value) {
        this.keyString = value;
    }

    /**
     * Gets the value of the activated property.
     * 
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * Sets the value of the activated property.
     * 
     */
    public void setActivated(boolean value) {
        this.activated = value;
    }

    /**
     * Gets the value of the reserved property.
     * 
     */
    public boolean isReserved() {
        return reserved;
    }

    /**
     * Sets the value of the reserved property.
     * 
     */
    public void setReserved(boolean value) {
        this.reserved = value;
    }

}
