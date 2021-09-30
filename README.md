The Clock Class
Clock was added in Java 8 and provides access to an instant in time using the best available system clock, and to be used as a time provider which can be effectively stubbed for testing purposes.

The current date and time depend on the time zone and, for globalized applications, a time provider is necessary to ensure that the date and time are created with the correct time zone.

This class helps us to test that our code changes work with different time zones or â when using a fixed clock â  that time doesn't affect our code.

The Clock class is abstract, so we cannot create an instance of it. The following factory methods can be used:


freestar
offset(Clock, Duration) â returns a clock that is offset by the specified Duration. The main use case for this is to simulate running in the future or the past
systemUTC() â  returns a clock representing the UTC time zone
fixed(Instant, ZoneId) â  always returns the same Instant. The leading use case for this is in testing, where the fixed clock ensures that tests are not dependent on the current clock
We're going to look into most of the methods available in the Clock class.

2.1. instant()
This method returns an instant representing the current instant defined by the clock:

Clock clock = Clock.systemDefaultZone();
Instant instant = clock.instant();
System.out.println(instant);
will produce:

2018-04-07T03:59:35.555Z
2.2. systemUTC()
This method returns a Clock object representing the current instant in the UTC zone:

Clock clock = Clock.systemUTC();
System.out.println("UTC time :: " + clock.instant());
will produce:


freestar
UTC time :: 2018-04-04T17:40:12.353Z
2.3. system()
This static method returns the Clock object for the timezone identified by the given time zone id:

Clock clock = Clock.system(ZoneId.of("Asia/Calcutta"));
System.out.println(clock.instant());
will produce:

2018-04-04T18:00:31.376Z
2.4. systemDefaultZone()
This static method returns a Clock object representing the current instant and using the default time zone of the system it's running on:

Clock clock = Clock.systemDefaultZone();
System.out.println(clock);
The above lines produce the following result (assuming our default time zone is âAsia/Calcuttaâ):

SystemClock[Asia/Calcutta]
We can achieve the same behavior by passing ZoneId.systemDefault():


freestar
Clock clock = Clock.system(ZoneId.systemDefault());
2.5. millis()
This method returns the current instant of the clock in milliseconds. It's provided to allow the use of the clock in high-performance use cases where the creation of an object would be unacceptable. This method can be used in places where we'd otherwise have used System.currentTimeInMillis():

Clock clock = Clock.systemDefaultZone();
System.out.println(clock.millis());
will produce:

1523104441258
2.6. offset()
This static method returns an instant from the specified base clock with the specified duration added.

If the duration is negative, then the resulting clock instant will be earlier than the given base clock.

Using offset, we can get instants in the past and future of the given base clock. If we pass a zero duration then we'll get the same clock as given base clock:

Clock baseClock = Clock.systemDefaultZone();

// result clock will be later than baseClock
Clock clock = Clock.offset(baseClock, Duration.ofHours(72));
System.out.println(clock5.instant());

// result clock will be same as baseClock                           
clock = Clock.offset(baseClock, Duration.ZERO);
System.out.println(clock.instant());

// result clock will be earlier than baseClock            
clock = Clock.offset(baseClock, Duration.ofHours(-72));
System.out.println(clock.instant());
will produce:

2018-04-10T13:24:07.347Z
2018-04-07T13:24:07.348Z
2018-04-04T13:24:07.348Z
2.7. tick()
This static method returns instants from the specified clock rounded to the nearest occurrence of the specified duration. The specified clock duration must be positive:

Clock clockDefaultZone = Clock.systemDefaultZone();
Clock clocktick = Clock.tick(clockDefaultZone, Duration.ofSeconds(30));

System.out.println("Clock Default Zone: " + clockDefaultZone.instant());
System.out.println("Clock tick: " + clocktick.instant());