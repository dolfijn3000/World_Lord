package ArticleControls.wlAcordion;

import ArticleControls.ArticleControl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

import java.io.File;

public class WLAcordionController extends ArticleControl {

    @FXML
    public AnchorPane Main;

    @FXML
    public HTMLEditor htmlEditor;

    private  WebView mWebView;

    public WLAcordionController(){
    }

    @FXML
    void initialize(){
        // add a custom button to the top toolbar.
        Node node = htmlEditor.lookup(".top-toolbar");
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
        mWebView = (WebView) htmlEditor.lookup(".web-view");
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

    @Override
    public Object GetData() {
        return null;
    }

}
