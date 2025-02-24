# Tucil1_13523011

<img src="https://github.com/user-attachments/assets/bb2ccccc-bcdc-44a5-9fcb-fd7c1cf93256" alt="image" width="400">
<br /> 
Muhammad Ra'if Alkautsar / 13523011

IQ Puzzle Pro Solver (Pazurupurosorubā) adalah sebuah program yang mampu untuk memecahkan sebuah papan IQ Puzzler Pro menggunakan algoritma brute force. Program ini menyediakan antarmuka command-line dan graphical.

## Cara Kompilasi/Run
Ada dua mode yang tersedia, yaitu mode GUI dan mode CLI. Main class dari mode GUI adalah MainGUI sedangkan main class dari mode CLI adalah MainCLI. Tersedia jar untuk masing-masing mode untuk mempermudah run program (tanpa perlu kompilasi).

Untuk melakukan kompilasi, buka terminal di root folder atau src, lalu ketik perintah berikut:

### Untuk mengcompile dari root folder ke bin
> javac --module-path "path-to-javafx\javafx-sdk-23.0.2\lib" --add-modules javafx.controls,javafx.fxml -d bin src/*.java

### Untuk mencompile dari src:
> javac --module-path "path-to-javafx\javafx-sdk-23.0.2\lib" --add-modules javafx.controls,javafx.fxml *.java 

### Cara run untuk mode CLI (pastikan run dari bin supaya input yang berada di /bin/input bisa terbaca):
> java MainCLI

### Cara run untuk mode GUI:
> java --module-path C:\javafx-sdk-23.0.2\lib --add-modules javafx.controls,javafx.fxml -jar MainGUI

### Cara run jar MainCLI yang berada di /bin: 
> java -jar MainCLI.jar

### Cara run jar MainGUI yang berada di /bin:
> java --module-path C:\javafx-sdk-23.0.2\lib --add-modules javafx.controls,javafx.fxml -jar MainGUI.jar
