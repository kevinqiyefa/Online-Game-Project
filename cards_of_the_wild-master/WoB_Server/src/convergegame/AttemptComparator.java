/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package convergegame;

import java.util.Comparator;

/**
 *
 * @author justinacotter
 */
public class AttemptComparator implements Comparator<ConvergeAttempt> {
    public int compare(ConvergeAttempt o1, ConvergeAttempt o2) {
        return o1.getAttemptId() < o2.getAttemptId() ? - 1 :
                (o1.getAttemptId() == o2.getAttemptId() ? 0 : 1);
    }
}

