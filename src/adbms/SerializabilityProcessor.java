/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adbms;

import java.time.Clock;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

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
        boolean isCyclic = hasCycle();       
        System.out.println("\nCycle check: " + isCyclic);
        
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
                    
                    boolean edgeExists=false;
                    for(Task itr: adjList[from.getTransaction() - 1])
                    {
                        if(itr.getTransaction() == to.getTransaction())
                            edgeExists = true;
                    }
                    if(!edgeExists)
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
    private boolean hasCycle()
    {
        List<Integer> unvisitedNodes = new ArrayList<>(); //List which contains all the nodes which are unvisited                
        List<Integer> currentNodes = new ArrayList<>();   //List which contains all the nodes which are currently being processed             
        List<Integer> visitedNodes = new ArrayList<>();   //List which contains all the nodes which are visited/processed completely
        
        //Add all the nodes in the graph
        for(int i=1; i<=adjList.length; i++)
            unvisitedNodes.add(i);
        
        while(unvisitedNodes.size()>0)
        {
            int current = unvisitedNodes.iterator().next();
            if(performDFS(current, unvisitedNodes, currentNodes, visitedNodes))
                return true;
        }
        
        return false;        
    }
    
    //Runs DFS
    private boolean performDFS(int current, List<Integer> unvisitedNodes, List<Integer> currentNodes, List<Integer> visitedNodes)
    {
        moveNode(current, unvisitedNodes, currentNodes);
        for(int adjacentNode: getAdjacentNodes(current))
        {
            if(visitedNodes.contains(adjacentNode))
                continue;    //Node is already visited
            
            if(currentNodes.contains(adjacentNode))
                return true; //Cycle exists at this node
            
            if(performDFS(adjacentNode, unvisitedNodes, currentNodes, visitedNodes))
                return true;                       
        }
        
        return false;
    }
    
    //Moves the node from one set to the other
    private void moveNode(int element, List<Integer> from, List<Integer> to)
    {
        from.remove(from.indexOf(element));
        to.add(element);
    }
    
    //Return all the adjacent nodes
    private List<Integer> getAdjacentNodes(int node)
    {
        List<Integer> adjacentNodes = new ArrayList<>();
        for(Task task: adjList[node - 1])
            adjacentNodes.add(task.getTransaction());
        
        return adjacentNodes;
    }
    
}
