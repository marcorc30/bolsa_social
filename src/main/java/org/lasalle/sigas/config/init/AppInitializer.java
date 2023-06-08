package org.lasalle.sigas.config.init;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.lasalle.sigas.config.JPAConfig;
import org.lasalle.sigas.config.MailConfig;
import org.lasalle.sigas.config.ModelMapperConfig;
import org.lasalle.sigas.config.SecurityConfig;
import org.lasalle.sigas.config.ServiceConfig;
import org.lasalle.sigas.config.WebConfig;
import org.modelmapper.ModelMapper;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{ SecurityConfig.class, JPAConfig.class, ServiceConfig.class, ModelMapperConfig.class};
//		return new Class<?>[]{ SecurityConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {WebConfig.class, MailConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		
		return new Filter[] {filter};
		
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}
	
}
