@echo off
setlocal
cd /d "%~dp0"

call build.bat
if errorlevel 1 exit /b %ERRORLEVEL%

set "JAVA_CMD=java"
where java >nul 2>nul
if errorlevel 1 (
    if defined JAVA_HOME if exist "%JAVA_HOME%\bin\java.exe" set "JAVA_CMD=%JAVA_HOME%\bin\java.exe"
)
if "%JAVA_CMD%"=="java" (
    for /d %%d in ("%ProgramFiles%\Java\jdk*") do if exist "%%d\bin\java.exe" set "JAVA_CMD=%%d\bin\java.exe"
)
if "%JAVA_CMD%"=="java" (
    echo java was not found. Install a JDK or set JAVA_HOME.
    exit /b 1
)

"%JAVA_CMD%" -cp bin net.wms.view.ModernTradingPlatform
