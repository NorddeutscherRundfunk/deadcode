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
/home/foo/workspace/project/src/main/webapp/test.jsp: 37
```