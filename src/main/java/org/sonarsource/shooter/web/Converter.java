package org.sonarsource.shooter.web;

import com.google.gson.Gson;
import gui.ava.html.Html2Image;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Converter {

  public static void main(String[] args) {
    File file = new File("foo.png");
    try {
      convert("javaTest:src/AdjustmentDAO.java", new FileOutputStream(file));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static void convert(String fileKey, OutputStream outputStream) {
    try {
      URL url = new URL("http://localhost:9000/api/sources/lines?key="+ URLEncoder.encode(fileKey, "UTF-8"));
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");

      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      String inputLine;
      StringBuffer content = new StringBuffer();
      while ((inputLine = in.readLine()) != null) {
        content.append(inputLine);
      }
      in.close();
      con.disconnect();

      Sources sources = new Gson().fromJson(content.toString(), Sources.class);
      int length = sources.sources.length;
      String html = Arrays.stream(sources.sources).map(l -> "<pre class =\"code\">"+l.code+"</pre>").collect(Collectors.joining(" "));
      html = "<html><head>"+css+"</head>"+html+"</html>";
      Html2Image generator = new Html2Image();
      generator.getParser().loadHtml(html);
      generator.getImageRenderer().setImageType("png").setAutoHeight(false).setHeight(length*20).setWidth(800).saveImage(outputStream, true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static class Sources {
    Line[] sources;
  }
  private static class Line {
    String code;
  }
private static final String css = "<style media=\"screen\" type=\"text/css\">\n"+".code {font-size:10px; line-height:1px;} .code .a {\n" +
    "  color: #808000;\n" +
    "}\n" +
    "/* constants */\n" +
    ".code .c {\n" +
    "  color: #660E80;\n" +
    "  font-style: normal;\n" +
    "  font-weight: bold;\n" +
    "}\n" +
    "/* javadoc */\n" +
    ".code .j {\n" +
    "  color: #666666;\n" +
    "  font-style: normal;\n" +
    "}\n" +
    "/* classic comment */\n" +
    ".code .cd {\n" +
    "  color: #666666;\n" +
    "  font-style: italic;\n" +
    "}\n" +
    "/* C++ doc */\n" +
    ".code .cppd {\n" +
    "  color: #666666;\n" +
    "  font-style: italic;\n" +
    "}\n" +
    "/* keyword */\n" +
    ".code .k {\n" +
    "  color: #0071ba;\n" +
    "  font-weight: 600;\n" +
    "}\n" +
    "/* string */\n" +
    ".code .s {\n" +
    "  color: #d4333f;\n" +
    "  font-weight: normal;\n" +
    "}\n" +
    "/* keyword light*/\n" +
    ".code .h {\n" +
    "  color: #000080;\n" +
    "  font-weight: normal;\n" +
    "}\n" +
    "/* preprocessing directive */\n" +
    ".code .p {\n" +
    "  color: #347235;\n" +
    "  font-weight: normal;\n" +
    "}\n" +
    ".sym {\n" +
    "  cursor: hand;\n" +
    "  cursor: pointer;\n" +
    "}\n" +
    ".highlighted {\n" +
    "  background-color: #b3d4ff;\n" +
    "  animation: highlightedFadeIn 0.3s forwards;\n" +
    "}"
    +"</style>";
}
