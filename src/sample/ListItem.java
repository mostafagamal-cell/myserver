package sample;

import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

class ListItem extends ListCell<User> {
   HBox box;
   TextField name;
   TextField email;
   Rectangle rectangle;
   Circle circle;
   ListItem() {
       circle= new Circle(13);
       circle.setFill(Color.GRAY);
       setStyle(" -fx-background-color: transparent;\n" +
               "    -fx-border-color: transparent ;-fx-font-size: 14 ;");
       box = new HBox();
       name = new TextField();
       email = new TextField();
       name.setEditable(true);
       email.setEditable(true);
       rectangle = new Rectangle(20, 20);
       rectangle.setFill(Color.RED);
   }

   @Override
   protected void updateItem(User item, boolean empty) {
       super.updateItem(item, empty);

       if (empty || item == null) {
           box.getChildren().clear();
           setText(null);
           setGraphic(null);
       } else {
           if (item.name.isEmpty() && item.email.isEmpty()) {
               name.setText("name\n  ");
               email.setText("email");
               name.setStyle(" -fx-background-color: transparent;-fx-text-fill: black; -fx-font-weight: bold;");
               email.setStyle(" -fx-background-color: transparent;-fx-text-fill: black; -fx-font-weight: bold;");
               rectangle.setFill(Color.RED);
           } else {
               name.setText(item.name);
               email.setText(item.email);
               name.setStyle(" -fx-background-color: transparent;-fx-text-fill: black; -fx-font-weight: bold;");
               email.setStyle(" -fx-background-color: transparent;-fx-text-fill: black; -fx-font-weight: bold;");

               if (item.Status==1) {
                   rectangle.setFill(Color.GREEN);
               } else if (item.Status==0){
                   rectangle.setFill(Color.RED);
               }else rectangle.setFill(Color.AQUA);
           }
           box.getChildren().setAll(circle,name, email, rectangle);
           setGraphic(box);
       }
   }
}
