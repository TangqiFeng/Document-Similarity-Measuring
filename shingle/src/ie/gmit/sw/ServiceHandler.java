package ie.gmit.sw;

import java.io.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

/* NB: You will need to add the JAR file $TOMCAT_HOME/lib/servlet-api.jar to your CLASSPATH
 *     variable in order to compile a servlet from a command line.
 */
@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB. The file size in bytes after which the file will be temporarily stored on disk. The default size is 0 bytes.
        maxFileSize=1024*1024*10,      // 10MB. The maximum size allowed for uploaded files, in bytes
        maxRequestSize=1024*1024*50)   // 50MB. he maximum size allowed for a multipart/form-data request, in bytes.
public class ServiceHandler extends HttpServlet {
    /* Declare any shared objects here. For example any of the following can be handled from
         * this context by instantiating them at a servlet level:
         *   1) An Asynchronous Message Facade: declare the IN and OUT queues or MessageQueue
         *   2) An Chain of Responsibility: declare the initial handler or a full chain object
         *   1) A Proxy: Declare a shared proxy here and a request proxy inside doGet()
         */
    private String environmentalVariable = null; //Demo purposes only. Rename this variable to something more appropriate
    private static long jobNumber = 0; // default job number
    private static int SHINGLE_SIZE; // shingle size defined in web.xml
    private static int INQUEUE_SIZE; // in_queue size defined in web.xml

    //bolocking queue, used to store in-queue
    private BlockingQueue<Job> in_queue;
    //map<taskNumber, result>, used to store out-queue
    // HashMap is not thread save, I use ConcurrentHashMap, but I do not think it is absolute save ...
    private static Map<String,Double> out_queue = new ConcurrentHashMap<>();


    /* This method is only called once, when the servlet is first started (like a constructor).
     * It's the Template Patten in action! Any application-wide variables should be initialised
     * here. Note that if you set the xml element <load-on-startup>1</load-on-startup>, this
     * method will be automatically fired by Tomcat when the web server itself is started.
     */
    public void init() throws ServletException {
        ServletContext ctx = getServletContext(); //The servlet context is the application itself.

        //Reads the value from the <context-param> in web.xml. Any application scope variables
        //defined in the web.xml can be read in as follows:
        environmentalVariable = ctx.getInitParameter("SOME_GLOBAL_OR_ENVIRONMENTAL_VARIABLE");
        SHINGLE_SIZE = Integer.parseInt(ctx.getInitParameter("SHINGLE_SIZE"));
        INQUEUE_SIZE = Integer.parseInt(ctx.getInitParameter("INQUEUE_SIZE"));
        in_queue = new LinkedBlockingDeque<Job>(INQUEUE_SIZE);

    }


    /* The doGet() method handles a HTTP GET request. Please note the following very carefully:
     *   1) The doGet() method is executed in a separate thread. If you instantiate any objects
     *      inside this method and don't pass them around (ie. encapsulate them), they will be
     *      thread safe.
     *   2) Any instance variables like environmentalVariable or class fields like jobNumber will
     *      are shared by threads and must be handled carefully.
     *   3) It is standard practice for doGet() to forward the method invocation to doPost() or
     *      vice-versa.
     */
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Step 1) Write out the MIME type
        resp.setContentType("text/html");

        //Step 2) Get a handle on the PrintWriter to write out HTML
        PrintWriter out = resp.getWriter();

        //Step 3) Get any submitted form data. These variables are local to this method and thread safe...
        String title = req.getParameter("txtTitle");
        String taskNumber = req.getParameter("frmTaskNumber");
        Part part = req.getPart("txtDocument");


        //Step 4) Process the input and write out the response.
        //The following string should be extracted as a context from web.xml
        out.print("<html><head><title>A JEE Application for Measuring Document Similarity</title>");
        out.print("</head>");
        out.print("<body>");
        //We could use the following to track asynchronous tasks. Comment it out otherwise...
        if (taskNumber == null){
            taskNumber = new String("T" + jobNumber);
            jobNumber++;
        }else{
            RequestDispatcher dispatcher = req.getRequestDispatcher("/poll");
            dispatcher.forward(req,resp);
            //Check out-queue for finished job with the given taskNumber
        }
        //Output some headings at the top of the generated page
        out.print("<H1>Processing request for Job#: " + taskNumber + "</H1>");
        out.print("<H3>Document Title: " + title + "</H3>");
        //Output doc content
        out.print("<h3>Uploaded Document Content</h3>");
        out.print("<font color=\"0000ff\">");
        Job job = new Job(taskNumber,title);
        /* File Upload: The following few lines read the multipart/form-data from an instance of the
        * interface Part that is accessed by Part part = req.getPart("txtDocument"). We can read
        * bytes or arrays of bytes by calling read() on the InputStream of the Part object. In this
        * case, we are only interested in text files, so it's as easy to buffer the bytes as characters
        * to enable the servlet to read the uploaded file line-by-line. Note that the uplaod action
        * can be easily completed by writing the file to disk if necessary. The following lines just
        * read the document from memory... this might not be a good idea if the file size is large! */
        BufferedReader br = new BufferedReader(new InputStreamReader(part.getInputStream()));
        StringBuffer buffer = new StringBuffer();
        String line = null;
        while ((line = br.readLine()) != null) {
            buffer.append(line);
        }
        out.print(buffer.toString()); // print txt content
        try {
            // get shingles from StringBuffer and save to job
            job.setShingles(getShingles(buffer));
            //Add job to in-queue
            in_queue.put(job);
            new Thread(() -> {
                try {
                    calculate();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            //calculate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Shingle w : job.getShingles()){
            out.print(w.getHashcode()+" ");
        }




        out.print("</font>");
        //Output some useful information for you (yes YOU!)
        out.print("<div id=\"r\"></div>");
        out.print("<font color=\"#993333\"><b>");
        out.print("Environmental Variable Read from web.xml: " + environmentalVariable);
        out.print("<br>This servlet should only be responsible for handling client request and returning responses. Everything else should be handled by different objects.");
        out.print("Note that any variables declared inside this doGet() method are thread safe. Anything defined at a class level is shared between HTTP requests.");
        out.print("</b></font>");

        out.print("<h3>Compiling and Packaging this Application</h3>");
        out.print("Place any servlets or Java classes in the WEB-INF/classes directory. Alternatively package ");
        out.print("these resources as a JAR archive in the WEB-INF/lib directory using by executing the ");
        out.print("following command from the WEB-INF/classes directory jar -cf my-library.jar *");

        out.print("<ol>");
        out.print("<li><b>Compile on Mac/Linux:</b> javac -cp .:$TOMCAT_HOME/lib/servlet-api.jar WEB-INF/classes/ie/gmit/sw/*.java");
        out.print("<li><b>Compile on Windows:</b> javac -cp .;%TOMCAT_HOME%/lib/servlet-api.jar WEB-INF/classes/ie/gmit/sw/*.java");
        out.print("<li><b>Build JAR Archive:</b> jar -cf jaccard.war *");
        out.print("</ol>");

        //We can also dynamically write out a form using hidden form fields. The form itself is not
        //visible in the browser, but the JavaScript below can see it.
        out.print("<form name=\"frmRequestDetails\" action=\"poll\">");
        out.print("<input name=\"txtTitle\" type=\"hidden\" value=\"" + title + "\">");
        out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
        out.print("</form>");
        out.print("</body>");
        out.print("</html>");

        //JavaScript to periodically poll the server for updates (this is ideal for an asynchronous operation)
        out.print("<script>");
        out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 10000);"); //Refresh every 10 seconds
        out.print("</script>");




    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    // get shingles from StringBuffer
    private ArrayList<Shingle> getShingles(StringBuffer buffer) throws Exception {
        ShingleRequestPara para = new ShingleRequestPara();
        para.setStringBuffer(buffer);
        para.setShingleSize(SHINGLE_SIZE);
        // create getEngWords and getShingleBlockingQueue requests
        ShingleRequest r1 = new ShingleRequest(ShingleRequest.getEngWords);
        ShingleRequest r2 = new ShingleRequest(ShingleRequest.getShingleArrayList);
        // create handler
        ShingleHandler h = new PreShingleHandler();
        // handle requests
        String words = h.handleShingle(r1,para).toString();
        String[] engWords = words.split(" ");
        para.setWords(engWords);
        return  (ArrayList<Shingle>) h.handleShingle(r2,para);
    }

    private void calculate() throws InterruptedException {
        Job job = in_queue.take();
        Set in_set = new TreeSet();
        job.getShingles().forEach((s)->in_set.add(s.getHashcode()));
        Set db_set = getShinglesFromDB();
        Set n = new TreeSet(in_set);
        n.retainAll(db_set);
        double jaccardValue =  n.size() / (in_set.size() + db_set.size() - n.size()) * 1.0;
        job.setResult(jaccardValue);

        addOutQueue(job);
    }

    private void addOutQueue(Job job)
    {
        out_queue.put(job.getTaskNumber(),job.getResult());
    }
    private Set getShinglesFromDB(){
        // at this moment skip DB stuff
        Set set = new TreeSet();
        set.add(-761363859);
        set.add(1650764645);
        set.add(239957193);
        set.add(110251550);
        return set;
    }

    protected static Map<String,Double> getOutQueue(){
        return out_queue;
    }

    protected static void removeOutQueue(String key){
        out_queue.remove(key);
    }
}