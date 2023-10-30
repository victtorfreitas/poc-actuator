## Custom Endpoints

This feature allows for customizing the export of data information. In this case, we will use the custom endpoint call
to `order-processor` for export information about transition stages.

Include `order-process`in your application properties for enable:

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health, info, metrics, order-process
```

Then create new Data Transfer Object (DTO) class.

```java
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.poc.actuator.repository.entity.Status;
import lombok.Builder;
import lombok.Setter;

import java.util.Map;

@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomHealth {
    @JsonAnyGetter
    private Map<String, Status> healthDetails;
}
```

This class will be export custom information about to order.

Then create a new class to disponibilization custom endpoint.

```java
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.stereotype.Component;

@Data
@Slf4j
@AllArgsConstructor
@Component
@Endpoint(id = "order-process")
public class CustomHealthEndPoint {

}
```

It’s required annotation this class with @Component and @EndPoint.

@Component - Indicates to Spring generate and auto-detection class.

@Endpoint - Indicates to actuator custom endpoint enable.

- ID - Name the path for the new endpoint.

Next, create three methods to export information.

### Get route

Firstly we’ll create a method responsible for enabling the GET route.

```java
        @ReadOperation
        public CustomHealth ordersProcessing(){
            List<OrderEntity> orders=repository
            .findAllByDateContainingAndEffected(getToday(),false);
    
            Map<String, Status> details=orders
            .stream()
            .collect(Collectors.toMap(OrderEntity::getRecipientName,OrderEntity::getStatus));
    
            return getCustomHealth(details);
        }
```

This method find all orders not effected by today date and export to type `CustomHealth`.

The `@ReadOperation` annotation enables the GET verb.

Note: The method `getToday()` is simply declares now date in format ISO:

```java
    private static String getToday() {
        return LocalDate.now().format(DateTimeFormatter.ISO_DATE);
    }
```

When you call the route `/actuator/order-process` using the GET verb, you will receive the method mentioned above as the
return.

It’s possible to send information using path parameters by adding an annotation to the method parameter:

```java
    import static org.springframework.boot.actuate.endpoint.annotation.Selector.Match.SINGLE;

    @ReadOperation
    public List<CustomHealth> customEndPointByName(@Selector(match = SINGLE) String name) {

    }
```

The `@Selector` annotation has a parameter called **match** with two possible values: ***`SINGLE`***(Default value) and
***`ALL_REMAINING` .***

- Single - This option is responsible for receiving single path parameter.
    - Example: `/actuator/order-process/VALUE_EXAMPLE1`
- All Remaining - This option is responsible for receiving multiple values in the path parameter.
    - Example: `/actuator/order-process/VALUE_EXAMPLE1/VALUE_EXAMPLE2`
        - Note: In this case, the parameter `name` has an array type (String[] or String…).

### Post route

Secondly we’ll create a method responsible for enabling the POST route.

```java
    @WriteOperation
    public void alertFraud(@Selector String name){
        var orderEntity=repository.findByRecipientNameAndEffected(name,false)
        .orElseThrow(CustomEndPointNotFoundException::new);
        orderEntity.setFraud(true);
        repository.save(orderEntity);
    }
```

This method alerts possible order fraud. The `@WriteOperation` annotation enables the POST verb.

When you call the route `/actuator/order-process/{{name}}` using the POST verb, the mentioned process will be executed.

In this case, we’ll modify the return status code for No Content (204) because it does not return anything.

```java
    import org.springframework.http.HttpStatus;
    import org.springframework.web.bind.annotation.ResponseStatus;

    @WriteOperation
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alertFraud(@Selector String name) {
    
    }
```

Note: It’s possible to modify the return status code of all methods in this class by adding the `@ResponseStatus`
annotation and selecting the desired status code from the `HttpStatus` class.

### Delete route

Finally, we’ll create a method responsible for enabling the DELETE route.

```java
    @DeleteOperation
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAlertFraud(@Selector String name) {
        var orderEntity=repository.findByRecipientNameAndEffected(name,false)
        .orElseThrow(CustomEndPointNotFoundException::new);
        orderEntity.setFraud(false);
        repository.save(orderEntity);
    }
```

This method remove alerts the order fraud by name. The `@DeleteOperation` annotation enables the Delete verb.

When you call the route `/actuator/order-process/{{name}}` using the Delete verb, the mentioned process will be
executed.

These are custom routes, but this is not the only configuration option available, For more customization, please refer
to the official documentation.