import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Presentation displaySessions = new Presentation();
        displaySessions.beginSessionOne(scanner);
        // Session two (Part 2) is called by beginSessionOne
    }
}



/** The class below populates the initial 2d Array, initiating classes below and conducting the presentation flow */
class Presentation {

    // Initializing 2D array variable for Part 1.
    private String[][] stateAndCapitals = {
            {"alabama", "alaska", "arizona", "arkansas", "california",
                    "colorado", "connecticut", "delaware", "florida","georgia",
                    "hawaii", "idaho", "illinois", "indiana", "iowa", "kansas",
                    "kentucky", "louisiana", "maine", "maryland", "massachusetts",
                    "michigan", "minnesota", "mississippi", "missouri", "montana",
                    "nebraska", "nevada", "new hampshire", "new jersey", "new mexico",
                    "new york", "north carolina", "north dakota", "ohio", "oklahoma",
                    "oregon", "pennsylvania", "rhode island", "south carolina", "south dakota",
                    "tennessee", "texas", "utah", "vermont", "virginia", "washington",
                    "west virginia","wisconsin", "wyoming"},
            {"montgomery", "juneau", "phoenix", "little rock", "sacramento",
                    "denver", "hartford", "dover", "tallahassee", "atlanta",
                    "honolulu", "boise", "springfield", "indianapolis", "des moines", "topeka",
                    "frankfort", "baton rouge", "augusta", "annapolis", "boston",
                    "lansing", "saint paul", "jackson", "jefferson city", "helena",
                    "lincoln", "carson city", "concord", "trenton", "santa fe",
                    "albany", "raleigh", "bismarck", "columbus", "oklahoma city",
                    "salem", "harrisburg", "providence", "columbia", "pierre",
                    "nashville", "austin", "salt lake city", "montpelier", "richmond", "olympia",
                    "charleston", "madison", "cheyenne"}
    };

    // Getters and Setters below
    public String[][] getArray(){
        return stateAndCapitals;
    }
    private void setArray(String[][] array){
        this.stateAndCapitals = array;
    }

    // The method below creates the presentation flow for part one. passing in scanner for input
    public void beginSessionOne(Scanner scanner){
        // Introduction prompt
        minorDelay(10, "\n" +
                "**************************   PART ONE    **************************\n" +
                "In 10 seconds, A (2D Array) list of states and capitals will populate below.\n" +
                "Each line indicates the relationship between the index, state and capital.\n" +
                "The index will be sorted by state in descending order by default.\n");

        // Showing current list. Sorted by State in ascending order.
        SessionOneMethods.createLine(getArray());

        minorDelay(5,"\nInitializing bubble sort for capitals in descending order in 5 seconds.\n");

        // Using getters and setters to change private array with bubble sort
        setArray(SessionOneMethods.bubblySort(getArray()));

        // Showing current list. Bubble sorted.
        SessionOneMethods.createLine(getArray());

        // Initializing questionnaire session with current array and scanner
        SessionOneMethods.questionnaire(getArray(), scanner);
        minorDelay(1, "This is end of the first part of the assignment.\n");

        // Initiating presentation flow for second part of assignment
        beginSessionTwo(scanner);

    }

    public void beginSessionTwo(Scanner scanner){
       minorDelay(10, "\n" +
                "**************************   PART TWO    **************************\n" +
                "In 10 seconds, The same list from before will once again populate below.\n" +
                "The 2D Array will be converted to a hashmap and displayed below in key-value pairs.\n");

        // Initializing instance of Part Two by passing in the current 2D array and randomizing it.
       SessionTwoMethods sessionTwo = new SessionTwoMethods(getArray());
        sessionTwo.printHashMap();

        minorDelay(5, "\nConverting HashMap to TreeMap and printing using generic binary search.\n");
        sessionTwo.printTreeMap();

        System.out.println("\nPlease provide the name of any state. \nPress \"Q\" to end presentation." +  "\n(If you make a typo, I'll correct you.)");
        sessionTwo.secondQuestionnaire(scanner);
    }

    // The method below creates a delay between printing to provide explanation for each method initiation.
    public static void minorDelay(int secondsToWait, String message){
        System.out.println(message);
        for(int i = secondsToWait; i > 0; i--) {
            String countDown  = ( i == 1)? (i + "\n\n") : (i + "   ") ;
            try{
                TimeUnit.SECONDS.sleep(1);
                if (secondsToWait > 1){
                    System.out.print(countDown);
                }
            } catch(java.lang.InterruptedException e){
                System.out.println("An error occurred while waiting: " + e);
            }
        }
    }

    // This method capitalizes the first letter and any letter after a space in a StringBuilder object.
    public static String formatWord(StringBuilder wordToFix){
        //Set to true to capitalize the first letter.
        boolean capitalizeNext = true;
        StringBuilder result = new StringBuilder();

        // iterating through characters in word.
        for (int i = 0; i < wordToFix.length(); i++) {
            char c = wordToFix.charAt(i);

            // Check for a space char, if found flip the boolean.
            if (c == ' ') {
                capitalizeNext = true;

                // if boolean is true, the sequential character is capitalized and boolean is set to false.
            } else if (capitalizeNext) {
                c = Character.toUpperCase(c);
                capitalizeNext = false;
            }
            result.append(c);
        }
        return result.toString();
    }

    //Copy paste for string....
    public static String formatWord(String wordToFix){
        //Set to true to capitalize the first letter.
        boolean capitalizeNext = true;
        StringBuilder result = new StringBuilder();

        // iterating through characters in word.
        for (int i = 0; i < wordToFix.length(); i++) {
            char c = wordToFix.charAt(i);

            // Check for a space char, if found flip the boolean.
            if (c == ' ') {
                capitalizeNext = true;

                // if boolean is true, the sequential character is capitalized and boolean is set to false.
            } else if (capitalizeNext) {
                c = Character.toUpperCase(c);
                capitalizeNext = false;
            }
            result.append(c);
        }
        return result.toString();
    }
}



/** The class below is for all methods used in Part one */
class SessionOneMethods {

    //This method populates the list of states and capitals from the 2D array provided.
    public static void createLine(String[][] array){
        // Initializing an int representing distance of space between state value and "Capital: "
        int stateNameWidth = 18;

        // Mutable StringBuilder is initialized as a dynamic variable for complete string
        StringBuilder tempOutput = new StringBuilder();

        //Loop through columns
        for (int i = 0; i < array[0].length; i++){

            // Clearing the value in temporary variable before appending subsequent data.
            tempOutput.setLength(0);

            // Retrieve value from cell
            for(int j = 0; j < array.length; j++){
                StringBuilder currentWord = new StringBuilder(array[j][i]);

                // if we are in the row designated for states, append the formatted string with correct capitalization (see formatWord method above)
                if (j == 0){
                    // Adding space before number, if needed, for consistency.
                    String emptySpace = (i < 10) ? " " : "" ;
                    tempOutput.append(String.format(emptySpace + i + ") " + "State: %-" + stateNameWidth + "s", Presentation.formatWord(currentWord)));

                } else{
                    // Appending string for current capital and printing the final string result.
                    tempOutput.append("Capital: ").append(Presentation.formatWord(currentWord));
                    System.out.println(tempOutput);
                }
            }
        }
    }

    // The method below is the bubble sort method.
    public static String[][] bubblySort(String[][] unsortedArray){
        int valueCount = unsortedArray[1].length;

        // Declaring temporary holder outside for loop.
        String[][] tempArray = new String[2][valueCount];

        // Loop through array from beginning, given the column size.
        for(int i = 0; i < valueCount - 1; i++){
            // Loop through array from the end.
            for(int j = 0; j < (valueCount - i - 1); j++){

                /** Using an int variable to indicate if a swap is necessary.
                 Using the generic statement compareTo */
                int needSwap = unsortedArray[1][j].compareTo(unsortedArray[1][j +1]);

                /** compareTo will return an int greater than zero if the
                 first value provided is lexicographically greater than the second value */

                if( needSwap > 0) {
                    // Swapping values
                    tempArray[0][j] = unsortedArray [0][j];
                    tempArray[1][j] = unsortedArray [1][j];
                    unsortedArray[0][j] = unsortedArray[0][j + 1];
                    unsortedArray[1][j] = unsortedArray[1][j + 1];
                    unsortedArray[0][j + 1] = tempArray[0][j];
                    unsortedArray[1][j + 1] = tempArray[1][j];
                }
            }
        }
        return unsortedArray;
    }

    public static void questionnaire(String[][] currentArray, Scanner scanner) {
        // Initializing score, and array for the indexes of previous answers.
        int score = 0;
        ArrayList<Integer> dontCheat = new ArrayList<>();

        // Intro prompt
        System.out.println(
                "\nPlease enter multiple state capitals, one per line and press enter." +
                "\nThe accumulative score for each correct answer will be displayed after each input." +
                "\npress \"Q\" and enter to quit.");

        while (true) {
            // Breaking the loop if the user has max score
            if(score == 50){
                System.out.println("\nWow. You've provided all the capitals.");
                Presentation.minorDelay(1, "");
                System.out.println("Good for you...");
                Presentation.minorDelay(1, "");
                break;
            }

            // Get input
            StringBuilder userInput = new StringBuilder(scanner.nextLine().toLowerCase());

            // Breaking loop if user enters q
            if(userInput.toString().equals("q")){
                System.out.println("Successfully exited");
                Presentation.minorDelay(1, "");
                break;
            }

            // Initiating flags to check for correct value, and duplicate answers
            boolean isValid = false;
            boolean isCheating = false;

            // Loop through array
            for (int i = 0; i < currentArray[1].length; i++) {
                // Storing word from array to compare
                StringBuilder word = new StringBuilder(currentArray[1][i]);
                // Checking if match is found
                if (userInput.toString().equals(word.toString())) {
                    // isCheating becomes true if answer is duplicate, score is not incremented and the loop breaks.
                    if (dontCheat.contains(i)) {
                        System.out.println("You already provided this capital.");
                        System.out.println("Your score remains as " + score + " ...cheater.");
                        isCheating = true;
                        break;
                    } else {
                        // isValid becomes true due to correct match and no duplicate answers, score increments and the loop breaks.
                        dontCheat.add(i);
                        score++;
                        isValid = true;
                        break;
                    }
                }
            }

            // Return specified prompt according to flags
            if (isValid && !isCheating) {
                // Storing index of previous answer in an int
                int stateIndex = dontCheat.get(dontCheat.size() - 1);
                // Retrieving name of the state in a StringBuilder object
                StringBuilder state = new StringBuilder(currentArray[0][stateIndex]);

                System.out.println("Correct, you provided the Capital of " + Presentation.formatWord(state));
                System.out.println("Your score is now " + score);

            } else if (!isValid && !isCheating) {
                System.out.println("Incorrect, your score is still " + score);
            }
        }
    }
}

/** The class below is for PART TWO */
class SessionTwoMethods {
    private HashMap<String, String> hashMap;
    private TreeMap<String, String> treeMap;

    public SessionTwoMethods(String[][] currentArray){
        // convert array to hash and tree, then store as fields
        this.hashMap = convertToMap(currentArray);
        this.treeMap = convertHashToTree(hashMap);
    }
    public void printHashMap(){
        createString(hashMap);
    }
    public void printTreeMap(){
        createString(treeMap);
    }
    public static TreeMap<String,String> convertHashToTree(HashMap <String, String> hashMap){
        TreeMap<String, String> treeMap = new TreeMap<>();
        for(Map.Entry<String, String> entry : hashMap.entrySet()){
            // Swapping values to sort by capital
            String key = entry.getValue();
            String value = entry.getKey();
            treeMap.put(key, value);
        }
        return treeMap;
    }

    // The method below turns the Array into a HashMap.
    public static HashMap<String,String> convertToMap(String[][] currentArray) {
        // Initializing HashMap
        Map<String, String> hashMap = new HashMap<>();

        // Looping through array and storing the state and value in hashMap
        for (int i = 0; i < currentArray[1].length; i++) {
            hashMap.put(currentArray[0][i].toLowerCase(), currentArray[1][i].toLowerCase());
        }
        //returning as HashMap
        return (HashMap<String, String>) hashMap;
    }

    public void createString(HashMap<String, String> object){
        for(Map.Entry<String, String> entry : object.entrySet()){
            StringBuilder tempOutput = new StringBuilder();
            String key = entry.getKey();
            String value = entry.getValue();

            tempOutput.append(String.format("Key: %1$-" + 16 + "s", key));
            tempOutput.append("\u2192    Value: " + value);

            System.out.println(Presentation.formatWord(tempOutput));
        }
    }

    public void createString(TreeMap<String, String> object){
        //loop through tree map (Binary search)
        for(Map.Entry<String, String> entry : object.entrySet()){
            // Temporary holder initiated
            StringBuilder tempOutput = new StringBuilder();

            // Correct variable naming since values are swapped
            String key = entry.getValue();
            String value = entry.getKey();

            //appending to the temporary holder
            tempOutput.append(String.format("Key: %1$-" + 16 + "s", key));
            tempOutput.append("\u2192    Value: " + value);

            //print with correct capitalization
            System.out.println(Presentation.formatWord(tempOutput));
        }
    }

    public void secondQuestionnaire(Scanner scanner){

        while (true) {
            //get input
            StringBuilder userInput = new StringBuilder(scanner.nextLine().toLowerCase());

            // break if quit
            if(userInput.toString().equals("q")){
                System.out.println("Presentation finished.");
                break;
            }

            // Converting to string
            String keyword = userInput.toString().toLowerCase();


            //Getting state/value from input, if no match then loop.
            StringBuilder state = new StringBuilder();

            if(this.hashMap.containsKey(keyword)){
                String value = this.hashMap.get(keyword);
                if (value != null){
                    state.append(value);
                }
            }

            // This is for fun and totally unnecessary, not part of assignment
            CheckWord gotta = new CheckWord();

            if(!state.toString().isEmpty()){
                System.out.println("The capital for " + Presentation.formatWord(keyword) + " is "+ Presentation.formatWord(state));
            } else {
                System.out.println("That's not a state.");

                // Possible typo. Checking for correction and storing in string
                if (!gotta.typo(keyword).toString().isEmpty()){
                    String correction = gotta.typo(keyword).toString();
                    String capital = this.hashMap.get(correction);
                    System.out.println("I think you meant " + Presentation.formatWord(correction) + "?... if so the capital would be " + Presentation.formatWord(capital) + ".");
                }
            }
        }
    }
}

/** The class below is sloppy spaghetti and just for fun */
class CheckWord {
    //This array is built for the Levenshtein distance calculator.
     private String[] capitals = {"alabama", "alaska", "arizona", "arkansas", "california",
            "colorado", "connecticut", "delaware", "florida","georgia",
            "hawaii", "idaho", "illinois", "indiana", "iowa", "kansas",
            "kentucky", "louisiana", "maine", "maryland", "massachusetts",
            "michigan", "minnesota", "mississippi", "missouri", "montana",
            "nebraska", "nevada", "new hampshire", "new jersey", "new mexico",
            "new york", "north carolina", "north dakota", "ohio", "oklahoma",
            "oregon", "pennsylvania", "rhode island", "south carolina", "south dakota",
            "tennessee", "texas", "utah", "vermont", "virginia", "washington",
            "west virginia","wisconsin", "wyoming"};

    public String[] getCapitals() {
        return capitals;
    }

    public StringBuilder typo(String keyword) {
        // Temp holder
        StringBuilder correction = new StringBuilder();

        for(String guess : getCapitals()) {
            // Levenshtein calculator returns string, appending to temp holder
            correction.append(calcLevenshteinDistance(guess, keyword));
        }
        return correction;
    }

    public String calcLevenshteinDistance(String correct, String typo){
        // making space for an extra row and column for empty string
        int size1 = correct.length() +1;
        int size2 = typo.length() +1;

        // initiating blank matrix to size
        int[][] matrix = new int[size1][size2];

        // Filling rows left to right, and columns top bottom
        for( int i = 0; i < matrix.length; ++i){
            matrix[i][0] = i;
        }
        for( int i = 0; i < matrix[0].length; ++i){
            matrix[0][i] = i;
        }

        /** Nested loops below target one cell per iteration (skipping empty string cells) */

        // Both iterators start at one to skip empty string cells
        for( int row = 1; row < matrix.length; row++){
            for(int col = 1; col < matrix[0].length; col++){
                // No operations. Copy previous answer in upper-left diagonal cell if chars match
                if(correct.charAt(row-1) == typo.charAt(col-1)){
                    matrix[row][col] = matrix[row-1][col-1];
                } else {
                    // Operations needed, find minimum previous answer and add an operation
                    int minimumAdjacent = Math.min(matrix[row][col -1], matrix[row-1][col]);
                    matrix[row][col] = Math.min(minimumAdjacent, matrix[row-1][col - 1]) + 1;
                }
            }
        }
        int result = matrix[matrix.length - 1][matrix[0].length - 1];


        return (result <=2) ? correct : "";
    }
}