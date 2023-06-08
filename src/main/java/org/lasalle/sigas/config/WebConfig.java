package org.lasalle.sigas.config;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.lasalle.sigas.controller.ResponsavelController;
import org.lasalle.sigas.controller.converter.BolsaAnteriorRespostaConverter;
import org.lasalle.sigas.controller.converter.ComposicaoFamiliarConverter;
import org.lasalle.sigas.controller.converter.DetalheProcessoSeletivoConverter;
import org.lasalle.sigas.controller.converter.EscolaridadeConverter;
import org.lasalle.sigas.controller.converter.EstadoCivilConverter;
import org.lasalle.sigas.controller.converter.FuncaoConverter;
import org.lasalle.sigas.controller.converter.FuncionarioConverter;
import org.lasalle.sigas.controller.converter.GrupoConverter;
import org.lasalle.sigas.controller.converter.IrRespostaConverter;
import org.lasalle.sigas.controller.converter.MoradiaAreaConverter;
import org.lasalle.sigas.controller.converter.MoradiaCondicaoConverter;
import org.lasalle.sigas.controller.converter.MoradiaImovelConverter;
import org.lasalle.sigas.controller.converter.MoradiaRespostaConverter;
import org.lasalle.sigas.controller.converter.MoradiaTipoConverter;
import org.lasalle.sigas.controller.converter.MotivoAlteracaoConverter;
import org.lasalle.sigas.controller.converter.NacionalidadeConverter;
import org.lasalle.sigas.controller.converter.NivelConverter;
import org.lasalle.sigas.controller.converter.NumeroEditalConverter;
import org.lasalle.sigas.controller.converter.ParentescoConverter;
import org.lasalle.sigas.controller.converter.PercentualParecerConverter;
import org.lasalle.sigas.controller.converter.ProcessoSeletivoConverter;
import org.lasalle.sigas.controller.converter.SerieConverter;
import org.lasalle.sigas.controller.converter.SituacaoResponsavelConverter;
import org.lasalle.sigas.controller.converter.TipoDespesaConverter;
import org.lasalle.sigas.controller.converter.TipoDocumentoComumConverter;
import org.lasalle.sigas.controller.converter.TipoEditalConverter;
import org.lasalle.sigas.controller.converter.TipoInstituicaoConverter;
import org.lasalle.sigas.controller.converter.TipoProcessoSeletivoConverter;
import org.lasalle.sigas.controller.converter.TipoProgramaGovernamentalConverter;
import org.lasalle.sigas.controller.converter.TipoRendaConverter;
import org.lasalle.sigas.controller.converter.TipoTransporteConverter;
import org.lasalle.sigas.controller.converter.TurnoConverter;
import org.lasalle.sigas.controller.converter.UnidadeConverter;
import org.lasalle.sigas.controller.converter.UsuarioConverter;
import org.springframework.beans.BeansException;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsViewResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
//import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.github.mxab.thymeleaf.extras.dataattribute.dialect.DataAttributeDialect;
import com.google.common.cache.CacheBuilder;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
@ComponentScan(basePackageClasses = { ResponsavelController.class })
@EnableWebMvc
@EnableSpringDataWebSupport
@EnableCaching
@EnableAsync
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;

	}

	@Bean
	public ViewResolver jasperReportsViewResolver(DataSource datasource) {
		JasperReportsViewResolver resolver = new JasperReportsViewResolver();
		resolver.setPrefix("classpath:/relatorios/");
		resolver.setSuffix(".jasper");
		resolver.setViewNames("relatorio_*");
		resolver.setViewClass(JasperReportsMultiFormatView.class);
		resolver.setJdbcDataSource(datasource);
		resolver.setOrder(0);
		
		return resolver;
		
		
	}
	
	
	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setCharacterEncoding("UTF-8");
		resolver.setContentType("text/html; charset=UTF-8");
		resolver.setOrder(1);

		return resolver;

	}

	// esse método processa os arquivos html
	@Bean
	public TemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setEnableSpringELCompiler(true);
		engine.setTemplateResolver(templateResolver());

		engine.addDialect(new LayoutDialect());
		engine.addDialect(new SpringSecurityDialect());
		engine.addDialect(new DataAttributeDialect());

		return engine;

	}

	// define o tipo de template e onde se localizam
	private ITemplateResolver templateResolver() {

		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(applicationContext);
		resolver.setPrefix("classpath:/templates/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode(TemplateMode.HTML);
		resolver.setCharacterEncoding("UTF-8");

		return resolver;

	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}

//	@Bean
//	public FormattingConversionService formattingConversionService() {
//
//		DefaultFormattingConversionService conversion = new DefaultFormattingConversionService();
//
//		NumberStyleFormatter bigDecimalFormatter = new NumberStyleFormatter("#,##0.00");
//		conversion.addFormatterForFieldType(BigDecimal.class, bigDecimalFormatter);
//		
//		NumberStyleFormatter longFormatter = new NumberStyleFormatter("#,##0.00");
//		conversion.addFormatterForFieldType(Long.class, longFormatter);
//		
//		NumberStyleFormatter intFormatter = new NumberStyleFormatter("#,##0.00");
//		conversion.addFormatterForFieldType(Integer.class, intFormatter);
//		
//		DateTimeFormatterRegistrar dateTimeFormatter = new DateTimeFormatterRegistrar();
//		dateTimeFormatter.setDateFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//		dateTimeFormatter.registerFormatters(conversion);
//
//		return conversion;
//	}

	@Bean
	public LocaleResolver localResolver() {
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}
	
	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		conversionService.addConverter(new UnidadeConverter());
		conversionService.addConverter(new SerieConverter());
		conversionService.addConverter(new ProcessoSeletivoConverter());
		conversionService.addConverter(new GrupoConverter());
		conversionService.addConverter(new DetalheProcessoSeletivoConverter());
		conversionService.addConverter(new NivelConverter());
		conversionService.addConverter(new UsuarioConverter());
		conversionService.addConverter(new NacionalidadeConverter());
		conversionService.addConverter(new TipoInstituicaoConverter());
		conversionService.addConverter(new TipoTransporteConverter());
		conversionService.addConverter(new TurnoConverter());
		conversionService.addConverter(new TipoDespesaConverter());
		conversionService.addConverter(new TipoRendaConverter());
		conversionService.addConverter(new TipoProgramaGovernamentalConverter());
		conversionService.addConverter(new ParentescoConverter());
		conversionService.addConverter(new SituacaoResponsavelConverter());
		conversionService.addConverter(new EscolaridadeConverter());
		conversionService.addConverter(new EstadoCivilConverter());		
		conversionService.addConverter(new MoradiaRespostaConverter());
		conversionService.addConverter(new MoradiaAreaConverter());
		conversionService.addConverter(new MoradiaTipoConverter());
		conversionService.addConverter(new MoradiaCondicaoConverter());
		conversionService.addConverter(new MoradiaImovelConverter());
		conversionService.addConverter(new IrRespostaConverter());
		conversionService.addConverter(new TipoProcessoSeletivoConverter());
		conversionService.addConverter(new ComposicaoFamiliarConverter());
		conversionService.addConverter(new TipoDocumentoComumConverter());
		conversionService.addConverter(new FuncionarioConverter());
		conversionService.addConverter(new FuncaoConverter());
		conversionService.addConverter(new PercentualParecerConverter());
		conversionService.addConverter(new TipoEditalConverter());
		conversionService.addConverter(new NumeroEditalConverter());
		conversionService.addConverter(new MotivoAlteracaoConverter());
		conversionService.addConverter(new BolsaAnteriorRespostaConverter());
		
		
		
		
		NumberStyleFormatter bigDecimalFormatter = new NumberStyleFormatter("#,##0.00");
		conversionService.addFormatterForFieldType(BigDecimal.class, bigDecimalFormatter);
		
		NumberStyleFormatter longFormatter = new NumberStyleFormatter("#,##0.00");
		conversionService.addFormatterForFieldType(Long.class, longFormatter);
		
		NumberStyleFormatter intFormatter = new NumberStyleFormatter("#,##0");
		conversionService.addFormatterForFieldType(Integer.class, intFormatter);
		
		DateTimeFormatterRegistrar dateTimeFormatter = new DateTimeFormatterRegistrar();
		dateTimeFormatter.setDateFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		dateTimeFormatter.registerFormatters(conversionService);

		
		
		
		return conversionService;
	}
	
	@Bean
	public CacheManager cacheManager() {
		
		CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder()
				.maximumSize(10)
				.expireAfterAccess(24, TimeUnit.HOURS);
		
		GuavaCacheManager cacheManager = new GuavaCacheManager();
		cacheManager.setCacheBuilder(cacheBuilder);
		
		return cacheManager;
	}
	

}
