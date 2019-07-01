package service.proxy;

import de.Steamon.*;
import service.Exceptions.ValidationException;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Alternative
@RequestScoped
public class ProxySteamonService implements Serializable, DefaultSteamonService {

    private Account account;
    private DefaultSteamonService steamonServiceStub;

    ProxySteamonService() {
        account = new Account();
        account.setUserName("flobank");
        account.setPassword("1234");

        connectService();
    }

    private DefaultSteamonService connectService() {
        try {
            DefaultSteamonServiceService steamonService = new DefaultSteamonServiceService();
            steamonServiceStub = steamonService.getDefaultSteamonServicePort();

            return steamonServiceStub;
        } catch (Exception e) {
        }

        return null;
    }

    @Override
    public List<Software> getSoftwareChoicesForFloBank(){
        return steamonServiceStub.getSoftwareChoicesForFloBank();
    }

    @Override
    public AccountItem activateKey(Account arg0, SoftwareKey arg1) throws AccountException_Exception {
        return null;
    }

    @Override
    public Account createAccount(Account arg0) throws AccountException_Exception {
        return null;
    }

    @Override
    public void downloadAndInstallSoftware(Account arg0, Software arg1) {

    }

    @Override
    public SoftwareKey buyKey(Software arg0, Account arg1, TransactionDTO arg2) throws AccountException_Exception {
        if(steamonServiceStub == null && connectService() == null) {
            throw new AccountException_Exception("The SteamonService is currently not available.", null);
        } else {
            return steamonServiceStub.buyKey(arg0, account, null);
        }
    }
}
