package com.samuelkupferschmid.dist;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;
import java.util.BitSet;

public class BloomFilter {

    private int k;
    private int m;
    private int n;
    private double p;

    private HashFunction[] hashFunctions;
    private BitSet hashSet;

    /**
     * @param n defines the expected length of elements
     * @param p defines the expected precision
     */
    public BloomFilter(int n, double p) {
        this.n = n;
        this.p = p;

        m =  -(int)((n * Math.log(p)) / (Math.log(2) * Math.log(2)));
        k =  -(int)(Math.log(p) / Math.log(2));

        hashFunctions = new HashFunction[k];

        for (int i=0; i<k; i++) {
            hashFunctions[i] = Hashing.murmur3_128(i);
        }

        hashSet = new BitSet(m/8);
    }

    public void insert(String value){
        for (HashFunction f : hashFunctions) {
            hashSet.set(getModuleHash(f,value),true);
        }
    }

    public boolean lookup(String value){
        for (HashFunction f : hashFunctions) {
            if (!hashSet.get(getModuleHash(f,value)))
                return false;
        }

        return true;
    }

    private int getModuleHash(HashFunction func, String value) {
        HashCode h = func.hashString(value, Charset.defaultCharset());
        return Math.abs(h.asInt()) % m;
    }
}
