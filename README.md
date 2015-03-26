[![Build Status](https://travis-ci.org/NorddeutscherRundfunk/deadcode.svg?branch=master)](https://travis-ci.org/NorddeutscherRundfunk/deadcode)
# deadcode
Tool to find useless imports in JSP-files and unused tags in a Java web-application

## usage
```
deadcode <webapproot> [<graphvizFile>]
```

```
example: deadcode /home/foo/workspace/project/src/main/webapp /tmp/deadcode.dot
```

## sample output
```
Unused Taglib imports:
2/2 unused imports (ex, fn): /test2.jsp
2/7 unused imports (gsa-ext, rx): /test.jsp

Unused tags:
/WEB-INF/tags/test: test4
/WEB-INF/tags/test: test5
2/3 Tags unused.

HighCommentRatioPages (> 30%):
/test2.jsp: 36
/test.jsp: 36
```

## Graphviz output
If the optional "graphvizFile" is specified a .dot-file will be created. Then this file can be used to generate a svg file (or similar) to show the usage in a very userfriendly way:
```
dot -Tsvg -o/tmp/deadcode.svg /tmp/deadcode.dot
```