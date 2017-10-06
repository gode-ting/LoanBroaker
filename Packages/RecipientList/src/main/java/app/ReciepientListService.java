/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import interfaces.ReciepientListServiceDelegate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Frederik
 */
public class ReciepientListService {

    ReciepientListServiceDelegate delegate;

    public ReciepientListService(ReciepientListServiceDelegate delegate) {
        this.delegate = delegate;
    }

    public void DistributeLoan(HashMap applicationAndBanks) {
        try {
            HashMap application = (HashMap) applicationAndBanks.get("application");
            ArrayList<HashMap> banks = (ArrayList<HashMap>) applicationAndBanks.get("banks");
            System.out.println(applicationAndBanks.toString());
            for (int i = 0; i < banks.size(); i++) {
                HashMap currBank = banks.get(i);
                delegate.didReciepientListServiceWithOptionalException(application, (String)currBank.get("bankId"), null);
            }

        } catch (Exception e) {
            delegate.didReciepientListServiceWithOptionalException(null, null, e);
        }
    }
}
