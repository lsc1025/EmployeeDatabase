/*
 * MainProject.java
 * Main class of the employee database
 * Group of Employee Database
 * Mr. Campbell's ICS4U
 * Mar 25, 2015
 */

package majorproj;

import java.util.*;

public class MainProject {

    private static Scanner in = new Scanner(System.in);
    private static Execute run;
    static Login log;
    
    /**
     * Main method
     * pre: none
     * post: none
     */
    public static void main(String[] args) {
        run = new Execute();
        run.version();
        run.initialize();
        login();   
    }
    
    /**
     * Login process for main method
     * pre: none
     * post: the user has been prompted to login
     */
    public static void login() {
        String[] usrData;
        log = new Login();
        usrData = log.getUser();
        menu(usrData);
    }
    
    /**
     * menu display
     * pre:temp[] is not empty
     * post: dynamic menu has been displayed
     */
    public static void menu(String[] temp) {
        Menu m = new Menu(temp[0],temp[1],log.getSize());
        m.greeting();
        run.receiveCmd(m.getCmd());
        run.execute();
        System.out.println("Should you enter 0 to exit the program,"
                + " or any number to contiune.");
        if (in.nextInt()!=0) {
            menu(temp);
        }
        System.out.println("Thank you for using! Good Bye!");
        System.exit(0);
    } 
    
    /**
     * update the login array
     * pre:none
     * post: current arrays have been written back and re-read from file, 
     * user has been prompted to re-login
     */
    public static void refresh() {
        log.save();
        log.update();
        run = new Execute();
        run.initialize();
        login();   
    }
}
