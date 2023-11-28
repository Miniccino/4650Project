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
    private final String[] questions = {
        "What is your favorite programming language?",
        "Which IDE do you prefer?",
        "What is your favorite development framework?"
    };
    private final String[] choices = { "Java", "Python", "JavaScript", "C#" };

    private final Map<String, Map<String, Integer>> allPollResults = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        showWelcomePage(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("startPoll".equals(action)) displayChoices(response, 0);
        else {
            int questionIndex = Integer.parseInt(request.getParameter("questionIndex"));
            String selectedAnswer = request.getParameter("selectedAnswer");
            processAnswer(questionIndex, selectedAnswer);

            int nextQuestionIndex = questionIndex + 1;
            if (nextQuestionIndex < questions.length) {
                response.setContentType("text/html");
                writeHtmlStart(response, "Poll");
                response.getWriter().write("<div class='container'>"
                                          + "<h1>" + questions[nextQuestionIndex] + "</h1>");
                displayChoices(response, nextQuestionIndex);
                response.getWriter().write("</div>");
                writeHtmlEnd(response);
            } else showResultsPage(response);
        }
    }

    private void showWelcomePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        writeHtmlStart(response, "Welcome to the Poll");
        response.getWriter().write("<div class='container'>"
                                  + "<h1>Welcome to the Poll</h1>"
                                  + "<p>Click below to start the poll.</p>"
                                  + "<form action='Project2' method='post'>"
                                  + "<input type='hidden' name='action' value='startPoll'>"
                                  + "<button type='submit' class='start-btn'>Start Poll</button>"
                                  + "</form>"
                                  + "</div>");
        writeHtmlEnd(response);
    }

    private void processAnswer(int questionIndex, String answer) {
        Map<String, Integer> pollResults = allPollResults.getOrDefault(questions[questionIndex], new HashMap<>());
        pollResults.put(answer, pollResults.getOrDefault(answer, 0) + 1);
        allPollResults.put(questions[questionIndex], pollResults);
    }

    private void displayResults(HttpServletResponse response, int questionIndex) throws IOException {
        Map<String, Integer> pollResults = allPollResults.getOrDefault(questions[questionIndex], new HashMap<>());
        response.getWriter().write("<p><b>Poll Results:</b></p>");
        pollResults.forEach((key, value) -> {
            try { response.getWriter().write(key + ": " + value + "<br>"); } 
            catch (IOException e) { e.printStackTrace(); }
        });

        response.getWriter().write("<br>");
    }

    private void displayChoices(HttpServletResponse response, int questionIndex) throws IOException {
        response.setContentType("text/html");
        writeHtmlStart(response, "Poll");
        response.getWriter().write("<div class='container'>"
                                  + "<h1>" + questions[questionIndex] + "</h1>"
                                  + "<form method=\"post\">"
                                  + "<input type=\"hidden\" name=\"questionIndex\" value=\"" + questionIndex + "\">");
        for (String choice : choices) 
            response.getWriter().write("<label class='choice'>"
                                      + "<input type=\"radio\" name=\"selectedAnswer\" value=\"" + choice + "\"> " + choice
                                      + "</label><br>");
        response.getWriter().write("<button type=\"submit\" class='submit-btn'>Submit Answer</button>"
                                  + "</form>"
                                  + "</div>");
        writeHtmlEnd(response);
    }

    private void showResultsPage(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        writeHtmlStart(response, "Poll Results");
        response.getWriter().write("<div class='container'>"
                                  + "<h1>All Poll Results</h1>");
        for (int i = 0; i < questions.length; i++) {
            response.getWriter().write("<h2>" + questions[i] + "</h2>");
            displayResults(response, i);
        }
        response.getWriter().write("<form action='Project2' method='post'>"
                                  + "<input type='hidden' name='action' value='startPoll'>"
                                  + "<button type='submit' class='start-btn'>Start Over</button>"
                                  + "</form>"
                                  + "</div>");
        writeHtmlEnd(response);
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
        response.getWriter().write("<style>"
                                + "html, body { margin: 0; padding: 0; font-family: Arial, sans-serif; background: #f4f4f4; height: 100%; display: flex; justify-content: center; align-items: center; }"
                                + ".container { width: 90%; max-width: 600px; margin: auto; padding: 20px; background: #fff; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); text-align: center; }"
                                + "h1, h2 { color: #333; margin-bottom: 1rem; }"
                                + ".choice { display: block; margin: 10px auto; padding: 10px; border: 1px solid #ddd; border-radius: 4px; cursor: pointer; transition: background-color 0.2s ease; }"
                                + ".choice:hover { background-color: #f0f0f0; }"
                                + "input[type='radio'] { margin-right: 10px; }"
                                + "button { background-color: #4CAF50; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; transition: background-color 0.2s; }"
                                + "button:hover { background-color: #45a049; }"
                                + "</style>");
    }
}
