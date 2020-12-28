package ru.vdv.tregulov.spring.security.configuration;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

//если у нас не будет прописан этот класс, то не будет запрашиваться форма для аутентификации
//где мы будем вводить username и password, т.е. SpringSecurity работать не будет
public class MySecurityInitializer extends AbstractSecurityWebApplicationInitializer {
}
