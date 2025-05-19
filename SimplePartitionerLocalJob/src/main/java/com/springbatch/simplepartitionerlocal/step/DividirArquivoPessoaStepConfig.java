package com.springbatch.simplepartitionerlocal.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.simplepartitionerlocal.dominio.Pessoa;

@Configuration
public class DividirArquivoPessoaStepConfig {
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step dividirArquivoPessoaStep(
			FlatFileItemReader<Pessoa> pessoaReader,
			MultiResourceItemWriter<Pessoa> pessoaWriter) {
		
		return stepBuilderFactory
				.get("dividirArquivoPessoaStep")
				.<Pessoa, Pessoa>chunk(2000)
				.reader(pessoaReader)
				.writer(pessoaWriter)
				.stream(pessoaWriter)
				.build();
		
	}
}
