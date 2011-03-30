call 	git add .
if %ERRORLEVEL% NEQ 0 (goto ERROR)
call 	git commit -a
if %ERRORLEVEL% NEQ 0 (goto ERROR)
call 	git fetch
if %ERRORLEVEL% NEQ 0 (goto ERROR)
call 	git merge origin/master
if %ERRORLEVEL% NEQ 0 (goto ERROR)
goto done

:ERROR
echo error %ERRORLEVEL%

:DONE

