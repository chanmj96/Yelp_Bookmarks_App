package com.example.mattchan.bookmarksapplication;

/**
 * Created by ttl64 on 1/14/2017.
 */

public class Pair<L,R>{
    private L l;
    private R r;
    public Pair(L l, R r){
        this.l = l;
        this.r = r;
    }

    public L getL(){ return l; }
    public R getR(){ return r; }
    public void setL(L l){ this.l = l; }
    public void setR(R r){ this.r = r; }
}
