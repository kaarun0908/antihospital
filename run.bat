@echo off
echo ==========================================
echo   Starting Hospital Management System...
echo ==========================================
if not exist "bin" mkdir bin
javac -encoding UTF-8 -d bin src\*.java
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Compilation failed! Please check Java errors above.
    pause
    exit /b %ERRORLEVEL%
)
java -cp bin Main
pause
