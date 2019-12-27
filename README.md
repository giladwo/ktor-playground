How to run:
1. Open the project directory in IntelliJ IDEA.
2. Go to `Edit Configurations`.
3. Under `Templates` choose `Application` (You can also choose `Kotlin`).
4. Click on `Create configuration`.
5. Fill the configuration form:
    1. Main class: `com.example.ApplicationKt`
    2. Working directory: <your working directory>
    3. Use classpath of module: `example.main` (Choose from the dropdown menu)
    4. JRE: <choose your JRE>
6. Click `OK`.
7. A `TBA_AUTH_KEY` environment variable is needed:
    1. Either create an `.env` file containing: `TBA_AUTH_KEY=<your api key>`
    2. Or add an environment variable `TBA_AUTH_KEY=<your api key>` to the run configuration manually.
