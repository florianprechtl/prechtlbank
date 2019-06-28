package service;

import entity.BankAccount;
import entity.BankInstitute;
import entity.SteamonKey;
import entity.repo.BankAccountRepo;
import org.apache.log4j.Logger;
import service.Exceptions.ValidationException;
import ui.models.LoginUserModel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

@ApplicationScoped
public class BankAccountService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private Logger logger;

    @Inject
    private TransactionService transactionService;

    @Inject
    private SteamonKeyService steamonKeyService;

    @Inject
    private BankAccountRepo bankAccountRepo;


    private void validateBankAccountInput(BankAccount bankAccount) throws ValidationException {
        if (bankAccount == null)
            throw new ValidationException("BankAccount is null.", null);

        if (bankAccount.getAccountStatus() == null)
            throw new ValidationException("The Account status is invalid.", null);

        if (bankAccount.getBankInstitute() == null)
            throw new ValidationException("The Banking institute is invalid.", null);
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public void createBankAccount(BankAccount bankAccount) throws ValidationException {
        validateBankAccountInput(bankAccount);
        em.persist(bankAccount);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void createFirstBankAccount(BankAccount bankAccount) throws ValidationException {
        validateBankAccountInput(bankAccount);
        // TODO: get steamonKey from Steamon
        steamonKeyService.persistSteamonKey("123456");
        createBankAccount(bankAccount);
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public BankAccount updateBankAccount(BankAccount bankAccount) throws ValidationException {
        logger.info("updateBankAccount :: Check bankAccount data");
        validateBankAccountInput(bankAccount);
        logger.info("updateBankAccount :: Update bankAccount");
        em.merge(bankAccount);
        logger.info("updateBankAccount :: Successfully updated bankAccount!");
        return bankAccount;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public void deleteBankAccountById(Long id) {
        BankAccount bankAccount = bankAccountRepo.getById(id);
        logger.info("deleteBankAccount :: Delete bankAccount");
        logger.info("deleteBankAccount :: Delete dependencies");
        transactionService.deleteTransactionsByIban(bankAccount.getIban());
        em.remove(bankAccount);
        logger.info("deleteBankAccount :: BankAccount successfully deleted");
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public void deleteBankAccountByUserId(Long userId) {
        List<BankAccount> bankAccounts =  em.createQuery("SELECT u FROM BankAccount AS u WHERE user.id = ?1", BankAccount.class)
                .setParameter(1, userId)
                .getResultList();
        logger.info("deleteBankAccount :: Delete bankAccount");
        logger.info("deleteBankAccount :: Delete dependencies");
        Iterator<BankAccount> bankAccountsIterator = bankAccounts.iterator();
        while(bankAccountsIterator.hasNext()) {
            BankAccount bankAccount = bankAccountsIterator.next();
            transactionService.deleteTransactionsByIban(bankAccount.getIban());
            em.remove(bankAccount);
        }
        logger.info("deleteBankAccount :: BankAccount successfully deleted");
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public List<BankAccount> getBankAccountsByBic(String bic) {
        List<BankAccount> bankAccounts = em.createQuery("SELECT b FROM BankAccount AS b WHERE b.bankInstitute.bic = ?1", BankAccount.class)
        .setParameter(1, bic)
        .getResultList();
        return bankAccounts;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public BankAccount getBankAccountByIban(String iban) {
        Query query = em.createQuery("SELECT u FROM BankAccount AS u WHERE u.iban = ?1", BankAccount.class);
        query.setParameter(1, iban);
        try {
            return (BankAccount) query.getSingleResult();
        }
        catch(Exception e) {
        }
        return null;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public BankAccount getBank() {
        Query query = em.createQuery("SELECT u FROM BankAccount AS u WHERE u.iban = ?1", BankAccount.class);
        query.setParameter(1, "BANK");
        try {
            return (BankAccount) query.getSingleResult();
        }
        catch(Exception e) {
        }
        return null;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public List<BankAccount> getBankAccountsOfUser(Long userId) {
        List<BankAccount> bankAccounts = em.createQuery("SELECT b FROM BankAccount AS b WHERE b.user.id = ?1", BankAccount.class)
                .setParameter(1, userId)
                .getResultList();
        return bankAccounts;
    }
}
