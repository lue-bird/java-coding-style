## `final` everywhere*

Basically everything is mutable and extensible by default

```java
// ↓ can be extend-ed
class A{
    // ↓ can be mutated
    Consumer<Stuff>
    take = stuff ->{
        // ↓ stuff can be mutated
        stuff = null;
        // ↓ can be mutated
        Stuff
        otherStuff = stuff;
        };
    
    // ↓ can be mutated
    static public
    String
    projectName = "hi";
    }
```

To remind others and future you, add `final` to
  - `class`es (à la composition > inheritance)
      - probably most non-`abstract` methods in an extendable `class`
  - all arguments, except in
    ```java
    static public final
    Function<Person, String>
    greet = person->
        "Hi, " + person.value
        ;
    static public
    String
    greet(Person person){return
        "Hi, " + person.value
        ;}
    ```
    where `->` or `return` signal an instant result value
  - all `static` variables
  - ...
