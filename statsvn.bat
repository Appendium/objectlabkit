svn log -v --xml https://svn.sourceforge.net/svnroot/objectlabkit/ > logfile.log
rem mkdir qalab\target\docs\statsvn
java -jar c:\java\statsvn\statsvn.jar -format xdoc -verbose -output-dir src\site\statsvn -tags "^1.0.1" -title ObjectLabKit -viewvc http://svn.sourceforge.net/viewvc/objectlabkit/trunk ./logfile.log .