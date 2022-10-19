package com.choice.university.config;

import static com.choice.university.constants.UniversityConstants.UNIVERSITY_NAMESPACE_URI;

import java.util.List;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

  @Bean
  public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
      ApplicationContext applicationContext) {
    var servlet = new MessageDispatcherServlet();
    servlet.setApplicationContext(applicationContext);
    servlet.setTransformWsdlLocations(true);
    return new ServletRegistrationBean<>(servlet, "/ws/*");
  }

  @Bean
  public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema schema) {
    var wsdl11Definition = new DefaultWsdl11Definition();
    wsdl11Definition.setPortTypeName("HotelsPort");
    wsdl11Definition.setLocationUri("/ws");
    wsdl11Definition.setTargetNamespace(UNIVERSITY_NAMESPACE_URI);
    wsdl11Definition.setSchema(schema);
    return wsdl11Definition;
  }

  @Bean
  public XsdSchema hotelsSchema() {
    return new SimpleXsdSchema(new ClassPathResource("schemas/hotels.xsd"));
  }

  @Override
  public void addInterceptors(List<EndpointInterceptor> interceptors) {
    var validatingInterceptor = new PayloadValidatingInterceptor();
    validatingInterceptor.setValidateRequest(true);
    validatingInterceptor.setXsdSchema(hotelsSchema());
    interceptors.add(validatingInterceptor);
  }
}