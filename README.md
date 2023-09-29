# OpenWeatherMap Java Application

This is a simple Java application that uses the OpenWeatherMap API to fetch and display weather data for a given city.

## Prerequisites

- Java 20 or later
- json.jar (Java JSON library)

## Installation

1. **Install Java**: Make sure you have Java installed on your system. If not, you can download it from the official Oracle website.

2. **Download json.jar**: This project uses the Java JSON library to parse JSON data. You can download `json.jar` from here. 

## Usage

1. **Add json.jar to your classpath**: After downloading `json.jar`, add it to your classpath. If you're using an IDE like Eclipse or IntelliJ, you can do this in the project settings. If you're compiling from the command line, you can use the `-cp` option followed by the path to `json.jar`.

2. **Compile and Run**: Compile the `Main.java` and `OpenWeatherMapAPI.java` files and run the `Main` class. You can do this in your IDE or from the command line using the `javac` and `java` commands.

3. **Enter a City Name**: Once the application is running, enter the name of a city in the text field and press Enter or click Submit. The application will display various weather parameters for that city.

Please note that city names with spaces should be entered without any special characters (for example, use "newyork" instead of "new york").

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

MIT
