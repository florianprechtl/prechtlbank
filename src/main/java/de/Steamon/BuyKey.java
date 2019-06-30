
package de.Steamon;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for buyKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="buyKey">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://service.steamon.de/}software" minOccurs="0"/>
 *         &lt;element name="arg1" type="{http://service.steamon.de/}account" minOccurs="0"/>
 *         &lt;element name="arg2" type="{http://service.steamon.de/}transactionDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "buyKey", propOrder = {
    "arg0",
    "arg1",
    "arg2"
})
public class BuyKey {

    protected Software arg0;
    protected Account arg1;
    protected TransactionDTO arg2;

    /**
     * Gets the value of the arg0 property.
     * 
     * @return
     *     possible object is
     *     {@link Software }
     *     
     */
    public Software getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Software }
     *     
     */
    public void setArg0(Software value) {
        this.arg0 = value;
    }

    /**
     * Gets the value of the arg1 property.
     * 
     * @return
     *     possible object is
     *     {@link Account }
     *     
     */
    public Account getArg1() {
        return arg1;
    }

    /**
     * Sets the value of the arg1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Account }
     *     
     */
    public void setArg1(Account value) {
        this.arg1 = value;
    }

    /**
     * Gets the value of the arg2 property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionDTO }
     *     
     */
    public TransactionDTO getArg2() {
        return arg2;
    }

    /**
     * Sets the value of the arg2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionDTO }
     *     
     */
    public void setArg2(TransactionDTO value) {
        this.arg2 = value;
    }

}
