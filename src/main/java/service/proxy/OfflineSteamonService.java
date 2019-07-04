package service.proxy;

import de.Steamon.*;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Alternative;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Alternative
@SessionScoped
public class OfflineSteamonService implements Serializable, DefaultSteamonService {

    /* Ich brauche für die Nutzung des SteamonService nur zwei von den angebotenen Funktionen. Deswegen implementiere ich auch nur diese aus*/

    @Override
    public List<Software> getSoftwareChoicesForFloBank() {
        List<Software> softwareList = new ArrayList<>();

        Software software = new Software();
        software.setTitle("Die MIMS 4: Zerstöre virtuelle Leben");
        softwareList.add(software);

        software = new Software();
        software.setTitle("Rätselfield 4: Die Wunder der Levolution");
        softwareList.add(software);

        software = new Software();
        software.setTitle("FIFA 75");
        softwareList.add(software);

        return softwareList;
    }

    @Override
    public AccountItem activateKey(Account arg0, SoftwareKey arg1) {
        return null;
    }

    @Override
    public Account createAccount(Account arg0) {
        return null;
    }

    @Override
    public SoftwareKey buyKey(Software arg0, Account arg1, TransactionDTO arg2) {
        SoftwareKey softwareKey = new SoftwareKey();
        // Nur um den Anschein zu wahren, wird hier einfach ein Dummykey erstellt
        softwareKey.setKeyString("DUMMY"
                + UUID.randomUUID().toString().substring(0, 4)
                + "-" + UUID.randomUUID().toString().substring(0, 4)
                + "-" + UUID.randomUUID().toString().substring(0, 4));
        return softwareKey;
    }
}
