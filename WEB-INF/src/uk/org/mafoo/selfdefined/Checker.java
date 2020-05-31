package uk.org.mafoo.selfdefined;

import javax.servlet.annotation.WebServlet;
import javax.management.RuntimeErrorException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import org.sqlite.*;

@WebServlet("/Checker")
public class Checker extends HttpServlet {

    Connection db;
    PreparedStatement p_checkWord, p_getWord;
    String urlBase = "https://www.selfdefined.app/definitions/";

    public void init() throws ServletException {
        // Do required initialization
        try {
            db = DriverManager.getConnection(
                "jdbc:sqlite:" + getServletContext().getRealPath("/WEB-INF/files/defs.db"));

            p_checkWord = db.prepareStatement("SELECT word, ref FROM words WHERE word LIKE ?");
            p_getWord = db.prepareStatement("SELECT title, slug, flag_level, flag_text, flag_for FROM definitions WHERE title LIKE ?");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load database: " + e.toString());
        }
    }

    private HashMap<String,String> getWord(String word) {

        try {
            p_getWord.setString(1, word);
            ResultSet rs = p_getWord.executeQuery();
            if(rs.next()) {
                HashMap<String,String> _return = new HashMap<String,String>();
                _return.put("word", rs.getString(1));
                _return.put("slug", rs.getString(2));
                _return.put("flagLevel", rs.getString(3));
                _return.put("flagText", rs.getString(4));
                _return.put("flagFor", rs.getString(5));
                return _return;
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    private void checkWord(String word, HashMap<String,String> wordMap, HashMap<String,Integer> wordCounts) {
        assert(p_checkWord != null);
        assert(word != null);

        wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);

        // Do we already know the answer?
        if(wordMap.containsKey(word)) {
            // If so, just increment the wordcount and return
            return;
        }

        try {
            p_checkWord.setString(1, word);
            ResultSet rs = p_checkWord.executeQuery();
            while(rs.next()) {
                if(rs.getString(1).equalsIgnoreCase(rs.getString(2))) {
                    wordMap.put(rs.getString(1), null);
                } else {
                    wordMap.put(rs.getString(1), rs.getString(2));
                }
            }
        } catch (SQLException e) {
            // FIXME
        }
    }

    private Report process(String[] input) {
        HashMap<String,String> wordMap = new HashMap<String,String>();
        HashMap<String,Integer> wordCounts = new HashMap<String,Integer>();

        for(String line : input) {
            for(String word : line.split("\\s+")) {
                checkWord(word, wordMap, wordCounts);
            }
        }

        Report report = new Report();
        for (String word : wordMap.keySet()) {
            String ref = wordMap.get(word);
            if(ref == null) ref = word;
            HashMap<String,String> canonicalWord = getWord(ref);
            report.add(
                canonicalWord.get("word"),
                wordCounts.get(word),
                canonicalWord.get("flagLevel"),
                canonicalWord.get("flagText"),
                canonicalWord.get("flagFor"),
                urlBase.concat(canonicalWord.get("slug"))
            );
        }

        return report;
    }

    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] vals = req.getParameterValues("input");
        if(vals != null) {
            Report r = process(vals);
            PrintWriter out = resp.getWriter();

            ArrayList<String> acceptHeaders = new ArrayList<String>();
            for(Enumeration<String> acceptHeaders_e = req.getHeaders("Accept"); acceptHeaders_e.hasMoreElements(); ) {
                acceptHeaders.add(acceptHeaders_e.nextElement());
            }
            if(acceptHeaders.contains("text/json")) {
                resp.setContentType("text/json");
                out.println(r.toJSONObject());
            } else {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("    <title>Language Checker</title>");
                out.println("    <link rel=\"stylesheet\" type=\"text/css\" href=\"base.css\" />");
                out.println("    <link href=\"https://fonts.googleapis.com/css2?family=Fira+Mono:wght@400;500&family=Roboto:wght@500&display=swap\" rel=\"stylesheet\">");
                out.println("</head>");
                out.println("<body>");
                out.println("    <h1>Language checker results</h1>");
                out.println(r.toHTML());
                out.println("</body></html>");
            }

        } else {
            throw new RuntimeException("No data");
        }
    }

    // public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    //     ArrayList<String> arr = new ArrayList<String>();

    //     try {
    //         BufferedReader inputData = req.getReader();
    //         String line;
    //         while((line = inputData.readLine()) != null) {
    //             arr.add(line);
    //         }
    //     } catch (IOException e) {
    //         // FIXME
    //     }

    //     if(arr.size() > 0) {
    //         Report r = process((String[]) arr.toArray(new String[0]));
    //         PrintWriter out = resp.getWriter();
    //         out.println(r.toJSONObject());
    //     } else {
    //         throw new RuntimeException("No data");
    //     }

    // }

    public void destroy() {
        // Close the database
    }

}