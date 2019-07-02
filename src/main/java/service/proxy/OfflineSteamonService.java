package service.proxy;

import de.Steamon.*;
import org.apache.log4j.Logger;
import service.BankAccountService;
import service.UserService;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@Alternative
@SessionScoped
public class OfflineSteamonService implements Serializable, DefaultSteamonService {

    /* Ich brauche für die Nutzung des SteamonService nur 2 von den angebotenen Funktionen. Deswegen implementiere ich auch nur diese aus*/

    @Inject
    private UserService userService;

    @Override
    public List<Software> getSoftwareChoicesForFloBank() {
        return null;
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
    public void downloadAndInstallSoftware(Account arg0, Software arg1) {

    }

    @Override
    public SoftwareKey buyKey(Software arg0, Account arg1, TransactionDTO arg2) {
        userService.loggerTest();
        return null;
    }
}
