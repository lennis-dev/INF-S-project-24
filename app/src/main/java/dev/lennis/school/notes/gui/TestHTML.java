package dev.lennis.school.notes.gui;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class TestHTML extends JFrame {
  public TestHTML() {

    JEditorPane html = new JEditorPane();
    html.setContentType("text/html");
    html.setText(page);
    html.setEditable(false);
    html.setVisible(true);
    html.setSize(400, 200);
    html.setEnabled(true);
    add(html);

    pack();
    Dimension preferredSize = html.getPreferredSize();
    setSize(preferredSize.width, preferredSize.height);

    html.addHyperlinkListener(
        new HyperlinkListener() {
          @Override
          public void hyperlinkUpdate(HyperlinkEvent e) {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
              if (e.getDescription().equals("#")) {
                // Extract form data
                Map<String, String> formData = extractFormData(html.getText());
                // Save the form data (print to console in this example)
                System.out.println("Form Data: " + formData);
              }
            }
          }
        });

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(null);
    setVisible(true);
  }

  private final String page =
      // html
      """
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Mobile Login</title>
            <style>
              body {
                background-color: #2F3136;
                color: #F2F3F5;
                width: 200px;
                height: 200px;
              }
              .input-group {
                align: center;
                margin: 10px 10px 10px 10px;
              }
              #title {
                margin: 10px 10px 10px 80px;
              }
              #submit {
                margin: 10px 10px 10px 80px;
              }
            </style>
        </head>
        <body>
          <form action="#" class="form">
            <h2 id="title">Login</h2>
            <div class="input-group">
            <label for="loginUser">Username</label>
              <input type="text" name="loginUser" id="loginUser" required />
            </div>
            <div class="input-group">
              <label for="loginPassword">Password</label>
              <input
                type="password"
                name="loginPassword"
                id="loginPassword"
                required
              />
            </div>
            <div id="submit">
              <input type="submit" value="Login"/>
            </div>
          </form>
        </body>
        </html>
      """;

  private static Map<String, String> extractFormData(String html) {
    Map<String, String> formData = new HashMap<>();
    String name =
        html.split("name='name'>")[1].split("<br><br>")[0].split("value='")[1].split("'")[0];
    String email =
        html.split("name='email'>")[1].split("<br><br>")[0].split("value='")[1].split("'")[0];
    formData.put("name", name);
    formData.put("email", email);
    return formData;
  }
}
