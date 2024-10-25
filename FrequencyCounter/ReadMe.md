## Frequency Counter

### Problem: 
Optimizing the Frequency Count

### Description:
Given a list of integers, your task is to count the frequency of each unique integer and then output the integer with the highest frequency. If there are multiple integers with the same highest frequency, return the smallest integer among them.

### Input:
Multiple lists of integers

### Output:
The integer with the highest frequency for each list, each on a new line put into a text file called output.txt

### Running the solution
This solution is written in Java and requires the following to run:
- **IntelliJ IDE**
- **An input file with multiple lists of integers**
  - Unzip FrequencyCounter.zip
  - Go to the Build Menu and select 'Build Project'
  - Go to the Run Menu and run FrequencyCounter
  - Select the input file
  - View the output file for result

### Running the JUnit tests
The unit tests require the following to run:
- maven installed for mvn commands
- Run the following commands:
  - mvn clean test
  - Select the inputTest.txt file when prompted
  - mvn verify
- View the jacoco test results in \target\site\jacoco\index.html