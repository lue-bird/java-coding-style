Enums can be handled exhaustively

```java
enum
Character{
    Birt,
    Orangloo,
    Iquipy
    }

static
Function<String, Character>
characterToString= (final var character)->{
    switch(character){
        case Birt:return
            "birt"
        case Orangloo:return
            "orangloo"
        case Iquipy:return
            "iquipy"
        }
    };
```

[java's latest features][java's latest features] make it more convenient:

```java
static
Function<Character, String>
characterToString= character->
    switch(character){
        case Birt->
            "birt"
        case Orangloo->
            "orangloo"
        case Iquipy->
            "iquipy"
        }
    ;
```

Often, though, you'll need something more powerful than an enum:

```
Expression ← either
    Number double
    Operation char BinaryOperator<Double>
```

A way to represent such a type would be

```java
public final
class
Number
    implements Expression{
    
    public final
    double
    value;
    ...
    }

public final
class
Operation
    implements Expression, BinaryOperator<Double>{
    
    public final
    Expression
    left,
    right;
    ...
    }

public
interface
Expression{

    static public
    Function<Expression, Double>
    evaluate(){return (final var expression)->{
        if(expression instanceof Number)return
            ((Number)expression).value
            ;
        else if(expression instanceof Operation){
            final Operation
            operation= (Operation)expression;
            return
            operation.apply(
                evaluate(operation.left),
                evaluate(operation.right)
                )
            }
        else{
            throw new IllegalArgumentException("expression kind not handled, yet");
            }
        };}
    }
```

This way,

- the implementation of `evaluate` is in one place
- we are able to see data different to each `Expression` kind

but

- even if we handle all permitted child-types, `else` is required
- if the `sealed interface` `permits` new children, we only get feedback at runtime where we need to handle the extra cases

[java's latest features][java's latest features] make it more convenient and a bit safer:

```java
public sealed
interface
Expression
    permits Number, Operation{
    
    static public
    Function<Expression, Double>
    evaluate= (final var expression)->{
        if(expression instanceof Number number)return
            number.value
            ;
        else if(expression instanceof Operation operation)return
            operation.apply(
                evaluate(operation.left()),
                evaluate(operation.right())
                )
            ;
        else{
            throw new IllegalArgumentException("expression kind not handled, yet");
            }
        };
    
    static public
    record
    Number(
        double
        value
        )
        implements Expression{
        }

    static public
    record
    Operation(
        Expression
        left,
        Expression
        right,
        BinaryOperator<Double>
        operation
        )
        implements Expression, BinaryOperator<Double>{
        ...
        }
    }
```

There's patterns that makes this safe:

## inherited matching

```java
public sealed
interface
Expression
    permits Number, Operation{
    
    static public
    Function<Expression, Double>
    evaluate=
        where(
            (Number number)->
                number.value,
            (Operation operation)->
                operation.apply(
                    evaluate(operation.left()),
                    evaluate(operation.right())
                    )
            )
        ;
    
    static @FunctionalInterface private
    interface
    Matchable<result>{
        result
        where(
            Function<Number, result> isNumber,
            Function<Operation, result> isOperation
            );
        }
    
    static public
    <result> Function<Expression, result>
    where(
        Function<Number, result> isNumber,
        Function<Operation, result> isOperation
        ){return expression->
        expression.match().where(isNumber, isOperation)
        ;}

    protected
    <result> Matchable<result>
    match();
    
    static public
    record
    Number(
        double
        value
        )
        implements Expression{
        
        @Override protected
        Matchable
        match(){return (isNumber, _isOperation)->
            isNumber.apply(this)
            ;}
        }

    static public
    record
    Operation(
        Expression
        left,
        Expression
        right,
        BinaryOperator<Double>
        operation
        )
        implements Expression, BinaryOperator<Double>{
        
        @Override protected
        Matchable
        match(){return (_isNumber, isOperation)->
            isOperation.apply(this)
            ;}
        ...
        }
    }
```

That's tasty.

-------

Now a bit of fun: Imagine `enum` not having a construct for exhaustive matching

```java
public
enum
Character
    extends Matchable{
    
    Birt((IsBirt isBirt, IsIquipy isIquipy, IsOrangloo isOrangloo)->
        isBirt.apply(isBirt)
        ),
    Orangloo((IsBirt isBirt, IsIquipy isIquipy, IsOrangloo isOrangloo)->
        isOrangloo.apply(isOrangloo)
        ),
    Iquipy((IsBirt isBirt, IsIquipy isIquipy, IsOrangloo isOrangloo)->
        isIquipy.apply(isIquipy)
        );
    
    public @FunctionalInterface
    interface
    IsBirt<result>
        extends Function<IsBirt<result>, result>{
        }
    public @FunctionalInterface
    interface
    IsOrangloo<result>
        extends Function<IsBirt<result>, result>{
        }
    public @FunctionalInterface
    interface
    IsIquipy<result>
        extends Function<IsBirt<result>, result>{
        }
    
    static @FunctionalInterface private
    interface
    Matchable{
        <result> result
        where(
            IsBirt<result> isBirt,
            IsIquipy<result> isIquipy,
            IsOrangloo<result> isOrangloo
            );
        }
    
    static public
    <result> Function<Expression, result>
    where(
        IsBirt<result> isBirt,
        IsIquipy<result> isIquipy,
        IsOrangloo<result> isOrangloo
        ){return expression->
        expression.match().where(isBirt, isIquipy, isOrangloo)
        ;}

    private final
    Matchable
    match;

    Character(
        final Matchable
        match
        ){
        this.match= match;
        }
    }
```

[java's latest features]: https://docs.oracle.com/en/java/javase/17/language/java-language-changes.html#GUID-6459681C-6881-45D8-B0DB-395D1BD6DB9B
