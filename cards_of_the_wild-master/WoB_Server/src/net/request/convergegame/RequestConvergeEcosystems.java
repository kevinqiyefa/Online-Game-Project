/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.request.convergegame;

import db.ConvergeEcosystemDAO;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import net.request.GameRequest;
import net.response.convergegame.ResponseConvergeEcosystems;
import util.Log;

/**
 *
 * @author justinacotter
 */
public class RequestConvergeEcosystems extends GameRequest {

    @Override
    public void parse(DataInputStream dataInput) throws IOException {
    }

    @Override
    public void process() throws Exception {
        //Log.consoleln("Processing RequestConvergeEcosystems");
        ResponseConvergeEcosystems response = new ResponseConvergeEcosystems();
        response.setConvergeEcosystems(ConvergeEcosystemDAO.getConvergeEcosystems());
//        client.add(response);
        
        //one time use to create file to port to client instead of requesting each time
        File file = new File("converge-ecosystems.txt");
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(response.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
