import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

public class Main extends JFrame {

    private JTextField cityField;
    private JLabel temperatureLabel, minTempLabel, maxTempLabel, conditionLabel, humidityLabel, windSpeedLabel,
            windDirectionLabel, visibilityLabel;

    public Main() {
        JLabel cityLabel = new JLabel("Enter city name: ");
        cityLabel.setHorizontalAlignment(JLabel.CENTER);
        cityLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        cityField = new JTextField();
        cityField.setHorizontalAlignment(JTextField.CENTER);

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 17));

        temperatureLabel = new JLabel();
        temperatureLabel.setFont(new Font("Arial", Font.PLAIN, 17));

        minTempLabel = new JLabel();
        minTempLabel.setFont(new Font("Arial", Font.PLAIN, 17));

        maxTempLabel = new JLabel();
        maxTempLabel.setFont(new Font("Arial", Font.PLAIN, 17));

        conditionLabel = new JLabel();
        conditionLabel.setFont(new Font("Arial", Font.PLAIN, 17));

        humidityLabel = new JLabel();
        humidityLabel.setFont(new Font("Arial", Font.PLAIN, 17));

        windSpeedLabel = new JLabel();
        windSpeedLabel.setFont(new Font("Arial", Font.PLAIN, 17));

        windDirectionLabel = new JLabel();
        windDirectionLabel.setFont(new Font("Arial", Font.PLAIN, 17));

        visibilityLabel = new JLabel();
        visibilityLabel.setFont(new Font("Arial", Font.PLAIN, 17));

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = cityField.getText();

                try {
                    OpenWeatherMapAPI weather = new OpenWeatherMapAPI(city);

                    temperatureLabel.setText("Temperature: " + weather.getTemperature() + "°C");
                    minTempLabel.setText("Min Temperature: " + weather.getMinTemperature() + "°C");
                    maxTempLabel.setText("Max Temperature: " + weather.getMaxTemperature() + "°C");
                    conditionLabel.setText("Weather Condition: " + weather.getWeatherCondition());
                    humidityLabel.setText("Humidity: " + weather.getHumidity() + "%");
                    windSpeedLabel.setText("Wind Speed: " + weather.getWindSpeed() + "m/s");
                    windDirectionLabel.setText("Wind Direction: " + weather.getWindDirection() + "°");
                    visibilityLabel.setText("Visibility: " + weather.getVisibility() + "meter");

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "An error occurred while retrieving weather data.");
                }
            }
        });

        setLayout(new GridLayout(11, 4));
        add(cityLabel);
        add(cityField);
        add(submitButton);
        add(temperatureLabel);
        add(minTempLabel);
        add(maxTempLabel);
        add(conditionLabel);
        add(humidityLabel);
        add(windSpeedLabel);
        add(windDirectionLabel);
        add(visibilityLabel);

        setTitle("OpenWeatherMap App");
        setSize(300, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
