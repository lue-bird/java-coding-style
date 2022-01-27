package util;
import java.util.function.*;

/**
 * from(x).ᐳ(f).eval() ist exakt das selbe wie f(x)/f.apply(x).
 * 
 * Durch diesen Stil zu Programmieren werden
 * externe static Funktionen, die Werte transformieren, auf eine Ebene mit Methoden gestellt
 * 
 * vorher:
 * sanitize = text->
 *     StringUtil.toInt(StringUtil.trim(text))
 *     ;
 * 
 * nachher:
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
    // zum Beispiel
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
    
    // Verändert den aktuellen Wert, ohne einen neuen Wert zurückzugeben
    // void set(...) Funktionen sind ein klassisches Beispiel
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
