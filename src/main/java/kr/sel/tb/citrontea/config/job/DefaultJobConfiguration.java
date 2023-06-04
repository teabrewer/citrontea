package kr.sel.tb.citrontea.config.job;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.sel.tb.citrontea.model.dto.PointDto;
import kr.sel.tb.citrontea.model.entity.UserPoint;
import kr.sel.tb.citrontea.util.NumberUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
@Slf4j
public class DefaultJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public JobRepository jobRepository() throws Exception {
        MapJobRepositoryFactoryBean factoryBean = new MapJobRepositoryFactoryBean();
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    @Bean
    public Job sampleJob(Step step1) {
        return jobBuilderFactory.get("sampleJob")
            .incrementer(new RunIdIncrementer())
            .start(step1)
            .build();
    }

    @Bean
    public Step step1(ItemReader<PointDto> reader, ItemProcessor<PointDto, UserPoint> processor,
            ItemWriter<UserPoint> writer) {
        log.info("[ STEP1 ] ***");
        return stepBuilderFactory.get("step1")
            .<PointDto, UserPoint>chunk(5)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }

    @Bean
    public ListItemReader<PointDto> reader() {
        log.info("[ reader ] ***");
        List<PointDto> items = IntStream.range(0, 30)
                .mapToObj(i -> new PointDto(i, 100, LocalDate.now().format(DateTimeFormatter.ISO_DATE),
                    NumberUtils.random(1, 10) * 1000))
                .collect(toList());
        return new ListItemReader<>(items);
    }

    @Bean
    public ItemProcessor<PointDto, UserPoint> processor(){
        return item -> {
            log.info("[ processor ] *** {}, {}, {}", item.getId(), item.getPointName(), item.getPoint());
            return UserPoint.builder()
                .id(item.getId())
                .pointName(item.getPointName())
                .userId(item.getUserId())
                .point(item.getPoint())
                .build();
        };
    }

    @Bean
    public ItemWriter<UserPoint> writer() {
        return chunk -> {
            log.info("[ writer ] *** chunk size : {}", chunk.size());
            for (UserPoint userPoint : chunk) {
                log.info("[ writer ] *** {} {} {}", userPoint.getId(), userPoint.getPointName(), userPoint.getPoint());
            }
        };
    }

}
