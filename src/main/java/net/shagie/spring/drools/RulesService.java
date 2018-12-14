package net.shagie.spring.drools;

import net.shagie.spring.drools.model.Item;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class RulesService {
    private KieContainer kContainer;

    @Bean("KieService")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public RulesService getRulesService() {
        return new RulesService();
    }

    void initializeRules() {
        KieServices ks = KieServices.Factory.get();
        KieFileSystem kieFileSystem = ks.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource("rules.drl"));
        KieBuilder kieBuilder = ks.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();
        kContainer = ks.newKieContainer(kieModule.getReleaseId());
    }

    int fireRules(Item item) {
        KieSession kieSession = kContainer.newKieSession();

        kieSession.insert(item);

        int rv = kieSession.fireAllRules();

        kieSession.dispose();
        return rv;
    }
}
