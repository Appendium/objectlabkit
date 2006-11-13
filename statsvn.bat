svn log -v --xml > logfile.log
rem mkdir qalab\target\docs\statsvn
java -jar c:\java\statsvn\statsvn.jar -css objectlab-statcvs.css -verbose -output-dir src\site\statsvn -title ObjectLabKit -viewvc http://svn.sourceforge.net/viewvc/objectlabkit/trunk ./logfile.log .