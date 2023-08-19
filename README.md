## Health Check

This feature is for health checkup applications. You can access the endpoint to view the health status:

```
/actuator/health
```

You should receive a response similar to this: 

```json
{
    "status": "UP"
}
```

If any service managed by Spring fails, you should receive a response similar to this: 

```json
{
    "status": "DOWN"
}
```

**Note:**  The Health Check feature is only available for services managed by Spring components. If you want to check the health of others services, you will need to create custom health check.
