package ru.vdv.tregulov.spring.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

import javax.sql.DataSource;

//этой аннотацией мы помечаем наш класс, как класс ответственный за секьюрити конфигурации
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;
    GrantedAuthority grantedAuthority;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//указываем в конфигурации, что информацию о наших юзерах содержит база данных, а информация
        //о подключении к этой БД находится в dataSource.
        //Используем параметр метода configure (auth) и в методе dataSource(прописываем
        // поле dataSource). Теперь спринг знает, что информацию о юзерах надо брать из БД
        auth.jdbcAuthentication().dataSource(dataSource);

//        UserBuilder userBuilder = User.withDefaultPasswordEncoder();
////имя метода inMemoryAuthentication говорит, что при процессе аутентификации надо сравнивать
//// введённые формы юзера и пароля с юзером и паролем, которые мы сейчас пропишем
//        auth.inMemoryAuthentication()
//                .withUser(userBuilder.username("zaur").password("zaur").roles("EMPLOYEE"))
//                .withUser(userBuilder.username("elena").password("elena").roles("HR"))
//                .withUser(userBuilder.username("ivan").password("ivan").roles("MANAGER", "HR")
//                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
            //здесь мы даём разрешение для конкретного URL конкретным ролям
            //hasAnyRole - означает, что страницу которая открывается по URL "/" будут видеть
            //все роли и прописываем эти роли
            .antMatchers("/").hasAnyRole("EMPLOYEE", "HR", "MANAGER")
            //hasRole(в скобках пишем роль, которой будет доступна инофрмация по URL "/hr_info")
            .antMatchers("/hr_info").hasRole("HR")
            //если есть несколько адресов URL, начинающихся одинаково, тогда после второго слэша
            //мы пишем ** это означает, что у юзера с такой ролью будет доступ на любой адрес
            //начинающийся на это название
            .antMatchers("/manager_info/**").hasRole("MANAGER")
            .and().formLogin().permitAll();
    }
}
