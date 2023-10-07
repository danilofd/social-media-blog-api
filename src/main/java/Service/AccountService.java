package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {

    public AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public Account registerAccount(Account account){
        try {
            Account accountFound = accountDAO.getAccountByUsername(account.getUsername());
            if(accountFound == null){
                if(!account.getUsername().isBlank() && account.getPassword().length() >= 4){
                    return accountDAO.registerAccount(account);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account login(Account account){
        try {
            return accountDAO.login(account);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
