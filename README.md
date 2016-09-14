# logfmt-java
A Java implementation of [logfmt](http://godoc.org/github.com/kr/logfmt), 
a Heroku logging format.

## Usage

```xml
<dependency>
  <groupId>com.heroku</groupId>
  <artifactId>logfmt</artifactId>
  <version>0.1</version>
</dependency>
```

```java
import com.heroku.logfmt.Logfmt;
...
Map<String, Object> parsed = Logfmt.parse("a=b thing=\"quoted thing\" justhename".toCharArray())
System.out.println(parsed.get("a"));
```

## Build

```sh
mvn clean install
```

## Benchmark
There's a crappy benchmark that can be run after building:

```sh
java -cp target/logfmt-0.2-SNAPSHOT.jar:target/test-classes/ com.heroku.logfmt.DumbBenchmark 1 "a=foo b=10ms c=cat E=\"123\" d foo= emp="
```

## TODO
* Reflection-based mapping

## LICENSE
The MIT License (MIT)

Copyright (C) 2014 Naaman Newbold.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
