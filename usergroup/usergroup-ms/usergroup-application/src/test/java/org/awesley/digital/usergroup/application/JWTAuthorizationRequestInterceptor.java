package org.awesley.digital.usergroup.application;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.awesley.infra.security.JwtTokenUtil;
import org.awesley.infra.security.model.Authority;
import org.awesley.infra.security.model.JwtAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.test.context.support.WithMockUser;

public class JWTAuthorizationRequestInterceptor implements ClientHttpRequestInterceptor {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	public JWTAuthorizationRequestInterceptor() {
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body,
			ClientHttpRequestExecution execution) throws IOException {
		
		WithMockUser mockUserAnnotation = annotationFromStackTrace(WithMockUser.class, 20);
		String username = mockUserAnnotation.username();
		String[] roles = mockUserAnnotation.roles();
		
		String token = generateJWTToken(username, roles);
		request.getHeaders().add("Authorization", "Bearer " + token);
		return execution.execute(request, body);
	}

	private String generateJWTToken(String username, String[] roles) {
		List<? extends Authority> authorities = 
				Arrays.stream(roles).map(r -> new JwtAuthority(r)).collect(Collectors.toList());
		
		String token = jwtTokenUtil.generateToken(username, authorities);
		
		return token;
	}

	private <T extends Annotation> T annotationFromStackTrace(Class<T> annotation, int maxDepth) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		if (maxDepth > stackTrace.length) {
			maxDepth = stackTrace.length;
		}
		
		try {
			for (int i = 1; i < maxDepth; i++) {
				StackTraceElement ste = stackTrace[i];
					Method stackFrameMethod = getStackFrameMethod(ste);
					T annotationInstance = stackFrameMethod.getAnnotation(annotation);
					if (null != annotationInstance) {
						return annotationInstance;
					}
			}
		} catch (ClassNotFoundException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		
		throw new RuntimeException("JWTAuthorizationRequestInterceptor: expected @WithMockUser annotation on a method on the call stack");
	}


	private Method getStackFrameMethod(StackTraceElement ste) throws NoSuchMethodException, ClassNotFoundException {
		Class<?> stackFrameClass = Class.forName(ste.getClassName());
		
		List<Method> methods = new ArrayList<Method>(Arrays.asList(stackFrameClass.getMethods()));
		methods.addAll(Arrays.asList(stackFrameClass.getDeclaredMethods()));
		
		Optional<Method> stackFrameMethod = 
				methods.stream().filter(m -> m.getName().equals(ste.getMethodName())).findFirst();
		
		if (stackFrameMethod.isPresent()) {
			return stackFrameMethod.get();
		}
		
		throw new NoSuchMethodException(ste.getMethodName());
	}
}
