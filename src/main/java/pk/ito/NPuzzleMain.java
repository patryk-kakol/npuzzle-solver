package pk.ito;

import io.quarkus.runtime.annotations.QuarkusMain;
import pk.ito.control.Menu;

/* Application main class, used to run user menu in the console */
@QuarkusMain
public class NPuzzleMain {

  public static void main(String ...args) {
    Menu menu = new Menu();
    menu.start();
  }

}
