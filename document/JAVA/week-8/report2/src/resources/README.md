## compile
```java
cd /home/nodove/workspace/note/document/JAVA/week-8/report2
mkdir -p out
javac -d out $(find src/main/java -name "*.java")
```
## run
```java
cd /home/nodove/workspace/note/document/JAVA/week-8/report2
java -cp out main.GameTest
```