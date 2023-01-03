package com.codede.spring;

import com.codede.spring.entity.Login;
import com.codede.spring.repo.LoginRepo;
import com.codede.spring.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    LoginService loginService;

    @Autowired
    LoginRepo loginRepo;

    private final JwtAuthenticationFilter authenticationFilter;
    //private final AuthenticationFilter

//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() {
//        return new JwtAuthenticationFilter();
//    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // Get AuthenticationManager bean
        return super.authenticationManagerBean();
    }

    //    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        Login login = loginRepo.findById(1);
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails user = User.withUsername(login.getUserName())
                .password(login.getPassword())
                .roles("USER_ROLE")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth)
//            throws Exception {
//        auth.userDetailsService(loginService)
//                .passwordEncoder(());
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .cors() // Ngăn chặn request từ một domain khác
//                .and()
//                .authorizeRequests()
//                .antMatchers("/api/login").permitAll() // Cho phép tất cả mọi người truy cập vào địa chỉ này
//                .anyRequest().authenticated(); // Tất cả các request khác đều cần phải xác thực mới được truy cập
        http
                .csrf()
                .disable()
                .authorizeRequests()

                .antMatchers("/api/login").permitAll()
                .anyRequest().authenticated();

        // Thêm một lớp Filter kiểm tra jwt
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
