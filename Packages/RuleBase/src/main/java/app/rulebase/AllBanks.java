
package app.rulebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllBanks {

    public HashMap<String, ArrayList> createBanks () {
        HashMap banks = new HashMap<String, ArrayList>();

        ArrayList list = new ArrayList();
        HashMap bankOne = new HashMap();
        bankOne.put("bankName", "Lån & Spar");
        bankOne.put("bankId", "bank-lån-and-spar");
        bankOne.put("minCreditScore", 0);
        
        HashMap bankTwo = new HashMap();
        bankTwo.put("bankName", "Jyske Bank");
        bankTwo.put("bankId", "bank-jyske-bank");
        bankTwo.put("minCreditScore", 200);
        
        HashMap bankThree = new HashMap();
        bankThree.put("bankName", "Nordea");
        bankThree.put("bankId", "bank-nordea");
        bankThree.put("minCreditScore", 400);
        
        HashMap bankFour = new HashMap();
        bankFour.put("bankName", "Danske Bank");
        bankFour.put("bankId", "bank-danske-bank");
        bankFour.put("minCreditScore", 700);
        
        list.add(bankOne);
        list.add(bankTwo);
        list.add(bankThree);
        list.add(bankFour);
        
        banks.put("banks", list);
        
        return banks;
    }
    
    public HashMap getBanksByCreditScore (int creditScore) {
        ArrayList<HashMap> allBanks = createBanks().get("banks");
        
        HashMap bankResults = new HashMap<String, ArrayList>();
        ArrayList<HashMap> listOfBanks = new ArrayList();
        
        for(HashMap bank: allBanks) {
            int bankCreditScore = (Integer)bank.get("minCreditScore");
            if (bankCreditScore <= creditScore) {
                listOfBanks.add(bank);
            }
        }
        
        bankResults.put("banks", listOfBanks);
        
        return bankResults;
    }
    
    public static void main(String[] args) {
        AllBanks app = new AllBanks();
        ArrayList<HashMap> allBanks = app.createBanks().get("banks");
        

        int creditScoreOne = 100;
        int creditScoreTwo = 250;
        int creditScoreThree = 500;
        int creditScoreFour = 800;
    }
}
