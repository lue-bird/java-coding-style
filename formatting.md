A formatter allows you to

- write code efficiently
- enforce consistency
- be able to adapt a better formatting style any time

The following style _doesn't have a formatter_...

Keep this in mind when deciding whether the _extra readability_ is worth the price.

-------

## The important part always comes at the end of the line

```java
public final class <Example> {
    public static void <main(String[] args)> {
        System.out.<println("Hello world")>;
    }
}
```

An extreme example just for fun:

```java
static <T, U, R, E extends Throwable> BiFunction<T, U, R> aBiFunctionThatUnsafelyThrowsUnchecked(final BiFunctionWithThrowable<T, U, R, E> bifunctionwiththrowable) throws E {
        return bifunctionwiththrowable.thatUnsafelyThrowsUnchecked();
    }
```

Putting the name to the beginning of the line already helps readability a lot:

```java
public final class
Example{
    public static void
    main(
        final String[]
        arguments
        ){
        System.out.
            println("Hello world")
            ;
        }
    }
```
Personally, I use

```java
static > @Override|@FunctionalInterface > visibility > final|sealed
type|class|interface|abstract class|enum|module
name
    extends
    implements
    permits
```

```java
public final
class
Example{
    public static
    void
    main(
        final String[]
        arguments
        ){
        System.out.
            println("Hello world")
            ;
        }
    }
```

with an exception for
- local variables, where the name can be on the same line for brevity
- `final` local variables, where `final` can be on the same line for brevity


## all-caps

```java
enum Fruit { BANANA }
static final Fruit YELLOW_FRUIT = BANANA;
```

This is making constants unpleasant and slow to read

Somehow this style is also saying: constants will be rare.

```java
enum
Fruit {
    banana
    }

static final
Fruit
yellowFruit = banana;
```

## 1-letter type variable names

It's almost never a good idea to use single letter variable name for a big scope.

Standard convention is even worse, though:

```java
<T, U, R> R map2(Function<T, U, R> ..., ...<T> ..., ...<U> ...)
```

Type names have completely lost a connection to the actual type meaning. It's like writing:

```java
<$0, $1, $2> $2 map2(Function<$0, $1, $2> ..., ...<$0> ..., ...<$1> ...)
```

If you start naming these:

```java
<AIn, BIn, Out> Out
map2(Function<AIn, BIn, Out> ..., ...<AIn> ..., ...<BIn> ...)
```

Which makes it harder to know whether some type is a variable or not. You might also run into name clashes with existing types.

A simple solution:

```java
<aIn, aIn, out> out
map2(Function<aIn, aIn, out> ..., ...<aIn> ..., ...<bIn> ...)
```

## other personal preferences

```java
Structure{

    members;
    function(
        arguments
        )
    }
```
Closing brackets are always indented as much as the members.
