/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adbms;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author Aditya Tiwari
 */
public class SerializabilityProcessor {

    //Schedule
    LinkedList<Task> schedule;
    
    //Number of Transactions
    int noOfTransactions;
    
    //The adjacency List of the precedence graph
    LinkedList<Task> adjList[];
    
    public SerializabilityProcessor(LinkedList<Task> schedule, int noOfTransactions) {
        this.schedule = schedule;
        this.noOfTransactions = noOfTransactions;
        
        //Initialize the adjacency list
        this.adjList = new LinkedList[noOfTransactions];
        for(int i=0; i<noOfTransactions; i++)
        {
            adjList[i] = new LinkedList<>();
        }
    }
    
    //Method which checks whether the given schedule is serializable or not
    boolean checkSerializability()
    {
        //Implemented
        formPrecedenceGraph();
        showPrecedenceGraph();
        
        //Yet to be implemented
        boolean isCyclic = checkCycle();        
        return !isCyclic;
    }        
    
    //Forms the precedence graph by identifying the conflict operations
    private void formPrecedenceGraph()
    {
        Task from;
        Task to;
        for(int i=0; i<schedule.size(); i++)
        {
            for(int j=i+1; j<schedule.size(); j++)
            {
                //If the two operations are conflicting then create a node from i to j
                if(checkConflict(schedule.get(i), schedule.get(j)))
                {
                    from = schedule.get(i);
                    to = schedule.get(j);
                    
                    boolean nodeExists=false;
                    for(Task itr: adjList[from.getTransaction() - 1])
                    {
                        if(itr.getTransaction() == to.getTransaction())
                            nodeExists = true;
                    }
                    if(!nodeExists)
                        adjList[from.getTransaction() - 1].add(to);
                }
            }
        }
    }    
    
    //View the Precedence Graph
    void showPrecedenceGraph()
    {
        System.out.println("Precedence graph");
        System.out.println("----------------\n");
        for(int i=0; i<noOfTransactions; i++)
        {
            System.out.println("Adjacency List for transaction " + (i+1));
            for(Task itr: adjList[i])
            {
                System.out.print(" -> " + itr.getTransaction());
            }
            System.out.println();
        }
    }
    
    //Checks whether the two operations are conflicting or not
    private boolean checkConflict(Task t1, Task t2)
    {
        //Both operations should be from different transactions and they should be on the same data item
        if((t1.getTransaction() != t2.getTransaction()) && (t1.getDataItem().equals(t2.getDataItem())))
        {
            if(!(t1.getOperation().equals("Commit") || t2.getOperation().equals("Commit")) && !(t1.getOperation().equals("Read") && t2.getOperation().equals("Read")))
            {
                return true;
            }
        }
        return false;
    }
    
    //Checks for a cycle in the precedence graph
    private boolean checkCycle()
    {
        return false;
    }
}
