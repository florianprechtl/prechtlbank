
package de.Steamon;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.Steamon package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AccountException_QNAME = new QName("http://service.steamon.de/", "AccountException");
    private final static QName _GetSoftwareChoicesForFloBank_QNAME = new QName("http://service.steamon.de/", "getSoftwareChoicesForFloBank");
    private final static QName _Account_QNAME = new QName("http://service.steamon.de/", "account");
    private final static QName _BuyKeyResponse_QNAME = new QName("http://service.steamon.de/", "buyKeyResponse");
    private final static QName _BuyKey_QNAME = new QName("http://service.steamon.de/", "buyKey");
    private final static QName _SoftwareKey_QNAME = new QName("http://service.steamon.de/", "softwareKey");
    private final static QName _TransactionDTO_QNAME = new QName("http://service.steamon.de/", "transactionDTO");
    private final static QName _Software_QNAME = new QName("http://service.steamon.de/", "software");
    private final static QName _StringIdEntity_QNAME = new QName("http://service.steamon.de/", "stringIdEntity");
    private final static QName _ActivateKeyResponse_QNAME = new QName("http://service.steamon.de/", "activateKeyResponse");
    private final static QName _CreateAccountResponse_QNAME = new QName("http://service.steamon.de/", "createAccountResponse");
    private final static QName _LongIdEntity_QNAME = new QName("http://service.steamon.de/", "longIdEntity");
    private final static QName _ActivateKey_QNAME = new QName("http://service.steamon.de/", "activateKey");
    private final static QName _Exception_QNAME = new QName("http://service.steamon.de/", "Exception");
    private final static QName _GetSoftwareChoicesForFloBankResponse_QNAME = new QName("http://service.steamon.de/", "getSoftwareChoicesForFloBankResponse");
    private final static QName _CreateAccount_QNAME = new QName("http://service.steamon.de/", "createAccount");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.Steamon
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreateAccountResponse }
     * 
     */
    public CreateAccountResponse createCreateAccountResponse() {
        return new CreateAccountResponse();
    }

    /**
     * Create an instance of {@link Software }
     * 
     */
    public Software createSoftware() {
        return new Software();
    }

    /**
     * Create an instance of {@link ActivateKeyResponse }
     * 
     */
    public ActivateKeyResponse createActivateKeyResponse() {
        return new ActivateKeyResponse();
    }

    /**
     * Create an instance of {@link GetSoftwareChoicesForFloBankResponse }
     * 
     */
    public GetSoftwareChoicesForFloBankResponse createGetSoftwareChoicesForFloBankResponse() {
        return new GetSoftwareChoicesForFloBankResponse();
    }

    /**
     * Create an instance of {@link CreateAccount }
     * 
     */
    public CreateAccount createCreateAccount() {
        return new CreateAccount();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link ActivateKey }
     * 
     */
    public ActivateKey createActivateKey() {
        return new ActivateKey();
    }

    /**
     * Create an instance of {@link AccountException }
     * 
     */
    public AccountException createAccountException() {
        return new AccountException();
    }

    /**
     * Create an instance of {@link BuyKey }
     * 
     */
    public BuyKey createBuyKey() {
        return new BuyKey();
    }

    /**
     * Create an instance of {@link SoftwareKey }
     * 
     */
    public SoftwareKey createSoftwareKey() {
        return new SoftwareKey();
    }

    /**
     * Create an instance of {@link TransactionDTO }
     * 
     */
    public TransactionDTO createTransactionDTO() {
        return new TransactionDTO();
    }

    /**
     * Create an instance of {@link GetSoftwareChoicesForFloBank }
     * 
     */
    public GetSoftwareChoicesForFloBank createGetSoftwareChoicesForFloBank() {
        return new GetSoftwareChoicesForFloBank();
    }

    /**
     * Create an instance of {@link Account }
     * 
     */
    public Account createAccount() {
        return new Account();
    }

    /**
     * Create an instance of {@link BuyKeyResponse }
     * 
     */
    public BuyKeyResponse createBuyKeyResponse() {
        return new BuyKeyResponse();
    }

    /**
     * Create an instance of {@link Profile }
     * 
     */
    public Profile createProfile() {
        return new Profile();
    }

    /**
     * Create an instance of {@link AccountItem }
     * 
     */
    public AccountItem createAccountItem() {
        return new AccountItem();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.steamon.de/", name = "AccountException")
    public JAXBElement<AccountException> createAccountException(AccountException value) {
        return new JAXBElement<AccountException>(_AccountException_QNAME, AccountException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSoftwareChoicesForFloBank }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.steamon.de/", name = "getSoftwareChoicesForFloBank")
    public JAXBElement<GetSoftwareChoicesForFloBank> createGetSoftwareChoicesForFloBank(GetSoftwareChoicesForFloBank value) {
        return new JAXBElement<GetSoftwareChoicesForFloBank>(_GetSoftwareChoicesForFloBank_QNAME, GetSoftwareChoicesForFloBank.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Account }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.steamon.de/", name = "account")
    public JAXBElement<Account> createAccount(Account value) {
        return new JAXBElement<Account>(_Account_QNAME, Account.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuyKeyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.steamon.de/", name = "buyKeyResponse")
    public JAXBElement<BuyKeyResponse> createBuyKeyResponse(BuyKeyResponse value) {
        return new JAXBElement<BuyKeyResponse>(_BuyKeyResponse_QNAME, BuyKeyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuyKey }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.steamon.de/", name = "buyKey")
    public JAXBElement<BuyKey> createBuyKey(BuyKey value) {
        return new JAXBElement<BuyKey>(_BuyKey_QNAME, BuyKey.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoftwareKey }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.steamon.de/", name = "softwareKey")
    public JAXBElement<SoftwareKey> createSoftwareKey(SoftwareKey value) {
        return new JAXBElement<SoftwareKey>(_SoftwareKey_QNAME, SoftwareKey.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransactionDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.steamon.de/", name = "transactionDTO")
    public JAXBElement<TransactionDTO> createTransactionDTO(TransactionDTO value) {
        return new JAXBElement<TransactionDTO>(_TransactionDTO_QNAME, TransactionDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Software }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.steamon.de/", name = "software")
    public JAXBElement<Software> createSoftware(Software value) {
        return new JAXBElement<Software>(_Software_QNAME, Software.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringIdEntity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.steamon.de/", name = "stringIdEntity")
    public JAXBElement<StringIdEntity> createStringIdEntity(StringIdEntity value) {
        return new JAXBElement<StringIdEntity>(_StringIdEntity_QNAME, StringIdEntity.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ActivateKeyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.steamon.de/", name = "activateKeyResponse")
    public JAXBElement<ActivateKeyResponse> createActivateKeyResponse(ActivateKeyResponse value) {
        return new JAXBElement<ActivateKeyResponse>(_ActivateKeyResponse_QNAME, ActivateKeyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAccountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.steamon.de/", name = "createAccountResponse")
    public JAXBElement<CreateAccountResponse> createCreateAccountResponse(CreateAccountResponse value) {
        return new JAXBElement<CreateAccountResponse>(_CreateAccountResponse_QNAME, CreateAccountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LongIdEntity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.steamon.de/", name = "longIdEntity")
    public JAXBElement<LongIdEntity> createLongIdEntity(LongIdEntity value) {
        return new JAXBElement<LongIdEntity>(_LongIdEntity_QNAME, LongIdEntity.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ActivateKey }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.steamon.de/", name = "activateKey")
    public JAXBElement<ActivateKey> createActivateKey(ActivateKey value) {
        return new JAXBElement<ActivateKey>(_ActivateKey_QNAME, ActivateKey.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.steamon.de/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSoftwareChoicesForFloBankResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.steamon.de/", name = "getSoftwareChoicesForFloBankResponse")
    public JAXBElement<GetSoftwareChoicesForFloBankResponse> createGetSoftwareChoicesForFloBankResponse(GetSoftwareChoicesForFloBankResponse value) {
        return new JAXBElement<GetSoftwareChoicesForFloBankResponse>(_GetSoftwareChoicesForFloBankResponse_QNAME, GetSoftwareChoicesForFloBankResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAccount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.steamon.de/", name = "createAccount")
    public JAXBElement<CreateAccount> createCreateAccount(CreateAccount value) {
        return new JAXBElement<CreateAccount>(_CreateAccount_QNAME, CreateAccount.class, null, value);
    }

}
