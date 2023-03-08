## PRG2 - Exercise 1 Template Repository
Use this repository as a template for your PRG2 exercise 1 submission. The template serves as a guideline to complete your exercise 1 with JavaFX.
You can do any changes you want to the code.

### Start the application
Start the application by either running the `main` method in the `FhmdbApplication` class or by running `mvn clean javafx:run` in Maven.

Note: If you run the application with `mvn clean javafx:run` you may face a incompatible version error (`java.lang.module.InvalidModuleDescriptorException: Unsupported major.minor version XXX`). If so, check the version of your system Java installation (Environment Variables). 
Maven uses the default system Java installation. If you have multiple Java installations, you can set the Java version for Maven by adding the following to your 
`pom.xml`:
```xml
<build>
  ...
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.10.1</version>
            <configuration>
                <source>17</source> // change to your system version here
                <target>17</target> // and here
            </configuration>
        </plugin>
        ...
    </plugins>
</build>
```
# Prog2 CSDC25BB Exercise 1
## Teammembers
- Judy Kardouh
- Laurin Knünz
## Assignment
The Framework for a Movie Database should be implemented The Code is based on Maven and must include automatic Unit Tests. A JavaFX Template is given but does not have to be used: https://github.com/leonardo1710/fhmdb-template

The Project must include:
- A GUI
- Unit Tests based on JUnit
- Code based on Maven
- A public GitHub Repository

to be positive
# Requirements
The Programm should manage Movie-Objects. The Movie Object is build upon this Pattern:

![image](https://user-images.githubusercontent.com/114426294/223725343-9a5f3139-7909-4980-9b1e-d0ceb7e42307.png)

The Following Features are to be implemented:
- Display (Containing a List of Movies, A "Sort" Button, a Text-Inputfield, A Dropdownmenu for the movie genres and a filterbutton)
- Filter (containing a filter by genre and a search by textstring using the given input)
- Sort (The List can be sorted in increasing or decreasing alphabetical order)
