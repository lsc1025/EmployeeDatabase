/*
 * Login.java
 * Login class that process with login database
 * Group of Employee Database
 * Mr. Campbell's ICS4U
 * Mar 26, 2015
 */
package majorproj;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class Login {
    Scanner input = new Scanner(System.in);
    final int Max_Trial = 10;//maximum trial is set to 10 times
    int trial = 0, userPos = 0; 
    ArrayList name;//ArrayLists for login data
    ArrayList word;
    ArrayList priv;
    String username;//stores the current input data
    String password;
    
    /**
     * constructor
     * username and password were initialized.
     */
    public Login() {//initialization
        username = null;
        password = null;
        name = new ArrayList();
        word = new ArrayList();
        priv = new ArrayList();
    }
    
    /**
     * read the login information
     * pre: username, password and privilege are not empty
     * post: Information has been read.
     */
    public void read(){
        File f = new File("C:\\log\\Authentication.dat");
        FileReader in;
        BufferedReader readFile;
        String lineOfText;
        try {
            in = new FileReader(f);
            readFile = new BufferedReader(in);
            while((lineOfText = readFile.readLine())!=null){
                name.add(lineOfText);//read data from the file
                word.add(readFile.readLine());
                priv.add(readFile.readLine());
            }
            readFile.close();
            in.close();
        }   catch (FileNotFoundException e){
            System.out.println("File does not exist.");
            System.out.println("FileNotFoundException:"+e.getMessage());
        }   catch (IOException e){
            System.out.println("Problem reading file.");
            System.out.println("IOException:"+e.getMessage());
        }
    }
    
    /**
     * Check trial errors
     * pre: num >= 0
     * post: true or false has been returned.
     */
    public boolean check() {
        if (trial < 10) {
            return true;
        } else {
            return false;//if limit is reached, return false
        }
    }
    
    /**
     * Add one to the error trial
     * pre: none;
     * post: trial added.
     */
    public void error() {//add one to error trial count and write to file
        File f1 = new File("C:\\log\\check.dat");
            FileWriter out;
            BufferedWriter writeFile;
        try {
            out = new FileWriter(f1);
            writeFile = new BufferedWriter(out);
            trial ++;//add error count
            writeFile.write(String.valueOf(trial));
            writeFile.close();
            out.close();
        } catch (FileNotFoundException e){
            System.out.println("File does not exist.");
            System.out.println("FileNotFoundException:"+e.getMessage());
        } catch(IOException e){
            System.out.println("Problem reading file.");
            System.out.println("IOException:"+e.getMessage());
        }
    }
    
    /**
     * Read the login errors
     * pre: none
     * post: login error has been read.
     */
    public int geterror() {
        String lineOfInt;
         File f1 = new File("C:\\log\\check.dat");
            FileReader in;
            BufferedReader readFile;
        try {
            in = new FileReader(f1);
            readFile = new BufferedReader(in);
            while((lineOfInt = readFile.readLine()) != null) {
                trial = Integer.parseInt(lineOfInt);//fetch error
            }
            readFile.close();
            in.close();
        } catch (FileNotFoundException e){
            System.out.println("File does not exist.");
            System.out.println("FileNotFoundException:"+e.getMessage());
        } catch(IOException e){
            System.out.println("Problem reading file.");
            System.out.println("IOException:"+e.getMessage());
        }
        return trial;
    }
    
    /**
     * reset the error trials
     * pre: none
     * post: error has been reset.
     */
    public void reset() {
        File f1 = new File("C:\\log\\check.dat");
            FileWriter out;
            BufferedWriter writeFile;
        try {
            out = new FileWriter(f1);
            writeFile = new BufferedWriter(out);
            writeFile.write(String.valueOf(0));//reset error trial count to zero
            writeFile.close();
            out.close();
        } catch (FileNotFoundException e){
            System.out.println("File does not exist.");
            System.out.println("FileNotFoundException:"+e.getMessage());
        } catch(IOException e){
            System.out.println("Problem reading file.");
            System.out.println("IOException:"+e.getMessage());
        }
    }

    /**
     * Authenticate the user information
     * pre: usernameinput, passwordinout != null, username, password is not empty
     * post: True or false has been returned according to the input info.
     */
    public boolean Authenticate(String usernameinput, String passwordinput) {
        int success = 0;
        for (int i = 0; i < name.size(); i++) {
            if (usernameinput.equals(name.get(i))) {
                if (!isValid(i + 1)) {//check if the user has been deleted
                    success = 0;
                    System.out.println("The user you are trying to login as "
                            + "is no longer valid.");
                    break;
                }
                if (passwordinput.equals(word.get(i))) {
                    success = 1;//success
                    userPos = i;//fetch user position
                    username = usernameinput;//record input data for re-authentication
                    password = passwordinput;
                    break;
                } else {
                    success = 0;
                }
            } else {
                success = 0;
            }
        }
        return success == 1;
    }
    
    /**
     * the whole login process
     * pre: none
     * post: user has or not logged in and true or false has been returned
     */
    public boolean login() {
        int success = 0;
        String userName, passWord;
        System.out.println("Please enter your username and password to"
                + " log in.");
        while (check()) {//check trial limit
            System.out.print("Username: ");
            userName = input.nextLine();
            System.out.print("Password: ");
            passWord = input.nextLine();
            if (Authenticate(userName, passWord)) {//if pass authentication
                success = 1;
                reset();//reset trial error count
                break;
            } else {
                error();//add an error count
                success = 0;//mark as failure
                System.out.println("Authentication failed, remaining attempt(s):"
                        + " " + (Max_Trial - geterror()));
            }
        }
        if (!check()) {
            success = 0;
            System.out.println("Unsuccessful trial limit reached, please contact"
                    + " the administrator.");
        }
        return success == 1;
    } 
    /**
     * run the login process and return username, privilege and password
     * pre: none
     * post: user login information has been returned
     */
    public String[] getUser() {
        read();
        if(login()) {
            String user = username + ":" + priv.get(userPos)+":"+word.get(userPos);
            return user.split(":");//if logged in, return username, privilege and password
        } else {
            return null;
        }
    }
    /**
     * write back the user information to the file
     * pre: none
     * user login information has been written back to the file
     */
    public void save() {
        File f1 = new File("C:\\log\\Authentication.dat");
            FileWriter out;
            BufferedWriter writeFile;
        try {
            System.out.print("Synchronizing data...");
            out = new FileWriter(f1);
            writeFile = new BufferedWriter(out);
            for (int i = 0; i < name.size(); i++) {
                writeFile.write(name.get(i).toString());
                writeFile.newLine();
                writeFile.write(word.get(i).toString());
                writeFile.newLine();
                writeFile.write(priv.get(i).toString());
                writeFile.newLine();
            }
            writeFile.close();
            out.close();
            System.out.println("Complete!");
        } catch (FileNotFoundException e){
            System.out.println("File does not exist.");
            System.out.println("FileNotFoundException:"+e.getMessage());
        } catch(IOException e){
            System.out.println("Problem reading file.");
            System.out.println("IOException:"+e.getMessage());
        }
    }
    /**
     * change the user password
     * pre: none
     * post: password has been changed and saved to file, arrays updated
     */
    public void change() {
        String first = null, second =null, username = null;
        if (reauthenticate()) {
            System.out.println("Please enter the new password:");
            first = input.next();
            System.out.println("Please re-enter the password you just typed in:");
            second = input.next();
            while (!first.equals(second)) {
                System.out.println("Password mismatch.");//to check whether mismatch
                System.out.println("Please enter the new password:");
                first = input.next();
                System.out.println("Please re-enter the password you just typed "
                        + "in:");
                second = input.next();
            }
            word.set(userPos, second);//replace the old password with the new one
            save();
            update();//save and update 
            System.out.println("Password has been successfully updated");
        } else {
            System.out.println("Access denied, you failed the authentication.");
        }
    }
    /**
     * re-authenticate the user
     * pre: none
     * post: true or false has been returned upon success or not
     */
    public boolean reauthenticate() {
        geterror();
        System.out.println("Reauthentication required, please re-enter your "
                + "password:");
        String check;
        check = input.nextLine();
        int success = 1;
        while (!check.equals(word.get(userPos))) {
            error();
            success = 0;
            if (!check()) {//check trial errors
                System.out.println("Unsuccessful trial limit reached, please"
                        + " contact the administrator.");
                break;
            } else {//if password is wrong, authentication would be failure
                System.out.println("Authentication failed, remaining attempt(s):"
                        + " " + (Max_Trial - geterror()));
            }
            System.out.println("Reauthentication required, please re-enter your"
                    + " password:");
            check = input.nextLine();
        }
        if (check.equals(word.get(userPos))) {
            success = 1;
        }
        if (success == 0) {
            return false;
        } else {
            reset();//if password is correct, approve and trial error counter reset
            System.out.println("Approved.");
            return true;  
        }
    }
    /**
     * add a new user
     * pre: none
     * post: a new user login information has been written to the file
     */
    public void newuser() {
        System.out.print("Enter the new user name: ");
        String user = input.next();
        String first, second;
        System.out.println("Please enter a new password:");
            first = input.nextLine();
            System.out.println("Please re-enter the password you just typed in:");
            second = input.nextLine();
            while (!first.equals(second)) {
                System.out.println("Password mismatch.");//check if mismatch
                System.out.println("Please enter the new password:");
                first = input.nextLine();
                System.out.println("Please re-enter the password you just typed "
                        + "in:");
                second = input.nextLine();
            }
            name.add(user);
            word.add(second);
            System.out.print("Now set the priviledge: ");
            String pvi = input.nextLine();
            priv.add(pvi);//set privilege code
            save();
            System.out.print(user + " has been successfully created.");
            update();
    }
    /**
     * update the array list with the data in the file, save is pre-required
     * pre: none
     * post: ArrayList has been erased and replaced with the data from the login file
     */
    public void update() {
        name = new ArrayList();
        word = new ArrayList();
        priv = new ArrayList();
        read();
    }
    /**
     * Determine whether the user is still valid in the database
     * pre: 9999>= employeeNum >999
     * post: result returned
     */
    public boolean isValid(int employeeNum) {
        return !priv.get((employeeNum - 1) %1000).toString().substring(0, 1).equals("2");
    }
    
    
    
    /**
     * delete user information
     * pre: none
     * post: the user has been marked as deleted
     */
    public void delete() {
        System.out.println("Please enter the four digits employee code"
                    + " of the employee you want to delete");
        int usrRequest;
        usrRequest = input.nextInt();
        while (usrRequest<=1000||usrRequest>9999||!isExist(usrRequest)
                ||!isValid(usrRequest)) {
            if (usrRequest<=1000||usrRequest>9999) {
                System.out.println("Invalid input, try again");
            } else {
                if (!isExist(usrRequest)) {
                    System.out.println("User does not exist, try again.");
                } else {
                    System.out.println("Invalid user, try again.");
                }
            }
            usrRequest = input.nextInt();
        }
        int pos = usrRequest%1000;
        String temp = priv.get(pos).toString();
        priv.set(pos - 1, "2" + temp.substring(1));
        save();
        update(); 
    }
     
    /**
     * Return size of the user stored in the database
     * pre: none
     * post: result returned
     */
    public int getSize () {
        return name.size();
    }
    
    /**
     * Determine whether the employee number is exist in the database
     * pre: 9999>= employeeNum >999
     * post: result returned
     */
    public boolean isExist(int employeeNum) {
        return employeeNum%1000 <= name.size();
    }
}
