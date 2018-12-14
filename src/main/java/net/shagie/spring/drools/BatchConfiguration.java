package net.shagie.spring.drools;

import net.shagie.spring.drools.model.Item;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.Arrays;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("KieService")
    public RulesService service;

    @Bean
    public FlatFileItemReader<Item> reader() {
        return new FlatFileItemReaderBuilder<Item>()
                .name("itemReader")
                .resource(new ClassPathResource("/data/items.csv"))
                .delimited()
                .names(new String[]{"id", "name", "cost"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Item>() {{
                    setTargetType(Item.class);
                }})
                .build();
    }

    @Bean
    public ItemNameProcessor nameProcessor() {
        return new ItemNameProcessor();
    }

    @Bean
    public ItemRuleProcessor ruleProcessor() {
        return new ItemRuleProcessor(service);
    }

    @Bean
    public CompositeItemProcessor<Item, Item> compositeProcessor() {
        CompositeItemProcessor<Item, Item> rv = new CompositeItemProcessor<>();
        rv.setDelegates(Arrays.asList(
                nameProcessor(),
                ruleProcessor()
        ));
        return rv;
    }

    // Job Steps

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Item, Item> chunk(10)
                .reader(reader())
                .processor(compositeProcessor())
                .build();
    }

    @Bean
    public Job importUserJob(Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }
}
