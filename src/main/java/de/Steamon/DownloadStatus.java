
package de.Steamon;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for downloadStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="downloadStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DOWNLAOD_IN_PROGRESS"/>
 *     &lt;enumeration value="INSTALLED"/>
 *     &lt;enumeration value="UPDATING"/>
 *     &lt;enumeration value="WAITING"/>
 *     &lt;enumeration value="PAUSED"/>
 *     &lt;enumeration value="CANCELED"/>
 *     &lt;enumeration value="RUNNING"/>
 *     &lt;enumeration value="NOT_INSTALLED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "downloadStatus")
@XmlEnum
public enum DownloadStatus {

    DOWNLAOD_IN_PROGRESS,
    INSTALLED,
    UPDATING,
    WAITING,
    PAUSED,
    CANCELED,
    RUNNING,
    NOT_INSTALLED;

    public String value() {
        return name();
    }

    public static DownloadStatus fromValue(String v) {
        return valueOf(v);
    }

}
