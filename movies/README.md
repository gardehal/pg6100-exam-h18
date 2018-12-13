
# Movies Api


[ Reflection ](#Reflection)

[ References ](#References)

<a name="Reflection"></a>
## Reflection

A very awkward exam period, where every 2 weeks there was a different 
assignment, which due to the time limit, was prioritized, which 
left little time for this exam. Especially annoying is that this 
exam which has by far the longest project time given of all the exams, 
was pushed down on the list until the last few weeks. Then the internet 
was cut by some roadwork and was down for a few days, which made development 
harder. 

When the internet came back and all other exams were out of the way I could 
devote my attention to this project. Shorty after I started working on the project
I encountered a heap of problems. The first problem is that I had an outdated 
version of CrudRepository, which was a simple enough problem, though I had some issues
getting IntelliJ to understand I wanted a different version.
Then, when trying to run the project, I get the stacktrace below.
Searching the internet for various parts, reading though and trying to trace 
the problem down, updating and plying with various versions, and even removing 
code segment by segment did nothing. It was either the trace below or a Spring error
saying it could not find the parts of the code I removed.

```
DEBUG StatusLogger org.slf4j.helpers.Log4jLoggerFactory is not on classpath. Good!

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.0.6.RELEASE)

2018-12-10 21:36:08.100 ERROR 2900 --- [           main] o.s.b.f.s.DefaultListableBeanFactory     : Destroy method on bean with name 'org.springframework.boot.autoconfigure.internalCachingMetadataReaderFactory' threw an exception

java.lang.IllegalStateException: ApplicationEventMulticaster not initialized - call 'refresh' before multicasting events via the context: org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext@47542153: startup date [Mon Dec 10 21:36:08 CET 2018]; root of context hierarchy
	at org.springframework.context.support.AbstractApplicationContext.getApplicationEventMulticaster(AbstractApplicationContext.java:414) [spring-context-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.context.support.ApplicationListenerDetector.postProcessBeforeDestruction(ApplicationListenerDetector.java:97) ~[spring-context-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.beans.factory.support.DisposableBeanAdapter.destroy(DisposableBeanAdapter.java:253) ~[spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.destroyBean(DefaultSingletonBeanRegistry.java:578) [spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.destroySingleton(DefaultSingletonBeanRegistry.java:554) [spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.destroySingleton(DefaultListableBeanFactory.java:961) [spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.destroySingletons(DefaultSingletonBeanRegistry.java:523) [spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.destroySingletons(DefaultListableBeanFactory.java:968) [spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.context.support.AbstractApplicationContext.destroyBeans(AbstractApplicationContext.java:1030) [spring-context-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:556) [spring-context-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:140) [spring-boot-2.0.6.RELEASE.jar:2.0.6.RELEASE]
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:754) [spring-boot-2.0.6.RELEASE.jar:2.0.6.RELEASE]
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:386) [spring-boot-2.0.6.RELEASE.jar:2.0.6.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:307) [spring-boot-2.0.6.RELEASE.jar:2.0.6.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1242) [spring-boot-2.0.6.RELEASE.jar:2.0.6.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1230) [spring-boot-2.0.6.RELEASE.jar:2.0.6.RELEASE]
	at org.pg6100.movies.MovieApplicationKt.main(MovieApplication.kt:45) [classes/:na]

2018-12-10 21:36:08.104 DEBUG 2900 --- [           main] .c.l.ClasspathLoggingApplicationListener : Application failed to start with classpath: ...
2018-12-10 21:36:08.119 ERROR 2900 --- [           main] o.s.boot.SpringApplication               : Application run failed

org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'org.springframework.context.annotation.internalConfigurationAnnotationProcessor': Initialization of bean failed; nested exception is java.lang.NullPointerException
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:564) ~[spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:483) ~[spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.beans.factory.support.AbstractBeanFactory$1.getObject(AbstractBeanFactory.java:306) ~[spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:230) ~[spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:302) ~[spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:202) ~[spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.context.support.PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(PostProcessorRegistrationDelegate.java:87) ~[spring-context-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.context.support.AbstractApplicationContext.invokeBeanFactoryPostProcessors(AbstractApplicationContext.java:687) ~[spring-context-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:525) ~[spring-context-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:140) ~[spring-boot-2.0.6.RELEASE.jar:2.0.6.RELEASE]
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:754) [spring-boot-2.0.6.RELEASE.jar:2.0.6.RELEASE]
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:386) [spring-boot-2.0.6.RELEASE.jar:2.0.6.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:307) [spring-boot-2.0.6.RELEASE.jar:2.0.6.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1242) [spring-boot-2.0.6.RELEASE.jar:2.0.6.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1230) [spring-boot-2.0.6.RELEASE.jar:2.0.6.RELEASE]
	at org.pg6100.movies.MovieApplicationKt.main(MovieApplication.kt:45) [classes/:na]
Caused by: java.lang.NullPointerException: null
	at org.springframework.core.BridgeMethodResolver.findBridgedMethod(BridgeMethodResolver.java:60) ~[spring-core-5.0.10.RELEASE.jar:5.0.10.RELEASE]
	at org.springframework.beans.GenericTypeAwarePropertyDescriptor.<init>(GenericTypeAwarePropertyDescriptor.java:70) ~[spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.beans.CachedIntrospectionResults.buildGenericTypeAwarePropertyDescriptor(CachedIntrospectionResults.java:366) ~[spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.beans.CachedIntrospectionResults.<init>(CachedIntrospectionResults.java:302) ~[spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.beans.CachedIntrospectionResults.forClass(CachedIntrospectionResults.java:189) ~[spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.beans.BeanWrapperImpl.getCachedIntrospectionResults(BeanWrapperImpl.java:173) ~[spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.beans.BeanWrapperImpl.getLocalPropertyHandler(BeanWrapperImpl.java:226) ~[spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.beans.BeanWrapperImpl.getLocalPropertyHandler(BeanWrapperImpl.java:63) ~[spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.beans.AbstractNestablePropertyAccessor.getPropertyHandler(AbstractNestablePropertyAccessor.java:739) ~[spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.beans.AbstractNestablePropertyAccessor.isWritableProperty(AbstractNestablePropertyAccessor.java:571) ~[spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyPropertyValues(AbstractAutowireCapableBeanFactory.java:1533) ~[spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.populateBean(AbstractAutowireCapableBeanFactory.java:1276) ~[spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:553) ~[spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	... 15 common frames omitted


Process finished with exit code 1
```

With barely any progress since the 7th of december, it being the 12th as I write this, I see no way to finish
my own module before the deadline. I've talked to the other group members and they seem to struggle as well.
I will therefore write about what I believe is wrong, what I did to try to fix it, what I want to do or could do
(but don't necessarily have time for), and what the development would be further down the line.

#### What went wrong

I've already said I'm not entirely sure what went wrong, which is why I specifically said "believe" in the paragraph above.
Looking at the stack, it seems that there are 2 errors, one of them causing two exceptions. The first is a
IllegalStateException, caused by something in the Spring framework, which is called by the MovieApplication. 
This could mean several things, either that the project does not pick up an external resource (like a dependency),
or it could be a missing setting somewhere.
I did model the project after the modules found in [ references ](#References) with the same resources and dependencies.

The second error seems to be caused by a Nullpointer which later turn into an error creating a bean.
I would assume this is due to inexperience with Kotlin, or possibly the DTOs fields.

#### What was done to fix it

When encountering the problems the first time the first thing I did was to read the stacktrace which didn't make
much sense initially. Then I took to the internet for heal, searching for a mix of keywords and lines from the stacktrace.
I did find a few people with similar problems, but no one with a similar system. After trying all suggested
solutions I went though the documentation of the third party dependencies. 
Earlier in the project I found that one of my dependencies was outdated and cached so it wouldn't update.
Deleting the dependency from my computer and re-downloading the new version worked, so I tried this with all dependencies.
Unfortunately this didn't work for the problems in the stacktrace.
Combining this with trying different versions of the dependencies, invalidating chances, restarting IntelliJ, and
refreshing and updating Maven did not do anything.

It even went so far I tried ripping out all the code except the minimum required code to run the project, adding code
function by function (when I could) to see where the issue was. This did not give any more insight either, 
with all the problems heaping on when adding Spring components back.

#### What could I do

I have not tried rebuilding the project from scratch, as in deleting everything in my module, even the entire project, 
then adding code piece by piece. I did not consult the other group members about my problems, though we compared errors 
to see if there was a common element. Since the whole group is struggling with errors it was almost pointless to unload 
more problems.

#### Future actions and further development

Keep in mind this is a high level view of what would happen after the errors above were resolved. There would be error and I
don't take this into account.

Assuming I managed to fix the errors, next up would be to either make a HTML page and test the API manually correcting 
any mistakes I encountered, or make unit tests. I would prioritize HTML since it's specified that it's part of the 
assignment, while tests would be extra credits.
We discussed how we would do HTML in the group and came to the conclusion that it would be best to do the same thing as
was shown class, mostly in PG6300, where dynamic HTML was rendered with a method. By doing this we could draw on experience
from PG6300 courses/repository and exam, in addition to PG6100. I do believe the code would have to be altered a bit
to make it work.

Working out the issues and getting my API up and running, I would've coordinated with the rest of the group, discussing
what endpoints we used, standardizing and linking up the APIs to each other. Then we would have to repeat the process 
for the shared API and make the HTML for it and the homepage. Finally I would add tests, and other extra features.

<a name="References"></a>
## References

Used code from following files in PG6100 repository:

org.tsdes.advanced.rest.newsrestv2
- api - NewsRestApi (MovieRestApi)
- dto - NewsConverter, NewsDto (MovieConverter, MovieDto)
- NewsRestApplication (MovieRepository)
    
org.tsdes.advanced.examplenews
- NewsRepository (MovieRepository)
- MovieEntity (NewsEntity)

org.tsdes.advanced.frontend.spa-rest.spa-rest-frontend
- frontend (public)