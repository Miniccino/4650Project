package Kevin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Project2")
public class Project2 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final String question = "What is your favorite programming language?";
    private final String[] choices = { "Java", "Python", "JavaScript", "C#" };

    private final Map<String, Integer> pollResults = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("showPoll".equals(action)) {
            showPollPage(request, response);
        } else {
            showWelcomePage(request, response);
        }
    }

    private void showWelcomePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        writeHtmlStart(response, "Welcome");
        response.getWriter().write("<div class='container'>");
        response.getWriter().write("<h1>Welcome to the Poll</h1>");
        response.getWriter().write("<p>Click below to start the poll.</p>");
        response.getWriter().write("<form action='?action=showPoll' method='post'>");
        response.getWriter().write("<button type='submit'>Hi</button>");
        response.getWriter().write("</form>");
        response.getWriter().write("</div>");
        writeHtmlEnd(response);
    }

    private void showPollPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        writeHtmlStart(response, "Poll");
        response.getWriter().write("<div class='container'>");
        response.getWriter().write("<div class='poll-section'>");
        response.getWriter().write("<h1>Poll</h1>");
        response.getWriter().write("<p>" + question + "</p>");
        displayChoices(response);
        response.getWriter().write("</div>");
        response.getWriter().write("</div>");
        writeHtmlEnd(response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String selectedAnswer = request.getParameter("selectedAnswer");
        processAnswer(selectedAnswer);
        response.setContentType("text/html");
        writeHtmlStart(response, "Poll Results");
        response.getWriter().write("<div class='container'>");
        response.getWriter().write("<div class='poll-section'>");
        response.getWriter().write("<h1>Poll Results</h1>");
        displayResults(response);
        response.getWriter().write("<div class='result-section'>");
        displayChoices(response);
        response.getWriter().write("</div>");
        response.getWriter().write("</div>");
        response.getWriter().write("</div>");
        writeHtmlEnd(response);
    }

    private void processAnswer(String answer) {
        if (answer != null && !answer.isEmpty()) {
            pollResults.put(answer, pollResults.getOrDefault(answer, 0) + 1);
        }
    }

    private void displayResults(HttpServletResponse response) throws IOException {
        response.getWriter().write("<p><b>Poll Results:</b></p>");
        for (Map.Entry<String, Integer> entry : pollResults.entrySet()) {
            response.getWriter().write(entry.getKey() + ": " + entry.getValue() + "<br>");
        }
        response.getWriter().write("<br>");
    }

    private void displayChoices(HttpServletResponse response) throws IOException {
        response.getWriter().write("<form method='post'>");
        response.getWriter().write("<div class='choice-container'>");
        for (String choice : choices) {
            String choiceId = choice.replace(" ", "_");
            response.getWriter().write("<input type='radio' id='" + choiceId + "' name='selectedAnswer' value='" + choice + "'>");
            response.getWriter().write("<label for='" + choiceId + "' class='choice'>" + choice + "</label>");
        }
        response.getWriter().write("</div><br>");
        response.getWriter().write("<button type='submit'>Submit Answer</button>");
        response.getWriter().write("</form>");
    }

    private void writeHtmlStart(HttpServletResponse response, String title) throws IOException {
        response.getWriter().write("<!DOCTYPE html><html><head><title>" + title + "</title>");
        addStyleBlock(response);
        response.getWriter().write("</head><body>");
    }

    private void writeHtmlEnd(HttpServletResponse response) throws IOException {
        response.getWriter().write("</body></html>");
    }

    private void addStyleBlock(HttpServletResponse response) throws IOException {
        // Embedded styles
        response.getWriter().write("<style>");
        response.getWriter().write("html, body { height: 100%; margin: 0; display: flex; justify-content: center; align-items: center; }");
        response.getWriter().write("body { background-color: #f4f4f4; font-family: Arial, sans-serif; }");
        response.getWriter().write(".container { width: 90%; max-width: 600px; text-align: center; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); padding: 2rem; border-radius: 0.5rem; background: white; }");
        response.getWriter().write("h1 { color: #333; margin-bottom: 2rem; }");
        response.getWriter().write("p { color: #555; }");
        response.getWriter().write(".poll-section, .result-section { margin-bottom: 1rem; }");
        response.getWriter().write(".choice-container { display: flex; justify-content: center; flex-wrap: wrap; }");
        response.getWriter().write("label.choice { padding: 1rem; margin: 0.5rem; border: 1px solid #ddd; border-radius: 0.3rem; cursor: pointer; transition: transform 0.1s ease; }");
        response.getWriter().write("label.choice:hover { background-color: #f0f0f0; transform: translateY(-3px); }");
        response.getWriter().write("input[type='radio']:checked + label { background-color: #bde0fe; border-color: #89c2d9; }");
        response.getWriter().write("button { background-color: #4CAF50; color: white; padding: 1rem 2rem; border: none; border-radius: 0.3rem; cursor: pointer; transition: background-color 0.2s; }");
        response.getWriter().write("button:hover { background-color: #45a049; }");
        response.getWriter().write("</style>");
    }
}
