/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.simjob;

/**
 *
 * @author justinacotter
 */
public class NodeTimesteps {

    protected int nodeId;
    protected double[] biomass, ppBiomass, predBiomass, predShareGrpBiomass, preyBiomass,
            preyShareGrpBiomass, dietGrpBiomass, totBiomass;
    protected double[][] trophGrpBiomass;

    public NodeTimesteps(int nodeId, int count) {
        this.nodeId = nodeId;
        biomass = new double[count];
        for (int i = 0; i < count; i++) {
            biomass[i] = 0;
        }
    }

    public int getTimesteps() {
        return biomass.length;
    }

    public void setBiomass(int idx, double val) {
        biomass[idx] = val;
    }

    public double getBiomass(int idx) {
        return biomass[idx];
    }
    
    public int getNodeId() {
        return nodeId;
    }
    
    public double getAvgBiomass () {
        double avg = 0;
        for (double bm : biomass) {
            avg += bm;
        }
        if (biomass.length != 0) {
            return avg / (double) biomass.length;
        } else {
            return 0;
        }
    }
}
