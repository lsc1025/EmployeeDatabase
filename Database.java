/*
 * Database.java
 * A database storing employees' information.
 * Group of Employee Database
 * Mr. Campbell's ICS4U
 * Mar 25, 2015
 */
package majorproj;
import java.util.ArrayList;
import java.io.*;
public class Database extends Login {
    int counter = 0;
    Login user = new Login();
    ArrayList ge;//ArrayList declaration 
    ArrayList ho;
    ArrayList hr;
    ArrayList sa;
    /**
     * constructor
     * employee information has been initialized.
     */
    public Database() {//initialization 
        ge = new ArrayList();
        ho = new ArrayList();
        hr = new ArrayList();
        sa = new ArrayList();
    }
    
    /**
     * Read employee information from the database
     * pre: none
     * post: Information has been read.
     */
    public void read() {
        File database = new File("C:\\log\\Data.dat");
        FileReader in;
        BufferedReader readFile;
        String lineOfText;
        
        try {
            System.out.print("Initializing database...");
            in = new FileReader(database);
            readFile = new BufferedReader(in);
            while ((lineOfText = readFile.readLine())!= null) {
                ge.add(split(lineOfText));//read from the file and split into arrays
                ho.add(split(readFile.readLine()));//(continued) from strings  
                hr.add(split(readFile.readLine()));//(continued) separated by colons
                sa.add(split(readFile.readLine()));
                counter++;//count
            }
            readFile.close();
            in.close();
            System.out.println("Complete!");
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist.");
            System.err.println("FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Problem reading file.");
            System.err.println("IOException: " + e.getMessage());
        }
    }
    /**
     * Add new employee to Database
     * pre: none;
     * post: A new employee has been added to the database.
     */
    public void add(int employeeNum) {
        System.out.println("Settig up default information");
        ge.add(split("NA:NA:NA:NA:NA:NA:NA:NA:NA"));
        ho.add(split("NA:NA:NA:NA:NA:NA:NA:NA"));
        hr.add(split(String.valueOf(counter + 1001) + ":NA:NA:NA:NA"));
        sa.add(split("NA:NA:NA:NA:NA"));   
    }
    
    /**
     * delete employee
     * pre: none
     * post: Employee information has been written back to the employee database.
     */
    public void writeback() {
        File database = new File("C:\\log\\Data.dat");
        FileWriter out;
        BufferedWriter writeFile;
        ArrayList newgen = new ArrayList();
        ArrayList newhom = new ArrayList();
        ArrayList newhr = new ArrayList();
        ArrayList newsal = new ArrayList();
        System.out.println("Adding back...");
        for (int i = 0; i < ge.size(); i++) {
            newgen.add(combine((String[]) ge.get(i)));//combine the string arrays
            newhom.add(combine((String[]) ho.get(i)));//(continued) to strings
            newhr.add(combine((String[]) hr.get(i)));//(continued) separated 
            newsal.add(combine((String[]) sa.get(i)));//(continued) by colons
        }
        try {
            out = new FileWriter(database);
            writeFile = new BufferedWriter(out);
            for (int i = 0; i < newgen.size(); i++) {
                writeFile.write(newgen.get(i).toString());//write file
                writeFile.newLine();
                writeFile.write(newhom.get(i).toString());
                writeFile.newLine();
                writeFile.write(newhr.get(i).toString());
                writeFile.newLine();
                writeFile.write(newsal.get(i).toString());
                writeFile.newLine();
            }
            writeFile.close();
            out.close();
            System.out.println("Complete!");
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist.");
            System.err.println("FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Problem reading file.");
            System.err.println("IOException: " + e.getMessage());
        }    
    }
    
    /**
     * combine the String arrays to strings
     * pre: com[] is not empty
     * post: String array has been combined to string with colons to separate data.
     */
    public String combine(String[] com) {
        String out = null;
        for (int i = 0; i < com.length; i++) {
            if (i == 0) {//if it’s the first char, add a colon after it
                out = com[i] + ":";
            } else if (i != 1) {//if it’s not the 1st one nor 2nd, add one colon before it
                out = out + ":" + com[i];
            } else {//if it’s the second one, there is no need to add colons
                out = out + com[i];
            } 
        }
        return out;
    }
    
    /**
     * split the strings to string arrays
     * pre: string is not empty
     * post: String has been splitter to arrays with colons removed.
     */
    public String[] split(String str) {
        String[] temp;
        int[] mem;
        int pos = 0;
        int counter = 0;
        while (pos < str.length()) { 
            if (str.charAt(pos) == (':')) {//locate the colon position
                counter++;
            }
            pos++;
        }
        temp = new String[counter + 1];//temp array ready for conversion 
        mem = new int[counter];//memorize the colon index location
        pos = 0;
        counter = 0;
        while (pos < str.length()) {
            if (str.charAt(pos) == ':') {
                mem[counter] = pos;//record the colon position
                counter++;
            }
            pos++;
        }
        for (int i = 0; i < temp.length; i++) {
            if (i == temp.length - 1) {//if it’s the last char, trim it
                temp[i] = str.substring(mem[i - 1] + 1);
            } else if (i == 0) {//if first char, trim it
                temp[i] = str.substring(0, mem[0]);
            } else {//if in the middle, trim it(3 situations are dealt with differently)
                temp[i] = str.substring(mem[i - 1] + 1, mem[i]);
            }
        }
        return temp;
    }
    
    /**
     * get the corresponding employee information
     * pre: string array is not empty
     * post; corresponding ArrayLList has been returned upon selection
     * @param category
     * @return 
     */
    public ArrayList<String[]> getInfo(int category) {
        switch (category) {
            case 1:
                return ge;
            case 2:
                return ho;
            case 3:
                return hr;
            case 4:
                return sa;
        }
        return null;
    } 
}
