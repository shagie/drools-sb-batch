package net.shagie.spring.drools;

import net.shagie.spring.drools.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

public class ItemNameProcessor implements ItemProcessor<Item, Item> {

    private static final Logger log = LoggerFactory.getLogger(ItemNameProcessor.class);

    @Override
    public Item process(final Item item) {
        final Item transformedItem = new Item(item);
        transformedItem.setName(transformedItem.getName().toUpperCase());

        log.info("Converting (" + item + ") into (" + transformedItem + ")");

        return transformedItem;
    }

}