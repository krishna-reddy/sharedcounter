package com.examples;


import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Controller
public class CounterServiceServlet{
	private static final PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("transactions-optional");
	private static PersistenceManager pm = pmf.getPersistenceManager();
	private BigInteger count=BigInteger.ZERO;

	@RequestMapping(value="/sharedcounter/createCounterEntry",method=RequestMethod.POST)
	public @ResponseBody String createCounterEntry(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
	
		pm=pmf.getPersistenceManager();
		String projectIdentity=req.getParameter("projectIdentity");
		String counterName=req.getParameter("counterName");
		Key key = KeyFactory.createKey(CounterJDO.class.getSimpleName(),projectIdentity);
        Transaction tx = pm.currentTransaction();
        CounterJDO cjdo = new CounterJDO();
        cjdo.setCounterKey(key);
        cjdo.setCounterName(counterName);
        count=count.add(BigInteger.ONE);
        cjdo.setCount(count);
        JSONObject json=new JSONObject();
        try{
		tx.begin();
		pm.makePersistent(cjdo);
		json.put("counterName", counterName);
		json.put("count", count);
		tx.commit();
		}catch(Exception e1){
			
			
		}
		
		finally
		{
		    if (tx.isActive())
		    {
		        // Error occurred so rollback the transaction
		        tx.rollback();
		    }
		    pm.close();
		}
		return json.toJSONString();
		
		
		

	}//createCounterEntry
	
	@RequestMapping(value="/sharedcounter/updateCounterEntry",method=RequestMethod.POST)
	public @ResponseBody String updateCounterEntry(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		pm=pmf.getPersistenceManager();
		JSONObject json=new JSONObject();
		String projectIdentity=req.getParameter("projectIdentity");
		String counterName=req.getParameter("counterName");
		CounterJDO cjdo=pm.getObjectById(CounterJDO.class,projectIdentity);
		count=cjdo.getCount();
		count=count.add(BigInteger.ONE);
		cjdo.setCount(count);
		cjdo.setCounterName(counterName);
		Transaction tx=pm.currentTransaction();
		try{
		tx.begin();
		pm.makePersistent(cjdo);
		json.put("counterName", counterName);
		json.put("count", count);
		tx.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
		    if (tx.isActive())
		    {
		        // Error occurred so rollback the transaction
		        tx.rollback();
		    }
		    pm.close();
		}
		return json.toJSONString();
	}
	
	@RequestMapping(value="sharedcounter/deleteCounterEntry",method=RequestMethod.POST)
	public @ResponseBody String deleteCounterEntry(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		JSONObject json=new JSONObject();

		pm=pmf.getPersistenceManager();
		String projectIdentity=req.getParameter("projectIdentity");
		String counterName=req.getParameter("counterName");
		CounterJDO cjdo=pm.getObjectById(CounterJDO.class,projectIdentity);
		count=cjdo.getCount();
		if(!count.equals(BigInteger.ZERO)){
		count=count.subtract(BigInteger.ONE);
		}
		cjdo.setCount(count);
		
		
		Transaction tx=pm.currentTransaction();
		try{
		tx.begin();
		pm.makePersistent(cjdo);
		json.put("counterName", counterName);
		json.put("count", count);
		tx.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
		    if (tx.isActive())
		    {
		        // Error occurred so rollback the transaction
		        tx.rollback();
		    }
		    pm.close();
		}
		
		return json.toJSONString();
	}
	
	
	@RequestMapping(value="/sharedcounter/getCount",method=RequestMethod.POST)
	public @ResponseBody String getCount(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		JSONObject json=new JSONObject();

		pm=pmf.getPersistenceManager();
		String projectIdentity=req.getParameter("projectIdentity");
		CounterJDO cjdo = null;
		try{
		cjdo=pm.getObjectById(CounterJDO.class,projectIdentity);
		json.put("counterName", cjdo.getCounterName());
		json.put("count", cjdo.getCount());
		}catch(Exception e){
			
		}

		return json.toJSONString();
	}
}
