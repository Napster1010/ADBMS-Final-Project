/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adbms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author VARUN
 */
public class RecoverabilityProcessor {
   
    private LinkedList<Task> schedule;
    
    //Number of Transactions
    private int noOfTransactions;
    
    //Stores the indexes on which the transactions committed
    private Map<Integer, Integer> commitOrderMap = new HashMap<>();
    
    //Stores the result of recoverability checker
    private String result;
   
    public RecoverabilityProcessor(LinkedList<Task> schedule, int noOfTransactions) {
        this.schedule = schedule;
        this.noOfTransactions = noOfTransactions;
    }
    
    String getResult()
    {
        return result;
    }
    
    //Method which checks whether the given schedule is recoverable or not
    boolean checkRecoverability()
    {
        findCommitOrders();
        System.out.println(commitOrderMap);
        
        boolean flag=false;
        for(int i=0; i<schedule.size(); i++)
        {
            if("Write".equals(schedule.get(i).getOperation()))
            {
                for(int j=i+1; j<schedule.size(); j++)
                {
                    if("Read".equals(schedule.get(j).getOperation()) && schedule.get(i).getDataItem().equals(schedule.get(j).getDataItem()) && (schedule.get(i).getTransaction() != schedule.get(j).getTransaction()))
                    {
                        System.out.println("HELLLOOOOOOO");
                        if(commitOrderMap.get(schedule.get(i).getTransaction()) > commitOrderMap.get(schedule.get(j).getTransaction()))
                        {
                            result = "The given schedule is not recoverable as\nTransaction-" + schedule.get(j).getTransaction() + " reads data item " + schedule.get(j).getDataItem() + " written\npreviously by Transaction-" + schedule.get(i).getTransaction() + " and commits\nbefore Transaction-" + schedule.get(i).getTransaction();
                           flag = true;
                           break;
                        }
                    }
                }
                if(flag)
                    break;
            }
        }
        
        if(flag)
            return true;
        else
        {
            result = "The given schedule is recoverable.";            
            return false;
        }
    }            
        
    //Find the commit orders of all transactions in the given schedule
    void findCommitOrders()
    {
        for(Task task: schedule)
        {
            if("Commit".equals(task.getOperation()))            
                commitOrderMap.put(task.getTransaction(), schedule.indexOf(task));            
        }
    }
}

    
