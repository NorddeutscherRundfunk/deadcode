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
1/1 unused imports (ex): /test2.jsp
3/7 unused imports (gsa-ext, rx, fn): /test.jsp

Unused tags:
/WEB-INF/tags/foo: getBar
1/5 Tags unused.

HighCommentRatioPages (> 30%):
/test.jsp: 37
```

## Graphviz output
If the optional "graphvizFile" is specified a .dot-file will be created. Then this file can be used to generate a svg file (or similar) to show the usage in a very userfriendly way:
```
dot -Tsvg -o/tmp/deadcode.svg /tmp/deadcode.dot
```