## `static` functions

classes have a public 0-argument constructor by default

```java
class
A{
    }

// ↓ possible
new A();
```

Constructors are
1. possibly not descriptive
2. often "misused" – taking many setting arguments in different orders and numbers

```java
class
Row{
    public
    Row(float spacing, Element... elements){
        ...
        }
    public
    Row(float spacing, List<Element> elements){
        ...
        }
    public
    Row(Element... elements){
        this(0, elements)
        }
    public
    Row(List<Element> elements){
        this(0, elements);
        }
    ...
}
new Row(
    20,
    label,
    textInput,
    button
    );
```

Both can be avoided with `static` factories in combination with `static` setting adjusters which return the reference:

```java
final class
Row{
    private
    Row(List<Element> elements){
        ...
        }
    
    static public
    Function<List<Element>, Row>
    row(){return elements->
        new Row(elements)
        ;}
    static public
    Function<Element[], Row>
    row(){return elements->
        new Row(elements)
        ;}
    
    static public
    UnaryOperator<Row>
    spaced(float spacing){
        ...
        }
    ...
}
from(
    label,
    textInput,
    button
    ).
    ᐳ(row()).
    ᐳ(Row.spaced(20)).eval()
    ;
```

Where do `from`, `ᐳ` and `eval` come from?

See the "`Pipable`" interface in the example code.
