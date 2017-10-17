/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.simjob;

import core.GameServer;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import metadata.Constants;
import model.SpeciesType;
import simulation.SimulationException;
import util.CSVParser;

/**
 *Create and run simulation for the converge game
 * 
 * @author justinacotter
 */
public class SimJobConverge {

    SimJob simJob;
    SimJobManager jobMgr;
    int jobId = SimJob.NO_ID;
    int status = Constants.STATUS_FAILURE;

    public SimJobConverge(String config, int timesteps) {

        GameServer.getInstance();  //load species information
        /* read in experimental variables only used for running simulation jobs*/
        SpeciesType.loadSimTestNodeParams(Constants.ECOSYSTEM_TYPE);
        SpeciesType.loadSimTestLinkParams(Constants.ECOSYSTEM_TYPE); 


        jobMgr = new SimJobManager();
        simJob = new SimJob(config, timesteps);
        jobMgr.setSimJob(simJob);

        try {
            jobId = jobMgr.runSimJob();
        } catch (Exception ex) {
            Logger.getLogger(SimJobConverge.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            status = jobMgr.getStatus();            
        }
        
    }
    
    public int getStatus () {
        return status;
    }
    
    //need to strip out headers and any data following biomass data
    public String getJobCSV () {
        String csv = simJob.getCsv();
        String newCsv = "";

        csv = csv.replaceAll("Grains, seeds", "Grains and seeds");
        List<List<String>> dataSet = CSVParser.convertCSVtoArrayList(csv);
        //remove header lines
        while (!dataSet.isEmpty()) {
            //exit when column header data is found
            //System.out.println(dataSet.get(0).toString());
            if (dataSet.get(0).size() > simJob.getTimesteps()) {
                break;
            }
            dataSet.remove(0);
        }
        //remove trailing information (follows at least one trailing blank)
        for (int i=0; i < dataSet.size(); i++) {
            if (dataSet.get(i).size() < simJob.getTimesteps()) {
                break;
            }
            String line = dataSet.get(i).toString();
            //remove square brackets added by "toString()"
            newCsv = newCsv + line.substring(1, line.length()-1) + "\n";
        }
        
        return newCsv;
    }

}
