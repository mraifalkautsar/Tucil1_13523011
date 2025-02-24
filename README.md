# Tucil1_13523011

![logo_repo](https://github.com/user-attachments/assets/69ab5e6a-d451-43ab-96af-2bcf0c1b6acd)
Muhammad Ra'if Alkautsar / 13523011

IQ Puzzle Pro Solver (Pazurupurosorubā) adalah sebuah program yang mampu untuk memecahkan sebuah papan IQ Puzzler Pro menggunakan algoritma brute force. Program ini menyediakan antarmuka command-line dan graphical.

## Cara Kompilasi/Run
Cara kompilasi: 

Buka terminal di root folder atau src, lalu ketik perintah berikut:

### Untuk mengcompile dari root folder ke bin
> javac --module-path "path-to-javafx\javafx-sdk-23.0.2\lib" --add-modules javafx.controls,javafx.fxml -d bin src/*.java

atau 

> javac -d bin src/*.java 

### Untuk mencompile dari src:
> javac --module-path "path-to-javafx\javafx-sdk-23.0.2\lib" --add-modules javafx.controls,javafx.fxml *.java 

atau 

> javac *.java

### Cara run untuk mode CLI (pastikan run dari bin supaya input yang berada di /bin/input bisa terbaca):
> java --module-path "C:\javafx-sdk-23.0.2\lib" --add-modules javafx.controls,javafx.fxml MainCLI

### Cara run untuk mode GUI:
> java --module-path C:\javafx-sdk-23.0.2\lib --add-modules javafx.controls,javafx.fxml -jar MainGUI

### Cara run jar MainGUI yang berada di /bin:
> java --module-path C:\javafx-sdk-23.0.2\lib --add-modules javafx.controls,javafx.fxml -jar MainGUI.jar

### Cara run jar MainCLI yang berada di /bin: 
> java -jar MainCLI.jar
