package com.example;

import com.example.demospringbatch.listener.JobListener;
import com.example.demospringbatch.model.Person;
import com.example.demospringbatch.processor.PersonItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    //en caso q se requiera leer de un archivo
    //@Value("classpath:paises.csv")
    //private Resource resource;
    @Bean
    public FlatFileItemReader<Person> reader(){
        return new FlatFileItemReaderBuilder<Person>()
                .name("personItemReader")
                .resource(new ClassPathResource("sample-data.csv"))
                .delimited()
                .names(new String[]{"firstName", "lastName", "phone"}) //debe estar en el mismo orden de los atributos de person
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>(){{
                   setTargetType(Person.class);
                }})
                .build();

    }

    @Bean
    public PersonItemProcessor processor(){
        return new PersonItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Person> writer(DataSource dataSource){
        return new JdbcBatchItemWriterBuilder<Person>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO person (first_name, last_name,phone) VALUES(:firstName, :lastName, :phone)")
                .dataSource(dataSource)
                .build();
    }

    //con esto se indica listener q accion hacer en caso de que cambie el esttaus
    @Bean
    public Job importPersonJob(JobListener listener, Step step1){
        return jobBuilderFactory.get("importPersonJob")
                .incrementer(new RunIdIncrementer()) //se debe incrementar para q los jobs manejen un id unico
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Person> writer){
        return stepBuilderFactory.get("step1")
                .<Person, Person> chunk(10) //se procesa cada 10 registros
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }
}
