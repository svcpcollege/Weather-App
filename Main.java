import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class Main extends JFrame {

    private JTextField cityField;
    private JButton submitButton;
    private JLabel loadingLabel;
    private List<JLabel> weatherLabels;
    private JToggleButton unitToggle;

    private boolean useMetricUnits = true; // Use metric units by default

    public Main() {
        createUI();
        setTitle("Weather App");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0, 153, 204));

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(0, 153, 204));

        cityField = new JTextField(15);
        submitButton = new JButton("Get Weather");
        loadingLabel = new JLabel("Loading...");
        loadingLabel.setForeground(Color.WHITE);
        loadingLabel.setVisible(false);

        inputPanel.add(cityField);
        inputPanel.add(submitButton);
        inputPanel.add(loadingLabel);

        // Create a toggle button for units
        unitToggle = new JToggleButton("Use Metric Units");
        unitToggle.addActionListener(this::toggleUnits);
        inputPanel.add(unitToggle);

        mainPanel.add(inputPanel, BorderLayout.NORTH);

        JPanel weatherInfoPanel = new JPanel(new GridLayout(8, 1));
        weatherLabels = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            JLabel label = new JLabel();
            weatherLabels.add(label);
            weatherInfoPanel.add(label);
        }
        mainPanel.add(weatherInfoPanel, BorderLayout.CENTER);

        submitButton.addActionListener(this::fetchData);
        cityField.addActionListener(this::fetchDataOnEnter);

        add(mainPanel);
    }

    private void toggleUnits(ActionEvent e) {
        useMetricUnits = !useMetricUnits;
        if (useMetricUnits) {
            unitToggle.setText("Use Metric Units");
        } else {
            unitToggle.setText("Use Imperial Units");
        }
    }

    private void fetchData(ActionEvent e) {
        String city = cityField.getText();

        if (!loadingLabel.isVisible()) {
            loadingLabel.setVisible(true);

            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() throws Exception {
                    try {
                        OpenWeatherMapAPI weather = new OpenWeatherMapAPI(city);
                        String[] weatherData = {
                                "Temperature: " + formatTemperature(weather.getTemperature()) + "°",
                                "Min Temperature: " + formatTemperature(weather.getMinTemperature()) + "°",
                                "Max Temperature: " + formatTemperature(weather.getMaxTemperature()) + "°",
                                "Weather Condition: " + weather.getWeatherCondition(),
                                "Humidity: " + weather.getHumidity() + "%",
                                "Wind Speed: " + formatWindSpeed(weather.getWindSpeed()) + " "
                                        + (useMetricUnits ? "m/s" : "mph"),
                                "Wind Direction: " + weather.getWindDirection() + "°",
                                "Visibility: " + formatVisibility(weather.getVisibility())
                                        + (useMetricUnits ? " m" : " miles")
                        };
                        for (int i = 0; i < 8; i++) {
                            weatherLabels.get(i).setText(weatherData[i]);
                        }
                    } catch (IOException | URISyntaxException ex) {
                        showError("An error occurred while retrieving weather data.");
                    } finally {
                        loadingLabel.setVisible(false);
                    }
                    return null;
                }
            };

            worker.execute();
        }
    }

    private void fetchDataOnEnter(ActionEvent e) {
        fetchData(e);
    }

    private String formatTemperature(double temperature) {
        return useMetricUnits ? String.format("%.1f°C", temperature)
                : String.format("%.1f°F", (temperature * 9 / 5) + 32);
    }

    private String formatWindSpeed(double windSpeed) {
        return useMetricUnits ? String.format("%.1f", windSpeed) : String.format("%.1f", windSpeed * 2.23694); // Convert
                                                                                                               // m/s to
                                                                                                               // mph
    }

    private String formatVisibility(double visibility) {
        return useMetricUnits ? String.format("%.1f", visibility) : String.format("%.1f", visibility / 1609.34); // Convert
                                                                                                                 // meters
                                                                                                                 // to
                                                                                                                 // miles
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
