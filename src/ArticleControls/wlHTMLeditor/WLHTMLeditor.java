package ArticleControls.wlHTMLeditor;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.InputEvent;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

import java.io.File;

public class WLHTMLeditor extends HTMLEditor {

    private  WebView mWebView;
    public StringProperty html;

    public String getHtml() {
        return html.get();
    }

    public StringProperty htmlProperty() {
        return html;
    }

    public void setHtml(String html) {
        this.html.set(html);
    }

    public WLHTMLeditor(){
        html = new SimpleStringProperty("html");
        // add a custom button to the top toolbar.
        Node node = this.lookup(".top-toolbar");
        if (node instanceof ToolBar) {
            ToolBar bar = (ToolBar) node;
            Button smurfButton = new Button("IMAGE");
            bar.getItems().add(smurfButton);
            smurfButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent arg0) {
                    File file = new File("C:\\Users\\Gebruiker\\Pictures\\jorre.jpg");
                    insertHtmlAfterCursor("<img src=' " + file.toURI() + "'/>");
                }
            });
        }
        mWebView = (WebView) this.lookup(".web-view");
        init();
    }

    public void init() {
        addEventHandler(InputEvent.ANY, new EventHandler<InputEvent>() {

            @Override
            public void handle(InputEvent event) {
                setHtml(getHtmlText());
            }
        });
    }

    public void insertHtmlAfterCursor(String html) {
        //replace invalid chars
        html = html.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "\\r")
                .replace("\n", "\\n");
        //get script
        String script = String.format(
                "(function(html) {"
                        + "  var sel, range;"
                        + "  if (window.getSelection) {"
                        + "    sel = window.getSelection();"
                        + "    if (sel.getRangeAt && sel.rangeCount) {"
                        + "      range = sel.getRangeAt(0);"
                        + "      range.deleteContents();"
                        + "      var el = document.createElement(\"div\");"
                        + "      el.innerHTML = html;"
                        + "      var frag = document.createDocumentFragment(),"
                        + "        node, lastNode;"
                        + "      while ((node = el.firstChild)) {"
                        + "        lastNode = frag.appendChild(node);"
                        + "      }"
                        + "      range.insertNode(frag);"
                        + "      if (lastNode) {"
                        + "        range = range.cloneRange();"
                        + "        range.setStartAfter(lastNode);"
                        + "        range.collapse(true);"
                        + "        sel.removeAllRanges();"
                        + "        sel.addRange(range);"
                        + "      }"
                        + "    }"
                        + "  }"
                        + "  else if (document.selection && "
                        + "           document.selection.type != \"Control\") {"
                        + "    document.selection.createRange().pasteHTML(html);"
                        + "  }"
                        + "})(\"%s\");", html);
        //execute script
        mWebView.getEngine().executeScript(script);
    }
}
