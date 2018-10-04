/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.LinkedList;

/**
 *
 * @author Napster
 */
public class GraphTest {
    static LinkedList<Integer> adjList[];   
    
    public static void main(String[] args)
    {
        int v=5;                
        adjList = new LinkedList[v];
        for(int i=0;i<v;i++)
        {
            adjList[i] = new LinkedList<>();
        }
        
        addEdge(0, 1); 
        addEdge(0, 4); 
        addEdge(1, 2); 
        addEdge(1, 3); 
        addEdge(1, 4); 
        addEdge(2, 3); 
        addEdge(3, 4);
        
        printGraph();
    }
    
    static void printGraph()
    {
        for(int i=0;i<adjList.length;i++)
        {
            System.out.println("Adjacency list for vertex " + i);
            System.out.print("Head");
            for(Integer num: adjList[i])
            {
                System.out.print(" -> " + num);
            }
            System.out.println();
        }
    }
    
    static void addEdge(int from, int to)
    {
        adjList[from].add(to);        
    }
}
