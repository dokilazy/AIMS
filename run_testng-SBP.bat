set ProjectPath=D:\QC\Automation\Framework\myFW
cd %ProjectPath%
set classpath=%ProjectPath%\target\classes;%ProjectPath%\lib\*
java org.testng.TestNG %ProjectPath%\testng-SBP.xml
pause