package Kevin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class ClassProject
 */
@WebServlet("/ClassProject")
public class ClassProject extends HttpServlet {
	private static final long serialVersionUID = 1L;
 	private final String question = "What is the capital of France?";
	private final String[] choices = { "Paris", "Berlin", "London", "Rome" };

	/**
	* @see HttpServlet#HttpServlet()
	*/
	public ClassProject() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the selected answer from the client
		String selectedAnswer = request.getParameter("selectedAnswer");
		String result = processAnswer(selectedAnswer);
	
		// Send the result back to the client as HTML
		response.getWriter().write("<html><body>"
					+ "<h1>Question</h1>"
					+ "<p>" + question + "</p>"
	
					// Display the selected answer and result
					+ "<p>You selected: " + selectedAnswer + "</p>"
					+ "<p>Result: " + result + "</p>");
	
		// Display the choices again
		displayChoices(response);
	
		response.getWriter().write("</body></html>");
	}
	private String processAnswer(String answer) {
		// Perform server-side processing here
		// For simplicity, just checking if the answer is correct
		return (answer.equals("Paris")) ? "Correct!" : "Incorrect!";
	}
	private void displayChoices(HttpServletResponse response) throws IOException {
		response.getWriter().write("<form method=\"post\">");

		for (String choice : choices)
			response.getWriter().write("<input type=\"radio\" name=\"selectedAnswer\" value=\"" + choice + "\">"
						  + choice + "<br>");

		response.getWriter().write("<br>"
					+ "<button type=\"submit\">Submit Answer</button>"
					+ "</form>");
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.getWriter().write("<!DOCTYPE html>"
					+ "<html>"
					+ "<head><title>Question Picker</title></head>"
					+ "<body>"
					  
					+ "<h1>Question</h1>"
					+ "<p>" + question + "</p>");
		
		// Display choices for the initial page
		displayChoices(response);
		
		response.getWriter().write("</body>" + "</html>");
	}
}
