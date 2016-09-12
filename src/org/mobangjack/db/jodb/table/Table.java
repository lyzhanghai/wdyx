package org.mobangjack.db.jodb.table;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mobangjack.db.consts.mysql.Charset;
import org.mobangjack.db.consts.mysql.Collate;
import org.mobangjack.db.consts.mysql.Engine;


@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Table {
	String name() default "";
	String comment() default "";
	String engine() default Engine.InnoDB;
	int auto_increment() default 1;
	String charset() default Charset.utf8;
	String collate() default Collate.utf8_general_ci;
}
