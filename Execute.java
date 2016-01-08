/*
 * Execute.java
 * The class that process all of the execution.
 * Group of Employee Database
 * Mr. Campbell's ICS4U
 * Mar 27, 2015
 */
package majorproj;

import java.util.*;

public class Execute extends MainProject {
    
    int usrPos;
    int[] cmd;
    Database database;
    Scanner in = new Scanner(System.in);

    /**
     * Constructor
     * Initialize object of database
     */
    public Execute() {
        database = new Database();
    }
    
    /**
     * Initialize information from the database file 
     * pre: none
     * post: none
     */
    public void initialize() {
        database.read();
    }
    
    /**
     * Display current version and database name
     * pre: none
     * post: none
     */
    public void version() {
        System.out.println("Employee Database v 1.1p");
    }
    
    
    /**
     * Get user's command code from menu class
     * pre: none
     * post:user command code stored in class 
     */
    public void receiveCmd(int[] command) {
        usrPos = command[3];
        cmd = command;
    }
    
    /**
     * Process the command code
     * pre: int command[]contain three user's command code
     * post: command executed
     */
    public void execute() {
        
        switch (cmd[0]) {
            case 0:
                read();
                break;
            case 1:
                read();
                write();
                break;
            case 2:
                if (log.reauthenticate()) {
                    log.newuser();
                    database.add(log.getSize()+1001);
                    database.writeback();
                    MainProject.refresh();
                }
                break;
            case 4:
                log.change();
                MainProject.login();
                break;
            case 3:
                if (log.reauthenticate()) {
                    log.delete();
                }
                break;
            default:
                break;
        }
        
    }
    
    /**
     * Read the data according to the user's command code
     * pre: command code integer array
     * post: required data displayed
     */
    public void read() {
        System.out.println("The data you request is:");
        System.out.println(database.getInfo(cmd[1]).get(cmd[3])[cmd[2]]);
    }
    
    /**
     * Modify the data according to user's command code
     * pre: none
     * post; modified data stored in program 
     */
    public void write() {
        
        String usrInput;
        String usrReInput; 
        
        do {
            System.out.println("Please enter the new data you want to update:");
            usrInput = in.nextLine();
            System.out.println("Please reenter the new data again:");
            usrReInput = in.nextLine();
            if (!usrInput.equals(usrReInput)) {
                System.out.println("Inputs mismatch, please try again.");
                
            }
        } while (!usrInput.equals(usrReInput));
        
        database.getInfo(cmd[1]).get(cmd[3])[cmd[2]] = usrInput;
        
        writeBack();
    }
    
    /**
     * Apply changes to database file
     * pre: none
     * post: chance saved in database file
     */
    public void writeBack() {
        boolean flag;
        do {
            flag = log.reauthenticate();
            if (flag) {
                database.writeback();
            }
        } while (!flag);
    }
}
