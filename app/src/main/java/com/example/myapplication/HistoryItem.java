package com.example.myapplication;

import java.io.Serializable;

public class HistoryItem implements Serializable {
    public int startDeposit;
    public int period;
    public int interest;
    public int deposit;
    public long finalInterest;
    public long finalDeposit;

    public HistoryItem(int startDeposit, int period, int interest, int deposit, long finalInterest, long finalDeposit) {
        this.startDeposit = startDeposit;
        this.period = period;
        this.interest = interest;
        this.deposit = deposit;
        this.finalInterest = finalInterest;
        this.finalDeposit = finalDeposit;
    }
}
