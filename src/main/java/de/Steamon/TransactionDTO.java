
package de.Steamon;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for transactionDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="transactionDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="boughtasGift" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="senderEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transactionDTO", propOrder = {
    "boughtasGift",
    "senderEmail"
})
public class TransactionDTO {

    protected boolean boughtasGift;
    protected String senderEmail;

    /**
     * Gets the value of the boughtasGift property.
     * 
     */
    public boolean isBoughtasGift() {
        return boughtasGift;
    }

    /**
     * Sets the value of the boughtasGift property.
     * 
     */
    public void setBoughtasGift(boolean value) {
        this.boughtasGift = value;
    }

    /**
     * Gets the value of the senderEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenderEmail() {
        return senderEmail;
    }

    /**
     * Sets the value of the senderEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenderEmail(String value) {
        this.senderEmail = value;
    }

}
