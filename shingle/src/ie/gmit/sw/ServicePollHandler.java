package ie.gmit.sw;

import java.io.*;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * @author Tangqi Feng
 * @version 1.0
 *
 * this class handle the result check request from web client,
 * get job out-Queue from ServiceHandler
 * check request job is/not in the out-Queue, if yes, take out from out-Queue
 * return html page (with result) to client
 */
public class ServicePollHandler extends HttpServlet {
    public void init() throws ServletException {
        ServletContext ctx = getServletContext();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        String title = req.getParameter("txtTitle");
        String taskNumber = req.getParameter("frmTaskNumber");
        int counter = 1;
        if (req.getParameter("counter") != null){
            counter = Integer.parseInt(req.getParameter("counter"));
            counter++;
        }
        // check outQueue
        Map<String,Double> outQueue = ServiceHandler.getOutQueue();
        Double result = outQueue.get(taskNumber);
        if (result != null){
            // move out the job from out_queue
            ServiceHandler.removeOutQueue(taskNumber);
            // Output the result page
            out.print("<html><head><title>A JEE Application for Measuring Document Similarity</title>");
            out.print("</head>");
            out.print("<body>");
            out.print("<H1>Processing request for Job#: " + taskNumber + "</H1>");
            out.print("<H3>Document Title: " + title + "</H3>");
            out.print("<b><font color=\"ff0000\">A total of " + counter + " polls have been made for this request.</font></b> ");
            out.print("<H3>Jaccard Similarity: <font color=\"0000ff\">" + result*100 + "% </font></H3>");
            out.print("</body>");
            out.print("</html>");
            return;
        }
        out.print("<html><head><title>A JEE Application for Measuring Document Similarity</title>");
        out.print("</head>");
        out.print("<body>");
        out.print("<H1>Processing request for Job#: " + taskNumber + "</H1>");
        out.print("<H3>Document Title: " + title + "</H3>");
        out.print("<b><font color=\"ff0000\">A total of " + counter + " polls have been made for this request.</font></b> ");
        out.print("Please wait for the result ^_^ ...");
        out.print("<form name=\"frmRequestDetails\">");
        out.print("<input name=\"txtTitle\" type=\"hidden\" value=\"" + title + "\">");
        out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
        out.print("<input name=\"counter\" type=\"hidden\" value=\"" + counter + "\">");
        out.print("</form>");
        out.print("</body>");
        out.print("</html>");
        out.print("<script>");
        out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 5000);"); //Refresh every 5 seconds
        out.print("</script>");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}