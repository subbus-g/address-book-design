import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

class AddressBook
{
    // class variable
    static final Scanner scanner = new Scanner(System.in);
    static Set<String> emptyContacts = new HashSet<>();
    static Set<String> nonEmptyContacts = new HashSet<>();

    // instance method
    void createNewContact() throws Exception 
    {
        System.out.print("Enter name of the contact:");
        String contactName = scanner.nextLine();
        File file = new File(contactName);
        if (file.exists()) 
        {
            System.out.println("contact " + file.getName() + " already exists!");
        } 
        else 
        {
            if (file.createNewFile()) 
            {
                System.out.println("new contact " + file.getName() + " is created successfully");
                emptyContacts.add(file.getName());
            } 
            else 
            {
                System.out.println("file creation failed!");
            }
        }

    }
    void writeFile(String fileName,String content)throws Exception
    {
        FileWriter fw = new FileWriter(fileName);
        fw.write(content);
        fw.close();
        nonEmptyContacts.add(fileName);
    }
    void fillContactDetails()throws Exception
    {
        System.out.print("enter empty contact name which is going to be filed:");
        String contactName = scanner.nextLine();
        if (emptyContacts.contains(contactName)) 
        {
            String details = "";
            System.out.print("enter first name:");
            details += scanner.nextLine() + "\n";
            System.out.print("enter last name:");
            details += scanner.nextLine() + "\n";
            System.out.print("enter address:");
            details += scanner.nextLine() + "\n";
            System.out.print("enter city:");
            details += scanner.nextLine() + "\n";
            System.out.print("enter state:");
            details += scanner.nextLine() + "\n";
            System.out.print("enter zip:");
            details += scanner.nextLine() + "\n";
            System.out.print("enter phone number:");
            details += scanner.nextLine() + "\n";
            writeFile(contactName, details);
            emptyContacts.remove(contactName);
            System.out.println("the given contents are successfully added in " + contactName);
        }
        else 
        {
            System.out.println(contactName + " is not empty contact or it is not created");
            System.out.println("use other option 1 to create new contact or option 5 to edit already created one");
        }

    }

    void displayAllContacts() 
    {
        boolean flag = false;
        if (emptyContacts.size() != 0) 
        {
            System.out.println("the empty contacts are:");
            for (String contact : emptyContacts) 
            {
                System.out.println(contact);
            }
            flag=true;
        }
        if (nonEmptyContacts.size() != 0) 
        {
            System.out.println("the non empty contacts are:");
            for (String contact : nonEmptyContacts) 
            {
                System.out.println(contact);
            }
            flag=true;
        } 
        if(!flag)
        {
            System.out.println("no contacts are created yet");
        }

    }
    void readFile(String fileName)throws Exception
    {
        FileReader fileReader = new FileReader(fileName);
        int character;
        while ((character = fileReader.read()) != -1) 
        {
            System.out.print((char) character);
        }
        fileReader.close();
    }
    void viewContactInfo()throws Exception
    {
        System.out.print("enter name of the contact to view:");
        String contactName = scanner.nextLine();
        if(emptyContacts.contains(contactName))
        {
            System.out.println("pleast fill the contact "+ contactName+ " before viewing it");
            return;
        }
        else if(!nonEmptyContacts.contains(contactName))
        {
            System.out.println("please create the contact "+contactName+" before viewing it");
            return;
        }
        System.out.println("the content of " + contactName + " is:");
        readFile(contactName);
    }
    void editContactInfo()throws Exception
    {
        System.out.print("enter name of the contact to edit:");
        String contactName = scanner.nextLine();
        if(emptyContacts.contains(contactName))
        {
            System.out.println("pleast fill the contact "+ contactName + " before editing it");
            return;
        }
        else if(!nonEmptyContacts.contains(contactName))
        {
            System.out.println("please create the contact "+ contactName +" before editing it");
            return;
        }
        System.out.println("The content of " + contactName + " at present is:");
        ArrayList<String> arrayList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(contactName))) 
        {
            while (reader.ready()) 
            {
                String line = reader.readLine();
                System.out.println(line);
                arrayList.add(line);
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        System.out.println("select a field to edit");
        System.out.println("f for first name");
        System.out.println("l for last name");
        System.out.println("a for Address");
        System.out.println("c for city");
        System.out.println("s for state");
        System.out.println("z for zip");
        System.out.println("p for phonenumber");
        System.out.print("enter field to edit:");
        String choice = scanner.nextLine().trim().toLowerCase();
        int field = 0;
        switch (choice) 
        {
            case "f":
                field = 0;
                break;
            case "l":
                field = 1;
                break;
            case "a":
                field = 2;
                break;
            case "c":
                field = 3;
                break;
            case "s":
                field = 4;
                break;
            case "z":
                field = 4;
                break;
            case "p":
                field = 6;
                break;
            default:
                System.out.println("please enter field correctly");
                break;
        }
        System.out.print("enter new data of the field:");
        String newData = scanner.nextLine();
        String newContent = "";
        for (int i = 0; i < arrayList.size(); i++) 
        {
            if (i == field) 
            {
                arrayList.add(i, newData);
            }
            newContent += arrayList.get(i) + "\n";
        }
        String option;
        do 
        {
            System.out.println("enter... S for SAVE     SA for SAVE AS      C for CANCEL");
            option = scanner.nextLine().trim().toLowerCase();

        } while (!(option.equals("s") || option.equals("sa")) || option.equals("c"));

        switch (option) 
        {
            // the field is updated in the same file
            case "s":
                writeFile(contactName, newContent);
                System.out.println(contactName + " is edited successfully");
                break;
            // the field is updated in givenfilenamewithoutextension.csv file
            case "sa":
                String extension = ".csv";
                String newContactName = contactName.replaceFirst("[.][^.]+$", "") + extension;
                writeFile(newContactName, newContent);
                nonEmptyContacts.add(newContactName);
                System.out.println("changes are saved successfully in " + newContactName + " file");
                break;
            case "c":
                System.out.println("changes are not saved");
                return;
            default:
                System.out.println("please select either S or SA or C");
                break;
        }
    }
    void deleteContact()
    {
        System.out.print("enter contact name to delete:");
        String contactName = scanner.nextLine().trim();
        File file = new File(contactName);
        if(file.exists())
        {
            if(file.delete())
            {
                if(emptyContacts.contains(contactName))
                {
                    emptyContacts.remove(contactName);
                }
                else
                {
                    nonEmptyContacts.remove(contactName);
                }
                System.out.println(contactName + " is deleted succesfully");
            }
            else
            {
                System.out.println("file deletion failed");
            }

        }
        else
        {
            System.out.println(contactName + "doesn't exists");
        }
    }
}

public class AddressBookLauncher 
{
    // class variable
    static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception 
    {
        AddressBook addressBook = new AddressBook();
        while (true) 
        {
            System.out.println("-----------------------------");
            System.out.println("1.create a new contact");
            System.out.println("2.fill contact details");
            System.out.println("3.display all contacts");
            System.out.println("4.view contact information");
            System.out.println("5.edit contact information");
            System.out.println("6.delete contact information");
            System.out.println("7.exit");
            System.out.print("enter option:");
            String option = scanner.nextLine().trim();
            switch (option) 
            {
                case "1":
                    addressBook.createNewContact();
                    break;
                case "2":
                    addressBook.fillContactDetails();
                    break;
                case "3":
                    addressBook.displayAllContacts();
                    break;
                case "4":
                    addressBook.viewContactInfo();
                    break;
                case "5":
                    addressBook.editContactInfo();
                    break;
                case "6":
                    addressBook.deleteContact();
                    break;
                case "7":
                    System.out.println("exiting....");
                    System.exit(0);
                default:
                    System.out.println("please enter the correct choice");
                    break;
            }

        }

    }
}