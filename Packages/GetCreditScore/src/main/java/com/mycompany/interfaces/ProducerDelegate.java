/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.interfaces;

import java.io.IOException;

/**
 *
 * @author emilgras
 */
public interface ProducerDelegate {
    public void didProduceMessageWithOptionalException(IOException ex);
}
