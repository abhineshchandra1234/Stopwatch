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
- To use notification manager in Android, we need to create a `NotificationCompat.Builder` object or notification and set its properties like - title, text, icon and actions. Then we can use the `NotificationManager` system service to issue the notification.
- We are using `getSystemService()` method to obtain a reference to the NotificationManager system service and casting it to a NotificationManager object.
---
## Services
- We have the `StopwatchService` class extending the service class
- We have done dependency injection using annotation `AndroidEntryPoint`
- We have injected two objects `notificationManager` and `notificationBuilder`. `notificationManager` as the name implies will manage the notification, and will take care of the notification service. `notificationBuilder` as the name implies will provide a layout for the notification. we will inject both objects through `Inject` annotation as the fields.
- we have created a `StopwatchBinder` object named `binder` for inter-process communication, which will return a replica of StopwatchService running in a separate thread of the same service.
- We have created a `Duration` object named `duration`. It will tell the amount of time, one instant of time is away from another instant.
- We have created a `Timer` class object named `timer`. It allows the thread to execute tasks in the background, which can be executed once or at regular intervals.
- Then we have states for `seconds`, `minutes`, `hours` and `currentState`.
- we can also reset the values of the above states privately.
- `currentState` uses the StopwatchState enum class values
---
- We have first overridden `onBind` method, which returns the communication channel (IBinder) to the service. Multiple clients can connect to the service at once. however, the system calls `onBind` method to retrieve the IBinder only when the first client binds. The system then delivers the same IBinder to any additional clients that bind, without calling `onBind` again.
---
- We have overridden the `onStartCommand` method, It is called every time a client starts a service using startService(Intent intent). It is called multiple times and is used to start and communicate with the service. We should do things in these methods that are needed each time a client requests something from our service. Suppose we don't implement `onStartCommand`. In that case, we won't be able to get any information from the intent that the client passes to `onStartCommand` and our service will not be able to do any useful work.
- In `onStartCommand`, we are first fetching string values from intent
- If the string value is `started` of enum class `StopwatchState`, then we do the following. set the stop button. set the stop button will build the notification using notificationBuilder and notify the notificationManager. The stop button will be added to the notification. Start the foreground service, which will start the foreground service and display the notification. `startStopwatch` will start the timer from 0. `startStopwatch` also has a callback `onTick` which is passing hours, minutes and seconds values back to `updateNotification` method. `updateNotification` is rebuilding the notification with new values and notifying the `NotificationManager`.
---
## References
- [Android onCreate or onStartCommand for starting service](https://stackoverflow.com/questions/14182014/android-oncreate-or-onstartcommand-for-starting-service)
