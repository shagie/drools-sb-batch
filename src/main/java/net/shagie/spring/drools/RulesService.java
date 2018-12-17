package net.shagie.spring.drools;

import net.shagie.spring.drools.model.Item;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RulesService {
    private KieContainer kContainer;

    @PostConstruct
    void initializeRules() {
        KieServices ks = KieServices.Factory.get();
        KieFileSystem kieFileSystem = ks.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource("rules.drl"));
        KieBuilder kieBuilder = ks.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();
        kContainer = ks.newKieContainer(kieModule.getReleaseId());
    }

    void fireRules(Item item) {
        KieSession kieSession = kContainer.newKieSession();
        kieSession.insert(item);
        kieSession.fireAllRules();
        kieSession.dispose();
    }
}
