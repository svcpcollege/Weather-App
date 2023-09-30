import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class Main extends JFrame {

    private JTextField cityField;
    private JLabel[] weatherLabels;
    private JButton submitButton, clearButton;
    private JLabel loadingLabel;
    private boolean isLoading = false;

    public Main() {
        createUI();
        setContentPane(createBackgroundPanel());
        setTitle("OpenWeatherMap App");
        setSize(new Dimension(400, 400));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createUI() {
        cityField = new JTextField();
        submitButton = new JButton("Submit");
        clearButton = new JButton("Clear");
        loadingLabel = new JLabel("Loading...");
        loadingLabel.setForeground(Color.WHITE);
        loadingLabel.setVisible(false);

        weatherLabels = new JLabel[8];
        for (int i = 0; i < 8; i++) {
            weatherLabels[i] = new JLabel();
            weatherLabels[i].setFont(new Font("Arial", Font.PLAIN, 17));
            weatherLabels[i].setForeground(Color.WHITE);
        }

        clearButton.addActionListener(e -> clearWeatherInfo());

        ActionListener fetchActionListener = e -> {
            if (e.getSource() == submitButton || e.getSource() == cityField) {
                if (!isLoading) {
                    isLoading = true;
                    loadingLabel.setVisible(true);
                    fetchData();
                }
            }
        };

        cityField.addActionListener(fetchActionListener);
        submitButton.addActionListener(fetchActionListener);
    }

    private JPanel createBackgroundPanel() {
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 153, 204), 0, getHeight(),
                        new Color(0, 51, 102));
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        backgroundPanel.setLayout(new GridBagLayout());
        backgroundPanel.setForeground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel cityLabel = new JLabel("Enter city name: ");
        cityLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        cityLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        backgroundPanel.add(cityLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        backgroundPanel.add(cityField, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        backgroundPanel.add(submitButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        backgroundPanel.add(clearButton, gbc);

        gbc.gridy = 3;
        backgroundPanel.add(loadingLabel, gbc);

        for (int i = 0; i < 8; i++) {
            gbc.gridy = 4 + i;
            backgroundPanel.add(weatherLabels[i], gbc);
        }

        return backgroundPanel;
    }

    private void fetchData() {
        String city = cityField.getText();

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    OpenWeatherMapAPI weather = new OpenWeatherMapAPI(city);
                    String[] weatherData = {
                            "Temperature: " + weather.getTemperature() + "°C",
                            "Min Temperature: " + weather.getMinTemperature() + "°C",
                            "Max Temperature: " + weather.getMaxTemperature() + "°C",
                            "Weather Condition: " + weather.getWeatherCondition(),
                            "Humidity: " + weather.getHumidity() + "%",
                            "Wind Speed: " + weather.getWindSpeed() + "m/s",
                            "Wind Direction: " + weather.getWindDirection() + "°",
                            "Visibility: " + weather.getVisibility() + " meter"
                    };
                    for (int i = 0; i < 8; i++) {
                        weatherLabels[i].setText(weatherData[i]);
                    }
                } catch (IOException ex) {
                    showError("An error occurred while retrieving weather data.");
                } catch (URISyntaxException ex) {
                    showError("An error occurred while creating the URL.");
                } finally {
                    isLoading = false;
                    loadingLabel.setVisible(false);
                }
                return null;
            }
        };

        worker.execute();
    }

    private void clearWeatherInfo() {
        cityField.setText("");
        for (int i = 0; i < 8; i++) {
            weatherLabels[i].setText("");
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}
