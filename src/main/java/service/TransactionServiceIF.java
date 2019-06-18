package service;

import entity.dto.LoginDTO;
import entity.dto.TransactionDTO;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(serviceName="TransactionServiceIF")
public interface TransactionServiceIF {
    @WebMethod
    TransactionDTO transfer(LoginDTO loginDTO, TransactionDTO transactionDTO) throws TransactionService.TransactionException, UserService.LoginException;

    @WebMethod
    TransactionDTO directDebit(LoginDTO loginDTO, TransactionDTO transactionDTO) throws TransactionService.TransactionException, UserService.LoginException;
}
