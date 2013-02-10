@echo off
title Server
echo Launching Server...
"C:/Program Files/Java/jre7/bin/java.exe" -server -Xmx1024m -cp bin;lib/*; com.rs.Launcher true true true
pause