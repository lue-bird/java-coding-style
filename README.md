Java by default means

- mutable objects: `person.setName`
    - references to mutable objects
        - overriding `equals` and `hashCode`
- statements > expressions:
  ```java
  doX();
  doY();
  ```
- `RuntimeException`s en masse
- bad error messages
- mutable variables: `setName(`~~`final`~~` String name)`
- package public members:
    - ~~`private`~~` String name;`
    - (implicit) constructors
- extensible classes: ~~`final`~~` class Person;`
- no exhaustive type matching (only as a [java 17 preview feature](https://docs.oracle.com/en/java/javase/17/language/pattern-matching-switch-expressions-and-statements.html))
- eager evaluation
- unreadable formatting
    - actually useful info near the end of the line:
        ```java
        public final class <Example> {
            public static void <main(String[] args)> {
                System.out.<println("Hello world")>;
            }
        }
        ```
    - all-caps:
      ```java
      enum Fruit { BANANA }
      static final Fruit YELLOW_FRUIT = BANANA;
      ```
    - 1-letter type variable names:
      ```java
      <T, U, V> V
      map2(...<T> ..., ...<U> ...)
      ```
      
- verbosity
- gotchas:
  ```java
  'h' + 'e' + 'l' + 'l' + 'o' // == 532
  ```

... **it's easy to write bad code in java**.

After a year with [elm](https://elm-lang.org/), I disagree with nearly every basic design decision.

If you are required to use java – just like me, here are some tips to make the best of java:

- [union-type](union-type.md)
- [`final`-everywhere*](final-everywhere.md)
- [`static` functions](static-functions.md)
- [formatting](formatting.md)

Note: This list covers _only a few tips_.
