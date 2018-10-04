/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adbms;

/**
 *
 * @author Aditya Tiwari
 */
public class Task {
    private String operation;
    
    private int transaction;
    
    private String dataItem;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getTransaction() {
        return transaction;
    }

    public void setTransaction(int transaction) {
        this.transaction = transaction;
    }

    public String getDataItem() {
        return dataItem;
    }

    public void setDataItem(String dataItem) {
        this.dataItem = dataItem;
    }        

    @Override
    public String toString() {
        return "Task{" + "operation=" + operation + ", transaction=" + transaction + ", dataItem=" + dataItem + '}';
    }

    public Task(String operation, int transaction, String dataItem) {
        this.operation = operation;
        this.transaction = transaction;
        this.dataItem = dataItem;
    }

    public Task() {
    }
}
