/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.request.convergegame;

import db.ConvergeHintDAO;
import java.io.DataInputStream;
import java.io.IOException;
import net.request.GameRequest;
import net.response.convergegame.ResponseConvergeHintCount;
import util.Log;

/**
 *
 * @author justinacotter
 */
public class RequestConvergeHintCount extends GameRequest {
    
    @Override
    public void parse(DataInputStream dataInput) throws IOException {
    }

    @Override
    public void process() throws Exception {
        ResponseConvergeHintCount response = new ResponseConvergeHintCount();
        response.setCount(ConvergeHintDAO.getConvergeHintCount());

        client.add(response);
        //Log.consoleln("Processed RequestConvergeHintCount");
    }
}
