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
import net.response.convergegame.ResponseConvergeHint;
import util.DataReader;
import util.Log;

/**
 *
 * @author justinacotter
 */
public class RequestConvergeHint extends GameRequest {
    
    private int hintIdOffset;

    @Override
    public void parse(DataInputStream dataInput) throws IOException {
        hintIdOffset = DataReader.readInt(dataInput);
    }

    @Override
    public void process() throws Exception {
        ResponseConvergeHint response = new ResponseConvergeHint();
        response.setConvergeHint(
                    ConvergeHintDAO.getNextConvergeHint(hintIdOffset));
        client.add(response);
        //Log.consoleln("Processed RequestConvergeHint");
    }
}
