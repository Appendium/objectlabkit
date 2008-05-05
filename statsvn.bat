svn log -v --xml https://objectlabkit.svn.sourceforge.net/svnroot/objectlabkit/ > svn.log
rem mkdir src\site\statsvn
java -jar c:\java\statsvn\statsvn.jar -xdoc -verbose -output-dir src\site\statsvn -xml -tags "^1.0.1|^1.1.0|^1.2.0" -title ObjectLabKit -exclude "**/qalab.xml" -viewvc http://svn.sourceforge.net/viewvc/objectlabkit/trunk ./svn.log .