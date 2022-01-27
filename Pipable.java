import java.util.function.*;

/**
 * from(x).ᐳ(f).eval() ist exactly the same as f(x)/f.apply(x).
 * 
 * By programming in the style
 * Data ᐳ (external) static functions ᐳ other transformations
 * these transforming functions now become similar to normal methods
 * 
 * before:
 * sanitize = text->
 *     StringUtil.toInt(StringUtil.trim(text))
 *     ;
 * 
 * after:
 * sanitize = text->
 *     from(text).
 *         ᐳ(StringUtil.trim)
 *         ᐳ(StringUtil.toInt)
 */
public
interface
Pipable<value>
    extends Supplier<value>{
    
    static public
    <initialValue> Pipable<initialValue>
    from(initialValue initial){return ()->
        initial
        ;}
    static public
    <initialValue> Pipable<initialValue>
    from(Supplier<initialValue> supplyInitial){return ()->
        supplyInitial.get()
        ;}
    // for example
    // from(1.0, 2.0, 3.0) → Pipable<Double[]>
    static public
    <initialElement> Pipable<initialElement[]>
    from(initialElement... initialArray){return ()->
        initialArray
        ;}
    
    public default
    <returnValue> Pipable<returnValue>
    ᐳ(
        Function<?super value, returnValue>
        apply
        ){return ()->
        apply.apply(this.eval())
        ;}
    
    // changes the current value without returning a new value
    // void set(...) functions are a classical example
    public default
    Pipable<value>
    ᐳdo(
        Consumer<?super value>
        consoom
        ){return ()->{
        final value
        evaluated = this.eval();
        consoom.accept(evaluated);
        return
        evaluated
        ;};}
    
    public default
    value
    eval(){return
        this.get()
        ;}
    }
