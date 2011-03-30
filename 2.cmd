call git push
if %ERRORLEVEL% NEQ 0 (goto ERROR)
goto done

:ERROR
echo error %ERRORLEVEL%

:DONE

pause
