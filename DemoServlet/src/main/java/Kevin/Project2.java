package Kevin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class Project2
 */
@WebServlet("/Project2")
public class Project2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String question = "What is your favorite programming language?";
    private final String[] choices = { "Java", "Python", "JavaScript", "C#" };

    // In-memory data structure to store poll results
    private final Map<String, Integer> pollResults = new HashMap<>();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the selected answer from the client
        String selectedAnswer = request.getParameter("selectedAnswer");
        processAnswer(selectedAnswer);

        // Send the updated poll results back to the client as HTML
        response.getWriter().write("<html><body>");
        response.getWriter().write("<h1>Poll Results</h1>");
        response.getWriter().write("<p>" + question + "</p>");

        // Display the updated poll results
        displayResults(response);

        // Display choices for the next poll
        displayChoices(response);

        response.getWriter().write("</body></html>");
    }
    private void processAnswer(String answer) {
        // Update poll results in the in-memory data structure
        pollResults.put(answer, pollResults.getOrDefault(answer, 0) + 1);
    }

    // Generate the HTML code for displaying poll results
    private void displayResults(HttpServletResponse response) throws IOException {
        response.getWriter().write("<p><b>Poll Results:</b></p>");
        for (Map.Entry<String, Integer> entry : pollResults.entrySet()) {
            response.getWriter().write(entry.getKey() + ": " + entry.getValue() + "<br>");
        }
        response.getWriter().write("<br>");
    }
    private void displayChoices(HttpServletResponse response) throws IOException {
        response.getWriter().write("<form method=\"post\">");

        for (String choice : choices) {
            response.getWriter().write("<input type=\"radio\" name=\"selectedAnswer\" value=\"" + choice + "\">");
            response.getWriter().write(choice + "<br>");
        }

        response.getWriter().write("<br>");
        response.getWriter().write("<button type=\"submit\">Submit Answer</button>");
        response.getWriter().write("</form>");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.getWriter().write("<!DOCTYPE html>");
        response.getWriter().write("<html>");
        response.getWriter().write("<head><title>Poll</title></head>");
        response.getWriter().write("<body>");

        response.getWriter().write("<h1>Poll</h1>");
        response.getWriter().write("<p>" + question + "</p>");

        // Display choices for the initial page
        displayChoices(response);

        response.getWriter().write("</body>");
        response.getWriter().write("</html>");
    }

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Project2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
		/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	

}
