# __Project setup steps:__
___

# __To ```Clone``` application you need:__
* ```git clone <username>/task2-application.git ```
* go to the folder ```cd ./'your project folder'```
* paste project url from the first step

# __To ```Run``` application you need:__

* open folder with project in the command prompt ```cd ./'your project folder'```
* enter ```docker compose up -d --build```

# __To ```Stop``` application you need:__

* enter ```docker-compose down```

---

# __How application works:__

## __Search:__

* For find all employees use: GET ```http://localhost:8080/employee/search```
* For find specific employee by his id use: GET ```http://localhost:8080/employee/search?id=<id>```

    _instead of `<id>` paste your desired `id`_
* For find by firstname, lastname, department or position use: GET ```http://localhost:8080/employee/search?<firstname/lastname/department/position>=```
    For example:
    
    * For find employee by `positionId` = `2` : GET ```http://localhost:8080/employee/search?positionId=2```

    * Find employee by `departmentId` = `1` : GET ```http://localhost:8080/employee/search?departmentId=1```
* There is an option to combine searching parameters: GET ```http://localhost:8080/employee/search?firstName=Alexey&departmentId=1```

## __Create:__

* For create new employee use: POST ``` http://localhost:8080/employee/create```

    With such JSON body:

    ```
    {
    "firstName":"Ivan",
    "lastName":"Kozlov",
    "departmentId": "1",
    "positionId": "2"
    }
  ```

## __Update:__

* For update new employee use: POST ``` http://localhost:8080/employee/update```

  With such JSON body:

    ```
    {
    "id":"4",
    "firstName":"Ivan",
    "lastName":"Kozlov",
    "departmentId": "1",
    "positionId": "3"
    }
  ```

## __Delete:__

* For delete employee use: DELETE ```http://localhost:8080/employee/delete?id=3```