import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

public class Main extends JFrame {

    private JTextField cityField;
    private JLabel temperatureLabel, minTempLabel, maxTempLabel, conditionLabel, humidityLabel, windSpeedLabel,
            windDirectionLabel, visibilityLabel;
    private JButton submitButton, clearButton;
    private JLabel loadingLabel;

    public Main() {
        // Create a custom JPanel for the gradient background
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Create a gradient paint for the background
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 153, 204), 0, getHeight(),
                        new Color(0, 51, 102));
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // Set the layout for the background panel
        backgroundPanel.setLayout(new GridBagLayout());
        backgroundPanel.setForeground(Color.WHITE); // Set text color to white

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding to components

        JLabel cityLabel = new JLabel("Enter city name: ");
        cityLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        cityLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        backgroundPanel.add(cityLabel, gbc);

        cityField = new JTextField();
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH; // Allow field to expand
        gbc.weightx = 1.0; // Horizontal weight to expand
        backgroundPanel.add(cityField, gbc);

        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 17));
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0; // Reset horizontal weight
        backgroundPanel.add(submitButton, gbc);

        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.PLAIN, 17));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        backgroundPanel.add(clearButton, gbc);

        loadingLabel = new JLabel("Loading...");
        loadingLabel.setFont(new Font("Arial", Font.PLAIN, 17));
        loadingLabel.setForeground(Color.WHITE);
        loadingLabel.setVisible(false);
        gbc.gridy = 3;
        backgroundPanel.add(loadingLabel, gbc);

        temperatureLabel = new JLabel();
        temperatureLabel.setFont(new Font("Arial", Font.PLAIN, 17));
        temperatureLabel.setForeground(Color.WHITE); // Set text color to white
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        backgroundPanel.add(temperatureLabel, gbc);

        minTempLabel = new JLabel();
        minTempLabel.setFont(new Font("Arial", Font.PLAIN, 17));
        minTempLabel.setForeground(Color.WHITE); // Set text color to white
        gbc.gridy = 5;
        backgroundPanel.add(minTempLabel, gbc);

        maxTempLabel = new JLabel();
        maxTempLabel.setFont(new Font("Arial", Font.PLAIN, 17));
        maxTempLabel.setForeground(Color.WHITE); // Set text color to white
        gbc.gridy = 6;
        backgroundPanel.add(maxTempLabel, gbc);

        conditionLabel = new JLabel();
        conditionLabel.setFont(new Font("Arial", Font.PLAIN, 17));
        conditionLabel.setForeground(Color.WHITE); // Set text color to white
        gbc.gridy = 7;
        backgroundPanel.add(conditionLabel, gbc);

        humidityLabel = new JLabel();
        humidityLabel.setFont(new Font("Arial", Font.PLAIN, 17));
        humidityLabel.setForeground(Color.WHITE); // Set text color to white
        gbc.gridy = 8;
        backgroundPanel.add(humidityLabel, gbc);

        windSpeedLabel = new JLabel();
        windSpeedLabel.setFont(new Font("Arial", Font.PLAIN, 17));
        windSpeedLabel.setForeground(Color.WHITE); // Set text color to white
        gbc.gridy = 9;
        backgroundPanel.add(windSpeedLabel, gbc);

        windDirectionLabel = new JLabel();
        windDirectionLabel.setFont(new Font("Arial", Font.PLAIN, 17));
        windDirectionLabel.setForeground(Color.WHITE); // Set text color to white
        gbc.gridy = 10;
        backgroundPanel.add(windDirectionLabel, gbc);

        visibilityLabel = new JLabel();
        visibilityLabel.setFont(new Font("Arial", Font.PLAIN, 17));
        visibilityLabel.setForeground(Color.WHITE); // Set text color to white
        gbc.gridy = 11;
        backgroundPanel.add(visibilityLabel, gbc);
        // Action listeners for buttons and text field
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearWeatherInfo();
            }
        });

        cityField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchData();
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchData();
            }
        });

        setContentPane(backgroundPanel);

        setTitle("OpenWeatherMap App");
        setSize(new Dimension(400, 400));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void fetchData() {
        String city = cityField.getText();

        loadingLabel.setVisible(true); // Show loading indicator

        // Use SwingWorker to perform API request in the background
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    OpenWeatherMapAPI weather = new OpenWeatherMapAPI(city);

                    // Update labels with weather data
                    temperatureLabel.setText("Temperature: " + weather.getTemperature() + "°C");
                    minTempLabel.setText("Min Temperature: " + weather.getMinTemperature() + "°C");
                    maxTempLabel.setText("Max Temperature: " + weather.getMaxTemperature() + "°C");
                    conditionLabel.setText("Weather Condition: " + weather.getWeatherCondition());
                    humidityLabel.setText("Humidity: " + weather.getHumidity() + "%");
                    windSpeedLabel.setText("Wind Speed: " + weather.getWindSpeed() + "m/s");
                    windDirectionLabel.setText("Wind Direction: " + weather.getWindDirection() + "°");
                    visibilityLabel.setText("Visibility: " + weather.getVisibility() + " meter");

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "An error occurred while retrieving weather data.");
                } catch (URISyntaxException ex) {
                    JOptionPane.showMessageDialog(null, "An error occurred while creating the URL.");
                }
                return null;
            }

            @Override
            protected void done() {
                loadingLabel.setVisible(false); // Hide loading indicator
            }
        };

        worker.execute(); // Execute the SwingWorker
    }

    private void clearWeatherInfo() {
        // Clear all labels and the input field
        cityField.setText("");
        temperatureLabel.setText("");
        minTempLabel.setText("");
        maxTempLabel.setText("");
        conditionLabel.setText("");
        humidityLabel.setText("");
        windSpeedLabel.setText("");
        windDirectionLabel.setText("");
        visibilityLabel.setText("");
    }

    public static void main(String[] args) {
        new Main();
    }
}
