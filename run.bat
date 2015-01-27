@ECHO OFF
::
::	Ficheiro .bat para testar o programa desenvolvido na cadeira de LAPR1
::	Este ficheiro abre a consola do windows e pede o nome dos ficheiros a
::	carregar ao utilizador. Desclarando possíveis erros.
::
ECHO.
ECHO.
ECHO		 oooooooooo.         .o.       oooooooooo.    .oooo.     .oooo.   
ECHO		 `888'   `Y8b       .888.      `888'   `Y8b  d8P'`Y8b  .dP""Y88b  
ECHO		  888      888     .8"888.      888     888 888    888       ]8P' 
ECHO		  888      888    .8' `888.     888oooo888' 888    888     ^<88b.  
ECHO		  888      888   .88ooo8888.    888    `88b 888    888      `88b. 
ECHO		  888     d88'  .8'     `888.   888    .88P `88b  d88' o.   .88P  
ECHO		 o888bood8P'   o88o     o8888o o888bood8P'   `Y8bd8P'  `8bd88P'   
ECHO.
ECHO                                          Projecto LAPR1 2014-2015
ECHO.
ECHO.
:run
ECHO.
SET /P ler=Introduza o nome do ficheiro HTML que pretende carregar: 
SET /P escrever=Introduza o nome do ficheiro HTML para guardar os dados: 
ECHO.
java -jar dist_lapr1\Lapr1.jar %ler% %escrever%
SET estado=%ERRORLEVEL%	
if %ERRORLEVEL% EQU 0 goto run