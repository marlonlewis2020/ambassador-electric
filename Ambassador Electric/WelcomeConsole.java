import java.util.*;

public class WelcomeConsole {

// Scanner for the console inputs
static Scanner console = new Scanner (System.in);

public static void main(String[] args) {

    // Prints the welcome message from the method.
    welcomeMessage();

 getUser();



// Method for the welcome message, a void because it returns no values.
}
private static String getUser() {
	// TODO Auto-generated method stub
	return null;
}
static void welcomeMessage ()
{
    System.out.println("Ambassador Electric!\n");
}

// Method that prompts the user to enter their name, scans it, then returns it.
static String getID ()
{
    String id;
    System.out.println("Please enter the employee's ID(Enter a -1 when finished): ");
    id = console.nextLine();
    return id;

}



}