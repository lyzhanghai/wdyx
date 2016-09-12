package org.mobangjack.db.jodb.table.column;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Column {
	String name() default "";
	String type();
	boolean nullAllowed() default true;
	String defaultVal() default "";
	boolean auto_increment() default false;
	String charset() default "";
	String collate() default "";
	boolean unique() default false;
	boolean primary() default false;
	String comment() default "";
	String encrypt() default "";
	String encryptKey() default "";
}
