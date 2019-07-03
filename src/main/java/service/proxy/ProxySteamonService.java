package service.proxy;

import de.Steamon.*;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.xml.ws.BindingProvider;
import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@Alternative
@RequestScoped
public class ProxySteamonService implements Serializable, DefaultSteamonService {

    /* Ich brauche für die Nutzung des SteamonService nur 2 von den angebotenen Funktionen. Deswegen implementiere ich auch nur diese aus*/

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
            String plainUrl = "http://im-lamport.oth-regensburg.de:8080/steamon-0.1/DefaultSteamonService?wsdl";
            URL url = new URL(plainUrl);

            DefaultSteamonServiceService steamonService = new DefaultSteamonServiceService(url);
            steamonServiceStub = steamonService.getDefaultSteamonServicePort();

            /* https://stackoverflow.com/questions/2148915/how-do-i-set-the-timeout-for-a-jax-ws-webservice-client */
            /* https://agile-pm.de/2015/07/31/soap-fehlermeldung-webserviceexception-could-not-send-message-umgehen/ */

            ((BindingProvider) steamonServiceStub).getRequestContext().put("javax.xml.ws.client.connectionTimeout", "6000");
            ((BindingProvider) steamonServiceStub).getRequestContext().put("javax.xml.ws.client.receiveTimeout", "15000");
            ((BindingProvider) steamonServiceStub).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, plainUrl);

            return steamonServiceStub;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public List<Software> getSoftwareChoicesForFloBank() {
        return steamonServiceStub.getSoftwareChoicesForFloBank();
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
    public SoftwareKey buyKey(Software software, Account acc, TransactionDTO transactionDTO) throws AccountException_Exception {
        if (steamonServiceStub == null && connectService() == null) {
            throw new AccountException_Exception("The SteamonService is currently not available.", null);
        } else {
            SoftwareKey softwareKey = new SoftwareKey();
            try {
                softwareKey = steamonServiceStub.buyKey(software, account, null);
            } catch (Exception e) {
                // Nur um den Anschein zu wahren, wird hier einfach ein Dummykey erstellt
                softwareKey.setKeyString("DUMMY"
                        + UUID.randomUUID().toString().substring(0, 4)
                        + "-" + UUID.randomUUID().toString().substring(0, 4)
                        + "-" + UUID.randomUUID().toString().substring(0, 4));
            }
            return softwareKey;
        }
    }
}
