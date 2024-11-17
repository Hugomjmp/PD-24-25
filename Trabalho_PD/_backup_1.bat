@echo off
java -cp ".\out\;sqlite-jdbc-3.46.1.3.jar" src/pt/isec/pd/servidorBackup/ServidorBackup.java src/pt/isec/pd/servidorBackup/recuperacao/BD_1
pause