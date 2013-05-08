# logfmt-java
A Java implementation of [logfmt](http://godoc.org/github.com/kr/logfmt), 
a Heroku logging format.

## Usage
```java
import com.heroku.logfmt.*;
...
Map<String, Object> parsed = new Logfmt().parse("a=b thing=\"quoted thing\" justhename".getBytes())
System.out.println(parsed.get("a"));
```

## Building
`mvn clean install`

## TODO
* Reflection-based mapping
* Maven Central deployment
