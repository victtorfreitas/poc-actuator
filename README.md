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