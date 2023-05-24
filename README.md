# Pingouins

Bienvenue sur le jeu Pingouins.

# Règles pour compiler le jeu

```bash
javac -cp src -d out src/*/*/*.java src/*/*/*/*.java src/*/Pingouins.java

java -cp out Pingouins
```

# Règles pour compiler les tests

```bash
javac -cp src -d out src/*/*/*.java src/*/Pingouins.java src/*/*/*/*.java src/tests/Tests/*.java

java -ea -cp out tests.Tests.TestsPingouins

java -ea -cp out tests.Tests.TestsTerrains

java -ea -cp out tests.Tests.TestsCoups

java -ea -cp out tests.Tests.MyTest
```

# Pour exécuter Pingouins.jar

```bash
java -jar Pingouins.java
```
