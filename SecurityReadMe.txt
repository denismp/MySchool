File: src/main/resources/META-INF/spring/applicationContext-security.xml
Note: put intercept urls and authentication managers in this file.
	The following adds a default admin and user login
	<authentication-manager alias="authenticationManager">
	<!-- SHA-256 values can be produced using 'echo -n your_desired_password | sha256sum' (using normal *nix environments) -->
	<!-- 'pass123' converts to 9b8769a4a742959a2d0298c36fb70623f2dfacda8436237df08d8dfd5b37374c -->
		<authentication-provider>
			<password-encoder hash="sha-256" />
			<user-service>
				<user name="admin" password="9b8769a4a742959a2d0298c36fb70623f2dfacda8436237df08d8dfd5b37374c" authorities="ROLE_ADMIN" />
				<user name="user" password="9b8769a4a742959a2d0298c36fb70623f2dfacda8436237df08d8dfd5b37374c" authorities="ROLE_USER" />
			</user-service>
		</authentication-provider>
	</authentication-manager>

	The following are examples of authenticated protected urls
	<intercept-url pattern="/myschoolapp" access="isAuthenticated()"/>
	<intercept-url pattern="/app.html" access="isAuthenticated()"/>
	<intercept-url pattern="/app.js" access="isAuthenticated()"/>
	<intercept-url pattern="/app/**" access="isAuthenticated()"/>
	<intercept-url pattern="/subjects/json/**" access="isAuthenticated()" />

	The following are examples of role protected urls
	<intercept-url pattern="/artifacts" access="hasRole('ROLE_ADMIN')" />
	<intercept-url pattern="/artifacts/**" access="hasRole('ROLE_ADMIN')" />


File: src/main/webapp/WEB-INF/web.xml
Note: Add/Del following filters to Enable/Disable security
	<filter>
		<filter-name>springSecurityFilterChain</filter-name>
		<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
	</filter>

	<filter-mapping>
		<filter-name>springSecurityFilterChain</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>

File: src/main/webapp/WEB-INF/views/menu.jspx
Note: Add/Del sec:autorize elements
	<sec:authorize access="hasRole('ROLE_ADMIN')">
	<menu:category ...
	</menu:category>
	</sec:authorize>

	<sec:authorize access="isAuthenticated()">
	<menu:category ...
	</menu:category>
	</sec:authorize>

File: src/main/webapp/WEB-INF/i18n/messages.properties
Note: Add a message to the login page using the security_login_message property. For example the following displays user/pass
	security_login_message=You have tried to access a protected area of this application. Please login with admin/pass123 or user/pass123.

main page url: http://localhost:8080/MySchool
app  page url: http://localhost:8080/MySchool/myschoolapp
