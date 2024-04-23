package com.github.yyeerai.hybridserverapi.common.javascriptparse.script;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import java.io.Reader;

/**
 * Javascript引擎类
 *
 * @author Looly
 */
public class JavaScriptEngine extends FullSupportScriptEngine {

    public JavaScriptEngine() {
        super(ScriptUtil.createJsEngine());
    }

    /**
     * 引擎实例
     *
     * @return 引擎实例
     */
    public static JavaScriptEngine instance() {
        return new JavaScriptEngine();
    }

    //----------------------------------------------------------------------------------------------- Invocable
    @Override
    public Object invokeMethod(Object thiz, String name, Object... args) throws ScriptException, NoSuchMethodException {
        return super.invokeMethod(thiz, name, args);
    }

    @Override
    public Object invokeFunction(String name, Object... args) throws ScriptException, NoSuchMethodException {
        return super.invokeFunction(name, args);
    }

    @Override
    public <T> T getInterface(Class<T> clasz) {
        return super.getInterface(clasz);
    }

    @Override
    public <T> T getInterface(Object thiz, Class<T> clasz) {
        return super.getInterface(thiz, clasz);
    }

    //----------------------------------------------------------------------------------------------- Compilable
    @Override
    public CompiledScript compile(String script) throws ScriptException {
        return super.compile(script);
    }

    @Override
    public CompiledScript compile(Reader script) throws ScriptException {
        return super.compile(script);
    }

    //----------------------------------------------------------------------------------------------- ScriptEngine
    @Override
    public Object eval(String script, ScriptContext context) throws ScriptException {
        return super.eval(script, context);
    }

    @Override
    public Object eval(Reader reader, ScriptContext context) throws ScriptException {
        return super.eval(reader, context);
    }

    @Override
    public Object eval(String script) throws ScriptException {
        return super.eval(script);
    }

    @Override
    public Object eval(Reader reader) throws ScriptException {
        return super.eval(reader);
    }

    @Override
    public Object eval(String script, Bindings n) throws ScriptException {
        return super.eval(script, n);
    }

    @Override
    public Object eval(Reader reader, Bindings n) throws ScriptException {
        return super.eval(reader, n);
    }

    @Override
    public void put(String key, Object value) {
        super.put(key, value);
    }

    @Override
    public Object get(String key) {
        return super.get(key);
    }

    @Override
    public Bindings getBindings(int scope) {
        return super.getBindings(scope);
    }

    @Override
    public void setBindings(Bindings bindings, int scope) {
        super.setBindings(bindings, scope);
    }

    @Override
    public Bindings createBindings() {
        return super.createBindings();
    }

    @Override
    public ScriptContext getContext() {
        return super.getContext();
    }

    @Override
    public void setContext(ScriptContext context) {
        super.setContext(context);
    }

    @Override
    public ScriptEngineFactory getFactory() {
        return super.getFactory();
    }

}