# Pingouins

This project is an adaption in video game of the board game : "Hey! That's my fish !"

It was made for a school project, using the Java language with the Swing library for the GUI part

## Credits

 - BERNET Elsa - Graphic designer
 - COMBE Gaetan - AI developer
 - GREGOIRE Thomas - HMI developer / Responsible for connecting each module
 - SALLÉ Maxime - Engine developer
 - VIAL Fabien - HMI developer
 - YANG Déborah - QA tester

## How to execute the game from the jar

```bash
java -jar Pingouins.java
```

## Developpers details

### Rules to compile the game

```bash
javac -cp src -d out src/*/*/*.java src/*/*/*/*.java src/*/Pingouins.java

java -cp out Pingouins
```

### Rules to compile the tests

```bash
javac -cp src -d out src/*/*/*.java src/*/Pingouins.java src/*/*/*/*.java src/tests/Tests/*.java

java -ea -cp out tests.Tests.TestsPingouins

java -ea -cp out tests.Tests.TestsTerrains

java -ea -cp out tests.Tests.TestsCoups

java -ea -cp out tests.Tests.MyTest
```

