# TimeMeasureMent Java / Android 
Simple easy to use TimeMeasurement class for Java

### usage:
Init class
```java
TimeMesaure timeMesaure = TimeMesaure.getInstance();
```

Start Timemeasurement somewhere with a given TAG
```java
timeMesaure.mesureTimeStart("METHOD1");
```

Stop Timemeasurement somewhere with a given TAG
```java
> timeMesaure.mesureTimeStop("METHOD1");
```

Print output 
```java
timeMesaure.dropResultInLog();
```

Output:
```java
Measure for Tag METHOD1
      Times: 1, 0, 0, 7, 2, 8, 26, 7, 9, 7, 4, 5, 8, 7, 7, 2, 1, 3, 2, 3, 4, 2, 8, 3, 7, 8, 7, 6, 2, 5, 4, 2, 3, 3, 6, 2, 2, 64
      3, 4, 6, 7, 7, 11, 12, 8, 9, 6, 9
      Avarage: 6ms
      Median: 7ms
      Mean deviation: 6ms
      Span: 64ms
```
