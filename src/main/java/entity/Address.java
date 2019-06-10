package entity;
import org.apache.commons.text.StringEscapeUtils;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Address implements Serializable
{
    private String street;
    private String zip;
    private String city;
    private String country;


    public Address()
    {

    }

    public Address(String street, String zip, String city, String country)
    {
        this.street = street;
        this.zip = zip;
        this.city = city;
        this.country = country;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = StringEscapeUtils.escapeHtml4(country);
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = StringEscapeUtils.escapeHtml4(city);
    }

    public String getZip()
    {
        return zip;
    }

    public void setZip(String zip)
    {
        this.zip = StringEscapeUtils.escapeHtml4(zip);
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = StringEscapeUtils.escapeHtml4(street);
    }


    @Override
    public boolean equals(Object other)
    {
        if(other instanceof Address)
        {
            Address address = (Address)other;

            return getStreet().equals(address.getStreet())
                    && getZip().equals(address.getZip())
                    && getCity().equals(address.getCity())
                    && getCountry().equals(address.getCountry());
        }

        return false;
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(getStreet() + getZip() + getCity() + getCountry());
    }

    @Override
    public String toString()
    {
        return "Address{" +
                "street='" + street + '\'' +
                ", zip='" + zip + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
