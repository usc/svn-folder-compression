@ECHO OFF
SET version=0.1.0-SNAPSHOT

rem you know.
SET folderPath=C:\temp\

java -classpath ../../../target/folder-compression-app-%version%.jar com.googlecode.usc.folder.compression.App2 -folderPath %folderPath% -compressionType ZIP -excludedWords ".svn | target | target-eclipse | .classpath | .project | .settings" -help 


@PAUSE

