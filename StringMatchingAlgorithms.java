import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;

public class StringMatchingAlgorithms {
    public static void main(String[] args) throws Exception {
        // create file objects
        File inputFile = new File("input.txt");
        File outputFile = new File("patterns.txt");

        if(inputFile.exists() && outputFile.exists()){

            // create a scanner to read from the input file
            Scanner fileScanner = new Scanner(inputFile);
        
            // create a scanner to read the user's input (from console)
            Scanner inputScanner = new Scanner(System.in);

            // create a printwriter object to write into the patterns.txt
            PrintWriter writer = new PrintWriter(outputFile);

            // ask the user how many lines to be read from the input file
            System.out.println("How many lines you want to read from the text file?");

            // read from the console
            int numberOfLines = inputScanner.nextInt();
            System.out.println();

            // ask the user how many patterns to be generated
            System.out.println("How many patterns to be generated?");

            // read from the console
            int numberOfPatterns = inputScanner.nextInt();
            System.out.println();

            // ask the user about the length of each pattern
            System.out.println("What is the length of each pattern?");

            // read from the console
            int patternLength = inputScanner.nextInt();
            System.out.println();

            // read from the input file a specific number of lines
            String text = "";
            for(int i = 0; i < numberOfLines; i++){
                text = (text + fileScanner.nextLine()).toLowerCase();
            }

            // create a hashset to store all distinct characters present in the text
            HashSet<Character> characterSet = new HashSet<>();

            // add each character in the text to the hashset
            for(int i = 0; i < text.length(); i++){
                if(!characterSet.contains(text.charAt(i)))
                    characterSet.add(text.charAt(i));
            }

            // convert the hashset to an array
            Character[] characterArray = characterSet.toArray(new Character[characterSet.size()]);

            // Randomly generate patterns with a specific length
            // Create a String array to store the generated patterns
            String[] patterns = new String[numberOfPatterns];
            String pattern = "";
            for(int i = 0; i < numberOfPatterns; i++){
                for(int j = 0; j < patternLength; j++){
                    int randomIndex = (int)(Math.random()*characterArray.length-1);
                    // store the pattern in the String array
                    pattern += characterArray[randomIndex];
                }
                patterns[i] = pattern;
                writer.println(pattern);
                pattern = "";
            }

            System.out.println("\n" + numberOfPatterns + " Patterns, each of length " + patternLength
            + " have been generated in a file pattern.txt\n");


            // Iterate through the patterns & call the Brute Force String Matching method
            // compute the running time
            long bruteForceInitialTime = System.nanoTime();
            for(int i = 0; i < patterns.length; i++){
                int index = BruteForceStringMatch(text.toCharArray(), patterns[i].toCharArray());
            }
            long bruteForceFinalTime = System.nanoTime();

            System.out.println("Average time of search in Brute Force Approach: " + (bruteForceFinalTime-bruteForceInitialTime) + "\n");

            long horspooInitialTime = System.nanoTime();
            for(int i = 0; i < patterns.length; i++){
                int index = HorspoolStringMatch(patterns[i], text);
            }
            long horspoolFinalTime = System.nanoTime();

            System.out.println("Average time of search in Horspool Approach: " + (horspoolFinalTime-horspooInitialTime) + "\n");
        

            writer.flush();
            writer.close();
            fileScanner.close();

        }

    else
        System.out.println("File does not exist");

    } // end of the main method


    // Implementation of String Match Brute Force approach
    public static int BruteForceStringMatch(char[] text, char[] pattern){
        for(int i = 0; i < text.length - pattern.length; i++){
            int j = 0;
            while (j < pattern.length && pattern[j] == text[i + j]){
                j++;
            }

            if(j == pattern.length){
                return i;
            }

        }

        return -1;

    }


    // Implementation of Shift Table (HashTable) using Horspool algorithm
     public static Map<Character, Integer> shiftTable(String pattern) {
        Map<Character, Integer> table = new HashMap<>();
        int patternLength = pattern.length();

        for (int i=0; i<patternLength - 1; i++) {
            char Char = pattern.charAt(i);
            int shift = patternLength - i - 1;
            table.put(Char, shift);
            
        }
        System.out.println(table);
        return table;
    }


     // Implementation of Horspool algorithm
     public static int HorspoolStringMatch(String pattern, String text){
        int patternLength = pattern.length();
        int textLength = text.length();

        Map<Character, Integer> shiftTable = shiftTable(pattern);
        
        int i = patternLength-1;
        while (i <= textLength-1) {
            int k = 0;
            while (k <= i && (pattern.charAt(patternLength-1-k) == text.charAt(i-k))) {
                k = k+1;  
            }
            if (k == patternLength){
                return i-patternLength+1;
            }
            else{
                i = i+shiftTable.getOrDefault(text.charAt(i), patternLength);
            }
        }
        return -1;
    }


}
