CHCP
echo %USERPROFILE%
dir %USERPROFILE%\.android
FC debug.keystore %USERPROFILE%\.android\debug.keystore
IF %ERRORLEVEL% == 0 GOTO END
COPY  %USERPROFILE%\.android\debug.keystore %USERPROFILE%\.android\debug.keystore.bak
COPY ./debug.keystore %USERPROFILE%\.android\debug.keystore
:END
PAUSE
