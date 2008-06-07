/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import org.netbeans.microedition.util.Executable;

/**
 *
 * @author Chris2u
 */
public class SimpleCancellableTask extends org.netbeans.microedition.util.SimpleCancellableTask {

    private volatile boolean cancelled = false;
    
    public SimpleCancellableTask() {
        super();
    }

    public boolean isCancelled() throws InterruptedException {
        if (cancelled) {
            cancelled = false;
            throw new InterruptedException("Task cancelled");
        }
        return false;
    }
    
    /**
     * Cancel the task
     * @return
     */
    public boolean cancel() {
        cancelled = true;
        return true;
    } 
    
}
