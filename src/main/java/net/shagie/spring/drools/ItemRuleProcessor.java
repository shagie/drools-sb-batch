package net.shagie.spring.drools;

import net.shagie.spring.drools.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;

public class ItemRuleProcessor implements ItemProcessor<Item, Item> {

    private static final Logger LOG = LoggerFactory.getLogger(ItemRuleProcessor.class);

    private RulesService service;

    public ItemRuleProcessor(@Qualifier("KieService") RulesService service) {
        this.service = service;
    }

    @Override
    public Item process(final Item item) {
        final Item transformedItem = new Item(item);

        service.fireRules(transformedItem);

        LOG.info("Converting (" + item + ") into (" + transformedItem + ")");

        return transformedItem;
    }

}