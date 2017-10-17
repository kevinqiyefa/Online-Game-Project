package simulation.simjob;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.SQLException;

import metadata.Constants;
import db.SimJobDAO;
import simulation.SimulationEngine;
import simulation.SimulationException;
import simulation.SimulationIds;

/**
 * SimJobManager manages and submits simulation jobs to the simulation engine.
 * An instance of SimJobManager manages a single simulation job. 
 *
 * @author Justina
 */
public final class SimJobManager {

    private SimulationEngine simEngine;
    private SimJob job;
    private String manipId;
    private int status = Constants.STATUS_FAILURE;
    
    public SimJobManager() {
        job = null;
        this.simEngine = newSimEngine();
    }

    public SimulationEngine newSimEngine() {
        this.simEngine = new SimulationEngine();
        return this.simEngine;
    }

    public SimulationEngine getSimEngine() {
        return this.simEngine;
    }

    //create new sim job based on existing job's ID
    //9/25/14, JTC, added saveAsNew arg
    public SimJob createSimJobFromPrior(int job_id, boolean saveAsNew) throws SQLException {
        job = SimJobDAO.loadJobNoHistory(job_id, saveAsNew);
        return job;
    }

    public SimJob getSimJob() {
        return job;
    }

    public void setSimJob(SimJob newJob) {
        job = newJob;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int runSimJob() throws SQLException, SimulationException {

        //create string representation of node configuration settings
        try {
            job.buildNodeConfig();
        } catch (Exception ex) {
            Logger.getLogger(SimJobManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        //run simulation - 3 main steps
        String netId = "";  //12/22/14, JTC
        try {
            job.setManip_Timestamp((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
            int nextTimestep = 0;
            /*"runs" the simulation (execute ManipulationRequest), but with 0 timesteps, resulting in no
             biomass data.  Provides minimal data to create network: node IDs.  Same method as used for
             players to initialize their foodweb.*/
            int[] nodeListArray = job.getSpeciesNodeList();
            String descript = job.getJob_Descript();
            descript = descript.substring(0, Math.min(descript.length(),50));
            SimulationIds simIds = simEngine.createAndRunSeregenttiSubFoodwebForSimJob(nodeListArray, 
                    descript, 0, 0, true);
            manipId = simIds.getManipId();
            netId = simIds.getNetId();

            /*run timestep #1 (executeManipulationRequest); initializes all node and link parameters 
             that can affect manipulation*/
            simEngine.increaseMultipleSpeciesType(job.getSpeciesZoneList(), ++nextTimestep, false, manipId);

            /*runs manipulation timestep 2+ (executeManipulationRequest)*/
            simEngine.run(++nextTimestep, job.getTimesteps(), manipId, false);

            //save job with biomass information and job ID info
            job.setManipulation_Id(manipId);
            ConsumeMap consumeMap = new ConsumeMap(job.getSpeciesNodeList(),
                Constants.ECOSYSTEM_TYPE);
            PathTable pathTable = new PathTable(consumeMap, 
                    job.getSpeciesNodeList(), !PathTable.PP_ONLY);
            job.setCsv("Manipulation_id: " + manipId + "\n\n"
                    + simEngine.getBiomassCSVString(manipId) + "\n\n" 
                    + consumeMap.toString() + "\n\n"
                    + pathTable.toString());

            /*save job - note: job may not have a job ID until this time as it is sql generated,
             so this must be done before CSV header is created below*/
            job.saveJob();

            //save biomass information to disk
            String header = "Job_id: " + String.valueOf(job.getJob_Id());
            simEngine.saveBiomassCSVFileSimJob(manipId, header, job.getCsv());

            //delete manipulation;
            simEngine.deleteManipulation(manipId);
            System.out.printf("Simulation Job %s, created job ID: %s\n",
                    job.getJob_Descript(), job.getJob_Id());
            //12/22/14, JTC, delete network
            simEngine.deleteNetwork(netId);
            System.out.printf("Deleted net ID: %s\n", netId);
            status = Constants.STATUS_SUCCESS;

        } catch (SimulationException ex) {
            System.err.print("In SimJobManager - Simulation failed");
            if (manipId != null && !manipId.isEmpty()) {
                simEngine.deleteManipulation(manipId);
            }
            if (netId != null && !netId.isEmpty()) {
                simEngine.deleteNetwork(netId);
            }
            job.setManipulation_Id(null);
            job.setManip_Timestamp(null);
            job.setJob_Id(job.saveJob());
            status = Constants.STATUS_FAILURE;
            System.err.println(" (Job ID " + job.getJob_Id() + ").");
            //throw new SimulationException(ex.getMessage());
        }

        return (job.getJob_Id());
    }
}
