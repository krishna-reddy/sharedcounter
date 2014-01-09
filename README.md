sharedcounter
=============

shared counter app
==================
Purpose /Goal :
--------------	
As we know in GAE-Datastore, on single query we can retrieve max 1000 records only, this puts developers in backseat on getting the total record count of any table. 

-----To overcome this shortfall, we want to create counter app as a service. The objective is update the object count on every transaction using this service. 

Development Needs :
-----------------
1)eclipse
2)GAE plug in for eclipse
3)spring 3.x

Iteration  1 :

	Create a JDO with attribute counter and Name, and provide a rest service to insert/update the counter with the name of the counter. 

CounterJDO structure :
--------------------
			
Entity Name
Data Type
counterKey
Key
counterName
String
count
BigInteger



Service spec to Create new Counter :
-----------------------------------
URL structure 	: http://<appid>.appspot.com/sharedcounter/createCounterEntry

Method 		: POST

Request Structure 	: 
			{
			projectIdentity : <Project_Identity> , [Required]
			countertName	 : <counter_Name>     [Required]
			}

Response		: 
			{ 
counterName : <counter_Name> ,
count		: <count>
}



Service Spec to Update/Increment a Counter
-------------------------------------------

URL Structure		: http://<appid>.appspot.com/sharedcounter/updateCounterEntry
 
Method 		: POST

Request Structure 	: 
			{
			projectIdentity : <Project_Identity> , [Required]
			countertName	 : <counter_Name>     [Required]
			}

Response		: 
{ 
counterName : <counter_Name> ,
count		: <count>
}



Service Spec to Delete/Decrement a Counter:
-----------------------------------------

URL Structure		: http://<appid>.appspot.com/sharedcounter/deleteCounterEntry
 
Method 		: POST

Request Structure 	: 
			{
			projectIdentity : <Project_Identity> , [Required]
			countertName	 : <counter_Name>     [Required]
			}

Response		: 
{ 
counterName : <counter_Name> ,
count		: <count>
}




Service Spec to Get the Count:
-----------------------------

URL Structure		: http://<appid>.appspot.com/sharedcounter/getCount
 
Method 		: POST



Request Structure 	: 
			{
			projectIdentity : <Project_Identity> , 
			countertID : <counter_Key>     [Required]
			}
	
Response		: 
{ 
counterName : <counter_Name> ,
count		: <count>
}


