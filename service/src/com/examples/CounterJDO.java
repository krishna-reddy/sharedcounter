package com.examples;

import java.math.BigInteger;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class CounterJDO {
@PrimaryKey
private Key counterKey;
private String counterName;
private BigInteger count;
public Key getCounterKey() {
	return counterKey;
}
public void setCounterKey(Key counterKey) {
	this.counterKey = counterKey;
}
public String getCounterName() {
	return counterName;
}
public void setCounterName(String counterName) {
	this.counterName = counterName;
}
public BigInteger getCount() {
	return count;
}
public void setCount(BigInteger count) {
	this.count = count;
}


}
