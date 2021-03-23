package io.github.leassets.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import io.github.leassets.LeassetsServerApp;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = LeassetsServerApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
