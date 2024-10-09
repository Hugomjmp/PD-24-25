@echo off
:: ;slf4j-api-1.7.36.jar
java -cp ".\out\;sqlite-jdbc-3.46.1.3.jar" src/pt/isec/pd/servidores/ServidorPrincipal.java 123 Base_de_dados :: rmiService
pause