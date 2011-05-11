call git push origin master
if %ERRORLEVEL% NEQ 0 (goto ERROR)
goto done

:ERROR
echo error %ERRORLEVEL%

:DONE

