cd src
javac -d ../out Main.java

if ($?) {
    java -cp ../out Main.java
}

cd ..