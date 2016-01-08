/*
 * Menu.java
 * Contain menu class that display dynamic menu for the user
 * Group of Employee Database
 * Mr. Campbell's ICS4U
 * Mar 27, 2015
 */

package majorproj;

import java.util.*;

public class Menu extends Execute {
    
    String usrName;
    char[] asl;
    int[] command;
    int userSize;
    
    /**
     * Constructor
     * Initialize basic informations for dynamic menu
     */
    public Menu(String userName, String acessLevel, int maxSize) {
        
        usrName = userName;
        asl = acessLevel.toCharArray();
        command = new int[4];
        userSize = maxSize;
        
    }
    
    /**
     * Display greeting information to user
     * pre: userName!=null
     * post: greeting information displayed
     */
    public void greeting() {
        
        System.out.println("Welcome, you are now logged in as "
                + usrName + ".");
        
        mainMenu();
        
    }
    
    /**
     * Display the first menu for user
     * pre: none
     * post: User's choice saved
     */
    public void mainMenu() {
        
        System.out.println("Please enter an index for functions:\n"
                + "1.Browse\n2.Update\n3.Modify user data\n4.Log out\n0.Exit");
        int usrChoice = in.nextInt();
        switch (usrChoice) {
            case 1:
                command[0] = 0;
                secondMenu(true);
                break;
            case 2:
                if (asl[0] == '0') {
                    System.out.println("Access denided, please try again");
                    mainMenu();
                } else {
                    command[0] = 1;
                    secondMenu(false);
                }
                break;
            case 4:
                System.out.println("See you tomrrow!");
                MainProject.login();
                break;
            case 3:
                editUsrMenu();
                break;
            case 0:
                System.out.println("Thank you for using, good bye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid selection, try again.");
                mainMenu();
                break;
        }
        
    }
    
    /**
     * Display second layer of menu to user depending on their choices and
     * access levels
     * pre: none 
     * post: second menu displayed
     */
    public void secondMenu(boolean ifRead) {
        
        System.out.print("Please select the categories you want to ");
        
        if (ifRead) {
            System.out.println("browse:");
        } else {
            System.out.println("update:");
        }
        
        System.out.println("1.General\n2.Home\n3.HR\n4.Salary\n5.Back");
        int usrChoice = in.nextInt();
        command[1] = usrChoice;
        
        if (ifRead) {
            
            switch (usrChoice) {
                case 1:
                    if (asl[1]!='0') {
                        thirdMenu(1, ifRead);
                    } else {
                        System.out.println("Access denied, try again.");
                        secondMenu(ifRead);
                    }
                    break;
                case 2:
                    if (asl[2]!='0') {
                        thirdMenu(2, ifRead);
                    } else {
                        System.out.println("Access denied, try again.");
                        secondMenu(ifRead);
                    }
                    break;
                case 3:
                    if (asl[3]!='0') {
                        thirdMenu(3, ifRead);
                    } else {
                        System.out.println("Access denied, try again.");
                        secondMenu(ifRead);
                    }
                    break;
                case 4:
                    if (asl[4]!='0') {
                        thirdMenu(4, ifRead);
                    } else {
                        System.out.println("Access denied, try again.");
                        secondMenu(ifRead);
                    }
                    break;
                case 5:
                    mainMenu();
                    break;
                default:
                    System.out.println("Invalid selection, try again.");
                    secondMenu(ifRead);
                    break;
            }
            
        } else {
            
            switch (usrChoice) {
                case 1:
                    if (asl[1]=='2') {
                        thirdMenu(1, ifRead);
                    } else {
                        System.out.println("Access denied, try again.");
                        secondMenu(ifRead);
                    }
                    break;
                case 2:
                    if (asl[2]=='2') {
                        thirdMenu(2, ifRead);
                    } else {
                        System.out.println("Access denied, try again.");
                        secondMenu(ifRead);
                    }
                    break;
                case 3:
                    if (asl[3]=='2') {
                        thirdMenu(3, ifRead);
                    } else {
                        System.out.println("Access denied, try again.");
                        secondMenu(ifRead);
                    }
                    break;
                case 4:
                    if (asl[4]=='2') {
                        thirdMenu(4, ifRead);
                    } else {
                        System.out.println("Access denied, try again.");
                        secondMenu(ifRead);
                    }
                    break;
                case 5:
                    mainMenu();
                    break;
                default:
                    System.out.println("Invalid selection, try again.");
                    secondMenu(ifRead);
                    break;
            }
            
        }
        
    }
    
    /**
     * Display third layer of the menu to the user
     * pre: 4 >= category >=1 
     * post: menu displayed 
     */
    public void thirdMenu(int category, boolean ifRead) {
        
        System.out.print("Please select the information you want to ");
        if (ifRead) {
            System.out.println("browse:");
        } else {
            System.out.println("update:");
        }
        
        switch (category) {
            case 1:
                System.out.println("1.Last name\n2.First name\n"
                        + "3.Middle name\n4.Job title\n5.Dept code"
                        + "\n6.Buliding\n7.Office phone num\n"
                        + "8.Telephone extension\n9.Company email\n0.Back");
                
                break;
            case 2:
                System.out.println("1.Street address\n2.City\n3.Province\n"
                        + "4.Post code\n5.Phone number\n6.Birth date\n"
                        + "7.Martal status\n8.First name of spouse\n"
                        + "9.Personal email add\n0.Back");
                break;
            case 3:
               System.out.println("1.Employee number\n"
                       + "2.Social insurance number\n3.Date of hire\n"
                       + "4.Vacaation days available\n"
                       + "5.Sick days avaiable\n0.Back");
                break;
            case 4:
                System.out.println("1.Salary\n"
                        + "2.Salary level\n"
                        + "3.Pay interval\n4.Date of last salary increase\n"
                        + "5.Merit bonus amount\n0.Back");
            default:
                
                break;
        }
        
        int usrChoice = in.nextInt();
        command[2] = usrChoice;
        
        if (usrChoice == 0) {
            secondMenu(ifRead);
        } else {
            System.out.println("Please enter the four digits employee code"
                    + " of the employee you want to ");
            if (ifRead) {
                System.out.println("browse:");
            } else {
                System.out.println("update:");
            }
            int usrRequest;
            usrRequest = in.nextInt();
            while (usrRequest<=1000||usrRequest>9999||!log.isExist(usrRequest)
                ||!log.isValid(usrRequest)) {
                if (usrRequest<=1000||usrRequest>9999) {
                    System.out.println("Invalid input, try again");
                } else {
                    if (!log.isExist(usrRequest)) {
                        System.out.println("User does not exist, try again.");
                    } else {
                        System.out.println("Invalid user, try again.");
                    }
                }
            usrRequest = in.nextInt();
            }
            
            command[3] = usrRequest;
        }
        
        System.out.println("Please wait while we searching for the data.");
        
    }
        
    /**
     * Return the command code
     * pre: none
     * post: command code returned
     */
    public int[] getCmd() {
            
        command[2]--;
        command[3]--;
        command[3] %= 1000;
        /*
        Transform the command code to suit the formate in arraylists
        */
        return (command);
        
    }
    
    /**
     * Display menu for user update functions
     * pre: none
     * post: menu displayed
     */
    public void editUsrMenu() {
        
        System.out.println("Please enter the option you want:\n"
                + "1.Add new user\n2.Delet user\n3.Change Passcode\n4.Back");
        
        int usrChoice = in.nextInt();
        
        switch (usrChoice) {
            case 1:
                if (asl[4] == '0') {
                    System.out.println("Access denided, please try again");
                    editUsrMenu();
                } else {
                    command[0] = 2;
                }
                break;
            case 2:
                if (asl[4] == '0') {
                    System.out.println("Access denided, please try again");
                    editUsrMenu();
                } else {
                    command[0] = 3;
                }
                break;
            case 3:
                command[0] = 4;
                break;
            case 4:
                mainMenu();
                break;
            default:
                System.out.println("Invalid selection, try again.");
                editUsrMenu();
                break;
        }
        
        
    }
        
}
