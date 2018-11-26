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
    private List<String> result = new ArrayList<String>();
   
    public RecoverabilityProcessor(LinkedList<Task> schedule, int noOfTransactions) {
        this.schedule = schedule;
        this.noOfTransactions = noOfTransactions;
    }
    
    List getResult()
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
                            if(commitOrderMap.get(schedule.get(i).getTransaction()) > commitOrderMap.get(schedule.get(j).getTransaction()))
                            {
                               String resultText = "Transaction-" + schedule.get(j).getTransaction() + " reads data item " + schedule.get(j).getDataItem() + " written\npreviously by Transaction-" + schedule.get(i).getTransaction() + " and\ncommits before Transaction-" + schedule.get(i).getTransaction()+".\n"; 
                               result.add(resultText);
                               flag = true;
                            }
                        }
                    }
                            
                }
            }
            
            
            if(flag)
                return true;
            else
            {
                String resultText = "The given schedule is recoverable.";
                result.add(resultText);
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

    
