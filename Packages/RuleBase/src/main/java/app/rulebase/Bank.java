
package app.rulebase;

public class Bank {
    private String bankName;
    private String bankId;
    private int minCreditScore;

    public Bank(String bankName, String bankId, int minCreditScore) {
        this.bankName = bankName;
        this.bankId = bankId;
        this.minCreditScore = minCreditScore;
    }

    public int getMinCreditScore() {
        return minCreditScore;
    }

    public void setMinCreditScore(int minCreditScore) {
        this.minCreditScore = minCreditScore;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }
    
    
}
