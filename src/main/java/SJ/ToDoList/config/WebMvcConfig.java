package SJ.ToDoList.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
	public class WebMvcConfig implements WebMvcConfigurer{
		@Override
		public void addResourceHandlers(final ResourceHandlerRegistry registry) {
			registry.addResourceHandler("/**")
					.addResourceLocations("classpath:/templates/","classpath:/static/","classpath:/views/");
		}
		
}
