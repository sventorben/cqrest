package de.sven_torben.cqrest.annotations.methods;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.NameBinding;

@Documented 
@Target({ElementType.METHOD, ElementType.TYPE})  
@Retention(RetentionPolicy.RUNTIME)  
@HttpMethod("QUERY")   
@NameBinding  
public @interface QUERY {
}