# Path Wildcards

This wildcard is implemented by **JAVA** and follows(not strictly sure) a **subset** rules of gitignore wildcard. 

## Matching rules

| pattern | input                                  |
| ------- | -------------------------------------- |
| \*      | match one directory or file            |
| \*\*    | match 0, 1 or more directories or file |

## Matching examples

| pattern         | input           | output | explain                                                      |
| --------------- | --------------- | ------ | ------------------------------------------------------------ |
| build           | build/          | yes    | match a dir                                                  |
|                 | build           | yes    | match a regular file                                         |
|                 | build/xxx       | yes    | match sub contents of a dir                                  |
|                 | xx/build        | no     | only match files in current dir                              |
| build/          | build/xxx       | yes    | match sub contents of a dir                                  |
|                 | build           | no     | only match dir files                                         |
| \*/build/\*     | test/build/xx/x | yes    | leading \* matchs A dir, tailing \* matchs A reg-file or dir |
|                 | build/          | no     | leading \* MUST match A dir                                  |
|                 | test/build/     | no     | tailing \* MUST match A dir, "build" itself not included     |
| test/\*\*/build | test/x/x/build  | yes    | \*\* matchs multiple dir                                     |
|                 | test/build      | yes    | \*\* matchs 0 or multiple dir                                |
|                 | build           | no     |                                                              |
|                 | ttt/build/      | no     |                                                              |

## Env

java-1.8.0-openjdk-amd64

## Usage

```java
Wildcard wildcard = new Wildcard("test/**/build");

Assertions.assertTrue(wildcard.match("test/x/x/build"));
```
