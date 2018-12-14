package net.shagie.spring.drools;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RulesStartup {
    private RulesService rulesService;

    public RulesStartup(@Qualifier("KieService") RulesService service) {
        this.rulesService = service;
    }

    @PostConstruct
    private void init() {
        this.rulesService.initializeRules();
        System.out.println("RulesStartupService initialize rules");
    }
}
