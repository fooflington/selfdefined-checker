package uk.org.mafoo.selfdefined;

import java.util.ArrayList;
import org.json.*;

class Report {

    ArrayList<ReportWord> data;

    public Report() {
        data = new ArrayList<ReportWord>();
    }

    public void add(String word, Integer count, String flagLevel, String flagText, String flagFor, String url) {
        data.add(new ReportWord(word, count, flagLevel, flagText, flagFor, url));
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("[");
        int n = 0;
        for (ReportWord w : data) {
            if(n++ > 0) { s.append(","); }
            s.append(w);
        }
        s.append("]");

        return s.toString();
    }

    public JSONObject toJSONObject() {
        JSONArray arr = new JSONArray();
        for(ReportWord w : data) {
            arr.put(w.toJSONObject());
        }

        JSONObject obj = new JSONObject();
        obj.put("results", arr);
        return obj;
    }

    public String toHTML() {
        StringBuilder str = new StringBuilder();
        str.append("<table border='1'>\n");
        str.append("  <thead>\n");
        str.append("    <th>Word</th>\n");
        str.append("    <th>Count</th>\n");
        str.append("    <th>Flag Level</th>\n");
        str.append("    <th>Flag Text</th>\n");
        str.append("    <th>See-Also</th>\n");
        str.append("    <th>URL</th>\n");
        str.append("  </thead>\n");
        str.append("  <tbody>\n");
        for(ReportWord w : data) {
            str.append(w.toHTML());
        }
        str.append("  </tbody>\n");
        str.append("</table>\n");

        return str.toString();
    }
}