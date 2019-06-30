
package de.Steamon;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for accountItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="accountItem">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.steamon.de/}longIdEntity">
 *       &lt;sequence>
 *         &lt;element name="lastStart" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="totalHoursRun" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="downloadStatus" type="{http://service.steamon.de/}downloadStatus" minOccurs="0"/>
 *         &lt;element name="softwareKey" type="{http://service.steamon.de/}softwareKey" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "accountItem", propOrder = {
    "lastStart",
    "totalHoursRun",
    "downloadStatus",
    "softwareKey"
})
public class AccountItem
    extends LongIdEntity
{

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastStart;
    protected Integer totalHoursRun;
    @XmlSchemaType(name = "string")
    protected DownloadStatus downloadStatus;
    protected SoftwareKey softwareKey;

    /**
     * Gets the value of the lastStart property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastStart() {
        return lastStart;
    }

    /**
     * Sets the value of the lastStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastStart(XMLGregorianCalendar value) {
        this.lastStart = value;
    }

    /**
     * Gets the value of the totalHoursRun property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTotalHoursRun() {
        return totalHoursRun;
    }

    /**
     * Sets the value of the totalHoursRun property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTotalHoursRun(Integer value) {
        this.totalHoursRun = value;
    }

    /**
     * Gets the value of the downloadStatus property.
     * 
     * @return
     *     possible object is
     *     {@link DownloadStatus }
     *     
     */
    public DownloadStatus getDownloadStatus() {
        return downloadStatus;
    }

    /**
     * Sets the value of the downloadStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link DownloadStatus }
     *     
     */
    public void setDownloadStatus(DownloadStatus value) {
        this.downloadStatus = value;
    }

    /**
     * Gets the value of the softwareKey property.
     * 
     * @return
     *     possible object is
     *     {@link SoftwareKey }
     *     
     */
    public SoftwareKey getSoftwareKey() {
        return softwareKey;
    }

    /**
     * Sets the value of the softwareKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoftwareKey }
     *     
     */
    public void setSoftwareKey(SoftwareKey value) {
        this.softwareKey = value;
    }

}
