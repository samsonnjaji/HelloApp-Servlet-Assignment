package com.helloapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 * HelloServlet - My first servlet assignment
 * This is for the "Build, Deploy, and Test a Basic Java Servlet" assignment
 * I'm trying to make it show a nice welcome page when people visit
 *
 * @author [Your Name]
 */
public class HelloServlet extends HttpServlet {

    private int visitCount = 0;  // I want to count how many times people visit
    private LocalDateTime startTime;  // Keep track of when I started the servlet

    // Constructor - this gets called when Tomcat creates the servlet
    public HelloServlet() {
        super();
        System.out.println("HelloServlet constructor called - servlet being created");
    }

    // init method - this happens when the servlet first starts up
    @Override
    public void init() throws ServletException {
        super.init();
        startTime = LocalDateTime.now();  // Remember when we started
        System.out.println("HelloServlet init() - servlet ready at: " + startTime);
    }

    // doGet method - this is the main one that handles when people visit the page
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Every time someone visits, I'll count it
        visitCount++;
        System.out.println("Someone visited! This is visit number: " + visitCount);

        // Tell the browser we're sending HTML
        response.setContentType("text/html;charset=UTF-8");

        // Get session - this helps track users across requests
        HttpSession session = request.getSession(true);

        // Get the current time to show on the page
        LocalDateTime now = LocalDateTime.now();
        String currentTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Find out who's visiting (IP address and browser info)
        String clientIP = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        // Check if they gave us a name in the URL (like ?name=John)
        String userName = request.getParameter("name");
        if (userName == null || userName.trim().isEmpty()) {
            userName = "Student";  // Default name if they didn't provide one
        }

        // Figure out how long my servlet has been running
        long uptimeMinutes = java.time.Duration.between(startTime, now).toMinutes();

        // Now I'll create the HTML page to send back
        PrintWriter out = response.getWriter();

        // Start building the HTML - learned this structure in class
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("    <meta charset='UTF-8'>");
        out.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("    <title>Hello Servlet - My Assignment</title>");

        // CSS to make it look nice - I spent some time on this part
        out.println("    <style>");
        out.println("        body { font-family: Arial, sans-serif; margin: 40px; background: #f5f5f5; }");
        out.println("        .container { max-width: 800px; margin: 0 auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }");
        out.println("        h1 { color: #2c3e50; text-align: center; margin-bottom: 30px; }");
        out.println("        .welcome { background: #3498db; color: white; padding: 20px; border-radius: 8px; text-align: center; margin: 20px 0; }");
        out.println("        .info-box { background: #ecf0f1; padding: 15px; margin: 15px 0; border-radius: 5px; border-left: 4px solid #3498db; }");
        out.println("        .info-label { font-weight: bold; color: #34495e; }");
        out.println("        .nav-bar { background: #34495e; padding: 15px; border-radius: 8px; text-align: center; margin: 20px 0; }");
        out.println("        .nav-bar a { color: white; text-decoration: none; margin: 0 15px; padding: 8px 16px; border-radius: 20px; background: rgba(255,255,255,0.1); }");
        out.println("        .nav-bar a:hover { background: rgba(255,255,255,0.2); }");
        out.println("        .stats { background: #27ae60; color: white; padding: 20px; border-radius: 8px; text-align: center; margin: 20px 0; }");
        out.println("        .footer { text-align: center; margin-top: 30px; color: #7f8c8d; padding: 20px; background: #ecf0f1; border-radius: 8px; }");
        out.println("    </style>");
        out.println("</head>");
        out.println("<body>");

        out.println("    <div class='container'>");

        // Main heading for my page
        out.println("        <h1>🎉 Hello Servlet Assignment</h1>");

        // Navigation links - professor said we should be able to go back to the homepage
        out.println("        <div class='nav-bar'>");
        out.println("            <a href='" + request.getContextPath() + "/'>🏠 Home</a>");
        out.println("            <a href='" + request.getContextPath() + "/HelloServlet'>🎉 Servlet</a>");
        out.println("            <a href='" + request.getContextPath() + "/hello'>👋 Hello</a>");
        out.println("            <a href='" + request.getContextPath() + "/welcome'>🌟 Welcome</a>");
        out.println("        </div>");

        // The main welcome message - this is what the assignment asked for
        out.println("        <div class='welcome'>");
        out.println("            <h2>Hello, " + userName + "! Welcome to my first servlet!</h2>");
        out.println("            <p>This servlet successfully handles HTTP requests and generates dynamic content.</p>");
        out.println("        </div>");

        // Show the current server time
        out.println("        <div class='info-box'>");
        out.println("            <div class='info-label'>🕒 Server Time:</div>");
        out.println("            <div>" + currentTime + "</div>");
        out.println("        </div>");

        // Show who's visiting (their IP address)
        out.println("        <div class='info-box'>");
        out.println("            <div class='info-label'>🌐 Your IP Address:</div>");
        out.println("            <div>" + clientIP + "</div>");
        out.println("        </div>");

        // Some request information - learned about this in the servlet tutorial
        out.println("        <div class='info-box'>");
        out.println("            <div class='info-label'>📋 Request Information:</div>");
        out.println("            <div><strong>Method:</strong> " + request.getMethod() + "</div>");
        out.println("            <div><strong>URI:</strong> " + request.getRequestURI() + "</div>");
        out.println("            <div><strong>Context Path:</strong> " + request.getContextPath() + "</div>");
        out.println("        </div>");

        // Session stuff - this keeps track of users
        out.println("        <div class='info-box'>");
        out.println("            <div class='info-label'>💾 Session Information:</div>");
        out.println("            <div><strong>Session ID:</strong> " + session.getId() + "</div>");
        out.println("            <div><strong>New Session:</strong> " + (session.isNew() ? "Yes" : "No") + "</div>");
        out.println("        </div>");

        // Information about my servlet
        out.println("        <div class='info-box'>");
        out.println("            <div class='info-label'>🔧 Servlet Details:</div>");
        out.println("            <div><strong>Servlet Name:</strong> HelloServlet</div>");
        out.println("            <div><strong>Class:</strong> " + this.getClass().getSimpleName() + "</div>");
        out.println("            <div><strong>Uptime:</strong> " + uptimeMinutes + " minutes</div>");
        out.println("        </div>");

        // Show browser info but make it shorter so it fits nicely
        if (userAgent != null) {
            String shortAgent = userAgent.length() > 50 ? userAgent.substring(0, 50) + "..." : userAgent;
            out.println("        <div class='info-box'>");
            out.println("            <div class='info-label'>🌐 Browser:</div>");
            out.println("            <div>" + shortAgent + "</div>");
            out.println("        </div>");
        }

        // Statistics box - I think this looks cool
        out.println("        <div class='stats'>");
        out.println("            <h3>📊 Servlet Statistics</h3>");
        out.println("            <p><strong>Total Visits:</strong> " + visitCount + "</p>");
        out.println("            <p><strong>Status:</strong> Running Successfully</p>");
        out.println("            <p><strong>Technology:</strong> Java Servlet + Apache Tomcat</p>");
        out.println("        </div>");

        // Footer with assignment info
        out.println("        <div class='footer'>");
        out.println("            <h4>Assignment: Build, Deploy, and Test a Basic Java Servlet</h4>");
        out.println("            <p>This servlet demonstrates HTTP request handling, dynamic content generation, and proper servlet lifecycle.</p>");
        out.println("            <p><strong>Features:</strong> Session Management • Dynamic Content • Request Processing • Professional UI</p>");
        out.println("        </div>");

        out.println("    </div>");
        out.println("</body>");
        out.println("</html>");

        out.close();  // Always close the output stream when done

        System.out.println("Sent response to: " + clientIP);
    }

    // doPost method - handle POST requests (like form submissions)
    // For this assignment, I'll just treat POST the same as GET
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Just call doGet since I want the same response for both
        doGet(request, response);
    }

    // This method returns info about my servlet
    @Override
    public String getServletInfo() {
        return "HelloServlet - My first servlet assignment showing basic functionality";
    }

    // destroy method - called when Tomcat shuts down the servlet
    @Override
    public void destroy() {
        System.out.println("HelloServlet is being destroyed. Total visits: " + visitCount);
        super.destroy();  // Always call the parent destroy method
    }
}