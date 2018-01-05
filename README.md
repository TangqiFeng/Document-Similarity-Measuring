# Document-Similarity-Measuring

> author: [Tangqi Feng](https://tangqifeng.github.io/)

4th year advanced OOP project

> Module: Advanced Object Oriented Software Development / 4th Year  
> Lecturer: John.Healy

This is a Java web application that enables two or more text documents to be compared for similarity.

![structure](https://user-images.githubusercontent.com/22374434/34630248-4ffb2564-f263-11e7-940c-4edb5506a3fe.png)

## How this repository designed

This repository includes:
* Servlet Handler:
  * ServiceHandler.java => handle the main request from web client, handle job in-Queue and out-Queue, take jobs from in-Queue, put in out-Queue when finished
  * ServicePollHandler.java => handle the result check request from web client, check request job is/not in the out-Queue, if yes, take out from out-Queue(get from ServiceHandler.java), display html page (with result) to client
* ShingleHandler:
  * ShingleHandler.java => nterface defines the abstract handleShingle method, all handlers which implements this obey "The chain of responsibility design pattern" 
  * PreShingleHandler.java => implement ShingleHandler, have a private method to return words string[] from bufferReader, this class act the start of the chain, which means all request for handlers indicates through this class. (chain of responsibility)
  * GenerateShingleHandler.java => implement ShingleHandler, have methods to return ArrayList/BlockingQueue etc. Shingles with parameter String[]
  * CompareShingleHandler.java => implement ShingleHandler, have methods(default formula, minhash etc.) to return Jaccard result
  * ShingleRequest.java => a bean class, a part of "The chain of responsibility design pattern" component. includes all command or valid method of ShingleHandler, acts as parameter to call different methods defined in ShingleHandlers
  * ShingleRequestPara.java => a bean class, includes all necessary parameter attributes for calling methods of ShingleHandler, acts as parameter to call different methods defined in ShingleHandlers
* MinHash calculation:
  * Consumer.java => contains threadPool to calculate min hashs, and put the resust to the map stored in "MapStore class"
  * MapStore class => inside Consumer.java, used to store documents' MinHash codes.
* Servlet Stuff:
  * index.jsp => the initial page showing to people
  * web.xml => encapsulate any environmental variables, will read and initialised by a start-up servlet.

The UML Diagram:
![uml](https://user-images.githubusercontent.com/22374434/34631145-02d221bc-f267-11e7-83f8-aabdd5dece81.png)

## How to run?

* 1.Import project to your favorite IDEA (Eclipse, Intellij etc.)
  
  * do not forget to add Tomcat server !
  * add DB4O database (jar files in the folder db4o ) to your project library !
  
  2.open browser and type http://localhost:8080 to the address bar and use the services.
  
* 1.put the file -> **jaccard.war** to the **webapp** folder of your Tomcat server.

  2.add DB4O database (jar files in the folder db4o ) to the **lib** folder of your Tomcat server.
  
  3.open browser and type http://localhost:8080/jaccard to the address bar and use the services.
  
### Note: there are no data in db4o database, which means once the server running, the first doc you upload have 0.0% similarity.



