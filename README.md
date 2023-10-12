[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

# Path Wildcard

This wildcard is implemented by **JAVA** and follows(not strictly sure) a **subset** rules of gitignore wildcard.

## Matching rules

| pattern | input                                  |
| ------- | -------------------------------------- |
| \*      | match one directory or file            |
| \*\*    | match 0, 1 or more directories or file |

## Matching examples

| pattern                 | input                    | output | explain                              |
| ----------------------- | ------------------------ | ------ | ------------------------------------ |
| build                   | build/                   | yes    |                                      |
|                         | build                    | yes    |                                      |
|                         | build/xxx                | yes    |                                      |
|                         | xx/build                 | no     |                                      |
| build/                  | build/xxx                | yes    |                                      |
|                         | build                    | no     | only match dir files                 |
| \*/build/\*             | test/build/xx/x          | yes    |                                      |
|                         | build/                   | no     | leading \* MUST match **some** chars |
|                         | test/build/              | no     | tailing \* MUST match **some** chars |
| test/build/sample\*.txt | test/build/sample.txt    | yes    |                                      |
|                         | test/build/sampleABC.txt | yes    |                                      |
|                         | test/build/samABCple.txt | no     |                                      |
| test/\*\*/build         | test/x/x/build           | yes    | \*\* matchs multiple dir             |
|                         | test/build               | yes    | \*\* matchs 0 or multiple dir        |
|                         | build                    | no     |                                      |
|                         | ttt/build/               | no     |                                      |
| test/bu?ild/sample      | test/buAild/sample       | yes    |                                      |
|                         | test/buABild/sample      | no     | there are 2 chars between bu and ild |
|                         | test/bu/ild/sample       | no     | ? can not match /                    |

## Env

java-1.8.0-openjdk-amd64

## Usage

```java
Wildcard wildcard = new Wildcard("test/**/build");

Assertions.assertTrue(wildcard.match("test/x/x/build"));
```

## License

This project is licensed under the [GNU General Public License v3.0](https://www.gnu.org/licenses/gpl-3.0).
