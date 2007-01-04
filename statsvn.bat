svn log -v --xml https://svn.sourceforge.net/svnroot/objectlabkit/ > svn.log
rem mkdir src\site\statsvn
java -jar c:\java\statsvn\statsvn.jar -xdoc -verbose -output-dir src\site\statsvn -tags "^1.0.1|^1.1.0" -title ObjectLabKit -exclude "**/qalab.xml" -viewvc http://svn.sourceforge.net/viewvc/objectlabkit/trunk ./svn.log .