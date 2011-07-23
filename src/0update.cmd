@echo off
REM  WordPong Localization
REM  Files: wordpong\src\
REM    StripesResources.properties - primary english language files. All language edits are made here
REM    StripesResources_en.properties - copy of StripesResources.properties. Updated by this command file
REM    StripesResources_**.properties - foreign language translation maintained by UpdatePropertyFiles.class using google translate
copy StripesResources.properties StripesResources_en.properties 
echo Updating property files
REM must be run in wordpong\src path!
java -cp "../war/WEB-INF/classes;../lib-plugin/google-api-translate-java-0.95.jar"  com.wordpong.tools.translate.UpdatePropertyFiles
echo done.
pause