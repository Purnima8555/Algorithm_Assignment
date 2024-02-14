package Question6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ImageDownloader extends JFrame {
    private JTextField urlField1;
    private JTextField urlField2;
    private JButton downloadButton;
    private JButton pauseButton1;
    private JButton resumeButton1;
    private JButton cancelButton1;
    private JButton pauseButton2;
    private JButton resumeButton2;
    private JButton cancelButton2;
    private JLabel statusLabel1;
    private JLabel statusLabel2;
    private JLabel progressLabel1;
    private JLabel progressLabel2;
    private ImageDownloadTask downloadTask1;
    private ImageDownloadTask downloadTask2;
    private ExecutorService executor;

    public ImageDownloader() {
        setTitle("Image Downloader");
        setSize(600, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        // Left Panel
        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel leftInputPanel = new JPanel(new GridLayout(2, 1));
        JPanel leftStatusPanel = new JPanel(new GridLayout(2, 1));

        JLabel img1Label = new JLabel("Image 1");
        urlField1 = new JTextField();
        urlField1.setPreferredSize(new Dimension(250, 20)); // Decreased height
        progressLabel1 = new JLabel();
        statusLabel1 = new JLabel();
        pauseButton1 = new JButton("Pause");
        resumeButton1 = new JButton("Resume");
        cancelButton1 = new JButton("Cancel");

        leftInputPanel.add(new JLabel("URL:"));
        leftInputPanel.add(urlField1);
        leftInputPanel.add(pauseButton1);
        leftInputPanel.add(resumeButton1);
        leftInputPanel.add(cancelButton1);
        leftStatusPanel.add(progressLabel1);
        leftStatusPanel.add(statusLabel1);

        leftPanel.add(img1Label, BorderLayout.NORTH);
        leftPanel.add(leftInputPanel, BorderLayout.CENTER);
        leftPanel.add(leftStatusPanel, BorderLayout.SOUTH);

        // Right Panel
        JPanel rightPanel = new JPanel(new BorderLayout());
        JPanel rightInputPanel = new JPanel(new GridLayout(2, 1));
        JPanel rightStatusPanel = new JPanel(new GridLayout(2, 1));

        JLabel img2Label = new JLabel("Image 2");
        urlField2 = new JTextField();
        urlField2.setPreferredSize(new Dimension(250, 20)); // Decreased height
        progressLabel2 = new JLabel();
        statusLabel2 = new JLabel();
        pauseButton2 = new JButton("Pause");
        resumeButton2 = new JButton("Resume");
        cancelButton2 = new JButton("Cancel");

        rightInputPanel.add(new JLabel("URL:"));
        rightInputPanel.add(urlField2);
        rightInputPanel.add(pauseButton2);
        rightInputPanel.add(resumeButton2);
        rightInputPanel.add(cancelButton2);
        rightStatusPanel.add(progressLabel2);
        rightStatusPanel.add(statusLabel2);

        rightPanel.add(img2Label, BorderLayout.NORTH);
        rightPanel.add(rightInputPanel, BorderLayout.CENTER);
        rightPanel.add(rightStatusPanel, BorderLayout.SOUTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        downloadButton = new JButton("Download");
        downloadButton.setPreferredSize(new Dimension(100, 30));
        buttonPanel.add(downloadButton);

        // Add panels to the main panel
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(panel);

        downloadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                downloadImages();
            }
        });

        pauseButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (downloadTask1 != null) {
                    downloadTask1.pause();
                }
            }
        });

        resumeButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (downloadTask1 != null) {
                    downloadTask1.resume();
                }
            }
        });

        cancelButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                urlField1.setText("");
                if (downloadTask1 != null) {
                    downloadTask1.cancel();
                }
            }
        });

        pauseButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (downloadTask2 != null) {
                    downloadTask2.pause();
                }
            }
        });

        resumeButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (downloadTask2 != null) {
                    downloadTask2.resume();
                }
            }
        });

        cancelButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                urlField2.setText("");
                if (downloadTask2 != null) {
                    downloadTask2.cancel();
                }
            }
        });
    }

    private void downloadImages() {
        String url1 = urlField1.getText();
        String url2 = urlField2.getText();

        executor = Executors.newFixedThreadPool(2);
        downloadTask1 = new ImageDownloadTask(url1, "image1.jpg", statusLabel1, progressLabel1);
        executor.submit(downloadTask1);
        downloadTask2 = new ImageDownloadTask(url2, "image2.jpg", statusLabel2, progressLabel2);
        executor.submit(downloadTask2);
    }

    private class ImageDownloadTask implements Runnable {
        private String url;
        private String fileName;
        private JLabel statusLabel;
        private JLabel progressLabel;
        private volatile boolean paused;
        private volatile boolean cancelled;
        private static final int TOTAL_PROGRESS = 100;
        private static final int DOWNLOAD_SPEED = 2; // Speed in percentage per second

        public ImageDownloadTask(String url, String fileName, JLabel statusLabel, JLabel progressLabel) {
            this.url = url;
            this.fileName = fileName;
            this.statusLabel = statusLabel;
            this.progressLabel = progressLabel;
        }

        public void run() {
            try {
                URL imageUrl = new URL(url);
                URLConnection connection = imageUrl.openConnection();
                int fileSize = connection.getContentLength();

                InputStream inputStream = connection.getInputStream();
                FileOutputStream outputStream = new FileOutputStream(fileName);

                byte[] buffer = new byte[1024];
                int bytesRead;
                long totalBytesRead = 0;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    if (paused) {
                        while (paused) {
                            Thread.sleep(100);
                        }
                    }
                    if (cancelled) {
                        urlField1.setText("");
                        urlField2.setText("");
                        statusLabel.setText("");
                        progressLabel.setText("");
                        break;
                    }

                    outputStream.write(buffer, 0, bytesRead);
                    totalBytesRead += bytesRead;

                    int progress = (int) ((double) totalBytesRead / fileSize * TOTAL_PROGRESS);
                    SwingUtilities.invokeLater(() -> {
                        progressLabel.setText("Downloading: " + progress + "%");
                    });

                    // Slow down the progress increment to simulate slower download
                    Thread.sleep(1000 / DOWNLOAD_SPEED);
                }

                inputStream.close();
                outputStream.close();

                // Update status label with download success message
                SwingUtilities.invokeLater(() -> {
                    statusLabel.setText(statusLabel.getText() + "\n" + fileName + " downloaded");
                });
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                // Update status label with download error message
                SwingUtilities.invokeLater(() -> {
                    statusLabel.setText("Error downloading " + fileName);
                });
            }
        }

        public void pause() {
            paused = true;
        }

        public void resume() {
            paused = false;
        }

        public void cancel() {
            cancelled = true;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ImageDownloader().setVisible(true);
            }
        });
    }
}
