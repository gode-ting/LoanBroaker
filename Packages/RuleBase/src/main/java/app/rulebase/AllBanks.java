
package app.rulebase;

import java.util.ArrayList;
import java.util.List;

public class AllBanks {

    public List<Bank> getAllBanks () {
        List<Bank> banks = new ArrayList<Bank> ();
       
        Bank bankOne = new Bank("Lån & Spar", "bank-lån-and-spar", 0);
        Bank bankTwo = new Bank("Jyske Bank", "bank-jyske-bank", 200);
        Bank bankThree = new Bank("Nordea", "bank-nordea", 400);
        Bank bankFour = new Bank("Danske Bank", "bank-danske-bank", 700);;
        
        banks.add(bankOne);
        banks.add(bankTwo);
        banks.add(bankThree);
        banks.add(bankFour);
        
        return banks;
    }
    
    public List<Bank> getBanksByCreditScore (int creditScore) {
        List<Bank> allBanks = getAllBanks();
        List<Bank> banks = new ArrayList<Bank> ();
        
        for(Bank bank: allBanks) {
            int bankCreditScore = bank.getMinCreditScore();
            if (bankCreditScore <= creditScore) {
                banks.add(bank);
            }
        }
        
        return banks;
    }
    
    public static void main(String[] args) {
        AllBanks allBanks = new AllBanks();
        List<Bank> getAllBanks = allBanks.getAllBanks();    
        
        System.out.println("--- All banks ---");
        for(Bank bank: getAllBanks) {
            System.out.println("Bank name: " + bank.getBankName());
            System.out.println("Bank id: " + bank.getBankId());
            System.out.println("Bank min credit: " + bank.getMinCreditScore() + "\n");
        }
        
        int creditScoreOne = 100;
        int creditScoreTwo = 250;
        int creditScoreThree = 500;
        int creditScoreFour = 800;

        System.out.println("credit score one: " + allBanks.getBanksByCreditScore(creditScoreOne));
        System.out.println("credit score two: " + allBanks.getBanksByCreditScore(creditScoreTwo));
        System.out.println("credit score three: " + allBanks.getBanksByCreditScore(creditScoreThree));
        System.out.println("credit score four: " + allBanks.getBanksByCreditScore(creditScoreFour));
    }
}
