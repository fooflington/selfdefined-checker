package uk.org.mafoo.selfdefined;

import org.json.JSONObject;

class ReportWord {
    String word;
    Integer count;
    String flagLevel;
    String flagText;
    String flagFor;
    String url;

    public ReportWord(String word, Integer count, String flagLevel, String flagText, String flagFor, String url) {
        this.word = word;
        this.count = count;
        this.flagLevel = flagLevel;
        this.flagText = flagText;
        this.flagFor = flagFor;
        this.url = url;
    }

    public String toString() {
        return String.format(
            "{word=%s,count=%d,flagLevel=%s,flagText=%s,flagFor=%s,url=%s}",
            word, count, flagLevel, flagText, flagFor, url
        );
    }

    public JSONObject toJSONObject() {
        JSONObject jo = new JSONObject();
        jo.put("word", word);
        jo.put("count", count);
        jo.put("flagLevel", flagLevel);
        jo.put("flagText", flagText);
        jo.put("flagFor", flagFor);
        jo.put("url", url);

        return jo;
    }

    public String toHTML() {
        StringBuilder str = new StringBuilder();
        str.append("<tr>\n");
        str.append("  <td>"); str.append(word); str.append("</td>\n");
        str.append("  <td>"); str.append(count); str.append("</td>\n");
        str.append("  <td>"); str.append(flagLevel); str.append("</td>\n");
        str.append("  <td>"); str.append(flagText); str.append("</td>\n");
        str.append("  <td>"); str.append(flagFor); str.append("</td>\n");
        str.append("  <td><a href='"); str.append(url); str.append("'>ref</a></td>\n");
        str.append("</tr>\n");

        return str.toString();
    }
}