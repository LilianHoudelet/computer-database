package com.excilys.cdb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "com.excilys.cdb.mapper", "com.excilys.cdb.validator" })
public class BindingConfig {

}
