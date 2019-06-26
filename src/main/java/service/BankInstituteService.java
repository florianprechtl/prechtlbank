package service;

import entity.BankAccount;
import entity.BankInstitute;
import entity.Transaction;
import entity.repo.BankInstituteRepo;
import org.apache.log4j.Logger;
import service.Exceptions.ValidationException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

@ApplicationScoped
public class BankInstituteService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private Logger logger;

    @Inject
    private BankAccountService bankAccountService;

    @Inject
    private BankInstituteRepo bankInstituteRepo;

    private void validateBankInstitutionInput(BankInstitute bankInstitute) throws ValidationException {
        if (bankInstitute == null)
            throw new ValidationException("BankInstitute is null.", null);

        if (bankInstitute.getBic() == null)
            throw new ValidationException("The bic is invalid.", null);

        if (bankInstitute.getName() == null)
            throw new ValidationException("The name is invalid.", null);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public BankInstitute createBankInstitute(BankInstitute bankInstitute)  throws ValidationException {
        logger.info("createBankInstitute :: Check bankInstitute data");
        validateBankInstitutionInput(bankInstitute);
        logger.info("createBankInstitute :: Create bankInstitute");
        em.persist(bankInstitute);
        logger.info("createBankInstitute :: Successfully created bankInstitute!");
        return bankInstitute;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public BankInstitute updateBankInstitute(BankInstitute bankInstitute) throws ValidationException {
        logger.info("updateBankInstitute :: Check bankInstitute data");
        validateBankInstitutionInput(bankInstitute);
        logger.info("updateBankInstitute :: Update bankInstitute");
        em.merge(bankInstitute);
        logger.info("updateBankInstitute :: Successfully updated bankInstitute!");
        return bankInstitute;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteBankInstituteById(Long id) {
        BankInstitute bankInstitute = bankInstituteRepo.getById(id);
        logger.info("deleteBankInstitute :: Delete bankInstitute");
        // TODO: Remove dependent bankAccounts
        List<BankAccount> bankAccounts = bankAccountService.getBankAccountsByBic(bankInstitute.getBic());
        Iterator iterator = bankAccounts.iterator();
        while(iterator.hasNext()) {
            em.remove(iterator.next());
        }
        em.remove(bankInstitute);
        logger.info("deleteBankInstitute :: BankInstitute successfully deleted");
    }
}
