# Application Information

This feature is for show any information by application. There three main properties to use: Git, OS and Java. Then using each follow down.

Include `info` in your application properties for enable:

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health, info
  endpoint:
    info:
      enabled: true
```

Then is possible receive basic information when access endpoint `/actuator/info`.

Should receive similar to this:

```json
{
    "git": {
        "commit": {
            "id": "32b3440"
        }
    },
    "build": {
        "artifact": "actuator",
        "name": "actuator",
        "time": "2023-08-19T18:54:40.301Z",
        "version": "0.0.1-SNAPSHOT",
        "group": "com.poc"
    }
}
```

## Additional information

To enable extras information follow down.
### **Git info**

In your application properties add:

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health, info
  info:
    git:
      enabled: true
      mode: full
  endpoint:
    info:
      enabled: true
```

**Note:** The `mode` field can have two possible values: `full` or `simple`.

Then restart your application and access again the route `/actuator/info` receive response similar to this:

```json
{
    "git": {
        "build": {
            "version": "0.0.1-SNAPSHOT",
            "time": "2023-08-19T18:54:43Z"
        },
        "commit": {
            "id": {
                "full": "32b344024fbb521c26ef918fd53ae262766205cd",
                "abbrev": "32b3440"
            }
        }
    },
    "build": {
        "artifact": "actuator",
        "name": "actuator",
        "time": "2023-08-19T18:54:40.301Z",
        "version": "0.0.1-SNAPSHOT",
        "group": "com.poc"
    }
}
```

### OS (Operating System ) info

In your application properties add:

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health, info
  info:
    git:
      enabled: true
      mode: full
    os:
      enabled: true
```

Then restart your application and access again the route `/actuator/info` receive response similar to this:

```json
{
    "git": {
        "build": {
            "version": "0.0.1-SNAPSHOT",
            "time": "2023-08-19T18:54:43Z"
        },
        "commit": {
            "id": {
                "full": "32b344024fbb521c26ef918fd53ae262766205cd",
                "abbrev": "32b3440"
            }
        }
    },
    "build": {
        "artifact": "actuator",
        "name": "actuator",
        "time": "2023-08-19T18:54:40.301Z",
        "version": "0.0.1-SNAPSHOT",
        "group": "com.poc"
    },
    "os": {
        "name": "Windows 11",
        "version": "10.0",
        "arch": "amd64"
    }
}
```

### Java info

In your application properties add:

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health, info
  info:
    git:
      enabled: true
      mode: full
    os:
      enabled: true
    java:
      enabled: true
```

Then restart your application and access again the route `/actuator/info` receive response similar to this:

```json
{
    "git": {
        "build": {
            "version": "0.0.1-SNAPSHOT",
            "time": "2023-08-19T18:54:43Z"
        },
        "commit": {
            "id": {
                "full": "32b344024fbb521c26ef918fd53ae262766205cd",
                "abbrev": "32b3440"
            }
        }
    },
    "build": {
        "artifact": "actuator",
        "name": "actuator",
        "time": "2023-08-19T18:54:40.301Z",
        "version": "0.0.1-SNAPSHOT",
        "group": "com.poc"
    },
    "java": {
        "version": "17.0.7",
        "vendor": {
            "name": "Amazon.com Inc.",
            "version": "Corretto-17.0.7.7.1"
        },
        "runtime": {
            "name": "OpenJDK Runtime Environment",
            "version": "17.0.7+7-LTS"
        },
        "jvm": {
            "name": "OpenJDK 64-Bit Server VM",
            "vendor": "Amazon.com Inc.",
            "version": "17.0.7+7-LTS"
        }
    },
    "os": {
        "name": "Windows 11",
        "version": "10.0",
        "arch": "amd64"
    }
}
```

### Custom info
To custom information just add new class extending `InfoContributor` and manager by spring, such as:
````java
package com.poc.actuator.config;

import java.util.Collections;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class MyInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("Payment", Collections.singletonMap("key", "value"));
    }

}
````
**Note:** It’s possible to add custom values when using this example “Payment” as the main object. The following parameters are properties that can be exposed.

Then restart your application and access again the route `/actuator/info` receive response similar to this:

````json
{
    "git": {
        "build": {
            "version": "0.0.1-SNAPSHOT",
            "time": "2023-08-19T18:54:43Z"
        },
        "commit": {
            "id": {
                "full": "32b344024fbb521c26ef918fd53ae262766205cd",
                "abbrev": "32b3440"
            }
        }
    },
    "build": {
        "artifact": "actuator",
        "name": "actuator",
        "time": "2023-08-19T18:54:40.301Z",
        "version": "0.0.1-SNAPSHOT",
        "group": "com.poc"
    },
    "java": {
        "version": "17.0.7",
        "vendor": {
            "name": "Amazon.com Inc.",
            "version": "Corretto-17.0.7.7.1"
        },
        "runtime": {
            "name": "OpenJDK Runtime Environment",
            "version": "17.0.7+7-LTS"
        },
        "jvm": {
            "name": "OpenJDK 64-Bit Server VM",
            "vendor": "Amazon.com Inc.",
            "version": "17.0.7+7-LTS"
        }
    },
    "os": {
        "name": "Windows 11",
        "version": "10.0",
        "arch": "amd64"
    },
    "Payment": {
        "key": "value"
    }
}
````

## Metrics

This feature allows for exporting data to monitoring solutions such as Prometheus, Datadog and Actuator Admin.

Include `metrics` in your application properties for enable:

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health, info, metrics
```

Then receive all metrics names when access endpoint `/actuator/metrics`.

Should receive similar to this:
````json
{
    "names": [
        "application.ready.time",
        "application.started.time",
        "disk.free",
        "disk.total",
        "executor.active",
        "executor.completed",
        "executor.pool.core",
        "executor.pool.max",
        "executor.pool.size",
        "executor.queue.remaining",
        "executor.queued",
        "jvm.buffer.count",
        "jvm.buffer.memory.used",
        "jvm.buffer.total.capacity",
        "jvm.classes.loaded",
        "jvm.classes.unloaded",
        "jvm.gc.live.data.size",
        "jvm.gc.max.data.size",
        "jvm.gc.memory.allocated",
        "jvm.gc.memory.promoted",
        "jvm.gc.overhead",
        "jvm.gc.pause",
        "jvm.memory.committed",
        "jvm.memory.max",
        "jvm.memory.usage.after.gc",
        "jvm.memory.used",
        "jvm.threads.daemon",
        "jvm.threads.live",
        "jvm.threads.peak",
        "jvm.threads.states",
        "logback.events",
        "mongodb.driver.commands",
        "mongodb.driver.pool.checkedout",
        "mongodb.driver.pool.size",
        "mongodb.driver.pool.waitqueuesize",
        "process.cpu.usage",
        "process.start.time",
        "process.uptime",
        "system.cpu.count",
        "system.cpu.usage",
        "tomcat.sessions.active.current",
        "tomcat.sessions.active.max",
        "tomcat.sessions.alive.max",
        "tomcat.sessions.created",
        "tomcat.sessions.expired",
        "tomcat.sessions.rejected"
    ]
}
````
To obtain more details about any metrics, you can add the metrics name to the path, such as `/actuator/metrics/mongodb.driver.pool.size`.
Then you should receive a response similar to this:
````json
{
    "name": "mongodb.driver.pool.size",
    "description": "the current size of the connection pool, including idle and and in-use members",
    "baseUnit": null,
    "measurements": [
        {
            "statistic": "VALUE",
            "value": 1.0
        }
    ],
    "availableTags": [
        {
            "tag": "server.address",
            "values": [
                "localhost:27017"
            ]
        },
        {
            "tag": "cluster.id",
            "values": [
                "64f4c7570460d54ef2df50f6"
            ]
        }
    ]
}
````
