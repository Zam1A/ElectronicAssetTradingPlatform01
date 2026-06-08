@echo off
setlocal
cd /d "%~dp0"

set "JAVAC=javac"
where javac >nul 2>nul
if errorlevel 1 (
    if defined JAVA_HOME if exist "%JAVA_HOME%\bin\javac.exe" set "JAVAC=%JAVA_HOME%\bin\javac.exe"
)
if "%JAVAC%"=="javac" (
    for /d %%d in ("%ProgramFiles%\Java\jdk*") do if exist "%%d\bin\javac.exe" set "JAVAC=%%d\bin\javac.exe"
)
if "%JAVAC%"=="javac" (
    echo javac was not found. Install a JDK or set JAVA_HOME.
    exit /b 1
)

if not exist bin mkdir bin
if exist sources.txt del sources.txt

for /R src %%f in (*.java) do echo %%f>>sources.txt

"%JAVAC%" -encoding UTF-8 -d bin @sources.txt
set EXITCODE=%ERRORLEVEL%

del sources.txt
exit /b %EXITCODE%
