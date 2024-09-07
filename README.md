# Stopwatch Application
## Features
- Users can start, stop, resume and cancel the stopwatch from the app or through the persistent notification
- The app will keep on running in the foreground through persistent notification, even if you close the app
---
## Screenshots
<p align="center">
  <img src = "https://raw.githubusercontent.com/abhineshchandra1234/Stopwatch/master/app/src/main/res/drawable/screenshots/start_stopwatch.png" height=300px/>
  <img src = "https://raw.githubusercontent.com/abhineshchandra1234/Stopwatch/master/app/src/main/res/drawable/screenshots/running_stopwatch.png" height=300px/>
<img src = "https://raw.githubusercontent.com/abhineshchandra1234/Stopwatch/master/app/src/main/res/drawable/screenshots/notification_stopwatch.png" height=300px/>
</p>

---
## Topics Covered
- MVVM
- Jetpack Compose
- Services
- Permissions
- Notifications
- Dependency Injection
- Animations
---
## MVVM architecture
- we will try to cover the MVVM architecture of this project at a high level, covering all important features
<img src = "https://raw.githubusercontent.com/abhineshchandra1234/Stopwatch/master/app/src/main/res/drawable/screenshots/project_structure.png" height=500px/>

---
## Dependency Injection
- we have a `NotificationModule` object which will provide notification objects to the project wherever required, using its methods, it is annotated with `@Module`
- `@InstallIn(ServiceComponent::class)` and `@ServiceScoped` , we have different components like service, activity & fragment. By default, all bidings in the dagger are unscoped, which means each time binding is requested, the dagger will create a new binding instance. Whereas in scoped binding will only be created once per instance of the component it scoped to, and all requests for that binding will share the same instance
- Service scope will be created when the service is created and destroyed when the service is destroyed. For the whole duration, a single instance of binding will be used.
- we have a method `provideNotificationBuilder` which is annotated with `@Provides` and `@ServiceScoped`. `@Provides` is used as it returns an object of `NotificationCompat.Builder` which provides the notification layout. The app can use and change its certain features like content text, actions like - stop and cancel and its content intent
- We have passed `ApplicationContext` to `provideNotificationBuilder` method. This context is used by `ServiceHelper` class which discovers and instantiates services for that context.
- We are using the method `provideNotificationManager` to provide the NotificationManager object, It is annotated with `@ServiceScoped` and `@Provides` and takes `ApplicationContext` as a parameter
- To use notification manager in Android, we need to create a `NotificationCompat.Builder` object or notification and set its properties like - title, text, icon and actions and then we can use the `NotificationManager` system service to issue the notification.
- We are using `getSystemService()` method to obtain a reference to the NotificationManager system service and casting it to a NotificationManager object.
---
